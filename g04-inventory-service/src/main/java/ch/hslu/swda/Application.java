package ch.hslu.swda;

import ch.hslu.swda.api.controllers.InventoryController;
import io.micronaut.openapi.annotation.OpenAPIInclude;
import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@OpenAPIDefinition(
        info = @Info(
                title = "G04 Inventory API",
                version = "1.0.0"
        ), servers = {
                @Server(url = "https://www.g04.swda.hslu-edu.ch"),
                @Server(url = "http://localhost:8095"),
                @Server(url = "http://localhost:8090")
        }
)
@OpenAPIInclude(
        classes = {
                InventoryController.class
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
    public static void main(final String[] args) throws InterruptedException {
        final long startTime = System.currentTimeMillis();
        LOG.info("Service starting...");

        LOG.atInfo().addArgument(System.currentTimeMillis() - startTime).log("Service started in {}ms.");
        //Thread.sleep(6_000);

        Micronaut.run(Application.class, args);
    }
}
