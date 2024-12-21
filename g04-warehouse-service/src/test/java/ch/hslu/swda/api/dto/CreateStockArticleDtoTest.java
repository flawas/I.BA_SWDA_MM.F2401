package ch.hslu.swda.api.dto;

import ch.hslu.swda.data.dto.CreateStockArticleDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateStockArticleDtoTest {

    @Test
    void articleNumber() {
        CreateStockArticleDto stockDto = new CreateStockArticleDto(111111);
        assertEquals(111111, stockDto.articleNumber());
    }
}