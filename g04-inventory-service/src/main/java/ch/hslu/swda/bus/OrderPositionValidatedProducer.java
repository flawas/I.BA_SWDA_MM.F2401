package ch.hslu.swda.bus;

import ch.hslu.swda.api.dto.LogDto;
import ch.hslu.swda.api.dto.ReorderDto;
import ch.hslu.swda.api.dto.ValidatedOrderPositionDto;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import io.micronaut.rabbitmq.annotation.RabbitProperty;

@RabbitClient("fbs.inventory")
@RabbitProperty(name="contentType", value="application/json")
public interface OrderPositionValidatedProducer {
    @Binding("order.validated-order-position.reordered")
    void produceReordered(final ValidatedOrderPositionDto validatedOrderPositionDto);

    @Binding("order.validated-order-position.faulty")
    void produceFaulty(final ValidatedOrderPositionDto validatedOrderPositionDto);

    @Binding("order.validated-order-position.ready")
    void produceReady(final ValidatedOrderPositionDto validatedOrderPositionDto);

    @Binding("warehouse.reorder")
    void produceReorderAtWarehouse(final ReorderDto reorderDto);

    @Binding("log.reordered")
    void produceReorderLog(final LogDto logDto);
}
