import java.util.ArrayList;
import java.util.List;

public class EquipmentManager {

    private List<Equipment> equipments = new ArrayList<>();

    public void addEquipment(Equipment equipment) {
        equipments.add(equipment);
    }

    public List<Equipment> getEquipments() {
        return new ArrayList<>(equipments);
    }

    public void removeEquipment(Equipment equipment) {
        equipments.remove(equipment);
    }

    public String display() {
        StringBuilder sb = new StringBuilder();
        for (Equipment e : equipments) {
            sb.append(e).append("\n");
        }
        return sb.toString();
    }
}
