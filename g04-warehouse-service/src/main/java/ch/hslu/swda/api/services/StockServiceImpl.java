package ch.hslu.swda.api.services;

import ch.hslu.swda.api.controllers.WarehouseController;
import ch.hslu.swda.common.util.NumberUtil;
import ch.hslu.swda.data.dto.CreateLogDto;
import ch.hslu.swda.data.entities.Service;
import ch.hslu.swda.data.mappers.StockMappers;
import ch.hslu.swda.api.repository.StockRepository;
import ch.hslu.swda.data.dto.CreateStockArticleDto;
import ch.hslu.swda.data.dto.StockArticleDto;
import ch.hslu.swda.messaging.producers.BusinessEventLogProducer;
import ch.hslu.swda.stock.api.Stock;
import ch.hslu.swda.stock.api.StockException;
import ch.hslu.swda.stock.api.StockFactory;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the Stock service interface.
 */
@Singleton
public class StockServiceImpl implements Stock {

    private static final Logger LOG = LoggerFactory.getLogger(WarehouseController.class);
    private final Stock stock = StockFactory.getStock();
    private StockRepository stockRepository;
    private BusinessEventLogProducer businessEventLogProducer;


    public StockServiceImpl(final StockRepository stockRepository, BusinessEventLogProducer businessEventLogProducer) {
        this.stockRepository = stockRepository;
        this.businessEventLogProducer = businessEventLogProducer;
    }

    @Override
    public int getItemCount(int articleNumber) throws StockException {
        if (stockRepository.findByArticleNumber(articleNumber).isEmpty()) {
            createArticle(new CreateStockArticleDto(articleNumber));
            stockRepository.updateArticleStockByArticleNumber(articleNumber, stock.getItemCount(articleNumber));
            stockRepository.updateArticleDeliveryDateByArticleNumber(articleNumber, stock.getItemDeliveryDate(articleNumber));
            stockRepository.updateArticlePriceByArticleNumber(articleNumber, NumberUtil.generateRandomPrice());
        }
        businessEventLogProducer.produce(
                new CreateLogDto(
                        Instant.now(),
                        null,
                        Service.WAREHOUSE,
                        null,
                        "Returned ItemCount of article " + articleNumber
                ));
        return stockRepository.findByArticleNumber(articleNumber).get().getArticleStock();
    }

    @Override
    public LocalDate getItemDeliveryDate(int articleNumber) throws StockException {
        if (stockRepository.findByArticleNumber(articleNumber).isPresent()) {
            return stockRepository.findByArticleNumber(articleNumber).get().getArticleDeliveryDate();
        }
        businessEventLogProducer.produce(
                new CreateLogDto(
                        Instant.now(),
                        null,
                        Service.WAREHOUSE,
                        null,
                        "Returned ItemDeliveryDate of article " + articleNumber
                ));
        return LocalDate.of(0000, 0, 0);
    }

    @Override
    public int orderItem(int articleNumber, int articleOrderCount) throws StockException {
        if (stockRepository.findByArticleNumber(articleNumber).isPresent()) {
            int stockCount = stockRepository.findByArticleNumber(articleNumber).get().getArticleStock();
            if (articleOrderCount <= stockCount) {
                stockRepository.updateArticleStockByArticleNumber(articleNumber, stockCount - articleOrderCount);
                return articleOrderCount;
            }
        }
        return -1;
    }

    @Override
    public String reserveItem(int i, int i1) throws StockException {
        return null;
    }

    @Override
    public int orderReservation(String s) throws StockException {
        return 0;
    }

    @Override
    public String freeReservation(String s) throws StockException {
        return null;
    }

    /**
     * Retrieves all stock articles.
     *
     * @return A list of stock articles (StockArticleDto).
     */
    public List<StockArticleDto> getAll() {
        return stockRepository.findAll().stream()
                .map(StockMappers::toDto)
                .toList();
    }

    /**
     * Finds stock articles by their article number.
     *
     * @param articleNumber The article number to search for.
     * @return A list of stock articles matching the given article number.
     */
    public List<StockArticleDto> findByArticleNumber(final int articleNumber) {
        return stockRepository.findByArticleNumber(articleNumber).stream()
                .map(StockMappers::toDto)
                .toList();
    }

    private Optional<StockArticleDto> createArticle(CreateStockArticleDto createStockArticleDto) {
        businessEventLogProducer.produce(
                new CreateLogDto(
                        Instant.now(),
                        null,
                        Service.WAREHOUSE,
                        null,
                        "Created article of " + createStockArticleDto.toString()
                ));
        return Optional.ofNullable(StockMappers.toDto(stockRepository.save(StockMappers.toEntity(createStockArticleDto))));
    }
}
