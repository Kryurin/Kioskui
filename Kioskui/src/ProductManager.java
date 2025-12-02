import java.util.ArrayList;
import java.util.List;

public class ProductManager {

    private List<Product> products = new ArrayList<>(); // store objects now

    public void addProduct(Product product) {
        products.add(product);

    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public String display() {
        StringBuilder sb = new StringBuilder();
        for (Product p : products) {
            sb.append(p).append("\n");
        }
        return sb.toString();
    }
}
