package ch.hslu.swda.api.mappers;

import ch.hslu.swda.api.dto.ArticleDto;
import ch.hslu.swda.api.dto.StockDto;
import ch.hslu.swda.api.entities.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StockMapperTest {

    ArticleDto article = new ArticleDto(123456,1000, LocalDate.now(),12.95);

    @Test
    void toEntity() {
        StockDto stockDto = new StockDto("123456","test",100,50,0,article,"rk");
        var entity = StockMapper.toEntity(stockDto);

        assertEquals(stockDto.articleNumber(), entity.getArticleNumber());
        assertEquals(stockDto.stockDescription(), entity.getStockDescription());
        assertEquals(stockDto.stock(), entity.getStock());
        assertEquals(stockDto.minimalStock(), entity.getMinimalStock());
        assertEquals(stockDto.reservedStock(), entity.getReservedStock());
        assertEquals(stockDto.article(), entity.getArticle());
        assertEquals(stockDto.branchNumber(), entity.getBranchNumber());
    }

    @Test
    void toDto() {
        Stock stock = new Stock("123456","test",100,50,0,article,"rk");
        var dto = StockMapper.toDto(stock);

        assertEquals(stock.getArticleNumber(), dto.articleNumber());
        assertEquals(stock.getStockDescription(), dto.stockDescription());
        assertEquals(stock.getStock(),dto.stock());
        assertEquals(stock.getMinimalStock(), dto.minimalStock());
        assertEquals(stock.getReservedStock(), dto.reservedStock());
        assertEquals(stock.getArticle(), dto.article());
        assertEquals(stock.getBranchNumber(), dto.branchNumber());
    }

    @Test
    void ifNull() {
        assertNull(StockMapper.toEntity(null));
        assertNull(StockMapper.toDto(null));
    }
}