package ch.hslu.swda.api.controllers;

import ch.hslu.swda.api.services.OrderService;
import ch.hslu.swda.api.services.UserService;
import ch.hslu.swda.data.dto.order.CreateOrderDto;
import ch.hslu.swda.data.dto.order.OrderDto;
import ch.hslu.swda.data.dto.user.UserDto;
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
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Controller("/orders")
@ExecuteOn(TaskExecutors.IO)
@Slf4j
public final class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    public OrderController(
            final OrderService orderService,
            final UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    /**
     * Get all orders (READ).
     * @return List of orders (OrderDto).
     */
    @Get
    @Version("1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned all orders.")})
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Orders")
    public HttpResponse<List<OrderDto>> getOrders() {
        final List<OrderDto> orders = orderService.getAll();
        log.info("API: Returning all {} orders.", orders.size());
        return HttpResponse.ok(orders); // TODO-go: Is .contentType(MediaType.APPLICATION_JSON) needed here or is @Produces annotation sufficient?
    }

    /**
     * Get order by unique order number (READ).
     * @param number Unique order number.
     * @return Order (OrderDto).
     */
    @Get("/{number}")
    @Version("1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned requested order."),
            @ApiResponse(responseCode = "404", description = "Requested order with provided order number was not found!")})
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Orders")
    public HttpResponse<?> getOrder(@PathVariable("number") final String number) { // TODO-go: Is there a better return than <?>.
        final Optional<OrderDto> order = orderService.findByNumber(number);

        if (order.isEmpty()) {
            log.warn("API: No order found for given order number '{}'!", number);
            return HttpResponse.notFound(String.format("No order found for given order number '%s'!", number));
        }

        log.info("API: Returning order for '{}' order number.", order.get().number());
        return HttpResponse.ok(order.get());
    }

    /**
     * Create new order (CREATE).
     * @param orderDto New order data (CreateOrderDto).
     * @return Created order (OrderDto).
     */
    @Post()
    @Version("1")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created new order.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = OrderDto.class))),
            @ApiResponse(responseCode = "422", description = "The provided attributes are insufficient to create a new order!")})
    @Tag(name = "Orders")
    public HttpResponse<?> createOrder(@Body final CreateOrderDto orderDto) {

        final Optional<UserDto> customer = userService.findByEmail(orderDto.customerEmail());
        final Optional<UserDto> seller = userService.findByEmail(orderDto.sellerEmail());

        if (customer.isEmpty()) {
            log.warn("API: Order could not be created, as given customer with email '{}' was not found!", orderDto.customerEmail());
            return HttpResponse.unprocessableEntity();
        } else if (seller.isEmpty()) {
            log.warn("API: Order could not be created, as given seller with email '{}' was not found!", orderDto.sellerEmail());
            return HttpResponse.unprocessableEntity();
        }

        final Optional<OrderDto> order = orderService.create(orderDto);

        if (order.isEmpty()) {
            log.warn("API: Order for customer '{}' could not be created!", orderDto.customerEmail());
            return HttpResponse.unprocessableEntity(); // TODO-go: Good response type? (https://stackoverflow.com/questions/7939137/what-http-status-code-should-be-used-for-wrong-input)
        }

        log.info("API: Order for customer '{}' was created and given order number '{}'.", order.get().customerEmail(), order.get().number());
        return HttpResponse
                .status(HttpStatus.CREATED)
                .body(order.get());
    }
}
