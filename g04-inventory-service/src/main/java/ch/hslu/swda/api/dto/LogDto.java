package ch.hslu.swda.api.dto;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import org.bson.types.ObjectId;

import java.time.Instant;

@Serdeable
public record LogDto(
        @NonNull
        Instant triggeredAt,

        ObjectId triggeredBy,

        @NonNull
        String service,

        String orderNumber,

        @NonNull
        @NotBlank
        String message
) {
}
