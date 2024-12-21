package ch.hslu.swda.api.controllers;

import ch.hslu.swda.stock.api.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@Disabled
class WarehouseControllerTest {

    private Stock stockService;
    private WarehouseController warehouseController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        //warehouseController = new WarehouseController(articleService, reservationService);
    }


    @Test
    void createOrder() {
    }

    @Test
    void getReservations() {
    }

    @Test
    void getSpecificReservations() {
    }

    @Test
    void createReservation() {
    }

    @Test
    void deleteReservation() {
    }
}