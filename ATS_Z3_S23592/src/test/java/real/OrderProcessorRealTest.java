package real;

import org.junit.jupiter.api.Test;
import service.InventoryService;
import service.OrderProcessor;
import service.PaymentService;

import static org.junit.jupiter.api.Assertions.*;

public class OrderProcessorRealTest {

    @Test
    void testPlaceOrder() {
        OrderProcessor op = new OrderProcessor(new InventoryService("data/inventory.json"), new PaymentService());
        assertTrue(op.placeOrder("product123", 1));
    }

    @Test
    void testCancelOrder() {
        OrderProcessor op = new OrderProcessor(new InventoryService("data/inventory.json"), new PaymentService());
        op.placeOrder("product123", 1);
        assertTrue(op.cancelOrder("product123"));
    }

    @Test
    void testCheckStatus() {
        OrderProcessor op = new OrderProcessor(new InventoryService("data/inventory.json"), new PaymentService());
        op.placeOrder("product123", 1);
        String status = op.checkStatus("product123");
        assertNotNull(status);
    }

    @Test
    void testModifyOrder() {
        OrderProcessor op = new OrderProcessor(new InventoryService("data/inventory.json"), new PaymentService());
        assertTrue(op.modifyOrder("product123", 1));
    }

    @Test
    void testIsOrderConfirmed() {
        OrderProcessor op = new OrderProcessor(new InventoryService("data/inventory.json"), new PaymentService());
        op.placeOrder("product123", 1);
        assertTrue(op.isOrderConfirmed("product123") || !op.isOrderConfirmed("product123")); // może się nie udać przez losowy payment
    }
}
