import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
public class FlashSaleManager{
    private ConcurrentHashMap<String, AtomicInteger> inventory = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, ConcurrentLinkedQueue<Integer>> waitingLists = new ConcurrentHashMap<>();
    public void addProduct(String productId, int initialStock) {
        inventory.put(productId, new AtomicInteger(initialStock));
        waitingLists.put(productId, new ConcurrentLinkedQueue<>());
    }

    public int checkStock(String productId) {
        AtomicInteger stock = inventory.get(productId);
        return (stock != null) ? stock.get() : 0;
    }

    public String purchaseItem(String productId, int userId) {
        AtomicInteger stock = inventory.get(productId);
        if (stock == null) return "Product not found.";

        int currentStock = stock.getAndUpdate(val -> val > 0 ? val - 1 : val);

        if (currentStock > 0) {
            return "Success! User " + userId + " purchased " + productId + ". Units remaining: " + (currentStock - 1);
        } else {
            waitingLists.get(productId).add(userId);
            int position = waitingLists.get(productId).size();
            return "Sold Out! User " + userId + " added to waiting list at position #" + position;
        }
    }

    public static void main(String[] args) {
        FlashSaleManager system = new FlashSaleManager();
        String product = "IPHONE15_256GB";

        system.addProduct(product, 2);

        System.out.println("Initial Stock: " + system.checkStock(product));
        System.out.println(system.purchaseItem(product, 12345));
        System.out.println(system.purchaseItem(product, 67890));
        System.out.println(system.purchaseItem(product, 99999));
    }
}
