package ch.hslu.swda.api.repositories;

import ch.hslu.swda.data.entities.Log;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.CrudRepository;
import org.bson.types.ObjectId;

import java.util.Optional;
import java.util.UUID;

@MongoRepository
public interface LogRepository extends CrudRepository<Log, ObjectId> {

    Optional<Log> findByLogNumber(final UUID logNumber);
}
