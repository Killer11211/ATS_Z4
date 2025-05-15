package service;

public class PaymentService {
    public boolean processPayment(String productId, int quantity) {
        try {
            Thread.sleep(1000); // symulacja opóźnienia
        } catch (InterruptedException ignored) {}
        return Math.random() > 0.1;
    }
}
