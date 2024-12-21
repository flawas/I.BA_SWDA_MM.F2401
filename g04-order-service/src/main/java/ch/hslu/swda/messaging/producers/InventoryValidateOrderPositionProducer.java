package ch.hslu.swda.messaging.producers;

import ch.hslu.swda.data.dto.messaging.commands.validateOrder.ValidateOrderPositionDto;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import io.micronaut.rabbitmq.annotation.RabbitProperty;

@RabbitClient("fbs.order")
@RabbitProperty(name = "contentType", value = "application/json")
public interface InventoryValidateOrderPositionProducer {
    @Binding("inventory.validate-order-position")
    void produce(final ValidateOrderPositionDto validateOrderPosition);
}
