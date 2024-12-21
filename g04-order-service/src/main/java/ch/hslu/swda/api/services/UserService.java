package ch.hslu.swda.api.services;

import ch.hslu.swda.data.dto.user.UserDto;
import io.micronaut.core.annotation.NonNull;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDto> getAll();
    Optional<UserDto> findByEmail(@NonNull final String email);
    Optional<UserDto> create(@NonNull final UserDto userDto);
}
