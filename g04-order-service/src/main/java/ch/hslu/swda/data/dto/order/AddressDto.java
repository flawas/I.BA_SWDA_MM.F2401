package ch.hslu.swda.data.dto.order;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;

import java.util.Optional;

@Serdeable
public record AddressDto(
        @NonNull
        @NotBlank
        String firstname,

        @NonNull
        @NotBlank
        String lastname,

        @NonNull
        @NotBlank
        String address1,

        String address2,

        String address3,

        @NonNull
        @NotBlank
        String city,

        @NonNull
        @NotBlank
        String zipCode
) { }