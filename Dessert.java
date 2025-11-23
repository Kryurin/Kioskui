public class Dessert extends Product {
    private String dessertType;
    private String servingSize;

    public Dessert(String name, int id, int quantity, double price, String dessertType ,String servingSize) {
        super(name, id, quantity, price);
        this.dessertType = dessertType;
        this.servingSize = servingSize;
    }

    public String getDessertType() {
        return dessertType;
    }

    public String getServingSize() {
        return servingSize;
    }
}
