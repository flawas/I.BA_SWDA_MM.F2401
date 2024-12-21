package ch.hslu.swda.data.dto;

import ch.hslu.swda.data.entities.Service;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import org.bson.types.ObjectId;

import java.time.Instant;
import java.util.UUID;

@Serdeable
public record LogDto(
        @NonNull
        UUID logNumber,

        @NonNull
        Instant triggeredAt,

        ObjectId triggeredBy,

        @NonNull
        Service service,

        String orderNumber,

        @NonNull
        @NotBlank
        String message
) { }


