public abstract class Product {
    private String name;
    private int id;
    private int quantity;
    private double price;
    private String availability;

    public Product(String name, int quantity, double price, String availability) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.availability = availability;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public String getAvailability() {
        return availability;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected void setId(int id) {
        this.id= id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public int restock(int productQty){
        if (productQty <= 0){
            return this.quantity;
        }
        else {
            quantity = productQty + quantity;
            return quantity;
        }
    }

    public int reduceQuantity(int soldQty) {
        if (soldQty <= 0) return quantity; // invalid quantity, do nothing
        if (soldQty > quantity) soldQty = quantity; // cannot sell more than available
        quantity -= soldQty;
        return quantity;
    }


    @Override
    public String toString() {
        return toListString();
    }

    public String toListString() {
        return name + " (Qty: " + quantity + ", P" + String.format("%.2f", price) + ")" + ", Availability: " + availability;
    }

}
