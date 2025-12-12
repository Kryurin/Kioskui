import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class CashierPanelGUI extends JFrame {

    private ProductManager productManager;
    private EquipmentManager equipmentManager;
    private MembershipManager membershipManager;
    private StartMenu startMenu;

    public CashierPanelGUI(ProductManager product, EquipmentManager equipment, MembershipManager membership, StartMenu startMenu) {
        this.productManager = product;
        this.equipmentManager = equipment;
        this.membershipManager = membership;
        this.startMenu = startMenu;

        setTitle("Point and Play - Cashier Panel");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setBackground(new Color(40, 60, 90));

        // ===== MAIN PANEL =====
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(40, 60, 90));
        setContentPane(mainPanel);

        // ===== TOP BAR (TITLE + BACK BUTTON) =====
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(40, 60, 90));
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("WELCOME TO POINT AND PLAY", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Consolas", Font.BOLD, 36));

        JButton backBtn = createStyledButton("Back to Start Menu", 0, 0);
        backBtn.setPreferredSize(new Dimension(200, 40));
        backBtn.addActionListener(e -> {
            this.dispose();
            if (this.startMenu != null) this.startMenu.setVisible(true);
        });

        topBar.add(title, BorderLayout.CENTER);
        topBar.add(backBtn, BorderLayout.EAST);

        mainPanel.add(topBar, BorderLayout.NORTH);

        // Center content
        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(new Color(40, 60, 90));

        // Left box for category buttons
        JPanel leftBox = new JPanel();
        leftBox.setBackground(new Color(60, 80, 110));
        leftBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(20, 30, 50), 4),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        leftBox.setLayout(null);
        leftBox.setPreferredSize(new Dimension(260, 0));

        JLabel catLabel = new JLabel("CATEGORIES", SwingConstants.CENTER);
        catLabel.setForeground(Color.WHITE);
        catLabel.setFont(new Font("Consolas", Font.BOLD, 18));
        catLabel.setBounds(20, 10, 220, 30);

        JButton foodBtn = createStyledButton("Food", 30, 70);
        JButton drinkBtn = createStyledButton("Drink", 30, 130);
        JButton dessertBtn = createStyledButton("Dessert", 30, 190);

        leftBox.add(catLabel);
        leftBox.add(foodBtn);
        leftBox.add(drinkBtn);
        leftBox.add(dessertBtn);

        // Right box for products and order summary
        JPanel rightBox = new JPanel(null);
        rightBox.setBackground(new Color(60, 80, 110));
        rightBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(20, 30, 50), 4),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Item list (products or equipment)
        DefaultListModel<Object> itemListModel = new DefaultListModel<>();
        JList<Object> itemList = new JList<>(itemListModel);
        itemList.setFont(new Font("Dialog", Font.PLAIN, 14));
        JScrollPane productScroll = new JScrollPane(itemList);
        productScroll.setBounds(20, 20, 540, 300);

        // Controls to add to order
        JLabel qtyLabel = new JLabel("Quantity:");
        qtyLabel.setForeground(Color.WHITE);
        qtyLabel.setBounds(20, 330, 80, 25);
        JTextField qtyField = new JTextField("1");
        qtyField.setBounds(100, 330, 80, 25);

        JButton addToOrderBtn = createStyledButton("Add to Order", 200, 325);

        // Order area
        JLabel orderLabel = new JLabel("ORDER SUMMARY", SwingConstants.CENTER);
        orderLabel.setForeground(Color.WHITE);
        orderLabel.setFont(new Font("Consolas", Font.BOLD, 16));
        orderLabel.setBounds(20, 370, 540, 30);

        DefaultListModel<String> orderModel = new DefaultListModel<>();
        JList<String> orderList = new JList<>(orderModel);
        JScrollPane orderScroll = new JScrollPane(orderList);
        orderScroll.setBounds(20, 410, 540, 150);

        // Place order button
        JButton placeOrderBtn = createStyledButton("Place Order", 20, 570);

        // Add components to rightBox
        rightBox.add(productScroll);
        rightBox.add(qtyLabel);
        rightBox.add(qtyField);
        rightBox.add(addToOrderBtn);
        rightBox.add(orderLabel);
        rightBox.add(orderScroll);
        rightBox.add(placeOrderBtn);

        center.add(leftBox, BorderLayout.WEST);
        center.add(rightBox, BorderLayout.CENTER);

        mainPanel.add(center, BorderLayout.CENTER);

        // Helper: populate itemListModel by category
        ActionListener showByCategory = e -> {
            String cmd = e.getActionCommand();
            itemListModel.clear();
            if ("Equipment".equals(cmd)) {
                for (Equipment eq : equipmentManager.getEquipments()) itemListModel.addElement(eq.toListString());
                return;
            }
            for (Product p : productManager.getProducts()) {
                switch (cmd) {
                    case "Food" -> {
                        if (p instanceof Food) itemListModel.addElement(p.toListString());
                    }
                    case "Drink" -> {
                        if (p instanceof Drink) itemListModel.addElement(p.toListString());
                    }
                    case "Dessert" -> {
                        if (p instanceof Dessert) itemListModel.addElement(p.toListString());
                    }
                }
            }

        };

        foodBtn.setActionCommand("Food");
        drinkBtn.setActionCommand("Drink");
        dessertBtn.setActionCommand("Dessert");

        foodBtn.addActionListener(showByCategory);
        drinkBtn.addActionListener(showByCategory);
        dessertBtn.addActionListener(showByCategory);

        // Equipment category button
        JButton equipmentCatBtn = createStyledButton("Equipment", 30, 250);
        equipmentCatBtn.setActionCommand("Equipment");
        equipmentCatBtn.addActionListener(showByCategory);
        leftBox.add(equipmentCatBtn);

        // Default show food
        foodBtn.doClick();

        // Add to order handler
        List<Product> selectedProducts = new ArrayList<>();
        List<Integer> productQuantities = new ArrayList<>();
        final List<Equipment> selectedEquipments = new ArrayList<>();

        addToOrderBtn.addActionListener(e -> {
            Object sel = itemList.getSelectedValue();
            if (sel == null) {
                JOptionPane.showMessageDialog(this, "Select an item to add.", "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int qty = 1;
            try {
                qty = Integer.parseInt(qtyField.getText().trim());
                if (qty <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter a valid quantity.", "Invalid Quantity", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (sel instanceof Product selected) {
                String line = qty + " x " + selected.getName() + " - P" + String.format("%.2f", selected.getPrice()) + " each";
                orderModel.addElement(line);
                selectedProducts.add(selected);
                productQuantities.add(qty);
            } else if (sel instanceof Equipment eq) {
                // Equipment added as borrowed and free
                String line = qty + " x " + eq.getName() + " - Free (Borrowed)";
                orderModel.addElement(line);
                // store selection
                selectedEquipments.add(eq);
            }
        });

        placeOrderBtn.addActionListener(e -> {
            OrderProcess.placeOrder(
                    this,
                    selectedProducts,
                    productQuantities,
                    selectedEquipments,
                    orderModel,
                    foodBtn // or current category button
            );
        });
    }


    // Button design
    private JButton createStyledButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setBackground(new Color(200, 200, 200));
        button.setForeground(Color.DARK_GRAY);
        button.setFont(new Font("Consolas", Font.BOLD, 16));
        button.setBorder(new javax.swing.border.LineBorder(Color.DARK_GRAY, 4));
        button.setFocusPainted(false);
        button.setBounds(x, y, 200, 35);

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(180, 180, 180));
            }
            public void mouseExited(MouseEvent evt) {
                button.setBackground(new Color(200, 200, 200));
            }
        });

        return button;
    }
}