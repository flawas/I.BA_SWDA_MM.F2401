package ch.hslu.swda;

import io.micronaut.context.event.ShutdownEvent;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class ApplicationContext {

    // TODO-go: Does this fire?
    @EventListener
    public void onStartup(final StartupEvent startupEvent) {
        log.info("MICRONAUT: g04-order-service started...");
    }

    @EventListener
    public void onShutdown(final ShutdownEvent shutdownEvent) {
        log.info("MICRONAUT: g04-order-service shutdown.");
    }
}
