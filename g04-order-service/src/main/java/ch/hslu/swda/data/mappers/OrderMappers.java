package ch.hslu.swda.data.mappers;

import ch.hslu.swda.data.dto.order.CreateOrderDto;
import ch.hslu.swda.data.dto.order.OrderDto;
import ch.hslu.swda.data.entities.order.Order;

public final class OrderMappers {

    public static Order toEntity(
            final CreateOrderDto orderDto) {

        if (orderDto == null)
            return null;

        return new Order(
            orderDto.customerEmail(),
            orderDto.sellerEmail(),
            AddressMappers.toEntity(orderDto.shippingAddress()),
            AddressMappers.toEntity(orderDto.billingAddress()),
            orderDto.positions().stream()
                    .map(OrderPositionMappers::toEntity)
                    .toList()
        );
    }

    public static OrderDto toDto(final Order order) {
        if (order == null)
            return null;

        return new OrderDto(
            order.getNumber(),
            order.getCustomerEmail(),
            order.getSellerEmail(),
            AddressMappers.toDto(order.getShippingAddress()),
            AddressMappers.toDto(order.getBillingAddress()),
            order.getPositions().stream()
                    .map(OrderPositionMappers::toDto)
                    .toList(),
            order.getOrderDate(),
            order.getState()
        );
    }
}

// TODO-go: Solve mapping with bean mapping annotation?
/*
public interface OrderMappers {
    @Mapper
    Order toEntity(OrderDto orderDto);

    @Mapper
    OrderDto toDto(Order order);
}
*/