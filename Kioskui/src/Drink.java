public class Drink extends Product {
    private String drinkType;
    private String drinkSizes;


    public Drink(String name, int id, int quantity, double price, String drinkType , String drinkSizes) {
        super(name, id, quantity, price);
        this.drinkType = drinkType;
        this.drinkSizes = drinkSizes;
    }

    public String getDrinkType() {
        return drinkType;
    }

    public String getDrinkSizes() {
        return drinkSizes;
    }
}

