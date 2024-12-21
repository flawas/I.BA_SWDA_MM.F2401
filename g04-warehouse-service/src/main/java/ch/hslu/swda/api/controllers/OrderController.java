package ch.hslu.swda.api.controllers;

import ch.hslu.swda.api.services.OrderService;
import ch.hslu.swda.api.services.StockServiceImpl;
import ch.hslu.swda.data.dto.CreateOrderDto;
import ch.hslu.swda.data.dto.OrderDto;
import io.micronaut.core.version.annotation.Version;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * Controller for handling order operations.
 */
@Controller("/orders")
@ExecuteOn(TaskExecutors.IO)
public class OrderController {
    private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;

    @Inject
    StockServiceImpl stockService;

    /**
     * Constructor for OrderController.
     *
     * @param orderService The OrderService to be injected.
     */
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Retrieves all orders.
     *
     * @return HttpResponse containing a list of orders (OrderDto).
     */
    @Get()
    @Version("1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned all orders.")
    })
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Order")
    public HttpResponse<List<OrderDto>> getAllOrders() {
        final List<OrderDto> orders = orderService.getAll();
        LOG.info("API: Returning all {} orders.", orders.size());
        return HttpResponse.ok(orders);
    }

    /**
     * Creates a new order.
     *
     * @param createOrderDto The details of the order to be created.
     * @return HttpResponse containing the created order (OrderDto).
     */
    @Post()
    @Version("1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created new order.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = OrderDto.class))),
            @ApiResponse(responseCode = "422", description = "The provided attributes are insufficient to create a new order!")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Tag(name = "Order")
    public HttpResponse<?> createOrder(@Body final CreateOrderDto createOrderDto) {
        if (stockService.findByArticleNumber(createOrderDto.articleNumber()).isEmpty()) {
            LOG.error("API: ArticleNumber {} not found!", createOrderDto.articleNumber());
            return HttpResponse.status(HttpStatus.NOT_FOUND);
        }

        final Optional<OrderDto> order = orderService.create(createOrderDto);
        if (order.isEmpty()) {
            LOG.warn("API: Order with articleNumber {}, articleCount {} from customer {} could not be created!",
                    createOrderDto.articleNumber(), createOrderDto.articleCount(), createOrderDto.orderContact());
            return HttpResponse.unprocessableEntity();
        }
        LOG.info("API: Order '{}' was created.", order.get());
        return HttpResponse
                .status(HttpStatus.CREATED)
                .body(order.get());
    }
}
