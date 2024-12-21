package ch.hslu.swda.data.dto.user;

import ch.hslu.swda.data.entities.user.UserRole;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Serdeable
public record UserDto(
    @NonNull
    @NotBlank
    String firstname,

    @NonNull
    @NotBlank
    String lastname,

    @NonNull
    @NotBlank
    @Email
    String email,

    @NonNull
    UserRole role,

    @NonNull
    @NotBlank
    String branchNumber
) { }
