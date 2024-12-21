package ch.hslu.swda.data.entities;

import io.micronaut.core.annotation.Creator;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.bson.types.ObjectId;

import java.time.LocalDate;

/**
 * Entity class representing a stock article.
 */
@Data
@MappedEntity
public class StockArticle {

    /**
     * The ID of the stock article.
     */
    @Id
    @GeneratedValue
    private ObjectId id;

    /**
     * The article number of the stock article.
     */
    @NonNull
    @NotBlank
    private int articleNumber;

    /**
     * The current stock count of the stock article.
     */
    @Nullable
    private int articleStock;

    /**
     * The delivery date of the stock article.
     */
    @Nullable
    private LocalDate articleDeliveryDate;

    private double articlePrice;

    /**
     * Constructor for creating a stock article with a specified article number.
     *
     * @param articleNumber The article number of the stock article.
     */
    @Creator
    public StockArticle(final int articleNumber) {
        this.articleNumber = articleNumber;
    }

    /**
     * Default constructor for creating a stock article.
     */
    public StockArticle() {
    }
}
