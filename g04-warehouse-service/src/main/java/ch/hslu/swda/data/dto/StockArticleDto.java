package ch.hslu.swda.data.dto;

import com.mongodb.lang.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) representing a stock article.
 */
@Serdeable
public record StockArticleDto(
        /**
         * The article number of the stock article.
         */
        @NotNull
        @NotBlank
        int articleNumber,

        /**
         * The count of the stock article.
         */
        @Nullable
        int articleCount,

        /**
         * The delivery date of the stock article.
         */
        @Nullable
        LocalDate articleDeliveryDate,

        @Nullable
        double articlePrice
) { }
