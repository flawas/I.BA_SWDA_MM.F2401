package ch.hslu.swda.api;

import ch.hslu.swda.api.services.OrderService;
import ch.hslu.swda.api.services.OrderServiceImpl;
import ch.hslu.swda.data.dto.order.AddressDto;
import ch.hslu.swda.data.dto.order.OrderDto;
import ch.hslu.swda.data.dto.order.OrderPositionDto;
import ch.hslu.swda.data.entities.order.OrderPositionState;
import ch.hslu.swda.data.entities.order.OrderState;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.Unirest;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MicronautTest
public final class OrderControllerIT {

    private static final int PORT = 9090;
    private static final String RAW_URL = "http://%s:%s/api/order/orders";
    private static final String URL = String.format(RAW_URL, "localhost", PORT);

    @Inject
    OrderService orderService;

    @Test
    @Ignore
    public void testOrderController_getOrders_shouldReturnCollectionOfOrders() {

        // Arrange

        when(orderService.getAll())
            .then(invocation -> List.of(
                    new OrderDto(
                            "A",
                        "indirizzo@mail.com",
                        "venditore@mail.com",
                        new AddressDto("indi", "rizzo", "strada 13", "", "", "posizione", "6969"),
                        new AddressDto("indi", "rizzo", "strada 13", "", "", "posizione", "6969"),
                        List.of(
                                new OrderPositionDto(1, "wlan cavo", Optional.empty(), OrderPositionState.UNKNOWN),
                                new OrderPositionDto(2, "tastiera", Optional.empty(), OrderPositionState.UNKNOWN)
                        ),
                        Instant.now(),
                        OrderState.RECEIVED
                    ),
                    new OrderDto(
                            "B",
                            "indirizzo@mail.com",
                            "venditore@mail.com",
                            new AddressDto("indi", "rizzo", "strada 13", "", "", "posizione", "6969"),
                            new AddressDto("indi", "rizzo", "strada 13", "", "", "posizione", "6969"),
                            List.of(
                                    new OrderPositionDto(1, "wlan cavo", Optional.empty(), OrderPositionState.UNKNOWN),
                                    new OrderPositionDto(2, "tastiera", Optional.empty(), OrderPositionState.UNKNOWN)
                            ),
                            Instant.now(),
                            OrderState.RECEIVED
                    )
                )
            );

        // Act
        final HttpResponse<OrderDto[]> getResponse = Unirest.get(URL).asObject(OrderDto[].class);
        final OrderDto[] testee = getResponse.getBody();

        // Assert
        assertAll("Order",
                () -> assertThat(testee[0].number()).isNotBlank(),
                () -> assertThat(testee[0].customerEmail()).isEqualTo("indirizzo@mail.com"),
                () -> assertThat(testee[0].sellerEmail()).isEqualTo("venditore@mail.com"),
                () -> assertThat(testee[0].shippingAddress().address1()).isEqualTo("strada 13"),
                () -> assertThat(testee[0].billingAddress().address1()).isEqualTo("strada 13"),
                () -> assertThat(testee[0].positions()).hasSize(2),
                () -> assertThat(testee[0].state()).isEqualTo(OrderState.RECEIVED)
        );
    }

    @MockBean(OrderServiceImpl.class)
    OrderService orderService() {
        return mock(OrderService.class);
    }
}
