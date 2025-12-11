import java.util.ArrayList;
import java.util.List;

public class otherMethods {
    public static List<Product> sortByQuantity(List<Product> products) {
        if (products.size() <= 1) {
            return products;
        }

        int mid = products.size() / 2;
        List<Product> left = sortByQuantity(new ArrayList<>(products.subList(0, mid)));
        List<Product> right = sortByQuantity(new ArrayList<>(products.subList(mid, products.size())));

        return merge(left, right);
    }

    private static List<Product> merge(List<Product> left, List<Product> right) {
        List<Product> result = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            if (left.get(i).getQuantity() <= right.get(j).getQuantity()) {
                result.add(left.get(i));
                i++;
            } else {
                result.add(right.get(j));
                j++;
            }
        }

        // Add remaining elements
        while (i < left.size()) {
            result.add(left.get(i));
            i++;
        }
        while (j < right.size()) {
            result.add(right.get(j));
            j++;
        }

        return result;
    }

    public static boolean validateDrinkPrice(Drink drink, double newPrice, String size, List<Product> allProducts) {
        List<Drink> drinks = allProducts.stream()
                .filter(p -> p instanceof Drink)
                .map(p -> (Drink)p)
                .filter(d -> d.getDrinkType().equals(drink.getDrinkType()))
                .toList();

        for (Drink d : drinks) {
            if (d == drink) continue;
            if (sizeOrder(size) < sizeOrder(d.getDrinkSizes()) && newPrice >= d.getPrice()) return false;
            if (sizeOrder(size) > sizeOrder(d.getDrinkSizes()) && newPrice <= d.getPrice()) return false;
        }
        return true;
    }

    public static boolean validateDessertPrice(Dessert dessert, double newPrice, String size, List<Product> allProducts) {
        List<Dessert> desserts = allProducts.stream()
                .filter(p -> p instanceof Dessert)
                .map(p -> (Dessert)p)
                .filter(d -> d.getDessertType().equals(dessert.getDessertType()))
                .toList();

        for (Dessert d : desserts) {
            if (d == dessert) continue;
            if (sizeOrder(size) < sizeOrder(d.getServingSize()) && newPrice >= d.getPrice()) return false;
            if (sizeOrder(size) > sizeOrder(d.getServingSize()) && newPrice <= d.getPrice()) return false;
        }
        return true;
    }

    private static int sizeOrder(String size) {
        return switch (size) {
            case "Small", "1 Person" -> 0;
            case "Medium", "2 Person" -> 1;
            case "Large", "4 Person" -> 2;
            default -> 3;
        };
    }
}
