package ch.hslu.swda.api.services;

import ch.hslu.swda.data.dto.CreateLogDto;
import ch.hslu.swda.data.dto.CreateOrderDto;
import ch.hslu.swda.data.dto.OrderDto;
import ch.hslu.swda.data.entities.Service;
import ch.hslu.swda.data.mappers.OrderMappers;
import ch.hslu.swda.api.repository.OrderRepository;
import ch.hslu.swda.messaging.producers.BusinessEventLogProducer;
import jakarta.inject.Singleton;
import org.bson.types.ObjectId;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the OrderService interface.
 */
@Singleton
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final BusinessEventLogProducer businessEventLogProducer;

    /**
     * Constructor for OrderServiceImpl.
     *
     * @param repository The OrderRepository to be injected.
     */
    public OrderServiceImpl(OrderRepository repository, BusinessEventLogProducer businessEventLogProducer) {
        this.repository = repository;
        this.businessEventLogProducer = businessEventLogProducer;
    }

    /**
     * Retrieves all orders.
     *
     * @return A list of orders (OrderDto).
     */
    @Override
    public List<OrderDto> getAll() {
        return repository.findAll().stream()
                .map(OrderMappers::toDto)
                .toList();
    }

    /**
     * Creates a new order.
     *
     * @param orderDto The details of the order to be created (CreateOrderDto).
     * @return An optional containing the created order (OrderDto), or empty if creation failed.
     */
    @Override
    public Optional<OrderDto> create(CreateOrderDto orderDto) {
        businessEventLogProducer.produce(
                new CreateLogDto(
                        Instant.now(),
                        null,
                        Service.WAREHOUSE,
                        null,
                        "Created order of " + orderDto.toString()
                ));
        return Optional.ofNullable(OrderMappers.toDto(repository.save(OrderMappers.toEntity(orderDto))));
    }
}
