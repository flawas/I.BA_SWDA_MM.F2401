package ch.hslu.swda.api.repositories;

import ch.hslu.swda.data.entities.order.Order;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.CrudRepository;
import org.bson.types.ObjectId;

import java.util.Optional;

@MongoRepository
public interface OrderRepository extends CrudRepository<Order, ObjectId> {

    Optional<Order> findByNumber(final String number);
}
