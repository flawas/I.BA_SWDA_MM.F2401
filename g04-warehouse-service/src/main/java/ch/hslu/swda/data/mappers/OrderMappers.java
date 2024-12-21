package ch.hslu.swda.data.mappers;

import ch.hslu.swda.data.dto.CreateOrderDto;
import ch.hslu.swda.data.dto.OrderDto;
import ch.hslu.swda.data.entities.Order;

/**
 * Mapper class for converting between Order and CreateOrderDto/OrderDto.
 */
public class OrderMappers {

    /**
     * Converts a CreateOrderDto object to an Order entity.
     *
     * @param orderDto The CreateOrderDto object to convert.
     * @return The corresponding Order entity.
     */
    public static Order toEntity(final CreateOrderDto orderDto) {
        if (orderDto == null)
            return null;

        return new Order(
                orderDto.articleNumber(),
                orderDto.articleCount(),
                orderDto.orderContact()
        );
    }

    /**
     * Converts an Order entity to an OrderDto object.
     *
     * @param order The Order entity to convert.
     * @return The corresponding OrderDto object.
     */
    public static OrderDto toDto(final Order order) {
        if (order == null)
            return null;

        return new OrderDto(
                order.getOrderNumber(),
                order.getArticleNumber(),
                order.getArticleCount(),
                order.getArticleDeliveryDate(),
                order.getOrderDate(),
                order.getOrderContact()
        );
    }
}
