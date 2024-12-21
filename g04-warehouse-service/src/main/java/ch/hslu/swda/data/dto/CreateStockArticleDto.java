package ch.hslu.swda.data.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO (Data Transfer Object) representing the data required to create a stock article.
 */
public record CreateStockArticleDto(
        /**
         * The article number of the stock article.
         */
        @NotNull
        @NotBlank
        int articleNumber
) {
}
