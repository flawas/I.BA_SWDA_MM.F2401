package ch.hslu.swda;

import ch.hslu.swda.api.controllers.OrderController;
import ch.hslu.swda.api.controllers.PaymentController;
import ch.hslu.swda.api.controllers.PingController;
import ch.hslu.swda.api.controllers.UserController;
import io.micronaut.openapi.annotation.OpenAPIInclude;
import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.extern.slf4j.Slf4j;

import static io.micronaut.context.env.Environment.DEVELOPMENT;

/**
 * Microservice g04-order-service Application.
 */
@OpenAPIDefinition(
        info = @Info(
                title = "G04 Order API",
                version = "1.0",
                description = "REST API for orders in the G04 FBS."
        ), servers = {
            @Server(url = "https://www.g04.swda.hslu-edu.ch"),
            @Server(url = "http://localhost:8094"),
            @Server(url = "http://localhost:8090")
        }
)
@OpenAPIInclude(
        classes = {
                PingController.class,
                OrderController.class,
                UserController.class,
                PaymentController.class
        }
)
@Slf4j
public final class Application {

    private Application() { }

    /**
     * main-Methode. Startet einen Timer für den HeartBeat.
     * @param args Not in use.
     */
    public static void main(final String[] args) {

        log.info("Order-Service starting...");

        Micronaut.build(args)
                .mainClass(Application.class)
                .defaultEnvironments(DEVELOPMENT)
                .start();
    }
}
