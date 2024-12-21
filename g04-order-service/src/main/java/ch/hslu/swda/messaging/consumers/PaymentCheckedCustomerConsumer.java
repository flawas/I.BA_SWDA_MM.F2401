package ch.hslu.swda.messaging.consumers;

import ch.hslu.swda.api.services.OrderService;
import ch.hslu.swda.data.dto.messaging.commands.checkCustomer.CheckedCustomerPaymentDto;
import ch.hslu.swda.data.dto.messaging.commands.validateOrder.ValidatedOrderPositionDto;
import ch.hslu.swda.data.entities.order.OrderPositionState;
import ch.hslu.swda.data.entities.payment.PaymentState;
import io.micronaut.rabbitmq.annotation.Queue;
import io.micronaut.rabbitmq.annotation.RabbitListener;
import lombok.extern.slf4j.Slf4j;

// TODO-go: Extract and isolate all payment related stuff to separate ms/repo.
@RabbitListener
@Slf4j
public final class PaymentCheckedCustomerConsumer {

    private final OrderService orderService;

    public PaymentCheckedCustomerConsumer(final OrderService orderService) {
        this.orderService = orderService;
    }

    // TODO-go: Fix enum deserialization.
    /*@Queue("CheckedCustomerQ")
    public void consume(final CheckedCustomerPaymentDto checkedCustomerPayment) {
        log.info("MQ-CONSUMER: Consumed payment checked customer event (for customer '{}' with state '{}').",
                checkedCustomerPayment.customerEmail(),
                checkedCustomerPayment.state());
    }*/

    @Queue("CheckedCustomerPendingQ")
    public void consumePending(final CheckedCustomerPaymentDto checkedCustomerPayment) {
        log.info("MQ-CONSUMER: Consumed payment checked customer event (for customer '{}' with state 'pending').",
                checkedCustomerPayment.customerEmail());

        orderService.handleCustomerPaymentChecked(new CheckedCustomerPaymentDto(
                checkedCustomerPayment.orderNumber(),
                checkedCustomerPayment.customerEmail(),
                PaymentState.PENDING
        ));
    }

    @Queue("CheckedCustomerCompletedQ")
    public void consumeCompleted(final CheckedCustomerPaymentDto checkedCustomerPayment) {
        log.info("MQ-CONSUMER: Consumed payment checked customer event (for customer '{}' with state 'completed').",
                checkedCustomerPayment.customerEmail());

        orderService.handleCustomerPaymentChecked(new CheckedCustomerPaymentDto(
                checkedCustomerPayment.orderNumber(),
                checkedCustomerPayment.customerEmail(),
                PaymentState.COMPLETED
        ));
    }
}
