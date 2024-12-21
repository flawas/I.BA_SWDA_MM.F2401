package ch.hslu.swda.data.mappers;

import ch.hslu.swda.data.dto.CreateStockArticleDto;
import ch.hslu.swda.data.dto.StockArticleDto;
import ch.hslu.swda.data.entities.StockArticle;

/**
 * Mapper class for converting between StockArticle and CreateStockArticleDto/StockArticleDto.
 */
public class StockMappers {

    /**
     * Converts a CreateStockArticleDto object to a StockArticle entity.
     *
     * @param stockDto The CreateStockArticleDto object to convert.
     * @return The corresponding StockArticle entity.
     */
    public static StockArticle toEntity(final CreateStockArticleDto stockDto) {
        if (stockDto == null)
            return null;

        return new StockArticle(
                stockDto.articleNumber()
        );
    }

    /**
     * Converts a StockArticle entity to a StockArticleDto object.
     *
     * @param stockArticle The StockArticle entity to convert.
     * @return The corresponding StockArticleDto object.
     */
    public static StockArticleDto toDto(final StockArticle stockArticle) {
        if (stockArticle == null)
            return null;

        return new StockArticleDto(
                stockArticle.getArticleNumber(),
                stockArticle.getArticleStock(),
                stockArticle.getArticleDeliveryDate(),
                stockArticle.getArticlePrice()
        );
    }

}
