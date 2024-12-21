package ch.hslu.swda.data.mappers;

import ch.hslu.swda.data.dto.CreateLogDto;
import ch.hslu.swda.data.dto.LogDto;
import ch.hslu.swda.data.dto.OrderStatusChangedDto;
import ch.hslu.swda.data.entities.Log;
import ch.hslu.swda.data.entities.Service;

public class OrderStatusChangedMapper {
    public static Log toEntity(final OrderStatusChangedDto orderStatusChangedDto) {
        if (orderStatusChangedDto == null)
            return null;

        return new Log(
                orderStatusChangedDto.triggeredAt(),
                null,
                Service.ORDER,
                orderStatusChangedDto.orderNumber(),
                "Order " + orderStatusChangedDto.orderNumber() + " is in " + orderStatusChangedDto.state() + ". Seller email: " + orderStatusChangedDto.sellerEmail() + ". Buyer email: " + orderStatusChangedDto.customerEmail()
        );
    }
}