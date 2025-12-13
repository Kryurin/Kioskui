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

    public void setDrinkType(String drinkType) {
        this.drinkType = drinkType;
    }

    public void setDrinkSizes(String drinkSizes) {
        this.drinkSizes = drinkSizes;
    }


    @Override
    public String toListString() {
        return super.toListString() + ", Drink Type: " + drinkType + ", Drink Sizes: " + drinkSizes;
    }
}

