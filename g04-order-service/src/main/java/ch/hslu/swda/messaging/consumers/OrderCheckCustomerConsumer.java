package ch.hslu.swda.messaging.consumers;

import ch.hslu.swda.api.services.PaymentService;
import ch.hslu.swda.data.dto.messaging.commands.checkCustomer.CheckCustomerPaymentDto;
import ch.hslu.swda.data.dto.messaging.commands.checkCustomer.CheckedCustomerPaymentDto;
import ch.hslu.swda.data.dto.payment.PaymentDto;
import ch.hslu.swda.data.entities.payment.PaymentState;
import ch.hslu.swda.messaging.producers.OrderCheckedCustomerProducer;
import io.micronaut.rabbitmq.annotation.Queue;
import io.micronaut.rabbitmq.annotation.RabbitListener;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

// TODO-go: Remove this temporary consumer. Extract and isolate all payment related stuff to separate ms/repo.
@RabbitListener
@Slf4j
public final class OrderCheckCustomerConsumer {

    private final PaymentService paymentService;

    private final OrderCheckedCustomerProducer orderCheckedCustomerProducer;

    public OrderCheckCustomerConsumer(
            final PaymentService paymentService,
            final OrderCheckedCustomerProducer orderCheckedCustomerProducer
            ) {
        this.paymentService = paymentService;
        this.orderCheckedCustomerProducer = orderCheckedCustomerProducer;
    }

    @Queue("CheckCustomerQ")
    public void consume(final CheckCustomerPaymentDto checkCustomerPaymentDto) {
        log.info("MQ-CONSUMER: Payment consumed check customer with mail '{}'", checkCustomerPaymentDto.customerEmail());

        List<PaymentDto> payments = paymentService.findByCustomerEmail(checkCustomerPaymentDto.customerEmail());

        long unpaidPayments = payments.stream()
                .filter(x -> x.state().equals(PaymentState.PENDING))
                .count();

        if (unpaidPayments == 0) {
            log.info("MQ-CONSUMER: Customer with mail '{}' has no unpaid payments.", checkCustomerPaymentDto.customerEmail());
            orderCheckedCustomerProducer.produceCompleted(new CheckedCustomerPaymentDto(
                    checkCustomerPaymentDto.orderNumber(),
                    checkCustomerPaymentDto.customerEmail(),
                    PaymentState.COMPLETED
            ));
        } else {
            log.info("MQ-CONSUMER: Customer with mail '{}' has {} unpaid payments!", checkCustomerPaymentDto.customerEmail(), unpaidPayments);
            orderCheckedCustomerProducer.producePending(new CheckedCustomerPaymentDto(
                    checkCustomerPaymentDto.orderNumber(),
                    checkCustomerPaymentDto.customerEmail(),
                    PaymentState.PENDING
            ));
        }
    }
}
