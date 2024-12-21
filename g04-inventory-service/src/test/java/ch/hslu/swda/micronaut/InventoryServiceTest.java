package ch.hslu.swda.micronaut;

import com.mongodb.assertions.Assertions;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;

@Disabled
@MicronautTest
public class InventoryServiceTest {
    @Inject
    EmbeddedApplication<?> application;

    @Test
    public void ApplicationIsRunningTest() {
        Assertions.assertTrue(application.isRunning());
    }
}
