package ch.hslu.swda.api.controllers;

import ch.hslu.swda.api.services.UserService;
import ch.hslu.swda.data.dto.user.UserDto;
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
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Controller("/users")
@ExecuteOn(TaskExecutors.IO)
@Slf4j
public final class UserController {
    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    /**
     * Get all users (READ).
     * @return List of users (UserDto).
     */
    @Get
    @Version("1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned all users.")})
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Users")
    public HttpResponse<List<UserDto>> getUsers() {
        final List<UserDto> users = userService.getAll();
        log.info("API: Returning all {} users.", users.size());
        return HttpResponse.ok(users);
    }

    /**
     * Get user by unique email address (READ).
     * @param email Unique email address.
     * @return User (UserDto).
     */
    @Get("/{email}")
    @Version("1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned requested user data."),
            @ApiResponse(responseCode = "404", description = "Requested user with provided email address was not found!")})
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Users")
    public HttpResponse<?> getUser(@PathVariable("email") final String email) {
        final Optional<UserDto> user = userService.findByEmail(email);

        if (user.isEmpty()) {
            log.warn("API: No user found for given email address '{}'!", email);
            return HttpResponse.notFound(String.format("No user found for given email address '%s'!", email));
        }

        log.info("API: Returning user for email '{}'.", user.get().email());
        return HttpResponse.ok(user.get());
    }

    /**
     * Create new user (CREATE).
     * @param userDto New user data (UserDto).
     * @return Created user (UserDto).
     */
    @Post()
    @Version("1")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created new user.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "422", description = "The provided attributes are insufficient to create a new user!")})
    @Tag(name = "Users")
    public HttpResponse<?> createUser(@Body final UserDto userDto) {
        final Optional<UserDto> user = userService.create(userDto);

        if (user.isEmpty()) {
            log.warn("API: User with email '{}' could not be created!", userDto.email());
            return HttpResponse.unprocessableEntity();
        }

        log.info("API: User with email '{}' was created.", user.get().email());
        return HttpResponse
                .status(HttpStatus.CREATED)
                .body(user.get());
    }
}
