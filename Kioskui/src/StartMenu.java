import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class StartMenu extends JFrame {
    ProductManager product = new ProductManager();
    EquipmentManager equipment = new EquipmentManager();
    MembershipManager membership = new MembershipManager();

    public StartMenu() {
        setTitle("Point and Play - Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== TOP BAR =====
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(40, 60, 90));
        topPanel.setPreferredSize(new Dimension(0, 140));

        // --- TAB PANEL (admin button + title) ---
        JPanel tabPanel = new JPanel(new BorderLayout());
        tabPanel.setBackground(new Color(20, 40, 80));
        tabPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Admin button on the left
        ImageIcon icon = new ImageIcon("C:/Users/LENOVO/Desktop/Kioskui/Kioskui/assets/adminicon.png");
        Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        icon = new ImageIcon(img);
        JButton adminBtn = new JButton(icon);
        adminBtn.setBorderPainted(false);
        adminBtn.setFocusPainted(false);
        adminBtn.setContentAreaFilled(false);
        tabPanel.add(adminBtn, BorderLayout.WEST);

        // Title in the center
        JLabel title = new JLabel("WELCOME TO POINT AND PLAY", SwingConstants.CENTER);
        title.setFont(new Font("Consolas", Font.BOLD, 40));
        title.setForeground(Color.WHITE);
        tabPanel.add(title, BorderLayout.CENTER);

        topPanel.add(tabPanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // ===== CENTER BIG CASHIER BUTTON =====
        JPanel centerPanel = new JPanel(new GridBagLayout()); // layout handles centering
        centerPanel.setBackground(new Color(40, 60, 90));

        JButton cashierBtn = createStyledButton("CASHIER"); // hover effect included
        cashierBtn.setFont(new Font("Consolas", Font.BOLD, 70));
        cashierBtn.setPreferredSize(new Dimension(350, 150));

        centerPanel.add(cashierBtn); // automatically centered
        add(centerPanel, BorderLayout.CENTER);

        // ===== ACTIONS =====
        cashierBtn.addActionListener(e -> {
            CashierPanelGUI cashierPanel = new CashierPanelGUI(
                    new ProductManager(),
                    new EquipmentManager(),
                    new MembershipManager(),
                    new StartMenu()
            );
            cashierPanel.setVisible(true);
            this.setVisible(false);
        });

        adminBtn.addActionListener(e -> {
            AdminPanelGUI adminPanel = new AdminPanelGUI(product, equipment, membership);
            adminPanel.setVisible(true);
            this.setVisible(false);
        });
    }

    // ===== HELPER METHOD FOR STYLED BUTTONS =====
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(200, 200, 200));
        button.setForeground(Color.DARK_GRAY);
        button.setFont(new Font("Consolas", Font.BOLD, 16));
        button.setBorder(new LineBorder(Color.DARK_GRAY, 4));
        button.setFocusPainted(false);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(180, 180, 180));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(200, 200, 200));
            }
        });

        return button;
    }
}
