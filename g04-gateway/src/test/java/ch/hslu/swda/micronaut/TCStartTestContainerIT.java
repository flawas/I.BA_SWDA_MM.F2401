/*
 * Copyright 2024 Roland Gisler, Hochschule Luzern - Informatik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.hslu.swda.micronaut;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

/**
 * Testcases für Student Service. Verwendet TestContainer, d.h. die aktuelle
 * Version muss als Docker-Image verfügbar sein (gebunden an package-Lifecycle).
 */
@Testcontainers
final class TCStartTestContainerIT {

    private static final Logger LOG = LoggerFactory.getLogger(TCStartTestContainerIT.class);
    private static final int PORT = 8090;
    private static final String IMAGE = "swda-24fs/gateway-sample:latest";

    @Container
    private final GenericContainer<?> container
            = new GenericContainer<>(DockerImageName.parse(IMAGE))
                    .withStartupTimeout(Duration.ofSeconds(20))
                    .withExposedPorts(PORT);

    @Test
    void testContainerStartable() throws Exception {
        final String logs = container.getLogs();
        LOG.info(logs);
        assertThat(logs).contains("gestartet");
    }
}
