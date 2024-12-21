package ch.hslu.swda.api.dto;

import ch.hslu.swda.data.dto.StockArticleDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StockArticleDtoTest {

    @Test
    void articleNumber() {
        StockArticleDto stockArticleDto = new StockArticleDto(111111, 20, LocalDate.now(),20.45);
        assertEquals(111111, stockArticleDto.articleNumber());
    }

    @Test
    void articleCount() {
        StockArticleDto stockArticleDto = new StockArticleDto(111111, 20, LocalDate.now(),20.45);
        assertEquals(20, stockArticleDto.articleCount());
    }

    @Test
    void articleDeliveryDate() {
        LocalDate deliveryDate = LocalDate.now();
        StockArticleDto stockArticleDto = new StockArticleDto(111111, 20, deliveryDate,20.45);
        assertEquals(deliveryDate, stockArticleDto.articleDeliveryDate());
    }

    @Test
    void articlePrice() {
        StockArticleDto stockArticleDto = new StockArticleDto(111111, 20, LocalDate.now(), 20.45);
        assertEquals(20.45, stockArticleDto.articlePrice());
    }
}