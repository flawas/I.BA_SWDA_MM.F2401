package ch.hslu.swda.messaging.consumers;

import ch.hslu.swda.api.services.PaymentService;
import ch.hslu.swda.data.dto.messaging.events.orderStatusChanged.OrderStatusChangedDto;
import io.micronaut.rabbitmq.annotation.Queue;
import io.micronaut.rabbitmq.annotation.RabbitListener;
import lombok.extern.slf4j.Slf4j;

@RabbitListener
@Slf4j
public final class GeneratePaymentConsumer {

    private final PaymentService paymentService;

    public GeneratePaymentConsumer(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Queue("GeneratePaymentQ")
    public void consume(final OrderStatusChangedDto orderStatusChanged) {
        log.info("MQ-CONSUMER: Payment consumed order status 'ready' for '{}' therefore generating payment for customer '{}'.",
                orderStatusChanged.orderNumber(),
                orderStatusChanged.customerEmail());

        paymentService.handleGeneratePayment(orderStatusChanged.orderNumber(), orderStatusChanged.customerEmail());
    }
}
