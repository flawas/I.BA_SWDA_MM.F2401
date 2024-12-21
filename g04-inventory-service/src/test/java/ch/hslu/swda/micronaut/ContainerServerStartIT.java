package ch.hslu.swda.micronaut;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
public class ContainerServerStartIT {
    private static final Logger LOG = LoggerFactory.getLogger(ContainerServerStartIT.class);
    private static final String IMAGE = "swda-24fs/g04-inventory-service:latest";

    @Container
    private final GenericContainer<?> container = new GenericContainer<>(DockerImageName.parse(IMAGE))
            .withStartupTimeout(Duration.ofSeconds(60))
            .withEnv("RABBIT", "OFF")
            .waitingFor(Wait.forLogMessage(".*Service started.*\\n", 1));

    @Test
    public void testContainerStartable() throws Exception {
        final String logs = container.getLogs();
        LOG.info(logs);

        assertThat(logs).contains("Service started");
    }
}
