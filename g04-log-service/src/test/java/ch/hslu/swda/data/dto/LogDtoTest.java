package ch.hslu.swda.data.dto;

import ch.hslu.swda.data.entities.Service;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;


class LogDtoTest {

    @Mock
    Instant triggedredAt;
    ObjectId triggeredBy;
    String orderNumber = "FBS24_87Z6J";

    LogDto logDto = new LogDto(UUID.randomUUID(), triggedredAt, triggeredBy, Service.INVENTORY, orderNumber, "Test");

    @Test
    void triggeredAt() {
        Assertions.assertEquals(triggedredAt, logDto.triggeredAt());
    }

    @Test
    void triggeredBy() {
        Assertions.assertEquals(triggeredBy, logDto.triggeredBy());
    }

    @Test
    void service() {
        Assertions.assertEquals(Service.INVENTORY, logDto.service());
    }

    @Test
    void order() {
        Assertions.assertEquals(orderNumber, logDto.orderNumber());
    }

    @Test
    void message() {
        Assertions.assertEquals("Test", logDto.message());
    }
}