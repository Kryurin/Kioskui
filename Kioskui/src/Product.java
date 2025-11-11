public class Product {
    protected String name;
    protected String type;
    protected String id;
    protected double price;

    public Product(String name, String type, String id, double price) {
        this.name = name;
        this.type = type;
        this.id = id;
        this.price = price;
    }

    public String getName() { return name; }
    public String getType() { return type; }
    public String getId() { return id; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return type + ": " + name + " (ID: " + id + ", Price: P" + String.format("%.2f", price) + ")";
    }
}
