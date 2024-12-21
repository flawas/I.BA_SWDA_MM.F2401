package ch.hslu.swda.api.services;

import ch.hslu.swda.api.client.WarehouseClient;
import ch.hslu.swda.api.controllers.InventoryController;
import ch.hslu.swda.api.dto.*;
import ch.hslu.swda.api.entities.Branch;
import ch.hslu.swda.api.entities.OrderPositionState;
import ch.hslu.swda.api.entities.Stock;
import ch.hslu.swda.api.mappers.LogMapper;
import ch.hslu.swda.api.mappers.OrderPositionMapper;
import ch.hslu.swda.api.mappers.StockMapper;
import ch.hslu.swda.api.repositories.BranchRepository;
import ch.hslu.swda.api.repositories.InventoryRepository;
import ch.hslu.swda.bus.OrderPositionValidatedProducer;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Singleton
@Slf4j
public final class InventoryServiceImpl implements InventoryService {

    private static final Logger LOG = LoggerFactory.getLogger(InventoryController.class);
    private final BranchRepository branchRepository;
    private final InventoryRepository inventoryRepository;
    private final OrderPositionValidatedProducer validatedProducer;
    private final WarehouseClient warehouseClient;

    CompletableFuture<ArticleDto> articleDtoCompletableFuture;

    public InventoryServiceImpl(
            BranchRepository branchRepository,
            InventoryRepository repository,
            OrderPositionValidatedProducer validatedProducer,
            WarehouseClient warehouseClient
    ) {
        this.branchRepository = branchRepository;
        this.inventoryRepository = repository;
        this.validatedProducer = validatedProducer;
        this.warehouseClient = warehouseClient;
    }

    @Override
    public List<StockDto> getInventory() {
        Subscriber<List<ArticleDto>> articleDtoSubscriber = getSubscriber();

        List<Stock> stocks = inventoryRepository.findAll();
        if (stocks.isEmpty()) return Collections.emptyList();

        stocks.forEach(stock -> {
            articleDtoCompletableFuture = new CompletableFuture<>();
            warehouseClient.getArticleFromWarehouse(stock.getArticleNumber()).subscribe(articleDtoSubscriber);
            try {
                ArticleDto articleDto = articleDtoCompletableFuture.get();
                stock.setArticle(articleDto);
            } catch (InterruptedException | ExecutionException exception) {
                LOG.error(exception.getMessage());
            }
        });

        return stocks.stream().map(StockMapper::toDto).toList();
    }

    @Override
    public Optional<StockDto> findStockByArticleNumber(String articleNumber) {
        Subscriber<List<ArticleDto>> articleDtoSubscriber = getSubscriber();

        Optional<Stock> stock = inventoryRepository.findByArticleNumber(articleNumber);
        if (stock.isEmpty()) return Optional.empty();

        articleDtoCompletableFuture = new CompletableFuture<>();
        warehouseClient.getArticleFromWarehouse(stock.get().getArticleNumber()).subscribe(articleDtoSubscriber);
        try {
            ArticleDto articleDto = articleDtoCompletableFuture.get();
            stock.get().setArticle(articleDto);
        } catch (InterruptedException | ExecutionException exception) {
            LOG.error(exception.getMessage());
        }

        return stock.map(StockMapper::toDto);
    }

    @Override
    public Optional<StockDto> updateStockByArticleNumber(String articleNumber, StockDto stockDto) {
        // return if Stock isn't found
        Optional<Stock> findStock = inventoryRepository.findByArticleNumber(articleNumber);
        if (findStock.isEmpty()) return Optional.empty();

        Stock stockToUpdate = findStock.get();
        Stock stockChanges = StockMapper.toEntity(stockDto);

//        if (stockChanges.getStockDescription() != null) {
//            stockToUpdate.get().get().setStockDescription(...);
//        }
        if (stockChanges.getStock() != null) {
            stockToUpdate.setStock(stockChanges.getStock());
        }
        if (stockChanges.getMinimalStock() != null) {
            stockToUpdate.setMinimalStock(stockChanges.getMinimalStock());
        }

        inventoryRepository.update(stockToUpdate);
        return Optional.of(StockMapper.toDto(stockToUpdate));
    }

