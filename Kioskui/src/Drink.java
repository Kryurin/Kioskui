public class Drink extends Product {
    private String drinkType;
    private String drinkSizes;


    public Drink(String name, int quantity, double price, String drinkType , String drinkSizes) {
        super(name, quantity, price);
        this.drinkType = drinkType;
        this.drinkSizes = drinkSizes;
    }

    public String getDrinkType() {
        return drinkType;
    }

    public String getDrinkSizes() {
        return drinkSizes;
    }

    public String toString() {
        return super.toString() + "\n Drink Type: " + drinkType + ", Drink Sizes: " + drinkSizes + "\n";
    }
}

