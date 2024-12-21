package ch.hslu.swda.api.dto;

import ch.hslu.swda.api.entities.Branch;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;

@Serdeable
public record StockDto(
        @NotBlank String articleNumber,
        @NotBlank String stockDescription,
        @NotBlank Number stock,
        @NotBlank Number minimalStock,
        @NotBlank Number reservedStock,
        @NotBlank ArticleDto article,
        @NotBlank String branchNumber
        ) {
}
