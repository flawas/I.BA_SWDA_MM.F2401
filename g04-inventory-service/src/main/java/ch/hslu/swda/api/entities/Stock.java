package ch.hslu.swda.api.entities;

import ch.hslu.swda.api.dto.ArticleDto;
import io.micronaut.core.annotation.Creator;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
@MappedEntity
public final class Stock {
    @Id @GeneratedValue
    private ObjectId id;

    @NonNull @NotBlank
    private final String articleNumber;

    @NonNull @NotBlank
    private final String stockDescription;

    @NonNull @PositiveOrZero
    private Number stock;

    @NotNull @PositiveOrZero
    private Number minimalStock;

    @NotNull @PositiveOrZero
    private Number reservedStock;

    @NotNull
    private ArticleDto article;

    @NotNull
    private String branchNumber;

    //public Stock() {}

//    public Stock(String description, Number stock, Number minimalStock, Number reservedStock, ArticleDto article) {
//        var rand = ThreadLocalRandom.current().nextInt(100_000, 1_000_000);
//        this.articleNumber = String.valueOf(rand);
//        this.stockDescription = description;
//        this.stock = stock;
//        this.minimalStock = minimalStock;
//        this.reservedStock = 0;
//        this.article = article;
//    }

    @Creator
    public Stock(
            final String articleNumber,
            final String stockDescription,
            final Number stock,
            final Number minimalStock,
            final Number reservedStock,
            final ArticleDto article,
            final String branchNumber
    ) {
        this.articleNumber = articleNumber;
        this.stockDescription = stockDescription;
        this.stock = stock;
        this.minimalStock = minimalStock;
        this.reservedStock = 0;
        this.article = article;
        this.branchNumber = branchNumber;
    }

    public OrderPositionState shipArticle(Number amount) {

        if (amount.intValue() > stock.intValue()) {
            this.reservedStock = this.reservedStock.intValue() + amount.intValue();
            return OrderPositionState.REORDERED;
        }

        stock = stock.intValue() - amount.intValue();
        return OrderPositionState.READY;
    }
}
