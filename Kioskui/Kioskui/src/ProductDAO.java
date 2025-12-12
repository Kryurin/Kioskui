import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // INSERT PRODUCT
    public void addProduct(Product p) {
        String sql = "INSERT INTO products (name, quantity, price, type, " +
                "dessertType, servingSize, drinkType, drinkSizes, foodType) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, p.getName());
            stmt.setInt(2, p.getQuantity());
            stmt.setDouble(3, p.getPrice());

            if (p instanceof Food f) {
                stmt.setString(4, "Food");
                stmt.setString(5, null);
                stmt.setString(6, null);
                stmt.setString(7, null);
                stmt.setString(8, null);
                stmt.setString(9, f.getFoodType());
            }
            else if (p instanceof Drink d) {
                stmt.setString(4, "Drink");
                stmt.setString(5, null);
                stmt.setString(6, null);
                stmt.setString(7, d.getDrinkType());
                stmt.setString(8, d.getDrinkSizes());
                stmt.setString(9, null);
            }
            else if (p instanceof Dessert d) {
                stmt.setString(4, "Dessert");
                stmt.setString(5, d.getDessertType());
                stmt.setString(6, d.getServingSize());
                stmt.setString(7, null);
                stmt.setString(8, null);
                stmt.setString(9, null);
            }

            stmt.executeUpdate();

            // Retrieve generated ID
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    p.setId(id);  // you may need to add a protected setter in Product class
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ ALL PRODUCTS
    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String type = rs.getString("type");

                Product p = switch (type) {
                    case "Food" -> new Food(
                            rs.getString("name"),
                            rs.getInt("quantity"),
                            rs.getDouble("price"),
                            rs.getString("foodType")
                    );
                    case "Drink" -> new Drink(
                            rs.getString("name"),
                            rs.getInt("quantity"),
                            rs.getDouble("price"),
                            rs.getString("drinkType"),
                            rs.getString("drinkSizes")
                    );
                    case "Dessert" -> new Dessert(
                            rs.getString("name"),
                            rs.getInt("quantity"),
                            rs.getDouble("price"),
                            rs.getString("dessertType"),
                            rs.getString("servingSize")
                    );
                    default -> null;
                };

                if (p != null) {
                    p.setId(rs.getInt("id")); // Assign the ID from DB
                    list.add(p);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // DELETE PRODUCT BY ID
    public void deleteById(int id) {
        String sql = "DELETE FROM products WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(Product p) {
        String sql = "UPDATE products SET name = ?, quantity = ?, price = ?, " +
                "dessertType = ?, servingSize = ?, drinkType = ?, drinkSizes = ?, foodType = ? " +
                "WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getName());
            stmt.setInt(2, p.getQuantity());
            stmt.setDouble(3, p.getPrice());

            if (p instanceof Food f) {
                stmt.setString(4, null); // dessertType
                stmt.setString(5, null); // servingSize
                stmt.setString(6, null); // drinkType
                stmt.setString(7, null); // drinkSizes
                stmt.setString(8, f.getFoodType());
            } else if (p instanceof Drink d) {
                stmt.setString(4, null); // dessertType
                stmt.setString(5, null); // servingSize
                stmt.setString(6, d.getDrinkType());
                stmt.setString(7, d.getDrinkSizes());
                stmt.setString(8, null); // foodType
            } else if (p instanceof Dessert d) {
                stmt.setString(4, d.getDessertType());
                stmt.setString(5, d.getServingSize());
                stmt.setString(6, null); // drinkType
                stmt.setString(7, null); // drinkSizes
                stmt.setString(8, null); // foodType
            }

            stmt.setInt(9, p.getId()); // WHERE id = ?

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
