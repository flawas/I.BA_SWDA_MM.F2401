package ch.hslu.swda.api.services;

import ch.hslu.swda.api.repositories.UserRepository;
import ch.hslu.swda.data.dto.user.UserDto;
import ch.hslu.swda.data.mappers.UserMappers;
import jakarta.inject.Singleton;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

@Singleton
public final class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(UserMappers::toDto)
                .toList();
    }

    public Optional<UserDto> findByEmail(@NonNull final String email) {
        return userRepository.findByEmail(email).map(UserMappers::toDto);
    }

    public Optional<UserDto> create(@NonNull final UserDto UserDto) {
        return Optional.ofNullable(UserMappers.toDto(userRepository.save(UserMappers.toEntity(UserDto))));
    }
}
