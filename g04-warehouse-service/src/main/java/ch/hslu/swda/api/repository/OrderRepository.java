package ch.hslu.swda.api.repository;

import ch.hslu.swda.data.entities.Order;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.CrudRepository;
import org.bson.types.ObjectId;

import java.util.Optional;

/**
 * Repository interface for accessing order data.
 */
@MongoRepository
public interface OrderRepository extends CrudRepository<Order, ObjectId> {

    /**
     * Finds an order by its order number.
     *
     * @param orderNumber The order number to search for.
     * @return An optional containing the found order, or empty if not found.
     */
    Optional<Order> findOrderByOrderNumber(final int orderNumber);
}
