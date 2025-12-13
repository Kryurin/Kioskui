import java.util.List;

public class EquipmentManager {

    private EquipmentDAO dao = new EquipmentDAO();
    private List<Equipment> equipments;  // cache

    public void loadEquipments() {
        this.equipments = dao.getAllEquipments();
    }

    public List<Equipment> getEquipments() {
        if (equipments == null) loadEquipments();
        return equipments;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }

    public void addEquipment(Equipment e) {
        dao.addEquipment(e);
        loadEquipments();  // refresh cache
    }

    public void removeEquipment(int id) {
        dao.deleteById(id);
        loadEquipments();  // refresh cache
    }

    public String display() {
        StringBuilder sb = new StringBuilder();
        for (Equipment e : getEquipments()) {
            sb.append(e).append("\n");
        }
        return sb.toString();
    }
}
