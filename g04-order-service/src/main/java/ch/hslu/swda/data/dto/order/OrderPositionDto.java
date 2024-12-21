package ch.hslu.swda.data.dto.order;

import ch.hslu.swda.data.entities.order.OrderPositionState;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.Optional;

@Serdeable
public record OrderPositionDto(

    @Min(value = 1, message = "Number of pieces cannot be lower than minimum of 1")
    @Max(value = 50, message = "Number of pieces cannot be higher than maximum of 50")
    int amount,

    @NonNull
    @NotBlank
    String articleNumber,

    Optional<Float> price,

    @NonNull
    OrderPositionState state
) { }
