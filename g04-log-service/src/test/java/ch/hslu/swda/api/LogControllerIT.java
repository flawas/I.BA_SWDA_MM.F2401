package ch.hslu.swda.api;


import ch.hslu.swda.api.services.LogService;
import ch.hslu.swda.api.services.LogServiceImpl;
import ch.hslu.swda.data.dto.LogDto;
import ch.hslu.swda.data.entities.Service;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.Unirest;
import org.bson.types.ObjectId;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.org.apache.commons.lang3.ObjectUtils;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MicronautTest
public final class LogControllerIT {

    private static final int PORT = 9090;
    private static final String RAW_URL = "http://%s:%s/api/log/logs";
    private static final String URL = String.format(RAW_URL, "localhost", PORT);

    @Inject
    LogService logService;

    @Test
    @Ignore
    public void testLogController_getLogs_shouldReturnCollectionOfLogs() {

        // Arrange

        when(logService.getAll())
                .then(invocation -> List.of(
                                new LogDto(
                                        UUID.randomUUID(),
                                        Instant.parse("2019-12-31T00:00:00.00Z"),
                                        ObjectId.get(),
                                        Service.LOG,
                                        "fbs12345",
                                        "Test1"
                                ),
                                new LogDto(
                                        UUID.randomUUID(),
                                        Instant.parse("2019-12-31T00:00:00.00Z"),
                                        ObjectId.get(),
                                        Service.LOG,
                                        "fbs12345",
                                        "Test2"
                                )
                        )
                );

        // Act
        final HttpResponse<LogDto[]> getResponse = Unirest.get(URL).asObject(LogDto[].class);
        final LogDto[] testee = getResponse.getBody();

        // Assert
        assertAll("Log",
                () -> assertThat(testee[0].logNumber().toString()).isNotBlank(),
                () -> assertThat(testee[0].triggeredAt().toString()).isEqualTo("2019-12-31T00:00:00.00Z"),
                () -> assertThat(testee[0].triggeredBy()).isEqualTo(ObjectId.get()),
                () -> assertThat(testee[0].service()).isEqualTo(Service.LOG),
                () -> assertThat(testee[0].orderNumber()).isEqualTo("fbs12345"),
                () -> assertThat(testee[0].message()).isNotBlank()
        );
    }

    @MockBean(LogServiceImpl.class)
    LogService logService() {
        return mock(LogService.class);
    }
}
