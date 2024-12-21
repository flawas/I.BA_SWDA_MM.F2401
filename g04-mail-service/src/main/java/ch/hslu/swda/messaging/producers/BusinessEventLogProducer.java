package ch.hslu.swda.messaging.producers;

import ch.hslu.swda.data.dto.CreateLogDto;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import io.micronaut.rabbitmq.annotation.RabbitProperty;

/**
 * A RabbitMQ message producer interface responsible for sending business event log messages.
 * This interface defines methods for producing messages to the RabbitMQ exchange "warehouse.order"
 * with specific bindings and message properties.
 */
@RabbitClient("fbs.log")
public interface BusinessEventLogProducer {

    @Binding("log.warehouse")
    @RabbitProperty(name = "contentType", value = "application/json")
    void produce(final CreateLogDto createLogDto);
}
