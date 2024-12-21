package ch.hslu.swda.messaging.consumers;

import ch.hslu.swda.api.repositories.LogRepository;
import ch.hslu.swda.data.dto.LogDto;
import ch.hslu.swda.data.dto.OrderStatusChangedDto;
import ch.hslu.swda.data.entities.Log;
import ch.hslu.swda.data.mappers.OrderStatusChangedMapper;
import io.micronaut.rabbitmq.annotation.Queue;
import io.micronaut.rabbitmq.annotation.RabbitListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RabbitListener
public final class OrderStatusChangedConsumer {
    private final LogRepository logRepository;

    private static final Logger LOG = LoggerFactory.getLogger(OrderStatusChangedConsumer.class);
    public OrderStatusChangedConsumer(LogRepository logRepository) {
        this.logRepository = logRepository;
    }
    @Queue("OrderStatusChangedQ")
    public void consume(final OrderStatusChangedDto orderStatusChanged) {
        LOG.info("Received LogStatusChangedDto for Order: ({}).", orderStatusChanged.orderNumber());
        Log log = OrderStatusChangedMapper.toEntity(orderStatusChanged);
        Log logSaveResult = logRepository.save(log);
        LOG.info("LogSaveResult. {}", logSaveResult);
    }
}