package ch.hslu.swda.messaging.consumers;

import ch.hslu.swda.api.services.OrderService;
import ch.hslu.swda.data.dto.CreateOrderDto;
import ch.hslu.swda.stock.api.Stock;
import io.micronaut.rabbitmq.annotation.Queue;
import io.micronaut.rabbitmq.annotation.RabbitListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A RabbitMQ message consumer responsible for processing order status change messages.
 * This class listens for messages from a specified queue and handles them accordingly.
 */
@RabbitListener
public final class OrderStatusChangedConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(OrderStatusChangedConsumer.class);

    private OrderService orderService;

    public OrderStatusChangedConsumer(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Listens for messages from the "WarehouseOrderReceivedQ" queue and processes them.
     *
     * @param createOrderDto The data transfer object representing the newly created order.
     */
    @Queue("WarehouseQ")
    public void consume(final CreateOrderDto createOrderDto) {
        LOG.info("MQ-CONSUMER: Consumed new order from ({}).", createOrderDto.orderContact());
        orderService.create(createOrderDto);
    }
}
