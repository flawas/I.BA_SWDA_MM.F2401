package ch.hslu.swda.api.entities;

import ch.hslu.swda.api.dto.ArticleDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StockTest {

    Stock stock;
    ArticleDto article = new ArticleDto(123456,1000, LocalDate.now(),12.95);

    @BeforeEach
    void setUp() {
        stock = new Stock("123456","test",100,50,0,article,"rk");
    }

    @Test
    void createStock() {
        assertNotNull(stock);
        assertEquals(0,stock.getReservedStock());
    }

    @Test
    void shipArticleReady() {
        OrderPositionState state = stock.shipArticle(10);

        assertEquals(90, stock.getStock());
        assertEquals(OrderPositionState.READY, state);
    }

    @Test
    void shipArticleReorder() {
        OrderPositionState state = stock.shipArticle(110);

        assertEquals(100, stock.getStock());
        assertEquals(110, stock.getReservedStock());
        assertEquals(OrderPositionState.REORDERED, state);
    }
}