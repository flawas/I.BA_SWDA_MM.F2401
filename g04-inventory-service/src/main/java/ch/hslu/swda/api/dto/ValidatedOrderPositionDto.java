package ch.hslu.swda.api.dto;

import ch.hslu.swda.api.entities.OrderPositionState;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

@Serdeable
public record ValidatedOrderPositionDto(
        @NotBlank String orderNumber,
        @NotBlank String articleNumber,
        @NotNull Optional<Float> price,
        @NotBlank OrderPositionState orderPositionState
) { }
