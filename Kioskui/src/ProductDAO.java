import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductDAO {

    public static boolean insertProduct(Product product) {
        String sql = "INSERT INTO product (id, name, quantity, price, product_type, foodType, drinkType, drinkSizes, dessertType, servingSize) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, product.getId());
            pstmt.setString(2, product.getName());
            pstmt.setInt(3, product.getQuantity());
            pstmt.setDouble(4, product.getPrice());

            // Determine type and set properties
            if (product instanceof Food) {
                pstmt.setString(5, "Food");
                pstmt.setString(6, ((Food) product).getFoodType());
                pstmt.setNull(7, java.sql.Types.VARCHAR);
                pstmt.setNull(8, java.sql.Types.VARCHAR);
                pstmt.setNull(9, java.sql.Types.VARCHAR);
                pstmt.setNull(10, java.sql.Types.VARCHAR);

            } else if (product instanceof Drink) {
                pstmt.setString(5, "Drink");
                pstmt.setNull(6, java.sql.Types.VARCHAR);
                pstmt.setString(7, ((Drink) product).getDrinkType());
                pstmt.setString(8, ((Drink) product).getDrinkSizes());
                pstmt.setNull(9, java.sql.Types.VARCHAR);
                pstmt.setNull(10, java.sql.Types.VARCHAR);

            } else if (product instanceof Dessert) {
                pstmt.setString(5, "Dessert");
                pstmt.setNull(6, java.sql.Types.VARCHAR);
                pstmt.setNull(7, java.sql.Types.VARCHAR);
                pstmt.setNull(8, java.sql.Types.VARCHAR);
                pstmt.setString(9, ((Dessert) product).getDessertType());
                pstmt.setString(10, ((Dessert) product).getServingSize());
            }

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
