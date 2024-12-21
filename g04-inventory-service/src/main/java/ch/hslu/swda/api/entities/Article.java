package ch.hslu.swda.api.entities;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Deprecated
@Serdeable
public class Article {
    @NotNull
    @NotBlank
    private int articleNumber;

    @Nullable
    private int articleCount;

    @Nullable
    private LocalDate articleDeliveryDate;

    @Nullable
    double articlePrice;
}
