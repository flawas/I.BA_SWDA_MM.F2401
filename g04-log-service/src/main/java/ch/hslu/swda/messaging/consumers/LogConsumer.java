package ch.hslu.swda.messaging.consumers;

import ch.hslu.swda.api.repositories.LogRepository;
import ch.hslu.swda.api.services.LogService;
import ch.hslu.swda.data.dto.LogDto;
import ch.hslu.swda.data.entities.Log;
import ch.hslu.swda.data.mappers.LogMappers;
import ch.hslu.swda.data.mappers.OrderStatusChangedMapper;
import io.micronaut.rabbitmq.annotation.Queue;
import io.micronaut.rabbitmq.annotation.RabbitListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RabbitListener
public final class LogConsumer {
    private final LogRepository logRepository;


    private static final Logger LOG = LoggerFactory.getLogger(LogConsumer.class);

    public LogConsumer(final LogService logService, LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Queue("LogReceivedQ")
    public void consume(final LogDto logDto) {

        LOG.info("Received log-message ({}).", logDto.message());
        Log log = LogMappers.toEntity(logDto);
        LOG.info(log.getMessage());
        Log logSaveResult = logRepository.save(log);
    }
}