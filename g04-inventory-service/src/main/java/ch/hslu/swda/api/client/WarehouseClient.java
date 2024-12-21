package ch.hslu.swda.api.client;

import ch.hslu.swda.api.dto.ArticleDto;
import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;
import org.reactivestreams.Publisher;

import java.util.List;

import static io.micronaut.http.HttpHeaders.ACCEPT;

@Client(id = "backbone://warehouse-service:8090/api/warehouse/products")
@Header(name = ACCEPT, value = MediaType.APPLICATION_JSON)
public interface WarehouseClient {

    @Get("/{articleNumber}")
    @SingleResult
    Publisher<List<ArticleDto>> getArticleFromWarehouse(
            @PathVariable("articleNumber") final String articleNumber
    );
}
