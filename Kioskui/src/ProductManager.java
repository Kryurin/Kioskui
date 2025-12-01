import java.util.List;

public class ProductManager {

    private ProductDAO dao = new ProductDAO();

    public void addProduct(Product p) {
        dao.addProduct(p);
    }

    public List<Product> getProducts() {
        return dao.getAllProducts();
    }

    public void removeProduct(int id) {
        dao.deleteById(id);
    }

    public String display() {
        StringBuilder sb = new StringBuilder();
        for (Product p : getProducts()) {
            sb.append(p).append("\n");
        }
        return sb.toString();
    }
}
