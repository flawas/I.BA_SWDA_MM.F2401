package ch.hslu.swda.api.services;

import ch.hslu.swda.api.repository.StockRepository;
import ch.hslu.swda.messaging.producers.BusinessEventLogProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class StockServiceImplTest {

    @Mock
    private StockRepository repository;
    @Mock
    StockServiceImpl stockService;
    @Mock
    BusinessEventLogProducer businessEventLogProducer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        stockService = new StockServiceImpl(repository, businessEventLogProducer);
    }
    @Test
    void getItemCount() {
    }

    @Test
    void getItemDeliveryDate() {
    }

    @Test
    void orderItem() {
    }

    @Test
    void reserveItem() {
    }

    @Test
    void orderReservation() {
    }

    @Test
    void freeReservation() {
    }
}