import java.util.ArrayList;
import java.util.List;

public class MembershipManager {
    private List<Membership> memberships = new ArrayList<>();

    public void addMembership(Membership membership) {
        memberships.add(membership);
    }

    public ArrayList<Membership> getMembership() {
        return new ArrayList<>(memberships);
    }

    public void removeMembership(Membership membership) {
        memberships.remove(membership);
    }

    public String display() {
        StringBuilder sb = new StringBuilder();
        for (Membership m : memberships) {
            sb.append(m).append("\n");
        }
        return sb.toString();
    }
}
