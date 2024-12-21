package ch.hslu.swda.data.dto.order;

import ch.hslu.swda.data.entities.order.OrderState;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.time.Instant;
import java.util.List;

@Serdeable
public record OrderDto(
    @NonNull
    @NotBlank
    String number,

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
    List<OrderPositionDto> positions,

    @NonNull
    Instant orderDate,

    @NonNull
    OrderState state
) { }

// TODO-go: Improve simple hateoas/hypermedia attempt -> Micronaut.hateos.Link annotation?