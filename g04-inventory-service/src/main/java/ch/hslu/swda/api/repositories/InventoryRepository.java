package ch.hslu.swda.api.repositories;

import ch.hslu.swda.api.entities.Stock;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.CrudRepository;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;


@MongoRepository
public interface InventoryRepository extends CrudRepository<Stock, ObjectId> {
    Optional<Stock> findByArticleNumber(final String articleNumber);

    List<Stock> findByBranchNumber(final String branchNumber);
}
