package ch.hslu.swda.data;

import ch.hslu.swda.data.dto.order.*;
import ch.hslu.swda.data.entities.order.Address;
import ch.hslu.swda.data.entities.order.Order;
import ch.hslu.swda.data.entities.order.OrderPosition;
import ch.hslu.swda.data.entities.order.OrderState;
import ch.hslu.swda.data.mappers.OrderMappers;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class OrderMappingTest {

    @Test
    final void testOrderMapping_whenCreateOrderDto_attributesSetInOrderEntity() {

        final CreateOrderDto createOrderDto = new CreateOrderDto(
                "indirizzo@mail.com",
                "venditore@mail.com",
                new AddressDto("indi", "rizzo", "strada 13", "", "", "posizione", "6969"),
                new AddressDto("indi", "rizzo", "strada 13", "", "", "posizione", "6969"),
                List.of(
                        new CreateOrderPositionDto(1, "wlan cavo"),
                        new CreateOrderPositionDto(2, "tastiera")
                )
        );

        final Order testee = OrderMappers.toEntity(createOrderDto);

        assertAll("Order",
                () -> assertThat(testee.getNumber()).isNotBlank(),
                () -> assertThat(testee.getCustomerEmail()).isEqualTo("indirizzo@mail.com"),
                () -> assertThat(testee.getSellerEmail()).isEqualTo("venditore@mail.com"),
                () -> assertThat(testee.getShippingAddress().getAddress1()).isEqualTo("strada 13"),
                () -> assertThat(testee.getBillingAddress().getAddress1()).isEqualTo("strada 13"),
                () -> assertThat(testee.getPositions()).hasSize(2),
                () -> assertThat(testee.getState()).isEqualTo(OrderState.RECEIVED)
            );
    }

    @Test
    final void testOrderMapping_whenOrderEntity_attributesSetInOrderDto() {

        final Order order = new Order(
                "FBS24_12345",
                "indirizzo@mail.com",
                "venditore@mail.com",
                new Address("indi", "rizzo", "strada 13", "", "", "posizione", "6969"),
                new Address("indi", "rizzo", "strada 13", "", "", "posizione", "6969"),
                List.of(
                        new OrderPosition(1, "wlan cavo"),
                        new OrderPosition(2, "tastiera")
                ),
                OrderState.RECEIVED
        );

        final OrderDto testee = OrderMappers.toDto(order);

        assertAll("Order",
                () -> assertThat(testee.number()).isEqualTo("FBS24_12345"),
                () -> assertThat(testee.customerEmail()).isEqualTo("indirizzo@mail.com"),
                () -> assertThat(testee.sellerEmail()).isEqualTo("venditore@mail.com"),
                () -> assertThat(testee.shippingAddress().address1()).isEqualTo("strada 13"),
                () -> assertThat(testee.billingAddress().address1()).isEqualTo("strada 13"),
                () -> assertThat(testee.positions()).hasSize(2),
                () -> assertThat(testee.state()).isEqualTo(OrderState.RECEIVED)
        );
    }
}
