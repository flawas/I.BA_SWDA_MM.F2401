package ch.hslu.swda.api.repositories;

import ch.hslu.swda.api.entities.Branch;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.CrudRepository;
import org.bson.types.ObjectId;
import java.util.Optional;


@MongoRepository
public interface BranchRepository extends CrudRepository<Branch, ObjectId> {
    Optional<Branch> findByBranchNumber(final String branchNumber);
}
