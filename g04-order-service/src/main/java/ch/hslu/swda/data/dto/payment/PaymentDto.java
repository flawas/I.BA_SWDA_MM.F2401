package ch.hslu.swda.data.dto.payment;

import ch.hslu.swda.data.entities.payment.PaymentState;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

@Serdeable
public record PaymentDto(
    @NonNull
    @NotBlank
    String number,

    @NonNull
    @NotBlank
    String orderNumber,

    @NonNull
    @NotBlank
    @Email
    String customerEmail,

    @NonNull
    Instant invoiceDate,

    @NonNull
    PaymentState state
) { }
