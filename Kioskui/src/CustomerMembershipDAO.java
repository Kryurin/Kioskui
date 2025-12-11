import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerMembershipDAO {

    // INSERT a new CustomerMembership
    public void addCustomerMembership(CustomerMembership cm) {
        String sql = "INSERT INTO customer_memberships (customer_name, membership_id, start_date, expiration_date, contact_info) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cm.getCustomerName());
            stmt.setInt(2, cm.getMembership().getId()); // link to Membership
            stmt.setDate(3, Date.valueOf(cm.getStartDate()));
            stmt.setDate(4, Date.valueOf(cm.getExpirationDate()));
            stmt.setString(5, cm.getContactInfo());

            stmt.executeUpdate();

            // Retrieve generated ID
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    cm.setId(rs.getInt(1));
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // READ all customer memberships
    public List<CustomerMembership> getAllCustomerMemberships() {
        List<CustomerMembership> list = new ArrayList<>();
        String sql = "SELECT cm.id, cm.customer_name, cm.start_date, cm.expiration_date, cm.contact_info, m.id AS membership_id, m.name, m.value, m.description " +
                "FROM customer_memberships cm " +
                "JOIN membership m ON cm.membership_id = m.id";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Membership membership = new Membership(
                        rs.getString("name"),
                        rs.getDouble("value"),
                        rs.getString("description")
                );
                membership.setId(rs.getInt("membership_id"));

                CustomerMembership cm = new CustomerMembership(
                        rs.getString("customer_name"),
                        membership,
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("expiration_date").toLocalDate(),
                        rs.getString("contact_info")
                );
                cm.setId(rs.getInt("id"));
                list.add(cm);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    // DELETE a customer membership by ID
    public void deleteById(int id) {
        String sql = "DELETE FROM customer_memberships WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
