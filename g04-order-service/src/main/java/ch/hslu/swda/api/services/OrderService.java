package ch.hslu.swda.api.services;

import ch.hslu.swda.data.dto.messaging.commands.checkCustomer.CheckedCustomerPaymentDto;
import ch.hslu.swda.data.dto.order.CreateOrderDto;
import ch.hslu.swda.data.dto.order.OrderDto;
import ch.hslu.swda.data.dto.messaging.commands.validateOrder.ValidatedOrderPositionDto;
import io.micronaut.core.annotation.NonNull;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<OrderDto> getAll();
    Optional<OrderDto> findByNumber(@NonNull final String orderNumber);
    Optional<OrderDto> create(@NonNull final CreateOrderDto orderDto);
    void handleOrderPositionValidated(@NonNull final ValidatedOrderPositionDto validatedOrderPositionDto);
    void handleCustomerPaymentChecked(@NonNull final CheckedCustomerPaymentDto checkedCustomerPaymentDto);
}
