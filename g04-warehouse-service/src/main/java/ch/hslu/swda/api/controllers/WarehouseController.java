package ch.hslu.swda.api.controllers;

import ch.hslu.swda.api.services.StockServiceImpl;
import ch.hslu.swda.data.dto.StockArticleDto;
import io.micronaut.core.version.annotation.Version;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Controller for handling warehouse operations.
 */
@Controller("/products")
@ExecuteOn(TaskExecutors.IO)
public class WarehouseController {
    private static final Logger LOG = LoggerFactory.getLogger(WarehouseController.class);

    @Inject
    StockServiceImpl stockService;

    /**
     * Retrieves all articles from the warehouse.
     *
     * @return HttpResponse containing a list of articles (StockArticleDto).
     */
    @Get()
    @Version("1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned all articles.")
    })
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Article")
    public HttpResponse<List<StockArticleDto>> getAllArticle() {
        final List<StockArticleDto> articles = stockService.getAll();
        LOG.info("API: Returning all {} articles.", articles.size());
        return HttpResponse.ok(articles);
    }

    /**
     * Retrieves a specific article from the warehouse by its article number.
     *
     * @param articleNumber The article number of the desired article.
     * @return HttpResponse containing the requested article (StockArticleDto).
     */
    @Get("/{articleNumber}")
    @Version("1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned the article.")
    })
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Article")
    public HttpResponse<?> getStockArticle(@PathVariable final int articleNumber) {
        stockService.getItemCount(articleNumber);
        final List<StockArticleDto> stockArticleDto = stockService.findByArticleNumber(articleNumber);
        LOG.info("API: Returning stockArticle {}", articleNumber);
        return HttpResponse
                .status(HttpStatus.FOUND)
                .body(stockArticleDto);
    }
}
