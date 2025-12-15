import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CashierPanelGUI extends JFrame {

    private ProductManager productManager;
    private EquipmentManager equipmentManager;
    private MembershipManager membershipManager;
    private DefaultListModel<Membership> membershipModel = new DefaultListModel<>();
    private StartMenu startMenu;
    private OrderQueue orderQueue;
    private CardLayout cards;
    private JPanel cardPanel;

    private Stack<String> placedOrderStack = new Stack<>();
    private DefaultListModel<String> orderModel;

    private List<Product> selectedProducts = new ArrayList<>();
    private List<Integer> selectedQuantities = new ArrayList<>();
    private List<Equipment> selectedEquipments = new ArrayList<>();

    public CashierPanelGUI(ProductManager product, EquipmentManager equipment,
                           MembershipManager membership, StartMenu startMenu) {
        this(product, equipment, membership, startMenu, new OrderQueue(10));
    }

    public CashierPanelGUI(ProductManager product, EquipmentManager equipment,
                           MembershipManager membership, StartMenu startMenu, OrderQueue orderQueue) {
        this.productManager = product;
        this.equipmentManager = equipment;
        this.membershipManager = membership;
        this.startMenu = startMenu;
        this.orderQueue = orderQueue;

        setTitle("Point and Play - Cashier Panel");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width / 2, 1920);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ===== CARD PANEL =====
        cards = new CardLayout();
        cardPanel = new JPanel(cards);

        // Create panels
        JPanel rightBox = createRightPanel();
        JPanel membershipPanel = createCustomerMembershipPanel();

        cardPanel.add(rightBox, "ITEM LIST");
        cardPanel.add(membershipPanel, "CUSTOMER MEMBERSHIP");

        cards.show(cardPanel, "ITEM LIST");

        // ===== MAIN PANEL =====
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(40, 60, 90));
        setContentPane(mainPanel);

        JPanel topBar = createTopBar();
        JPanel leftBox = createLeftBox();

        mainPanel.add(topBar, BorderLayout.NORTH);
        mainPanel.add(leftBox, BorderLayout.WEST);
        mainPanel.add(cardPanel, BorderLayout.CENTER);
    }

    private JPanel createTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(40, 60, 90));
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("WELCOME TO POINT AND PLAY", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Consolas", Font.BOLD, 36));

        JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        topRightPanel.setBackground(new Color(40, 60, 90));

        JButton backBtn = createStyledButton("Back to Start Menu", 0, 0);
        backBtn.setPreferredSize(new Dimension(200, 40));
        backBtn.addActionListener(e -> {
            this.dispose();
            if (this.startMenu != null) this.startMenu.setVisible(true);
        });

        topRightPanel.add(backBtn);

        topBar.add(title, BorderLayout.CENTER);
        topBar.add(topRightPanel, BorderLayout.EAST);
        return topBar;
    }

    private JPanel createLeftBox() {
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

        JButton foodBtn = createImageButton("Kioskui/assets/Meals.png", 50, 70, 150, 150);
        JButton drinkBtn = createImageButton("Kioskui/assets/drinks.png", 50, 250, 150, 150);
        JButton dessertBtn = createImageButton("Kioskui/assets/dessrt.png", 50, 430, 150, 150);
        JButton equipmentBtn = createImageButton("Kioskui/assets/Equipment.png", 50, 600, 150, 150);
        JButton customerMembershipButton = createStyledButton("Customer Membership", 30, 780);
        customerMembershipButton.addActionListener(e -> showCustomerMemberships());

        leftBox.add(catLabel);
        leftBox.add(foodBtn);
        leftBox.add(drinkBtn);
        leftBox.add(dessertBtn);
        leftBox.add(equipmentBtn);
        leftBox.add(customerMembershipButton);

        // Category actions
        ActionListener showByCategory = e -> {
            String cmd = e.getActionCommand();
            JList<Object> itemList = (JList<Object>) ((JScrollPane) ((JPanel) cardPanel.getComponent(0)).getComponent(0)).getViewport().getView();
            DefaultListModel<Object> itemListModel = (DefaultListModel<Object>) itemList.getModel();
            itemListModel.clear();

            if ("Equipment".equals(cmd)) {
                for (Equipment eq : equipmentManager.getEquipments()) itemListModel.addElement(eq);
                return;
            }

            for (Product p : productManager.getProducts()) {
                switch (cmd) {
                    case "Food" -> { if (p instanceof Food) itemListModel.addElement(p); }
                    case "Drink" -> { if (p instanceof Drink) itemListModel.addElement(p); }
                    case "Dessert" -> { if (p instanceof Dessert) itemListModel.addElement(p); }
                }
            }
        };

        foodBtn.setActionCommand("Food");
        drinkBtn.setActionCommand("Drink");
        dessertBtn.setActionCommand("Dessert");
        equipmentBtn.setActionCommand("Equipment");

        foodBtn.addActionListener(showByCategory);
        drinkBtn.addActionListener(showByCategory);
        dessertBtn.addActionListener(showByCategory);
        equipmentBtn.addActionListener(showByCategory);

        foodBtn.doClick(); // default

        return leftBox;
    }

    private JPanel createRightPanel() {
        JPanel rightBox = new JPanel(null);
        rightBox.setBackground(new Color(60, 80, 110));
        rightBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(20, 30, 50), 4),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Product / Equipment List
        DefaultListModel<Object> itemListModel = new DefaultListModel<>();
        JList<Object> itemList = new JList<>(itemListModel);
        itemList.setFont(new Font("Dialog", Font.PLAIN, 14));
        JScrollPane productScroll = new JScrollPane(itemList);
        productScroll.setBounds(20, 20, 540, 300);

        JLabel qtyLabel = new JLabel("Quantity:");
        qtyLabel.setForeground(Color.WHITE);
        qtyLabel.setBounds(20, 330, 80, 25);
        JTextField qtyField = new JTextField("1");
        qtyField.setBounds(100, 330, 80, 25);

        JButton addToOrderBtn = createStyledButton("Add to Order", 200, 325);

        // Order Summary
        JLabel orderLabel = new JLabel("ORDER SUMMARY", SwingConstants.CENTER);
        orderLabel.setForeground(Color.WHITE);
        orderLabel.setFont(new Font("Consolas", Font.BOLD, 16));
        orderLabel.setBounds(20, 370, 540, 30);

        orderModel = new DefaultListModel<>();
        JList<String> orderList = new JList<>(orderModel);
        JScrollPane orderScroll = new JScrollPane(orderList);
        orderScroll.setBounds(20, 410, 540, 250);

        JButton placeOrderBtn = createStyledButton("Place Order", 20, 680);
        placeOrderBtn.addActionListener(e -> placeOrder());
        JButton cancelItemBtn = createStyledButton("Cancel Item", 300, 680);

        rightBox.add(productScroll);
        rightBox.add(qtyLabel);
        rightBox.add(qtyField);
        rightBox.add(addToOrderBtn);
        rightBox.add(orderLabel);
        rightBox.add(orderScroll);
        rightBox.add(placeOrderBtn);
        rightBox.add(cancelItemBtn);

        // ===== ADD TO ORDER =====
