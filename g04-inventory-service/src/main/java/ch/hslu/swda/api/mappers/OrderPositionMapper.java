package ch.hslu.swda.api.mappers;

import ch.hslu.swda.api.dto.ValidateOrderPositionDto;
import ch.hslu.swda.api.dto.ValidatedOrderPositionDto;
import ch.hslu.swda.api.entities.OrderPositionState;

import java.util.Optional;

public class OrderPositionMapper {
    public static ValidatedOrderPositionDto toValidatedDto(
            final ValidateOrderPositionDto dto,
            final OrderPositionState state,
            final Optional<Float> price
    ) {
        if (dto == null) {
            return null;
        }

        return new ValidatedOrderPositionDto(
                dto.orderNumber(),
                dto.articleNumber(),
                price,
                state
        );
    }

    public static ValidatedOrderPositionDto toValidatedDto(
            final ValidateOrderPositionDto dto,
            final OrderPositionState state
    ) {
        return toValidatedDto(dto, state, Optional.empty());
    }

    public static ValidatedOrderPositionDto toValidatedDto(
            final ValidateOrderPositionDto dto,
            final OrderPositionState state,
            final Float price
    ) {
        return toValidatedDto(dto, state, Optional.of(price));
    }
}
