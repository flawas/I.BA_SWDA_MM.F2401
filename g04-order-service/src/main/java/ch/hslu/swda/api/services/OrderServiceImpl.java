package ch.hslu.swda.api.services;

import ch.hslu.swda.api.repositories.OrderRepository;
import ch.hslu.swda.api.repositories.UserRepository;
import ch.hslu.swda.data.dto.messaging.commands.checkCustomer.CheckCustomerPaymentDto;
import ch.hslu.swda.data.dto.messaging.commands.checkCustomer.CheckedCustomerPaymentDto;
import ch.hslu.swda.data.dto.messaging.commands.validateOrder.ValidateOrderPositionDto;
import ch.hslu.swda.data.dto.messaging.commands.validateOrder.ValidatedOrderPositionDto;
import ch.hslu.swda.data.dto.messaging.events.orderStatusChanged.OrderStatusChangedDto;
import ch.hslu.swda.data.dto.order.CreateOrderDto;
import ch.hslu.swda.data.dto.order.OrderDto;
import ch.hslu.swda.data.entities.order.Order;
import ch.hslu.swda.data.entities.order.OrderPosition;
import ch.hslu.swda.data.entities.order.OrderPositionState;
import ch.hslu.swda.data.entities.order.OrderState;
import ch.hslu.swda.data.entities.payment.PaymentState;
import ch.hslu.swda.data.entities.user.User;
import ch.hslu.swda.data.mappers.OrderMappers;
import ch.hslu.swda.messaging.producers.InventoryValidateOrderPositionProducer;
import ch.hslu.swda.messaging.producers.OrderStatusChangedProducer;
import ch.hslu.swda.messaging.producers.PaymentCheckCustomerProducer;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Singleton
@Slf4j
public final class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderStatusChangedProducer orderStatusChangedProducer;
    private final InventoryValidateOrderPositionProducer inventoryValidateOrderPositionProducer;
    private final PaymentCheckCustomerProducer paymentCheckCustomerProducer;
    private final MailService mailService;

    public OrderServiceImpl(
            final OrderRepository orderRepository,
            final UserRepository userRepository,
            final OrderStatusChangedProducer orderStatusChangedProducer,
            final InventoryValidateOrderPositionProducer inventoryValidateOrderPositionProducer,
            final PaymentCheckCustomerProducer paymentCheckCustomerProducer,
            final MailService mailService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderStatusChangedProducer = orderStatusChangedProducer;
        this.inventoryValidateOrderPositionProducer = inventoryValidateOrderPositionProducer;
        this.paymentCheckCustomerProducer = paymentCheckCustomerProducer;
        this.mailService = mailService;
    }

    public List<OrderDto> getAll() {
        return orderRepository.findAll().stream()
                .map(OrderMappers::toDto)
                .toList();
    }

    public Optional<OrderDto> findByNumber(@NonNull final String orderNumber) {
        return orderRepository.findByNumber(orderNumber).map(OrderMappers::toDto);
    }

    public Optional<OrderDto> create(@NonNull final CreateOrderDto orderDto) {

        Optional<User> customer = userRepository.findByEmail(orderDto.customerEmail());
        Optional<User> seller = userRepository.findByEmail(orderDto.sellerEmail());

        if (customer.isEmpty())
            throw new IllegalArgumentException("Order cannot be created when customer is null.");
        else if (seller.isEmpty())
            throw new IllegalArgumentException("Order cannot be created when seller is null.");

        Order order = OrderMappers.toEntity(orderDto);
        Order orderSaveResult = orderRepository.save(order);
        Optional<OrderDto> result = Optional.ofNullable(OrderMappers.toDto(orderSaveResult));

        if (result.isPresent()) {
            OrderDto orderResult = result.get();

            log.info("MQ-PRODUCER: Successfully created order '{}' -> producing order status received event.", orderResult.number());
            orderStatusChangedProducer.produceReceived(
                    new OrderStatusChangedDto(
                            orderResult.number(),
                            orderResult.customerEmail(),
                            orderResult.sellerEmail(),
                            Instant.now(),
                            OrderState.RECEIVED
                    ));

            log.info("MQ-PRODUCER: Successfully created order '{}' -> producing for each position validate event.", orderResult.number());
            orderResult.positions().forEach(orderPosition -> {
                log.info("MQ-PRODUCER: Validate order position for article {}x '{}' of order '{}' in branch '{}'.", orderPosition.amount(), orderPosition.articleNumber(), orderResult.number(), customer.get().getBranchNumber());
                inventoryValidateOrderPositionProducer.produce(
                    new ValidateOrderPositionDto(
                            orderResult.number(),
                            orderPosition.articleNumber(),
                            customer.get().getBranchNumber(),
                            orderPosition.amount()
                    ));
            });
        }

        return result;
    }

    // TODO-go: Nice spaghetti.
    @Override
    public void handleOrderPositionValidated(@NonNull final ValidatedOrderPositionDto validatedOrderPositionDto) {
        Optional<Order> order = orderRepository.findByNumber(validatedOrderPositionDto.orderNumber());

        if (order.isEmpty()) {
            log.error("MQ-CONSUMER: Couldn't resolve order with number '{}' of order position validated event.", validatedOrderPositionDto.orderNumber());
            throw new IllegalArgumentException("Couldn't resolve order when handling order position validated event.");
        }

        Order orderResult = order.get();
        Optional<OrderPosition> orderPosition = orderResult.getPositions().stream()
                .filter(x -> x.getArticleNumber().equals(validatedOrderPositionDto.articleNumber()))
                .findFirst();

        if (orderPosition.isEmpty()) {
            log.error("MQ-CONSUMER: Couldn't resolve product '{}' of order '{}' when handling order position validated event.", validatedOrderPositionDto.articleNumber(), validatedOrderPositionDto.orderNumber());
            throw new IllegalArgumentException("Couldn't resolve product when handling order position validated event.");
        }

        boolean isFirstValidatedOrderPosition = orderResult.getPositions().stream().allMatch(x -> x.getState() == OrderPositionState.UNKNOWN);

        if (isFirstValidatedOrderPosition) {
            orderResult.setState(OrderState.PARTIALLY_READY);

            log.info("MQ-PRODUCER: Order '{}' status set to partially ready as first validated event handled.", orderResult.getNumber());
            orderStatusChangedProducer.producePartiallyReady(
                    new OrderStatusChangedDto(
                            orderResult.getNumber(),
                            orderResult.getCustomerEmail(),
                            orderResult.getSellerEmail(),
                            Instant.now(),
                            OrderState.PARTIALLY_READY
                    ));
        }

        OrderPosition orderPositionResult = orderPosition.get();
        orderPositionResult.setState(validatedOrderPositionDto.state());
        orderPositionResult.setPrice(validatedOrderPositionDto.price());
        orderRepository.update(orderResult);

        boolean isLastValidatedOrderPosition = orderResult.getPositions().stream().noneMatch(x -> x.getState() == OrderPositionState.UNKNOWN);
        if (isLastValidatedOrderPosition) {
            boolean isAnyPositionFaulty = orderResult.getPositions().stream().anyMatch(x -> x.getState() == OrderPositionState.FAULTY);
            boolean isAnyPositionReordered = orderResult.getPositions().stream().anyMatch(x -> x.getState() == OrderPositionState.REORDERED);

            log.info("MQ-CONSUMER: All positions of order '{}' are validated. Faulty positions found: {}, reordered positions found: {}", validatedOrderPositionDto.orderNumber(), isAnyPositionFaulty, isAnyPositionReordered);

            if (isAnyPositionFaulty) {
                orderResult.setState(OrderState.FAULTY);
                orderRepository.update(orderResult);

                log.info("MQ-PRODUCER: Order '{}' status set to faulty.", orderResult.getNumber());
                orderStatusChangedProducer.produceFaulty(
                        new OrderStatusChangedDto(
                                orderResult.getNumber(),
                                orderResult.getCustomerEmail(),
                                orderResult.getSellerEmail(),
                                Instant.now(),
                                OrderState.FAULTY
                        ));
            } else if (!isAnyPositionReordered) {

                // TODO-go: Here we would check if customer has any unpaid orders. If so we would first put the state to needs_approval and wait for a sellers approval of the order.
                paymentCheckCustomerProducer.produce(
                        new CheckCustomerPaymentDto(
                                orderResult.getNumber(),
                                orderResult.getCustomerEmail()
                        )
                );
            }
        }
    }

    @Override
    public void handleCustomerPaymentChecked(@NonNull final CheckedCustomerPaymentDto checkedCustomerPaymentDto) {

        Optional<Order> order = orderRepository.findByNumber(checkedCustomerPaymentDto.orderNumber());
        boolean allOrdersPaidByCustomer = checkedCustomerPaymentDto.state().equals(PaymentState.COMPLETED);

        if (order.isEmpty())
            throw new IllegalStateException("The order number of checked customer payment event is invalid!");

        Order orderResult = order.get();

        if (allOrdersPaidByCustomer) {
            orderResult.setState(OrderState.READY);
            orderRepository.update(orderResult);

            log.info("MQ-PRODUCER: Order '{}' status set to ready.", orderResult.getNumber());
            orderStatusChangedProducer.produceReady(
                    new OrderStatusChangedDto(
                            orderResult.getNumber(),
                            orderResult.getCustomerEmail(),
                            orderResult.getSellerEmail(),
                            Instant.now(),
                            OrderState.READY
                    ));

            // TODO-go: Option to outsource email notification to separate notification service?
            mailService.handleSendReadyMail(
                    OrderMappers.toDto(orderResult)
            );

        } else {
            orderResult.setState(OrderState.NEEDS_APPROVAL);
            orderRepository.update(orderResult);

            log.info("MQ-PRODUCER: Order '{}' status set to needs approval.", orderResult.getNumber());
            orderStatusChangedProducer.produceReady(
                    new OrderStatusChangedDto(
                            orderResult.getNumber(),
                            orderResult.getCustomerEmail(),
                            orderResult.getSellerEmail(),
                            Instant.now(),
                            OrderState.NEEDS_APPROVAL
                    ));

            mailService.handleSendNeedsApprovalMail(
                    OrderMappers.toDto(orderResult)
            );
        }
    }
}
