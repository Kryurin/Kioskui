public class Food extends Product {
    private String foodType;

    public Food(String name, int id, int quantity, double price, String foodType) {

        super(name, id, quantity, price);
        this.foodType = foodType;
    }

    public String getFoodType() {
        return foodType;
    }
}
