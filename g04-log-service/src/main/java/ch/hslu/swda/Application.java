package ch.hslu.swda;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Demo für Applikationsstart.
 */
@OpenAPIDefinition(
        info = @Info(
                title = "G04 Log API",
                version = "1.0"
        ), servers = {
        @Server(url = "https://www.g04.swda.hslu-edu.ch"),
        @Server(url = "http://localhost:8097")
        }
)
public final class Application {

        private static final Logger LOG = LoggerFactory.getLogger(Application.class);

        private Application() {
        }

        /**
         * main-Methode. Startet einen Timer für den HeartBeat.
         * @param args Not in use.
         */
        public static void main(final String[] args) {

                LOG.info("Service starting...");
                Micronaut.run(Application.class, args);
        }
}

