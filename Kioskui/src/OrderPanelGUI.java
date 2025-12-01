import javax.swing.*;
import java.awt.*;

public class OrderPanelGUI extends JFrame {

    private ProductManager productManager;
    private EquipmentManager equipmentManager;
    private MembershipManager membershipManager;

    public OrderPanelGUI(ProductManager product, EquipmentManager equipment, MembershipManager membership) {
        this.productManager = product;
        this.equipmentManager = equipment;
        this.membershipManager = membership;

        setTitle("Order Panel");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Only close this frame
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        setBackground(new Color(40, 60, 90));

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(40, 60, 90));
        setContentPane(mainPanel);

        // Title
        JLabel title = new JLabel("ORDER PANEL", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Consolas", Font.BOLD, 36));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(title, BorderLayout.NORTH);
    }
}
