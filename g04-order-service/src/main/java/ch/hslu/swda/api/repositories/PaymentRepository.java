package ch.hslu.swda.api.repositories;

import ch.hslu.swda.data.entities.payment.Payment;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.CrudRepository;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

@MongoRepository
public interface PaymentRepository extends CrudRepository<Payment, ObjectId> {

    Optional<Payment> findByNumber(final String number);

    List<Payment> findByCustomerEmail(final String customerEmail);
}
