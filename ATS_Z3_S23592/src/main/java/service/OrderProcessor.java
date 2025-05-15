package service;

import java.util.HashMap;
import java.util.Map;

public class OrderProcessor {
    private final InventoryService inventoryService;
    private final PaymentService paymentService;
    private final Map<String, String> orderStatus = new HashMap<>();

    public OrderProcessor(InventoryService inv, PaymentService pay) {
        this.inventoryService = inv;
        this.paymentService = pay;
    }

    public boolean placeOrder(String productId, int quantity) {
        if (!inventoryService.isProductAvailable(productId, quantity)) {
            orderStatus.put(productId, "Unavailable");
            return false;
        }
        if (!paymentService.processPayment(productId, quantity)) {
            orderStatus.put(productId, "Payment Failed");
            return false;
        }
        boolean reserved = inventoryService.reserveProduct(productId, quantity);
        orderStatus.put(productId, reserved ? "Confirmed" : "Reservation Failed");
        return reserved;
    }

    public boolean cancelOrder(String productId) {
        orderStatus.put(productId, "Cancelled");
        return true;
    }

    public String checkStatus(String productId) {
        return orderStatus.getOrDefault(productId, "Not Found");
    }

    public boolean modifyOrder(String productId, int newQuantity) {
        cancelOrder(productId);
        return placeOrder(productId, newQuantity);
    }

    public boolean isOrderConfirmed(String productId) {
        return "Confirmed".equals(orderStatus.get(productId));
    }
}
