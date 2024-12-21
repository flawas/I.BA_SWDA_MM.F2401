package ch.hslu.swda.data.entities.order;

import io.micronaut.core.annotation.Creator;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.bson.types.ObjectId;

import java.time.Instant;
import java.util.List;

import static ch.hslu.swda.util.StringUtil.generateRandomText;

@Data
@MappedEntity
public final class Order {

    @Id
    @GeneratedValue
    private ObjectId id;

    @NonNull
    @NotBlank
    private String number;

    @NonNull
    @NotBlank
    @Email
    private String customerEmail;

    @NonNull
    @NotBlank
    @Email
    private String sellerEmail;

    @NonNull
    @NotBlank
    private Address shippingAddress;

    @NonNull
    @NotBlank
    private Address billingAddress;

    @NonNull
    @NotEmpty
    private List<OrderPosition> positions;

    @NonNull
    @DateCreated
    private Instant orderDate;

    @NonNull
    private OrderState state;

    public Order() { }

    public Order(
            final String customerEmail,
            final String sellerEmail,
            final Address shippingAddress,
            final Address billingAddress,
            final List<OrderPosition> positions
    ) {
        this(
            "O" + generateRandomText(5),
            customerEmail,
            sellerEmail,
            shippingAddress,
            billingAddress,
            positions,
            OrderState.RECEIVED
        );
    }

    @Creator
    public Order(
            final String number,
            final String customerEmail,
            final String sellerEmail,
            final Address shippingAddress,
            final Address billingAddress,
            final List<OrderPosition> positions,
            final OrderState state
    ) {
        this.number = number;
        this.customerEmail = customerEmail;
        this.sellerEmail = sellerEmail;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.positions = positions;
        this.state = state;
    }
}
