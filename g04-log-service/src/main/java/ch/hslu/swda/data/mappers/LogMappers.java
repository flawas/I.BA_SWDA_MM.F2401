package ch.hslu.swda.data.mappers;

import ch.hslu.swda.data.dto.CreateLogDto;
import ch.hslu.swda.data.dto.LogDto;
import ch.hslu.swda.data.entities.Log;

import java.util.UUID;

public final class LogMappers {

    public static Log toEntity(final CreateLogDto logDto) {
        if (logDto == null)
            return null;

        return new Log(
                logDto.triggeredAt(),
                logDto.triggeredBy(),
                logDto.service(),
                logDto.orderNumber(),
                logDto.message()
        );
    }

    public static Log toEntity(final LogDto logDto) {
        if (logDto == null)
            return null;

        return new Log(
                logDto.triggeredAt(),
                logDto.triggeredBy(),
                logDto.service(),
                logDto.orderNumber(),
                logDto.message()
        );
    }

    public static LogDto toDto(final Log log) {
        if (log == null)
            return null;

        return new LogDto(
                log.getLogNumber(),
                log.getTriggeredAt(),
                log.getTriggeredBy(),
                log.getService(),
                log.getOrderNumber(),
                log.getMessage()
        );
    }
}