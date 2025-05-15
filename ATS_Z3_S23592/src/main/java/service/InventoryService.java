package service;

import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class InventoryService {
    private final String filePath;

    public InventoryService(String filePath) {
        this.filePath = filePath;
    }

    public boolean isProductAvailable(String productId, int quantity) {
        try {
            Thread.sleep(1000); // symulacja opóźnienia
        } catch (InterruptedException ignored) {}
        JSONObject inv = readInventory();
        return inv.optInt(productId, 0) >= quantity;
    }

    public boolean reserveProduct(String productId, int quantity) {
        JSONObject inv = readInventory();
        int current = inv.optInt(productId, 0);
        if (current >= quantity) {
            inv.put(productId, current - quantity);
            writeInventory(inv);
            return true;
        }
        return false;
    }

    private JSONObject readInventory() {
        try {
            return new JSONObject(new String(Files.readAllBytes(Paths.get(filePath))));
        } catch (IOException e) {
            return new JSONObject();
        }
    }

    private void writeInventory(JSONObject inventory) {
        try {
            Files.write(Paths.get(filePath), inventory.toString(4).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
