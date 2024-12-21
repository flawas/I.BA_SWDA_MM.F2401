package ch.hslu.swda;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.ClassRule;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

/**
 * Testcases für Student Service. Verwendet TestContainer, d.h. die aktuelle
 * Version muss als Docker-Image verfügbar sein (gebunden an package-Lifecycle).
 */

@Disabled
final class ContainerServerStartIT {

    private static final Logger LOG = LoggerFactory.getLogger(ContainerServerStartIT.class);
    private static final String IMAGE = "cr.gitlab.switch.ch/hslu/shared/devops/docker-cache/swda/rabbitmq:3.9.29-management-alpine";
    private static final String DOCKER_COMPOSE_FILE = "src/test/resources/stack.local.yml";

    @ClassRule
    public static DockerComposeContainer environment =
            new DockerComposeContainer(new File("src/test/resources/stack.local.yml"))
                    .withExposedService("swda-24fs/g04-warehouse-service:latest", 8096, Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30)))
                    .waitingFor("swda-24fs/g04-warehouse-service:latest", Wait.forLogMessage("MICRONAUT", 1));

    @Test
    void testContainerStartable() throws Exception {
        environment
                .waitingFor("swda-24fs/g04-mail-warehouse:latest", Wait.forLogMessage("MICRONAUT: Startet", 1))
                .start();
        final String logs = String.valueOf(environment.getContainerByServiceName("swda-24fs/g04-warehouse-service"));
        LOG.error(logs);
        assertThat(logs).contains("Startup completed");
    }
}
