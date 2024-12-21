package ch.hslu.swda.micronaut.controller;

        import ch.hslu.swda.micronaut.api.LogApi;
        import io.micronaut.core.version.annotation.Version;
        import io.micronaut.http.annotation.Produces;
        import io.micronaut.http.annotation.QueryValue;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import io.micronaut.http.MediaType;
        import io.micronaut.http.annotation.Controller;
        import io.micronaut.http.annotation.Get;

        import java.util.UUID;


@Controller("/api/log")
public class LogController {

    private static final Logger LOG = LoggerFactory.getLogger(LogController.class);

    LogApi logApi = new LogApi();

    @Get
    @Version("1")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllLogs() {
        LOG.info("API: Returning all logs." );
        return logApi.getAllLogs();
    }

    @Get("/{id}")
    @Version("1")
    @Produces(MediaType.TEXT_PLAIN)
    public String getLog(@QueryValue("id") final UUID id) {
        LOG.info("API: Returning log {}", id);
        return logApi.getSpecificLog(id);
    }
}
