import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembershipDAO {

    // INSERT
    public void addMembership(Membership m) {
        String sql = "INSERT INTO membership (name, value, description) VALUES (?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, m.getName());
            stmt.setDouble(2, m.getValue());
            stmt.setString(3, m.getDescription());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    m.setId(rs.getInt(1));
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // READ ALL
    public List<Membership> getAllMemberships() {
        List<Membership> list = new ArrayList<>();
        String sql = "SELECT * FROM membership";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Membership m = new Membership(
                        rs.getString("name"),
                        rs.getDouble("value"),
                        rs.getString("description")
                );
                m.setId(rs.getInt("id"));
                list.add(m);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    // DELETE
    public void deleteById(int id) {
        String sql = "DELETE FROM membership WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
