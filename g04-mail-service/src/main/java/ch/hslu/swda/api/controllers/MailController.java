package ch.hslu.swda.api.controllers;

import ch.hslu.swda.api.services.MailService;
import ch.hslu.swda.data.dto.CreateMailDto;
import ch.hslu.swda.data.dto.MailDto;
import io.micronaut.core.version.annotation.Version;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


/**
 * Controller for handling order operations.
 */
@Controller("/mail")
@ExecuteOn(TaskExecutors.IO)
public class MailController {
    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @Post()
    @Version("1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created new mail.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = MailDto.class))),
            @ApiResponse(responseCode = "422", description = "The provided attributes are insufficient to create a new mail!")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Tag(name = "Mail")
    public HttpResponse<?> createMail(@Body final CreateMailDto createMailDto) {
        mailService.sendMail(createMailDto);
        return HttpResponse
                .status(HttpStatus.CREATED)
                .body(createMailDto);
    }

}
