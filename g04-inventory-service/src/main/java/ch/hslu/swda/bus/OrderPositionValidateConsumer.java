package ch.hslu.swda.bus;

import ch.hslu.swda.api.dto.ValidateOrderPositionDto;
import ch.hslu.swda.api.services.InventoryService;
import io.micronaut.rabbitmq.annotation.Queue;
import io.micronaut.rabbitmq.annotation.RabbitListener;

import java.util.logging.Logger;

@RabbitListener
public class OrderPositionValidateConsumer {
    private static final Logger LOG = Logger.getLogger(OrderPositionValidateConsumer.class.getName());
    private final InventoryService inventoryService;

    public OrderPositionValidateConsumer(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Queue("ValidatePositionQ")
    public void consume(final ValidateOrderPositionDto validateOrderPositionDto) {
        LOG.info("Consuming OrderPositionValidateDto: " + validateOrderPositionDto);
        inventoryService.validateOrderPosition(validateOrderPositionDto);
    }
}
