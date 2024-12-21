package ch.hslu.swda.data.dto.messaging.commands.validateOrder;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Serdeable
public record ValidateOrderPositionDto(
    @NonNull
    @NotBlank
    String orderNumber,

    @NonNull
    @NotBlank
    String articleNumber,

    @NonNull
    @NotBlank
    String branchNumber,

    @Min(value = 1, message = "Number of pieces cannot be lower than minimum of 1")
    @Max(value = 50, message = "Number of pieces cannot be higher than maximum of 50")
    int amount
) { }