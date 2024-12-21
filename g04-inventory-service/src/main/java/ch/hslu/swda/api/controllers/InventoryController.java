package ch.hslu.swda.api.controllers;

import ch.hslu.swda.api.dto.StockDto;
import ch.hslu.swda.api.services.InventoryService;
import io.micronaut.core.version.annotation.Version;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Controller()
@ExecuteOn(TaskExecutors.IO)
public final class InventoryController {

    private static final Logger LOG = LoggerFactory.getLogger(InventoryController.class);
    private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }

    @Get("/inventory")
    @Version("1")
    @Produces(MediaType.APPLICATION_JSON)
    public List<StockDto> getInventory() {
        final List<StockDto> inventory = service.getInventory();
        LOG.info("API: Returning whole inventory of size {}", inventory.size());
        return inventory;
    }

    @Get("/stock/{article_number}")
    @Version("1")
    @Produces(MediaType.APPLICATION_JSON)
    public StockDto getStock(@PathVariable("article_number") final String number) {
        final Optional<StockDto> stockDto = service.findStockByArticleNumber(number);
        if (stockDto.isEmpty()) {
            LOG.warn("API: No stock found for {} stock number", number);
            return null;
        }

        LOG.info("API: Returning stock for {} stock number", number);
        return stockDto.get();
    }

    @Put("/stock/{article_number}")
    @Version("1")
    @Produces(MediaType.APPLICATION_JSON)
    public StockDto updateStock(
            @PathVariable("article_number") final String number,
            @Body final StockDto stock)
    {
        final Optional<StockDto> stockDto = service.updateStockByArticleNumber(number, stock);
        if (stockDto.isEmpty()) {
            LOG.warn("API: No stock found for {} stock number", number);
            return null;
        }

        LOG.info("API: Returning updated stock for {} stock number", number);
        return stockDto.get();
    }

    @Post("/inventory")
    @Version("1")
    public void creatStock(@Body final StockDto stock) {
        service.createStock(stock);
    }
}
