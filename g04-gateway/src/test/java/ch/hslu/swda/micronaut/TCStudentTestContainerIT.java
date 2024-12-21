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
import static org.junit.jupiter.api.Assertions.assertAll;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.hslu.swda.model.Student;
import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.utility.DockerImageName;

/**
 * Testcases für Student Service. Verwendet TestContainer (Docker).
 */
@Testcontainers
final class TCStudentTestContainerIT {

    private static final Logger LOG = LoggerFactory.getLogger(TCStudentTestContainerIT.class);
    private static final int PORT = 8090;
    private static final String IMAGE = "swda-24fs/gateway-sample:latest";

    @Container
    private final GenericContainer<?> container
            = new GenericContainer<>(DockerImageName.parse(IMAGE))
                    .withStartupTimeout(Duration.ofSeconds(20))
                    .withExposedPorts(PORT);

    @Test
    void testGetStudentTwoJson() throws Exception {
        final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                    false);
        final HttpClient httpClient = HttpClient.newBuilder().version(Version.HTTP_2).build();
        final String url = String.format("http://%s:%d/api/v1/students/%d", container.getHost(),
                    container.getMappedPort(PORT), 2);
        final HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        final HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
        final String json = response.body();
        final Student student = mapper.readValue(json, Student.class);
        assertAll("Student", 
                    () -> assertThat(student.getId()).isEqualTo(2),
                    () -> assertThat(student.getFirstName()).isEqualTo("Babette"),
                    () -> assertThat(student.getLastName()).isEqualTo("Zweifel"),
                    () -> assertThat(student.getMonthOfBirth()).isEqualTo(4));
        LOG.info(container.getLogs());
    }
}