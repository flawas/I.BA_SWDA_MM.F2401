package ch.hslu.swda.data.dto.messaging.commands.checkCustomer;

import ch.hslu.swda.data.entities.payment.PaymentState;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Serdeable
public record CheckedCustomerPaymentDto(
    @NonNull
    @NotBlank
    String orderNumber,

    @NonNull
    @NotBlank
    @Email
    String customerEmail,

    @NonNull
    PaymentState state
) { }