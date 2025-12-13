import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private static int orderIdCounter = 1000;
    private int orderId;
    private List<String> items;
    private double totalPrice;
    private LocalDateTime createdTime;
    private String status;

    public Order(List<String> items, double totalPrice) {
        this.orderId = orderIdCounter++;
        this.items = new ArrayList<>(items);
        this.totalPrice = totalPrice;
        this.createdTime = LocalDateTime.now();
        this.status = "PENDING";
    }

    public int getOrderId() {
        return orderId;
    }

    public List<String> getItems() {
        return new ArrayList<>(items);
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getItemCount() {
        return items.size();
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return "Order #" + orderId + " [" + createdTime.format(formatter) + "] - " + items.size() + " items - P" + String.format("%.2f", totalPrice);
    }
}