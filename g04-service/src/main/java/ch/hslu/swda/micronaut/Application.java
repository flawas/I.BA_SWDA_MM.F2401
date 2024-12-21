package ch.hslu.swda.micronaut;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * Demo für Applikationsstart.
 */
@OpenAPIDefinition(
        info = @Info(
                title = "G04 Warehouse API",
                version = "1.0"
        ), servers = @Server(url = "https://guides.micronaut.io")
)
public class Application {
        public static void main(String[] args) {
                Micronaut.run(Application.class, args);
        }
}
