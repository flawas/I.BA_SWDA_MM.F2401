package ch.hslu.swda.data.mappers;

import ch.hslu.swda.data.dto.CreateLogDto;
import ch.hslu.swda.data.dto.LogDto;
import ch.hslu.swda.data.entities.Log;
import ch.hslu.swda.data.entities.Service;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LogMappersTest {

    @Mock
    ObjectId triggeredBy;


    Instant triggeredAt = Instant.parse("2024-06-02T00:00:00Z");
    LogDto logDto = new LogDto(UUID.randomUUID(),triggeredAt, triggeredBy, Service.INVENTORY, "FBS24_87Z6J", "Test");
    CreateLogDto createLogDto = new CreateLogDto(triggeredAt, triggeredBy, Service.INVENTORY, "FBS24_87Z6J", "Test");
    Log log = new Log(triggeredAt, triggeredBy, Service.INVENTORY, "FBS24_87Z6J", "Test");

    @Test
    void toEntityService() {
        assertEquals(log.getService(), LogMappers.toEntity(createLogDto).getService());
    }

    @Test
    void toEntityTriggeredAt() {
        assertEquals(log.getTriggeredAt(), LogMappers.toEntity(createLogDto).getTriggeredAt());
    }

    @Test
    void toEntityMessage() {
        assertEquals(log.getMessage(), LogMappers.toEntity(createLogDto).getMessage());
    }

    @Test
    void toDtoService() {
        assertEquals(logDto.service(), LogMappers.toDto(log).service());
    }

    @Test
    void toDtoTriggeredAt() {
        assertEquals(logDto.triggeredAt(), LogMappers.toDto(log).triggeredAt());
    }
    @Test
    void toDtoMessage() {
        assertEquals(logDto.message(), LogMappers.toDto(log).message());
    }
}