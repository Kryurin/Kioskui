public class Food extends Product {
    private String foodType;

    public Food(String name, int quantity, double price, String foodType) {

        super(name, quantity, price);
        this.foodType = foodType;
    }

    public String getFoodType() {
        return foodType;
    }

    public String toString() {
        return super.toString() + "\n Food Type: " + foodType + "\n";
    }
}
