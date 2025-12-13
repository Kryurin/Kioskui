import java.util.List;

public class MembershipManager {

    private MembershipDAO dao = new MembershipDAO();
    private List<Membership> memberships; // cache

    public void loadMemberships() {
        this.memberships = dao.getAllMemberships();
    }

    public List<Membership> getMemberships() {
        if (memberships == null) loadMemberships();
        return memberships;
    }

    public void setMemberships(List<Membership> memberships) {
        this.memberships = memberships;
    }

    public void addMembership(Membership m) {
        dao.addMembership(m);
        loadMemberships();  // refresh cache
    }

    public void removeMembership(int id) {
        dao.deleteById(id);
        loadMemberships();  // refresh cache
    }


    public String display() {
        StringBuilder sb = new StringBuilder();
        for (Membership m : getMemberships()) {
            sb.append(m).append("\n");
        }
        return sb.toString();
    }
}
