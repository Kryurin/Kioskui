import java.util.List;

public class MembershipManager {

    private MembershipDAO dao = new MembershipDAO();

    public void addMembership(Membership m) {
        dao.addMembership(m);
    }

    public List<Membership> getMemberships() {
        return dao.getAllMemberships();
    }

    public void removeMembership(int id) {
        dao.deleteById(id);
    }

    public String display() {
        StringBuilder sb = new StringBuilder();
        for (Membership m : getMemberships()) {
            sb.append(m).append("\n");
        }
        return sb.toString();
    }
}