    @Override
    public void validateOrderPosition(ValidateOrderPositionDto validateDto) {
        log.info("RMQ: Validating position for article '{}' of order '{}'", validateDto.articleNumber(), validateDto.orderNumber());

        Optional<Stock> repositoryStock = inventoryRepository.findByArticleNumber(validateDto.articleNumber());

        if (repositoryStock.isEmpty()) {
            log.info("RMQ: Article '{}' of order '{}' not found therefore validated as faulty.", validateDto.articleNumber(), validateDto.orderNumber());

            ValidatedOrderPositionDto validatedDto =
                    OrderPositionMapper.toValidatedDto(validateDto, OrderPositionState.FAULTY);
            validatedProducer.produceFaulty(validatedDto);
            return;
        }

        Stock stockEntity = repositoryStock.get();
        if (!stockEntity.getBranchNumber().equals(validateDto.branchNumber())) {
            log.info("RMQ: Article '{}' of order '{}' for branch {} does not exist.", validateDto.articleNumber(), validateDto.orderNumber(), validateDto.branchNumber());

            ValidatedOrderPositionDto validatedDto =
                    OrderPositionMapper.toValidatedDto(validateDto, OrderPositionState.FAULTY);
            validatedProducer.produceFaulty(validatedDto);
            return;
        }
        OrderPositionState resultState = stockEntity.shipArticle(validateDto.amount());

        switch (resultState) {
            case READY:
                log.info("RMQ: Article '{}' of order '{}' validated as ready.", validateDto.articleNumber(), validateDto.orderNumber());

                ValidatedOrderPositionDto readyDto =
                        OrderPositionMapper.toValidatedDto(validateDto, OrderPositionState.READY, (float)stockEntity.getArticle().articlePrice());
                validatedProducer.produceReady(readyDto);
                break;
            case REORDERED:
                log.info("RMQ: Article '{}' of order '{}' has to be reordered therefore marked as reordered.", validateDto.articleNumber(), validateDto.orderNumber());

                ValidatedOrderPositionDto validatedDto =
                        OrderPositionMapper.toValidatedDto(validateDto, OrderPositionState.REORDERED, (float)stockEntity.getArticle().articlePrice());
                validatedProducer.produceReordered(validatedDto);

                ReorderDto reorderDto = new ReorderDto(stockEntity.getArticleNumber(), stockEntity.getReservedStock().intValue(), "çan yücel");
                validatedProducer.produceReorderAtWarehouse(reorderDto);

                LogDto logDto = LogMapper.toDto(validateDto.orderNumber(), "REORDER ARTICLE: order amount exceeds local stock");
                validatedProducer.produceReorderLog(logDto);
                break;
        }

        if (stockEntity.getStock().intValue() <= stockEntity.getMinimalStock().intValue()) {
            ReorderDto reorderDto = new ReorderDto(stockEntity.getArticleNumber(), stockEntity.getMinimalStock().intValue() * 3, "çan yücel");
            validatedProducer.produceReorderAtWarehouse(reorderDto);

            LogDto logDto = LogMapper.toDto(validateDto.orderNumber(), "REORDER ARTICLE: stock amount below minimal stock");
            validatedProducer.produceReorderLog(logDto);
        }

        inventoryRepository.update(stockEntity);
    }

    public void createStock(StockDto stockDto) {
        Optional<Branch> branch = branchRepository.findByBranchNumber(stockDto.branchNumber());

        if (branch.isEmpty())
            throw new IllegalArgumentException("Stock cannot be checked when branch is not recognised.");

        inventoryRepository.save(StockMapper.toEntity(stockDto));
    }

    private Subscriber<List<ArticleDto>> getSubscriber()  {
        return new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(Long.MAX_VALUE);
            }
            @Override
            public void onNext(List<ArticleDto> dto) {
                articleDtoCompletableFuture.complete(dto.getFirst());
            }
            @Override
            public void onError(Throwable throwable) {
                articleDtoCompletableFuture.completeExceptionally(throwable);
            }
            @Override
            public void onComplete() {
                return;
            }
        };
    }
}