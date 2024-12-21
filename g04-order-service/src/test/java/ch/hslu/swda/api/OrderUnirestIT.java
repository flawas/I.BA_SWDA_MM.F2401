package ch.hslu.swda.api;

import ch.hslu.swda.data.dto.order.*;
import ch.hslu.swda.data.entities.order.OrderState;
import kong.unirest.core.GenericType;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.Unirest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Testcontainers
public final class OrderUnirestIT {

    private static final int PORT = 9090;
    private static final String DOCKER_IMAGE_TAG = "swda-24fs/g04-order-service:latest";
    private static final String RAW_URL = "http://%s:%s/api/order/orders";
    private static String url = String.format(RAW_URL, "localhost", PORT);

    @Container
    GenericContainer<?> container = new GenericContainer<>(DockerImageName.parse(DOCKER_IMAGE_TAG))
            .withStartupTimeout(Duration.ofSeconds(60))
            .withEnv("MICRONAUT_SERVER_PORT", String.valueOf(PORT))
            .withExposedPorts(PORT);

    @BeforeAll
    static void beforeAll() {
        Unirest.config().retryAfter(true, 5);
    }

    @AfterAll
    static void afterAll() {
        Unirest.shutDown();
    }

    @BeforeEach
    void beforeEach() {
        url = String.format(RAW_URL, container.getHost(), container.getMappedPort(PORT));
    }

    @Test
    public void testOrder_postOrderAndGetByNumber_shouldReturnOrder() {
        // Arrange
        final HttpResponse<OrderDto> postResponse = Unirest.post(url).body(
                new CreateOrderDto(
                        "indirizzo@mail.com",
                        "venditore@mail.com",
                        new AddressDto("indi", "rizzo", "strada 13", "", "", "posizione", "6969"),
                        new AddressDto("indi", "rizzo", "strada 13", "", "", "posizione", "6969"),
                        List.of(
                                new CreateOrderPositionDto(1, "wlan cavo"),
                                new CreateOrderPositionDto(2, "tastiera")
                        )
                )
        ).asObject(OrderDto.class);

        // Act
        final HttpResponse<OrderDto> getResponse = Unirest.get(url + postResponse.getBody().number()).asObject(OrderDto.class);
        final OrderDto testee = getResponse.getBody();

        // Assert
        assertAll("Order",
                () -> assertThat(testee.number()).isNotBlank(),
                () -> assertThat(testee.customerEmail()).isEqualTo("indirizzo@mail.com"),
                () -> assertThat(testee.sellerEmail()).isEqualTo("venditore@mail.com"),
                () -> assertThat(testee.shippingAddress().address1()).isEqualTo("strada 13"),
                () -> assertThat(testee.billingAddress().address1()).isEqualTo("strada 13"),
                () -> assertThat(testee.positions()).hasSize(2),
                () -> assertThat(testee.state()).isEqualTo(OrderState.RECEIVED)
        );
    }

    @Test
    public void testOrder_getRequest_shouldReturnAllOrderAsDtoList() {
        // Arrange
        Unirest.post(url).body(
                new CreateOrderDto(
                        "indirizzo@mail.com",
                        "venditore@mail.com",
                        new AddressDto("indi", "rizzo", "strada 13", "", "", "posizione", "6969"),
                        new AddressDto("indi", "rizzo", "strada 13", "", "", "posizione", "6969"),
                        List.of(
                                new CreateOrderPositionDto(1, "wlan cavo"),
                                new CreateOrderPositionDto(2, "tastiera")
                        )
                )
        );

        Unirest.post(url).body(
                new CreateOrderDto(
                        "indirizzo@mail.com",
                        "venditore@mail.com",
                        new AddressDto("indi", "rizzo", "strada 13", "", "", "posizione", "6969"),
                        new AddressDto("indi", "rizzo", "strada 13", "", "", "posizione", "6969"),
                        List.of(
                                new CreateOrderPositionDto(1, "wlan cavo"),
                                new CreateOrderPositionDto(2, "tastiera")
                        )
                )
        );

        // Act
        final HttpResponse<List<OrderDto>> getResponse = Unirest.get(url).asObject(new GenericType<List<OrderDto>>(){});
        final List<OrderDto> testee = getResponse.getBody();

        // Assert
        assertThat(testee.size()).isEqualTo(2);
    }
}
