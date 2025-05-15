package app;

import service.InventoryService;
import service.PaymentService;
import service.OrderProcessor;

public class Application {
    public static void main(String[] args) {
        InventoryService inventoryService = new InventoryService("data/inventory.json");
        PaymentService paymentService = new PaymentService();
        OrderProcessor orderProcessor = new OrderProcessor(inventoryService, paymentService);

        orderProcessor.placeOrder("product123", 2);
    }
}
