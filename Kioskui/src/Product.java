public class Product {
    private String name;
    private int id;
    private int quantity;
    private double price;


    public Product(String name,int id, int quantity, double price) {
        this.name = name;
        this.id = id;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }


    public double getPrice() {
        return price;
    }

    public int restock(int qty){
        if (qty <= 0){
            return this.quantity;
        }
        else {
            quantity = qty + quantity;
            return quantity;
        }
    }

    @Override
    public String toString() {
        return name + " (ID: " + id + "), " + " Quantity: " + quantity + ", Price: P" + String.format("%.2f", price);
    }
}
