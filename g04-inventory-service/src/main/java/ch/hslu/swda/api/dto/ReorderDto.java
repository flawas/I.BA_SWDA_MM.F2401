package ch.hslu.swda.api.dto;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Serdeable
public record ReorderDto(
        @NotNull
        @NotBlank
        String articleNumber,

        @NotNull
        @NotBlank
        int articleCount,

        @NotNull
        @NotBlank
        String orderContact
) {
}
