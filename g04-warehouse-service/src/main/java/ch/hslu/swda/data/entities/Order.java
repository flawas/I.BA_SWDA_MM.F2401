package ch.hslu.swda.data.entities;

import io.micronaut.core.annotation.Creator;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.bson.types.ObjectId;

import java.time.LocalDate;

import static ch.hslu.swda.common.util.NumberUtil.generateRandomNumber;

/**
 * Entity class representing an order.
 */
@Data
@MappedEntity
public class Order extends StockArticle {

    /**
     * The ID of the order.
     */
    @Id
    @GeneratedValue
    private ObjectId id;

    /**
     * The order number.
     */
    @GeneratedValue
    private int orderNumber;

    /**
     * The article number of the ordered product.
     */
    @NonNull
    @NotBlank
    private int articleNumber;

    /**
     * The quantity of the ordered product.
     */
    @NonNull
    @NotBlank
    private int articleCount;

    /**
     * The delivery date of the ordered product.
     */
    @NonNull
    @NotBlank
    LocalDate articleDeliveryDate;

    /**
     * The date when the order was placed.
     */
    @NotNull
    @NotBlank
    LocalDate orderDate;

    /**
     * The contact information for the order.
     */
    @NotNull
    @NotBlank
    String orderContact;

    /**
     * Default constructor for creating an order with current date as the order date and a generated order number.
     *
     * @param articleNumber The article number of the product to be ordered.
     * @param articleCount  The quantity of the product to be ordered.
     * @param orderContact  The contact information for the order.
     */
    public Order(int articleNumber, int articleCount, String orderContact) {
        this(articleNumber, articleCount, LocalDate.now(), orderContact, generateRandomNumber(5));
    }

    /**
     * Constructor for creating an order with a specified order date and order number.
     *
     * @param articleNumber The article number of the product to be ordered.
     * @param articleCount  The quantity of the product to be ordered.
     * @param orderDate     The date when the order was placed.
     * @param orderContact  The contact information for the order.
     * @param orderNumber   The order number.
     */
    @Creator
    public Order(int articleNumber, int articleCount, LocalDate orderDate, String orderContact, int orderNumber) {
        this.orderNumber = orderNumber;
        this.articleNumber = articleNumber;
        this.articleCount = articleCount;
        this.orderDate = orderDate;
        this.orderContact = orderContact;
    }
}
