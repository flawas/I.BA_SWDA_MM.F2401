package ch.hslu.swda.api.services;

import ch.hslu.swda.data.dto.CreateLogDto;
import ch.hslu.swda.data.dto.LogDto;
import ch.hslu.swda.data.mappers.LogMappers;
import ch.hslu.swda.api.repositories.LogRepository;
import jakarta.inject.Singleton;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Singleton
public final class LogServiceImpl implements LogService {

    private final LogRepository repository;

    public LogServiceImpl(LogRepository repository) {
        this.repository = repository;
    }

    public List<LogDto> getAll() {
        return repository.findAll().stream()
                .map(LogMappers::toDto)
                .toList();
    }

    public Optional<LogDto> findByLogNumber(@NonNull final UUID logNumber) {
        return repository.findByLogNumber(logNumber).map(LogMappers::toDto);
    }

    public Optional<LogDto> create(@NonNull final CreateLogDto logDto) {

        // Rabbitmq -> Event Log-service (neue Bestellung)
        // Validation -> ?
        // Received -> Confirmed
        // "Zahlung senden"
        // -> Stornierung?

        return Optional.ofNullable(LogMappers.toDto(repository.save(LogMappers.toEntity(logDto))));
    }
}
