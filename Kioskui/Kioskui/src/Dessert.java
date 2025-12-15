public class Dessert extends Product {
    private String dessertType;
    private String servingSize;

    public Dessert(String name, int quantity, double price, String availability, String dessertType, String servingSize) {
        super(name, quantity, price, availability);
        this.dessertType = dessertType;
        this.servingSize = servingSize;
    }

    public String getDessertType() {
        return dessertType;
    }

    public String getServingSize() {
        return servingSize;
    }

    public void setDessertType(String dessertType) {
        this.dessertType = dessertType;
    }

    public void setServingSize(String servingSize) {
        this.servingSize = servingSize;
    }

    @Override
    public String toListString() {
        return super.toListString() + ", Dessert Type: " + dessertType + ", Serving Size: " + servingSize + "\n";
    }
}
