import java.util.List;

public class ProductManager {

    private ProductDAO dao = new ProductDAO();
    private List<Product> products;

    // Load all products from DB
    public void loadProducts() {
        this.products = dao.getAllProducts();
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        if (products == null) loadProducts();
        return products;
    }

    public void addProduct(Product p) {
        dao.addProduct(p);
        loadProducts(); // refresh cache
    }

    public void removeProduct(int id) {
        dao.deleteById(id);
        loadProducts(); // refresh cache
    }

    public String display() {
        StringBuilder sb = new StringBuilder();
        for (Product p : getProducts()) {
            sb.append(p).append("\n");
        }
        return sb.toString();
    }
}
