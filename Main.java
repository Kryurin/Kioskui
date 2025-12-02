public class Main {
    public static void main(String[] args) {
        ProductManager product = new ProductManager();
        EquipmentManager equipment = new EquipmentManager();
        MembershipManager membership = new MembershipManager();

        AdminPanelGUI gui = new AdminPanelGUI(product, equipment, membership);
        gui.setVisible(true);
    }
}
