package ch.hslu.swda.api.mappers;

import ch.hslu.swda.api.dto.LogDto;
import org.bson.types.ObjectId;

import java.time.Instant;

public class LogMapper {
    public static LogDto toDto(
            final String orderNumber,
            final String message
    ) {
        return new LogDto(
                Instant.now(),
                ObjectId.get(),
                "INVENTORY",
                orderNumber,
                message
        );
    }
}
