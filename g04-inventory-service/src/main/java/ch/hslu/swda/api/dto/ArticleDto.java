package ch.hslu.swda.api.dto;

import com.mongodb.lang.Nullable;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Serdeable
public record ArticleDto(
        @NotNull
        @NotBlank
        int articleNumber,

        @Nullable
        int articleCount,

        @Nullable
        LocalDate articleDeliveryDate,

        @Nullable
        double articlePrice
) {}
