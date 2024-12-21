package ch.hslu.swda.data.dto;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Serdeable
public record CreateMailDto(
        @NotBlank
        @NotNull
        @Email
        String mailAddress,

        @NotBlank
        @NotNull
        String mailHeaderTitle,

        @NotNull
        @NotBlank
        String mailText
) {
}
