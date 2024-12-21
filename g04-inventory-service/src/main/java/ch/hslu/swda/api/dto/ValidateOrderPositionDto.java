package ch.hslu.swda.api.dto;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotNull;

@Serdeable
public record ValidateOrderPositionDto(
        @NotNull String orderNumber,
        @NotNull String articleNumber,
        @NotNull String branchNumber,
        @NotNull Number amount
) {
}
