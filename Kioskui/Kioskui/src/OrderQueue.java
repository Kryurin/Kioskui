import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

public class OrderQueue {

    //  Changed from LinkedList to Queue
    private final Queue<Order> queue = new LinkedList<>();
    private int maxSize = 10;

    public OrderQueue(int maxSize) {
        this.maxSize = Math.max(1, maxSize);
    }

    public synchronized boolean addOrder(Order order) {
        if (queue.size() >= maxSize) return false;
        queue.offer(order); // Queue-style add
        return true;
    }

    public synchronized Order deliverFirstOrder() {
        return queue.poll(); // Queue-style remove (returns null if empty)
    }

    public synchronized List<Order> getAllOrders() {
        return new ArrayList<>(queue);
    }

    public synchronized int size() {
        return queue.size();
    }

    // Backwards-compatible helper used by UI code
    public synchronized int getQueueSize() {
        return queue.size();
    }

    public synchronized int getMaxSize() {
        return maxSize;
    }

    public synchronized void setMaxSize(int maxSize) {
        this.maxSize = Math.max(1, maxSize);

        while (queue.size() > this.maxSize) {
            queue.poll();
        }
    }

    public synchronized void clear() {
        queue.clear();
    }
}
