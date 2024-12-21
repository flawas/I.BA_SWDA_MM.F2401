package ch.hslu.swda.api.controllers;

import ch.hslu.swda.api.services.OrderService;
import ch.hslu.swda.api.services.StockServiceImpl;
import ch.hslu.swda.data.dto.CreateOrderDto;
import ch.hslu.swda.data.dto.OrderDto;
import ch.hslu.swda.data.dto.StockArticleDto;
import io.micronaut.http.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllOrders() {
        // Arrange
        List<OrderDto> mockOrders = new ArrayList<>();
        when(orderService.getAll()).thenReturn(mockOrders);

        // Act
        HttpResponse<List<OrderDto>> response = orderController.getAllOrders();

        // Assert
        assertEquals(mockOrders, response.body());
        assertEquals(200, response.code());
    }

    @Disabled
    void testCreateOrder() {
        // Arrange
        CreateOrderDto createOrderDto = new CreateOrderDto(123456, 2, "Flavio");
        StockArticleDto articleDto = new StockArticleDto(123456, 50, LocalDate.of(2024, 04, 19), 19.29);
        OrderDto mockOrder = new OrderDto(20000, 123456, 50, LocalDate.now(), LocalDate.of(2024, 04, 19), "Flavio");
        when(orderService.create(createOrderDto)).thenReturn(Optional.of(mockOrder));

        // Act
        HttpResponse<?> response = orderController.createOrder(createOrderDto);

        // Assert
        assertEquals(mockOrder, response.body());
        assertEquals(201, response.code());
    }
}