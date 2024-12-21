package ch.hslu.swda.api.controllers;

import ch.hslu.swda.data.dto.LogDto;
import ch.hslu.swda.api.services.LogService;
import io.micronaut.core.version.annotation.Version;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller("/logs")
@ExecuteOn(TaskExecutors.IO)
public final class LogController {

    private static final Logger LOG = LoggerFactory.getLogger(LogController.class);
    private final LogService service;

    public LogController(LogService service) {
        this.service = service;
    }

    /**
     * Get all logs (READ).
     * @return List of logs (LogDto).
     */
    @Get
    @Version("1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned all logs.")})
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Logs")
    public HttpResponse<List<LogDto>> getLogs() {
        final List<LogDto> logs = service.getAll();
        LOG.info("API: Returning all {} logs.", logs.size());
        return HttpResponse.ok(logs); // TODO-go: Is .contentType(MediaType.APPLICATION_JSON) needed here or is @Produces annotation sufficient?
    }

    /**
     * Get log by unique log number (READ).
     * @param logNumber Unique log number.
     * @return Log (LogDto).
     */
    @Get("/{logNumber}")
    @Version("1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned requested log."),
            @ApiResponse(responseCode = "404", description = "Requested log with provided log number was not found!")})
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Logs")
    public HttpResponse<?> getLog(@PathVariable("logNumber") final String logNumber) { // TODO-go: Is there a better return than <?>.
        final Optional<LogDto> log = service.findByLogNumber(UUID.fromString(logNumber));

        if (log.isEmpty()) {
            LOG.warn("API: No log found for given log number '{}'!", logNumber);
            return HttpResponse.notFound(String.format("No log found for given log number '%s'!", logNumber));
        }

        LOG.info("API: Returning log for '{}' log number.", logNumber);
        return HttpResponse.ok(log.get());
    }
}
