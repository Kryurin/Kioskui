import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EquipmentDAO {

    public static boolean insertEquipment(Equipment equipment) {
        String sql = "INSERT INTO equipment (id, name) VALUES (?, ?)";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, equipment.getId());
            pstmt.setString(2, equipment.getName());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
