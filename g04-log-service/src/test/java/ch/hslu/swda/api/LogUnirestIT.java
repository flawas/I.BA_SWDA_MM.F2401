package ch.hslu.swda.api;


import ch.hslu.swda.data.dto.CreateLogDto;
import ch.hslu.swda.data.dto.LogDto;
import ch.hslu.swda.data.entities.Service;
import kong.unirest.core.GenericType;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.Unirest;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Testcontainers
public final class LogUnirestIT {

    private static final int PORT = 9090;
    private static final String DOCKER_IMAGE_TAG = "swda-24fs/g04-log-service:latest";
    private static final String RAW_URL = "http://%s:%s/api/log/logs";
    private static String url = String.format(RAW_URL, "localhost", PORT);

    @Container
    GenericContainer<?> container = new GenericContainer<>(DockerImageName.parse(DOCKER_IMAGE_TAG))
            .withStartupTimeout(Duration.ofSeconds(60))
            .withEnv("MICRONAUT_SERVER_PORT", String.valueOf(PORT))
            .withExposedPorts(PORT);

    @BeforeAll
    static void beforeAll() {
        Unirest.config().retryAfter(true, 5);
    }

    @AfterAll
    static void afterAll() {
        Unirest.shutDown();
    }

    @BeforeEach
    void beforeEach() {
        url = String.format(RAW_URL, container.getHost(), container.getMappedPort(PORT));
    }

    @Test
    public void testLog_postLogAndGetByNumber_shouldReturnLog() {
        // Arrange
        final HttpResponse<LogDto> postResponse = Unirest.post(url).body(
                new CreateLogDto(
                        Instant.parse("2019-12-31T00:00:00.00Z"),
                        ObjectId.get(),
                        Service.LOG,
                        "fbs12345",
                        "Test1"
                )
        ).asObject(LogDto.class);

        // Act
        final HttpResponse<LogDto> getResponse = Unirest.get(url + postResponse.getBody().logNumber()).asObject(LogDto.class);
        final LogDto testee = getResponse.getBody();

        // Assert
        assertAll("Log",
                () -> assertThat(testee.logNumber().toString()).isNotBlank(),
                () -> assertThat(testee.triggeredAt().toString()).isEqualTo("2019-12-31T00:00:00.00Z"),
                () -> assertThat(testee.triggeredBy()).isEqualTo(ObjectId.get()),
                () -> assertThat(testee.service()).isEqualTo(Service.LOG),
                () -> assertThat(testee.orderNumber()).isEqualTo("fbs12345"),
                () -> assertThat(testee.message()).isNotBlank()
        );
    }

    @Test
    public void testLog_getRequest_shouldReturnAllLogAsDtoList() {
        // Arrange
        Unirest.post(url).body(
                new CreateLogDto(
                        Instant.parse("2019-12-31T00:00:00.00Z"),
                        ObjectId.get(),
                        Service.LOG,
                        "fbs12345",
                        "Test2"
                )
        );

        Unirest.post(url).body(
                new CreateLogDto(
                        Instant.parse("2019-12-31T00:00:00.00Z"),
                        ObjectId.get(),
                        Service.LOG,
                        "fbs12345",
                        "Test2"
                )
        );

        // Act
        final HttpResponse<List<LogDto>> getResponse = Unirest.get(url).asObject(new GenericType<List<LogDto>>(){});
        final List<LogDto> testee = getResponse.getBody();

        // Assert
        assertThat(testee.size()).isEqualTo(2);
    }
}
