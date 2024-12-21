package ch.hslu.swda.data.entities.order;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Optional;

@Data
@Serdeable
public final class OrderPosition {

    @Min(value = 1, message = "Number of pieces cannot be lower than minimum of 1")
    @Max(value = 50, message = "Number of pieces cannot be higher than maximum of 50")
    private int amount;

    @NonNull
    @NotBlank
    private String articleNumber;

    private Optional<Float> price;

    @NonNull
    private OrderPositionState state;

    public OrderPosition() { }
    public OrderPosition(
            final int amount,
            final String articleNumber
    ) {
        this.amount = amount;
        this.articleNumber = articleNumber;
        this.price = Optional.empty();
        this.state = OrderPositionState.UNKNOWN;
    }

    public OrderPosition(
            final int amount,
            final String articleNumber,
            final OrderPositionState state
    ) {
        this.amount = amount;
        this.articleNumber = articleNumber;
        this.price = Optional.empty();
        this.state = state;
    }

    public OrderPosition(
            final int amount,
            final String articleNumber,
            final Optional<Float> price,
            final OrderPositionState state
    ) {
        this.amount = amount;
        this.articleNumber = articleNumber;
        this.price = price;
        this.state = state;
    }
}