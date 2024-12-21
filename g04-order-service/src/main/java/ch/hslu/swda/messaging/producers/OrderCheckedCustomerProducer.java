package ch.hslu.swda.messaging.producers;

import ch.hslu.swda.data.dto.messaging.commands.checkCustomer.CheckedCustomerPaymentDto;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import io.micronaut.rabbitmq.annotation.RabbitProperty;

// TODO-go: Remove this temporary producer. Extract and isolate all payment related stuff to separate ms/repo.
@RabbitClient("fbs.payment")
@RabbitProperty(name = "contentType", value = "application/json")
public interface OrderCheckedCustomerProducer {
    @Binding("order.checked-customer.pending")
    void producePending(final CheckedCustomerPaymentDto checkedCustomerPayment);

    @Binding("order.checked-customer.completed")
    void produceCompleted(final CheckedCustomerPaymentDto checkedCustomerPayment);
}
