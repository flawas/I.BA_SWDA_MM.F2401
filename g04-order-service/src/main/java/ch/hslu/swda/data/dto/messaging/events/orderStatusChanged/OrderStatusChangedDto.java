package ch.hslu.swda.data.dto.messaging.events.orderStatusChanged;

import ch.hslu.swda.data.entities.order.OrderState;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

@Serdeable
public record OrderStatusChangedDto(
    @NonNull
    @NotBlank
    String orderNumber,

    @NonNull
    @NotBlank
    @Email
    String customerEmail,

    @NonNull
    @NotBlank
    @Email
    String sellerEmail,

    @NonNull
    Instant triggeredAt,

    @NonNull
    OrderState state
) { }