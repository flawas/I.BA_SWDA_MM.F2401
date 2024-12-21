package ch.hslu.swda.micronaut;

import ch.hslu.swda.api.dto.StockDto;
import ch.hslu.swda.api.entities.Stock;
import kong.unirest.core.GenericType;
import kong.unirest.core.Unirest;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@Disabled
@Testcontainers
public class InventoryUnirestIT {

    private static final int PORT = 8090;
    private static final String DOCKER_IMAGE_TAG = "swda-24fs/g04-inventory-service:latest";
    private static final String RAW_URL = "http://%s:%s/api/inventory";
    private static String url = String.format(RAW_URL, "localhost", "8080");

    @Container
    GenericContainer<?> container = new GenericContainer<>(DockerImageName.parse(DOCKER_IMAGE_TAG))
            .withStartupTimeout(Duration.ofSeconds(60))
            .withExposedPorts(PORT);

    @BeforeAll
    static void beforeAll() {
        Unirest.config().retryAfter(false);
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
    public void inventory_getByIdRequest_returnArticleAsJson() {
        final String jsonResponse = Unirest.get(url).asString().getBody();

        System.out.println(jsonResponse);

        assertThat(jsonResponse).contains("""
            "id":"1234"
        """);
    }

    @Test
    public void inventory_getByIdRequest_returnArticleAsDto() {
        final StockDto dto = Unirest.get(url).asObject(StockDto.class).getBody();

        assertThat(dto).isNotNull();
        assertThat(dto.getClass()).isEqualTo(StockDto.class);
    }

    @Test
    public void inventory_getInventory_returnAllArticlesAsInventory() {
        final List<Stock> stocks = Unirest.get(url).asObject(new GenericType<List<Stock>>() {}).getBody();

        assertThat(stocks.size()).isNotZero();
    }
}
