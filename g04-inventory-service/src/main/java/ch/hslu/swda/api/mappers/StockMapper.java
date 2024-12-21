package ch.hslu.swda.api.mappers;

import ch.hslu.swda.api.dto.StockDto;
import ch.hslu.swda.api.entities.Stock;

public final class StockMapper {

    public static Stock toEntity(final StockDto dto) {
        if (dto == null) {
            return null;
        }

        return new Stock(
                dto.articleNumber(),
                dto.stockDescription(),
                dto.stock(),
                dto.minimalStock(),
                dto.reservedStock(),
                dto.article(),
                dto.branchNumber()
        );
    }

    public static StockDto toDto(final Stock stock) {
        if (stock == null) {
            return null;
        }

        return new StockDto(
                stock.getArticleNumber(),
                stock.getStockDescription(),
                stock.getStock(),
                stock.getMinimalStock(),
                stock.getReservedStock(),
                stock.getArticle(),
                stock.getBranchNumber()
        );
    }
}
