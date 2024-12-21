package ch.hslu.swda;

import io.micronaut.context.event.ShutdownEvent;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class ApplicationContext {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationContext.class);

    @EventListener
    public void onStartup(final StartupEvent event) {
        LOG.info("MICRONAUT: Started...");
    }

    @EventListener
    public void onShutdown(final ShutdownEvent event) {
        LOG.info("MICRONAUT: Shutdown...");
    }
}
