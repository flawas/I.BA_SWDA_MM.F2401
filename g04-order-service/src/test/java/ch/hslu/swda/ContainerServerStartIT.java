package ch.hslu.swda;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

/**
 * Testcases für Student Service. Verwendet TestContainer, d.h. die aktuelle
 * Version muss als Docker-Image verfügbar sein (gebunden an package-Lifecycle).
 */
@Testcontainers
final class ContainerServerStartIT {

    private static final Logger LOG = LoggerFactory.getLogger(ContainerServerStartIT.class);
    private static final String IMAGE = "swda-24fs/g04-order-service:latest";

    @Container
    private final GenericContainer<?> container
            = new GenericContainer<>(DockerImageName.parse(IMAGE))
                    .withStartupTimeout(Duration.ofSeconds(20))
                    .withEnv("RABBIT", "OFF")
                    .waitingFor(Wait.forLogMessage(".*Service started.*\\n", 1));

    @Test
    void testContainerStartable() throws Exception {
        final String logs = container.getLogs();
        LOG.info(logs);
        assertThat(logs).contains("Service started");
    }
}
