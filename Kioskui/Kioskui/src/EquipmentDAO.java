import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipmentDAO {

    // INSERT
    public void addEquipment(Equipment e) {
        String sql = "INSERT INTO equipment (name, description) VALUES (?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, e.getName());
            stmt.setString(2, e.getDescription());
            stmt.executeUpdate();

            // Get generated ID
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    e.setId(rs.getInt(1));
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // READ ALL
    public List<Equipment> getAllEquipments() {
        List<Equipment> list = new ArrayList<>();
        String sql = "SELECT * FROM equipment";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Equipment e = new Equipment(
                        rs.getString("name"),
                        rs.getString("description")
                );
                e.setId(rs.getInt("id"));
                list.add(e);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    // DELETE
    public void deleteById(int id) {
        String sql = "DELETE FROM equipment WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
