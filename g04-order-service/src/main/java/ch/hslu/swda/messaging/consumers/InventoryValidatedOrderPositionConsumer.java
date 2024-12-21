package ch.hslu.swda.messaging.consumers;

import ch.hslu.swda.api.services.OrderService;
import ch.hslu.swda.data.dto.messaging.commands.validateOrder.ValidatedOrderPositionDto;
import ch.hslu.swda.data.entities.order.OrderPositionState;
import io.micronaut.rabbitmq.annotation.Queue;
import io.micronaut.rabbitmq.annotation.RabbitListener;
import lombok.extern.slf4j.Slf4j;

@RabbitListener
@Slf4j
public final class InventoryValidatedOrderPositionConsumer {

    private final OrderService orderService;

    public InventoryValidatedOrderPositionConsumer(final OrderService orderService) {
        this.orderService = orderService;
    }

    // TODO-go: Fix enum deserialization.
    /*@Queue("ValidatedPositionQ")
    public void consume(final ValidatedOrderPositionDto validatedOrderPosition) {
        log.info("MQ-CONSUMER: Consumed order position validated event (article '{}' of order '{}' was validated with state '{}').",
                validatedOrderPosition.articleNumber(),
                validatedOrderPosition.orderNumber(),
                validatedOrderPosition.state());

        orderService.handleOrderPositionValidated(validatedOrderPosition);
    }*/

    @Queue("ValidatedPositionReorderedQ")
    public void consumeReordered(final ValidatedOrderPositionDto validatedOrderPosition) {
        log.info("MQ-CONSUMER: Consumed order position validated event (article '{}' of order '{}' was validated with state 'reordered').",
                validatedOrderPosition.articleNumber(),
                validatedOrderPosition.orderNumber());

        orderService.handleOrderPositionValidated(new ValidatedOrderPositionDto(
                validatedOrderPosition.orderNumber(),
                validatedOrderPosition.articleNumber(),
                validatedOrderPosition.price(),
                OrderPositionState.REORDERED
        ));
    }

    @Queue("ValidatedPositionFaultyQ")
    public void consumeFaulty(final ValidatedOrderPositionDto validatedOrderPosition) {
        log.info("MQ-CONSUMER: Consumed order position validated event (article '{}' of order '{}' was validated with state 'faulty').",
                validatedOrderPosition.articleNumber(),
                validatedOrderPosition.orderNumber());

        orderService.handleOrderPositionValidated(new ValidatedOrderPositionDto(
                validatedOrderPosition.orderNumber(),
                validatedOrderPosition.articleNumber(),
                validatedOrderPosition.price(),
                OrderPositionState.FAULTY
        ));
    }

    @Queue("ValidatedPositionReadyQ")
    public void consumeReady(final ValidatedOrderPositionDto validatedOrderPosition) {
        log.info("MQ-CONSUMER: Consumed order position validated event (article '{}' of order '{}' was validated with state 'ready').",
                validatedOrderPosition.articleNumber(),
                validatedOrderPosition.orderNumber());

        orderService.handleOrderPositionValidated(new ValidatedOrderPositionDto(
                validatedOrderPosition.orderNumber(),
                validatedOrderPosition.articleNumber(),
                validatedOrderPosition.price(),
                OrderPositionState.READY
        ));
    }
}
