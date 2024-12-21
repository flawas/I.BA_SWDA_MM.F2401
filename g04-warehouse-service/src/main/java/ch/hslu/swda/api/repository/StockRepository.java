package ch.hslu.swda.api.repository;

import ch.hslu.swda.data.entities.StockArticle;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.CrudRepository;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Repository interface for accessing stock article data.
 */
@MongoRepository
public interface StockRepository extends CrudRepository<StockArticle, ObjectId> {

    /**
     * Finds a stock article by its article number.
     *
     * @param articleNumber The article number to search for.
     * @return An optional containing the found stock article, or empty if not found.
     */
    Optional<StockArticle> findByArticleNumber(final int articleNumber);

    /**
     * Updates the stock of a stock article by its article number.
     *
     * @param articleNumber The article number of the stock article to update.
     * @param articleStock  The new stock value.
     */
    void updateArticleStockByArticleNumber(final int articleNumber, final int articleStock);

    /**
     * Updates the delivery date of a stock article by its article number.
     *
     * @param articleNumber        The article number of the stock article to update.
     * @param articleDeliveryDate  The new delivery date.
     */
    void updateArticleDeliveryDateByArticleNumber(final int articleNumber, final LocalDate articleDeliveryDate);

    void updateArticlePriceByArticleNumber(final int articleNumber, final double articlePrice);
}
