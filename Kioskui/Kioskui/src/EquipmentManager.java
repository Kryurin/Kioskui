import java.util.List;

public class EquipmentManager {

    private EquipmentDAO dao = new EquipmentDAO();

    public void addEquipment(Equipment e) {
        dao.addEquipment(e);
    }

    public List<Equipment> getEquipments() {
        return dao.getAllEquipments();
    }

    public void removeEquipment(int id) {
        dao.deleteById(id);
    }

    public String display() {
        StringBuilder sb = new StringBuilder();
        for (Equipment e : getEquipments()) {
            sb.append(e).append("\n");
        }
        return sb.toString();
    }
}
