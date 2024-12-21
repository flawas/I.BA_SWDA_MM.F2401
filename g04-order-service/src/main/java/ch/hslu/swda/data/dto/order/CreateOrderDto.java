package ch.hslu.swda.data.dto.order;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Serdeable
public record CreateOrderDto(
    @NonNull
    @NotBlank
    @Email
    String customerEmail,

    @NonNull
    @NotBlank
    @Email
    String sellerEmail,

    @NonNull
    @NotBlank
    AddressDto shippingAddress,

    @NonNull
    @NotBlank
    AddressDto billingAddress,

    @NonNull
    @NotEmpty
    List<CreateOrderPositionDto> positions
) { }
