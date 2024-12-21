package ch.hslu.swda.data.mappers;

import ch.hslu.swda.data.dto.order.CreateOrderPositionDto;
import ch.hslu.swda.data.dto.order.OrderPositionDto;
import ch.hslu.swda.data.entities.order.OrderPosition;

public final class OrderPositionMappers {
    public static OrderPosition toEntity(final OrderPositionDto orderPositionDto) {
        if (orderPositionDto == null)
            return null;

        return new OrderPosition(
            orderPositionDto.amount(),
            orderPositionDto.articleNumber(),
            orderPositionDto.price(),
            orderPositionDto.state()
        );
    }

    public static OrderPosition toEntity(final CreateOrderPositionDto orderPositionDto) {
        if (orderPositionDto == null)
            return null;

        return new OrderPosition(
                orderPositionDto.amount(),
                orderPositionDto.articleNumber()
        );
    }

    public static OrderPositionDto toDto(final OrderPosition orderPosition) {
        if (orderPosition == null)
            return null;

        return new OrderPositionDto(
            orderPosition.getAmount(),
            orderPosition.getArticleNumber(),
            orderPosition.getPrice(),
            orderPosition.getState()
        );
    }
}
