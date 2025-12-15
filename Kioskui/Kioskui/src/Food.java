public class Food extends Product {
    private String foodType;

    public Food(String name, int quantity, double price, String availability, String foodType) {

        super(name, quantity, price, availability);
        this.foodType = foodType;
    }

    public String getFoodType() {
        return foodType;
    }


    @Override
    public String toListString() {
        return super.toListString() + ", Food Type: " + foodType + "\n";
    }
}
