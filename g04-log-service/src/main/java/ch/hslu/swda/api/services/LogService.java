package ch.hslu.swda.api.services;

import ch.hslu.swda.data.dto.CreateLogDto;
import ch.hslu.swda.data.dto.LogDto;
import io.micronaut.core.annotation.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LogService {

    List<LogDto> getAll();
    Optional<LogDto> findByLogNumber(@NonNull final UUID logNumber);
    Optional<LogDto> create(@NonNull final CreateLogDto logDto);
}
