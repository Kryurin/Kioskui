import java.util.*;

public class OrderQueue {
    private Queue<Order> queue;
    private int maxSize;

    public OrderQueue(int maxSize) {
        this.queue = new LinkedList<>();
        this.maxSize = maxSize;
    }

    public synchronized boolean addOrder(Order order) {
        if (queue.size() < maxSize) {
            queue.add(order);
            notifyAll();
            return true;
        }
        return false;
    }

    public synchronized Order getFirstOrder() {
        return queue.peek();
    }

    public synchronized Order deliverFirstOrder() {
        Order order = queue.poll();
        notifyAll();
        return order;
    }

    public synchronized List<Order> getAllOrders() {
        return new ArrayList<>(queue);
    }

    public synchronized int getQueueSize() {
        return queue.size();
    }

    public synchronized void setMaxSize(int newMaxSize) {
        this.maxSize = newMaxSize;
    }

    public synchronized int getMaxSize() {
        return maxSize;
    }

    public synchronized boolean isFull() {
        return queue.size() >= maxSize;
    }

    public synchronized void clear() {
        queue.clear();
    }
}
