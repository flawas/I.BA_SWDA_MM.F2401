package ch.hslu.swda.api.services;

import ch.hslu.swda.data.dto.CreateOrderDto;
import ch.hslu.swda.data.dto.OrderDto;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing order-related operations.
 */
public interface OrderService {

    /**
     * Retrieves all orders.
     *
     * @return A list of orders (OrderDto).
     */
    List<OrderDto> getAll();

    /**
     * Creates a new order.
     *
     * @param orderDto The details of the order to be created (CreateOrderDto).
     * @return An optional containing the created order (OrderDto), or empty if creation failed.
     */
    Optional<OrderDto> create(final CreateOrderDto orderDto);
}