// ===== ADD TO ORDER =====
        addToOrderBtn.addActionListener(e -> {
            Object sel = itemList.getSelectedValue();
            if (sel == null) {
                JOptionPane.showMessageDialog(this, "Select an item to add.", "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int qty;
            try {
                qty = Integer.parseInt(qtyField.getText().trim());
                if (qty <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter a valid quantity.", "Invalid Quantity", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String line;
            if (sel instanceof Product p) {
                line = qty + " x " + p.getName() + " - P" + String.format("%.2f", p.getPrice()) + " each";
                selectedProducts.add(p);
                selectedQuantities.add(qty);
            } else if (sel instanceof Equipment eq) {
                line = qty + " x " + eq.getName() + " - Free (Borrowed)";
                selectedEquipments.add(eq);
            } else return;

            placedOrderStack.push(line); // keep stack updated
            refreshOrderList(); // refresh order summary
        });

// ===== CANCEL ITEM =====
        cancelItemBtn.addActionListener(e -> {
            int selectedIndex = orderList.getSelectedIndex();
            if (selectedIndex == -1) {
                JOptionPane.showMessageDialog(this, "Select an item to cancel.", "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Stack is last-in-first-out, so map index from order list to stack
            int stackIndex = placedOrderStack.size() - 1 - selectedIndex;
            String removed = placedOrderStack.remove(stackIndex); // remove from stack

            // Remove from selected lists based on type
            if (removed.contains(" - P")) {
                if (!selectedProducts.isEmpty() && selectedIndex < selectedProducts.size()) {
                    selectedProducts.remove(selectedIndex);
                    selectedQuantities.remove(selectedIndex);
                }
            } else if (removed.contains(" (Borrowed)")) {
                if (!selectedEquipments.isEmpty() && selectedIndex < selectedEquipments.size()) {
                    selectedEquipments.remove(selectedIndex);
                }
            }

            refreshOrderList(); // update JList
            JOptionPane.showMessageDialog(this, "Cancelled: " + removed, "Item Cancelled", JOptionPane.INFORMATION_MESSAGE);
        });


        return rightBox;
    }

    private JPanel createCustomerMembershipPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(40, 60, 90));
        panel.setLayout(null);

        JPanel boxPanel = new JPanel();
        boxPanel.setBackground(new Color(60, 80, 110));
        boxPanel.setBorder(new CompoundBorder(
                new LineBorder(new Color(20, 30, 50), 4),
                new EmptyBorder(20, 20, 20, 20)
        ));
        boxPanel.setLayout(null);
        boxPanel.setBounds(50, 50, 500, 500);

        JLabel title = new JLabel("ADD CUSTOMER MEMBERSHIP", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Consolas", Font.BOLD, 20));
        title.setBounds(50, 10, 400, 30);

        JTextField customerNameField = new JTextField();
        JComboBox<Membership> membershipDropdown = new JComboBox<>();
        JTextField startDateField = new JTextField();
        JTextField expirationDateField = new JTextField();
        JTextField contactField = new JTextField();

        customerNameField.setBounds(200, 100, 200, 30);
        membershipDropdown.setBounds(200, 150, 200, 30);
        startDateField.setBounds(200, 200, 200, 30);
        expirationDateField.setBounds(200, 250, 200, 30);
        contactField.setBounds(200, 300, 200, 30);

        JLabel nameLabel = new JLabel("Customer Name:");
        JLabel membershipLabel = new JLabel("Membership:");
        JLabel startLabel = new JLabel("Start Date (YYYY-MM-DD):");
        JLabel expLabel = new JLabel("Expiration Date (YYYY-MM-DD):");
        JLabel contactLabel = new JLabel("Contact Info:");

        nameLabel.setForeground(Color.WHITE);
        membershipLabel.setForeground(Color.WHITE);
        startLabel.setForeground(Color.WHITE);
        expLabel.setForeground(Color.WHITE);
        contactLabel.setForeground(Color.WHITE);

        nameLabel.setBounds(20, 100, 180, 30);
        membershipLabel.setBounds(20, 150, 180, 30);
        startLabel.setBounds(20, 200, 180, 30);
        expLabel.setBounds(20, 250, 180, 30);
        contactLabel.setBounds(20, 300, 180, 30);

        JButton addButton = createStyledButton("Add C.Membership", 40, 350);
        JButton backButton = createStyledButton("Back", 250, 350);

        MembershipDAO membershipDAO = new MembershipDAO();
        for (Membership m : membershipDAO.getAllMemberships()) membershipDropdown.addItem(m);

        addButton.addActionListener(e -> {
            try {
                String customerName = customerNameField.getText().trim();
                Membership selectedMembership = (Membership) membershipDropdown.getSelectedItem();
                LocalDate startDate = LocalDate.parse(startDateField.getText().trim());
                LocalDate expirationDate = LocalDate.parse(expirationDateField.getText().trim());
                String contact = contactField.getText().trim();

                if (customerName.isEmpty() || selectedMembership == null) {
                    JOptionPane.showMessageDialog(this, "Please fill all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                CustomerMembership cm = new CustomerMembership(customerName, selectedMembership, startDate, expirationDate, contact);
                CustomerMembershipDAO cmDAO = new CustomerMembershipDAO();
                cmDAO.addCustomerMembership(cm);
                CustomerMembershipCardGenerator.generateCard(cm);

                JOptionPane.showMessageDialog(this, "Customer membership added and card generated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                customerNameField.setText("");
                startDateField.setText("");
                expirationDateField.setText("");
                contactField.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Use correct date format.", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        backButton.addActionListener(e -> cards.show(cardPanel, "ITEM LIST"));

        boxPanel.add(title);
        boxPanel.add(nameLabel);
        boxPanel.add(customerNameField);
        boxPanel.add(membershipLabel);
        boxPanel.add(membershipDropdown);
        boxPanel.add(startLabel);
        boxPanel.add(startDateField);
        boxPanel.add(expLabel);
        boxPanel.add(expirationDateField);
        boxPanel.add(contactLabel);
        boxPanel.add(contactField);
        boxPanel.add(addButton);
        boxPanel.add(backButton);

        panel.add(boxPanel);
        return panel;
    }

    private JButton createStyledButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setBackground(new Color(200, 200, 200));
        button.setForeground(Color.DARK_GRAY);
        button.setFont(new Font("Consolas", Font.BOLD, 16));
        button.setBorder(new LineBorder(Color.DARK_GRAY, 4));
        button.setFocusPainted(false);
        button.setBounds(x, y, 200, 35);

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) { button.setBackground(new Color(180, 180, 180)); }
            public void mouseExited(MouseEvent evt) { button.setBackground(new Color(200, 200, 200)); }
        });

        return button;
    }

    private JButton createImageButton(String imgPath, int x, int y, int width, int height) {
        ImageIcon icon = new ImageIcon(imgPath);
        Image scaledImg = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledImg);

        JButton btn = new JButton(icon);
        btn.setBounds(x, y, width, height);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setOpaque(false);
        return btn;
    }

    private void showCustomerMemberships() {
        refreshLists();
        cards.show(cardPanel, "CUSTOMER MEMBERSHIP");
    }

    private void refreshLists() {
        MembershipDAO membershipDAO = new MembershipDAO();
        membershipManager.setMemberships(membershipDAO.getAllMemberships());
        membershipModel.clear();
        for (Membership m : membershipManager.getMemberships()) membershipModel.addElement(m);
    }

    private void refreshOrderList() {
        orderModel.clear();
        for (int i = placedOrderStack.size() - 1; i >= 0; i--) {
            orderModel.addElement(placedOrderStack.get(i));
        }
    }

    private void placeOrder() {
        if (placedOrderStack.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No items added.", "Empty Order", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double total = 0;
        StringBuilder errorMsg = new StringBuilder();

        // Loop through selected products to check stock and calculate total
        for (int i = 0; i < selectedProducts.size(); i++) {
            Product p = selectedProducts.get(i);
            int qty = selectedQuantities.get(i);
            if (qty > p.getQuantity()) {
                errorMsg.append("- ").append(p.getName())
                        .append(": Requested ").append(qty)
                        .append(", Available ").append(p.getQuantity()).append("\n");
            } else {
                total += p.getPrice() * qty;
            }
        }

        if (errorMsg.length() > 0) {
            JOptionPane.showMessageDialog(this, "Some items exceed available stock:\n" + errorMsg,
                    "Invalid Quantity", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Reduce product quantities
        for (int i = 0; i < selectedProducts.size(); i++) {
            Product p = selectedProducts.get(i);
            int qty = selectedQuantities.get(i);
            p.reduceQuantity(qty);
        }

        // Create order with current stack content
        List<String> orderItems = new ArrayList<>(placedOrderStack); // copy stack as is
        Order order = new Order(orderItems, total);

        if (orderQueue.addOrder(order)) {
            KitchenPanelGUI kitchen = KitchenPanelGUI.getInstance(orderQueue);
            kitchen.setVisible(true);
            kitchen.toFront();
            JOptionPane.showMessageDialog(this, "Order #" + order.getOrderId() + " sent to kitchen!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Kitchen queue is full! Order not sent.",
                    "Queue Full", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Clear everything after placing order
        placedOrderStack.clear();
        selectedProducts.clear();
        selectedQuantities.clear();
        selectedEquipments.clear();
        refreshOrderList();
    }

}