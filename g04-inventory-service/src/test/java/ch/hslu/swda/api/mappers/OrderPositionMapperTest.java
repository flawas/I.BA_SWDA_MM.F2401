package ch.hslu.swda.api.mappers;

import ch.hslu.swda.api.dto.ValidateOrderPositionDto;
import ch.hslu.swda.api.dto.ValidatedOrderPositionDto;
import ch.hslu.swda.api.entities.OrderPositionState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OrderPositionMapperTest {

    ValidateOrderPositionDto orderPositionDto;
    OrderPositionState state = OrderPositionState.READY;
    Float price = 12.95f;

    @BeforeEach
    void setUp() {
        orderPositionDto = new ValidateOrderPositionDto("123456", "456789","rk",10);
    }

    @Test
    void toValidatedDto() {
        ValidatedOrderPositionDto dto = OrderPositionMapper.toValidatedDto(orderPositionDto, state, Optional.of(price));

        assertEquals(orderPositionDto.orderNumber(), dto.orderNumber());
        assertEquals(orderPositionDto.articleNumber(), dto.articleNumber());
        assertEquals(state, dto.orderPositionState());
        assertTrue(dto.price().isPresent());
        assertEquals(price, dto.price().get());
    }

    @Test
    void toValidatedDto1() {
        ValidatedOrderPositionDto dto = OrderPositionMapper.toValidatedDto(orderPositionDto, state);

        assertEquals(orderPositionDto.orderNumber(), dto.orderNumber());
        assertEquals(orderPositionDto.articleNumber(), dto.articleNumber());
        assertEquals(state, dto.orderPositionState());
    }

    @Test
    void toValidatedDto2() {
        ValidatedOrderPositionDto dto = OrderPositionMapper.toValidatedDto(orderPositionDto, state, price);

        assertEquals(orderPositionDto.orderNumber(), dto.orderNumber());
        assertEquals(orderPositionDto.articleNumber(), dto.articleNumber());
        assertEquals(state, dto.orderPositionState());
        assertTrue(dto.price().isPresent());
        assertEquals(price, dto.price().get());
    }
}