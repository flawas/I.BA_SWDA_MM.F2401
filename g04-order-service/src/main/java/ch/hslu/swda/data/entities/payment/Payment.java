package ch.hslu.swda.data.entities.payment;

import io.micronaut.core.annotation.Creator;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.DateCreated;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.bson.types.ObjectId;

import java.time.Instant;

import static ch.hslu.swda.util.StringUtil.generateRandomText;

@Data
@MappedEntity
public final class Payment {

    @Id
    @GeneratedValue
    private ObjectId id;

    @NonNull
    @NotBlank
    private String number;

    @NonNull
    @NotBlank
    private String orderNumber;

    @NonNull
    @NotBlank
    private String customerEmail;

    @NonNull
    @DateCreated
    private Instant invoiceDate;

    @NonNull
    private PaymentState state;

    public Payment(
            final String orderNumber,
            final String customerEmail
    ) {
        this(
            "P" + generateRandomText(5),
            orderNumber,
            customerEmail,
            PaymentState.PENDING
        );
    }

    @Creator
    public Payment(
            final String number,
            final String orderNumber,
            final String customerEmail,
            final PaymentState state
    ) {
        this.number = number;
        this.orderNumber = orderNumber;
        this.customerEmail = customerEmail;
        this.state = state;
    }
}
