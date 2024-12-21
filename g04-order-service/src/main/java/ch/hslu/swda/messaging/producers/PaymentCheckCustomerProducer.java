package ch.hslu.swda.messaging.producers;

import ch.hslu.swda.data.dto.messaging.commands.checkCustomer.CheckCustomerPaymentDto;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import io.micronaut.rabbitmq.annotation.RabbitProperty;

@RabbitClient("fbs.order")
@RabbitProperty(name = "contentType", value = "application/json")
public interface PaymentCheckCustomerProducer {
    @Binding("payment.check-customer")
    void produce(final CheckCustomerPaymentDto checkCustomerPayment);
}
