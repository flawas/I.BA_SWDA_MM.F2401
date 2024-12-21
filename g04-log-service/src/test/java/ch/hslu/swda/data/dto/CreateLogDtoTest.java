package ch.hslu.swda.data.dto;

import ch.hslu.swda.data.entities.Service;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import java.time.Instant;
import java.util.UUID;

class CreateLogDtoTest {

    @Mock
    ObjectId triggeredBy;
    String orderNumber;
    Instant date;


    CreateLogDto createLogDto = new CreateLogDto(date,  triggeredBy, Service.ORDER, orderNumber, "Test");

    @Test
    void triggeredAt() {
        assertEquals(date, createLogDto.triggeredAt());
    }

    @Test
    void triggeredBy() {
        assertEquals(triggeredBy, createLogDto.triggeredBy());
    }

    @Test
    void service() {
        assertEquals(Service.ORDER, createLogDto.service());
    }

    @Test
    void order() {
        assertEquals(orderNumber, createLogDto.orderNumber());
    }

    @Test
    void message() {
        assertEquals("Test", createLogDto.message());
    }
}