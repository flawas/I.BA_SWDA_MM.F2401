package ch.hslu.swda.data.dto.messaging.commands.validateOrder;

import ch.hslu.swda.data.entities.order.OrderPositionState;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;

import java.util.Optional;

@Serdeable
public record ValidatedOrderPositionDto(
    @NonNull
    @NotBlank
    String orderNumber,

    @NonNull
    @NotBlank
    String articleNumber,

    Optional<Float> price,

    @NonNull
    OrderPositionState state
) { }