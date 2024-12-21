package ch.hslu.swda.data.dto;

import ch.hslu.swda.data.entities.Service;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class CreateLogDtoTest {

    CreateLogDto createLogDto = new CreateLogDto(
            Instant.ofEpochSecond(10),
            null,
            Service.WAREHOUSE,
            String.valueOf("Order123456"),
            "Teststring"
            );

    @Test
    void triggeredAt() {
        assertEquals(Instant.ofEpochSecond(10), createLogDto.triggeredAt());
    }

    @Test
    void triggeredBy() {
        assertEquals(null, createLogDto.triggeredBy());
    }

    @Test
    void service() {
        assertEquals(Service.WAREHOUSE, createLogDto.service());
    }

    @Test
    void orderNumber() {
        assertEquals("Order123456", createLogDto.orderNumber());
    }

    @Test
    void message() {
        assertEquals("Teststring", createLogDto.message());
    }
}