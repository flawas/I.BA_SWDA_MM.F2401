package ch.hslu.swda.api.controllers;

import io.micronaut.core.version.annotation.Version;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Controller()
@ExecuteOn(TaskExecutors.IO)
@Slf4j
public final class PingController {

    /**
     * Ping order service.
     *
     * @return Static hello world.
     */
    @Get
    @Version("1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully pinged.")})
    @Produces(MediaType.TEXT_PLAIN)
    @Tag(name = "Ping")
    public HttpResponse<String> ping() {
        log.info("API: Pinged.");
        return HttpResponse.ok("Hello world.");
    }
}