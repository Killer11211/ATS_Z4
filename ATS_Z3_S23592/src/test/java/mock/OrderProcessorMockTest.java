package mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.InventoryService;
import service.OrderProcessor;
import service.PaymentService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderProcessorMockTest {

    private InventoryService inventoryService;
    private PaymentService paymentService;
    private OrderProcessor orderProcessor;

    @BeforeEach
    void setup() {
        inventoryService = mock(InventoryService.class);
        paymentService = mock(PaymentService.class);
        orderProcessor = new OrderProcessor(inventoryService, paymentService);
    }

    @Test
    void testPlaceOrderSuccess() {
        when(inventoryService.isProductAvailable("product123", 2)).thenReturn(true);
        when(paymentService.processPayment("product123", 2)).thenReturn(true);
        when(inventoryService.reserveProduct("product123", 2)).thenReturn(true);
        assertTrue(orderProcessor.placeOrder("product123", 2));
    }

    @Test
    void testPlaceOrderUnavailable() {
        when(inventoryService.isProductAvailable("product123", 2)).thenReturn(false);
        assertFalse(orderProcessor.placeOrder("product123", 2));
    }

    @Test
    void testCancelOrder() {
        assertTrue(orderProcessor.cancelOrder("product123"));
    }

    @Test
    void testModifyOrder() {
        when(inventoryService.isProductAvailable("product123", 1)).thenReturn(true);
        when(paymentService.processPayment("product123", 1)).thenReturn(true);
        when(inventoryService.reserveProduct("product123", 1)).thenReturn(true);
        assertTrue(orderProcessor.modifyOrder("product123", 1));
    }

    @Test
    void testIsOrderConfirmed() {
        when(inventoryService.isProductAvailable("product123", 1)).thenReturn(true);
        when(paymentService.processPayment("product123", 1)).thenReturn(true);
        when(inventoryService.reserveProduct("product123", 1)).thenReturn(true);
        orderProcessor.placeOrder("product123", 1);
        assertTrue(orderProcessor.isOrderConfirmed("product123"));
    }
}
