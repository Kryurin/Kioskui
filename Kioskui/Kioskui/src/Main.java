public class Main {
    public static void main(String[] args) {
        ProductManager product = new ProductManager();
        EquipmentManager equipment = new EquipmentManager();
        MembershipManager membership = new MembershipManager();

        //AdminPanelGUI adminPanel = new AdminPanelGUI(product, equipment, membership);
        //adminPanel.setVisible(true);

        boolean hasProducts = !product.getProducts().isEmpty();
        boolean hasEquipment = !equipment.getEquipments().isEmpty();

        if (!hasProducts && !hasEquipment) {
            AdminPanelGUI adminPanel = new AdminPanelGUI(product, equipment, membership);
            adminPanel.setVisible(true);
        } else {
            StartMenu menu = new StartMenu();
            menu.setVisible(true);
        }
    }
}
