package ch.hslu.swda.messaging.producers;

import ch.hslu.swda.data.dto.messaging.events.orderStatusChanged.OrderStatusChangedDto;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import io.micronaut.rabbitmq.annotation.RabbitProperty;

@RabbitClient("fbs.order")
@RabbitProperty(name = "contentType", value = "application/json")
public interface OrderStatusChangedProducer {
    @Binding("order.status-changed.received")
    void produceReceived(final OrderStatusChangedDto orderStatusChanged);

    @Binding("order.status-changed.partially-ready")
    void producePartiallyReady(final OrderStatusChangedDto orderStatusChanged);

    @Binding("order.status-changed.faulty")
    void produceFaulty(final OrderStatusChangedDto orderStatusChanged);

    @Binding("order.status-changed.ready")
    void produceReady(final OrderStatusChangedDto orderStatusChanged);
}
