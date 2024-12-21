package ch.hslu.swda.data;

import ch.hslu.swda.data.entities.order.Address;
import ch.hslu.swda.data.entities.order.Order;
import ch.hslu.swda.data.entities.order.OrderPosition;
import ch.hslu.swda.data.entities.order.OrderState;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class OrderTest {

    @Test
    final void testOrder_whenConstructing_attributesSet() {
        final Order testee = new Order(
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

        assertAll("Order",
                () -> assertThat(testee.getNumber()).isEqualTo("FBS24_12345"),
                () -> assertThat(testee.getCustomerEmail()).isEqualTo("indirizzo@mail.com"),
                () -> assertThat(testee.getSellerEmail()).isEqualTo("venditore@mail.com"),
                () -> assertThat(testee.getShippingAddress().getAddress1()).isEqualTo("strada 13"),
                () -> assertThat(testee.getBillingAddress().getAddress1()).isEqualTo("strada 13"),
                () -> assertThat(testee.getPositions()).hasSize(2),
                () -> assertThat(testee.getState()).isEqualTo(OrderState.RECEIVED)
            );
    }

    @Test
    final void testOrder_whenConstructing_attributesSetAndAutoFilled() {
        final Order testee = new Order(
                "indirizzo@mail.com",
                "venditore@mail.com",
                new Address("indi", "rizzo", "strada 13", "", "", "posizione", "6969"),
                new Address("indi", "rizzo", "strada 13", "", "", "posizione", "6969"),
                List.of(
                        new OrderPosition(1, "wlan cavo"),
                        new OrderPosition(2, "tastiera")
                )
        );

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
    final void testOrder_EqualsVerifier() {
        EqualsVerifier
                .forClass(Order.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }
}
