/*
 * Copyright 2024 Roland Christen, HSLU Informatik, Switzerland
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
package ch.hslu.swda;

import ch.hslu.swda.api.controllers.WarehouseController;
import com.rabbitmq.client.impl.Environment;
import io.micronaut.openapi.annotation.OpenAPIInclude;
import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;


/**
 * Microservice g04-order-service Application.
 */
@OpenAPIDefinition(
        info = @Info(
                title = "G04 Warehouse API",
                version = "1.0",
                description = "REST API for warehouse in the G04 FBS."
        ), servers = {
        @Server(url = "https://www.g04.swda.hslu-edu.ch"),
        @Server(url = "http://localhost:8090")
}
)
@OpenAPIInclude(
        classes = {
                WarehouseController.class,
        }
)

public final class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    /**
     * Privater Konstruktor.
     */
    private Application() {
    }

    /**
     * main-Methode. Startet einen Timer für den HeartBeat.
     *
     * @param args not used.
     */
    public static void main(final String[] args) {
        final long startTime = System.currentTimeMillis();
        LOG.info("Service starting...");
        Micronaut.run(Application.class, args); // TODO-go: Fix Micronaut not shutdown correctly in IDE, blocking port
    }
}
