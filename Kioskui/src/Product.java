public abstract class Product {
    private String name;
    private int id;
    private int quantity;
    private double price;


    public Product(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    protected void setId(int id) {
        this.id = id;
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
        return "ID: " + id + ". Name: " + name + ", Quantity: " + quantity + ", Price: P" + String.format("%.2f", price);
    }
}
