package ch.hslu.swda.api.mappers;

import ch.hslu.swda.api.dto.LogDto;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class LogMapperTest {

    String service = "INVENTORY";
    String orderNumber = "123456";
    String message = "order by user";

    @Test
    void toDto() {
        LogDto dto = LogMapper.toDto(orderNumber, message);

        assertNotNull(dto.triggeredAt());
        assertNotNull(dto.triggeredBy());
        assertEquals(service, dto.service());
        assertEquals(orderNumber, dto.orderNumber());
        assertEquals(message, dto.message());
    }
}