package ch.hslu.swda.api.repositories;

import ch.hslu.swda.data.entities.user.User;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.CrudRepository;
import org.bson.types.ObjectId;

import java.util.Optional;

@MongoRepository
public interface UserRepository extends CrudRepository<User, ObjectId> {

    Optional<User> findByEmail(final String email);
}