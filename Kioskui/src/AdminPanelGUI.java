import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;
import java.time.LocalDate;

public class AdminPanelGUI extends JFrame {

    private static final String ADMIN_USER = "1";
    private static final String ADMIN_PASS = "1";

    private CardLayout cards;
    private JPanel cardPanel;

    private ProductManager productManager;
    private EquipmentManager equipmentManager;
    private MembershipManager membershipManager;

    private JTextArea productArea = new JTextArea();
    private JTextArea equipmentArea = new JTextArea();
    private JTextArea membershipArea = new JTextArea();

    private DefaultListModel<Product> productModel = new DefaultListModel<>();
    private DefaultListModel<Equipment> equipmentModel = new DefaultListModel<>();
    private DefaultListModel<Membership> membershipModel = new DefaultListModel<>();

    private JList<Product> productList;
    private JList<Equipment> equipmentList;
    private JList<Membership> membershipList;

    private JButton viewProductButton;
    private JButton viewEquipmentButton;
    private JButton viewMembershipButton;
    private JButton deleteButton;
    private JButton logoutButton;
    private JButton addMemberButton;

    public AdminPanelGUI(ProductManager product, EquipmentManager equipment, MembershipManager membership) {
        this.productManager = product;
        this.equipmentManager = equipment;
        this.membershipManager = membership;

        setTitle("Admin Panel");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        cards = new CardLayout();
        cardPanel = new JPanel(cards);

        cardPanel.add(createLoginPanel(), "LOGIN");
        cardPanel.add(createMainMenuPanel(), "MAIN");
        cardPanel.add(createProductPanel(), "PRODUCT");
        cardPanel.add(createEquipmentPanel(), "EQUIPMENT");
        cardPanel.add(createCustomerMembershipPanel(), "CUSTOMER MEMBERSHIP");
        cardPanel.add(createViewPanel(), "VIEW ITEMS");
        cardPanel.add(createDeletePanel(), "DELETE");
        cardPanel.add(createMembershipPanel(), "MEMBERSHIP");

        add(cardPanel);
        cards.show(cardPanel, "LOGIN");
    }

    private JPanel createLoginPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(40, 60, 90));
        mainPanel.setLayout(null);

        JPanel boxPanel = new JPanel();
        boxPanel.setBackground(new Color(60, 80, 110));
        boxPanel.setBorder(new CompoundBorder(
                new LineBorder(new Color(20, 30, 50), 4),
                new EmptyBorder(20, 20, 20, 20)
        ));
        boxPanel.setLayout(null);
        boxPanel.setBounds(250, 150, 300, 230);

        Font pixelFont = new Font("Consolas", Font.BOLD, 16);

        JLabel title = new JLabel("ADMIN PANEL", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Consolas", Font.BOLD, 20));
        title.setBounds(60, 10, 180, 30);

        JLabel userLabel = new JLabel("USERNAME");
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(pixelFont);
        userLabel.setBounds(30, 50, 120, 20);

        JTextField userField = new JTextField();
        userField.setBackground(new Color(220, 220, 220));
        userField.setBorder(new LineBorder(Color.DARK_GRAY, 3));
        userField.setBounds(30, 70, 240, 25);

        JLabel passLabel = new JLabel("PASSWORD");
        passLabel.setForeground(Color.WHITE);
        passLabel.setFont(pixelFont);
        passLabel.setBounds(30, 105, 120, 20);

        JPasswordField passField = new JPasswordField();
        passField.setBackground(new Color(220, 220, 220));
        passField.setBorder(new LineBorder(Color.DARK_GRAY, 3));
        passField.setBounds(30, 125, 240, 25);

        JButton loginBtn = new JButton("LOG IN");
        loginBtn.setBackground(new Color(200, 200, 200));
        loginBtn.setForeground(Color.DARK_GRAY);
        loginBtn.setFont(pixelFont);
        loginBtn.setBorder(new LineBorder(Color.DARK_GRAY, 4));
        loginBtn.setFocusPainted(false);
        loginBtn.setBounds(75, 170, 150, 35);

        loginBtn.addActionListener(e -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());
            if (user.equals(ADMIN_USER) && pass.equals(ADMIN_PASS)) {
                cards.show(cardPanel, "MAIN");
                userField.setText("");
                passField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        loginBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginBtn.setBackground(new Color(180, 180, 180));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginBtn.setBackground(new Color(200, 200, 200));
            }
        });

        boxPanel.add(title);
        boxPanel.add(userLabel);
        boxPanel.add(userField);
        boxPanel.add(passLabel);
        boxPanel.add(passField);
        boxPanel.add(loginBtn);

        mainPanel.add(boxPanel);
        return mainPanel;
    }

    private JButton createStyledButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setBackground(new Color(200, 200, 200));
        button.setForeground(Color.DARK_GRAY);
        button.setFont(new Font("Consolas", Font.BOLD, 16));
        button.setBorder(new LineBorder(Color.DARK_GRAY, 4));
        button.setFocusPainted(false);
        button.setBounds(x, y, 200, 35);

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

    private void refreshLists() {
        // Products
        productModel.clear();
        for (Product p : productManager.getProducts()) {
            productModel.addElement(p);
        }

        // Equipment
        equipmentModel.clear();
        for (Equipment e : equipmentManager.getEquipments()) {
            equipmentModel.addElement(e);
        }

        // Memberships
        membershipModel.clear();
        for (Membership m : membershipManager.getMemberships()) {
            membershipModel.addElement(m);
        }
    }

    private boolean canProceed(String action) {
        boolean checkItems = productManager.getProducts().size() >= 2 &&
                equipmentManager.getEquipments().size() >= 2;

        if (!checkItems) {
            JOptionPane.showMessageDialog(
                    this,
                    "You must add at least 2 products and 2 equipment before " + action + ".",
                    "Cannot " + action,
                    JOptionPane.WARNING_MESSAGE
            );
        }

        return !checkItems;
    }

    private void showViewItems() {
        if (canProceed("view")) return;

        productArea.setText(productManager.display());
        equipmentArea.setText(equipmentManager.display());
        membershipArea.setText(membershipManager.display());
        cards.show(cardPanel, "VIEW ITEMS");
    }

    private void deleteItems() {
        if (canProceed("delete")) return;

        refreshLists();
        cards.show(cardPanel, "DELETE");
    }

    private void logout() {
        if (canProceed("logout"))
            return;

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure the added items are correct?\nClick Yes to proceed to the Order Panel, No to stay in Main Menu.",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            OrderPanelGUI orderPanel = new OrderPanelGUI(productManager, equipmentManager, membershipManager);
            orderPanel.setVisible(true);

            this.dispose();
        } else {
            cards.show(cardPanel, "MAIN");
        }
    }

    private JPanel createMainMenuPanel() {
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
        boxPanel.setBounds(50, 50, 700, 450);

        JLabel title = new JLabel("MAIN MENU", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Consolas", Font.BOLD, 20));
        title.setBounds(250, 10, 200, 30);

        int panelWidth = 700;
        int buttonWidth = 200;
        int centerX = (panelWidth - buttonWidth) / 2;

        JButton productBtn = createStyledButton("Create Products", centerX, 100);
        JButton equipmentBtn = createStyledButton("Create Equipment", centerX, 170);
        JButton membershipBtn = createStyledButton("Manage Memberships", centerX, 240);
        JButton customerBtn = createStyledButton("Customer Management", centerX, 270);
        deleteButton = createStyledButton("Delete Items", centerX, 310);
        logoutButton = createStyledButton("Log Out", centerX, 380);

        productBtn.addActionListener(e -> cards.show(cardPanel, "PRODUCT"));
        equipmentBtn.addActionListener(e -> cards.show(cardPanel, "EQUIPMENT"));
        membershipBtn.addActionListener(e -> cards.show(cardPanel, "MEMBERSHIP"));
        customerBtn.addActionListener(e -> cards.show(cardPanel, "CUSTOMER MEMBERSHIP"));
        deleteButton.addActionListener(e -> deleteItems());
        logoutButton.addActionListener(e -> logout());

        boxPanel.add(title);
        boxPanel.add(customerBtn);
        boxPanel.add(productBtn);
        boxPanel.add(equipmentBtn);
        boxPanel.add(membershipBtn);
        boxPanel.add(deleteButton);
        boxPanel.add(logoutButton);

        panel.add(boxPanel);
        return panel;
    }

    private JPanel createProductPanel() {
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
        boxPanel.setBounds(50, 50, 700, 450);

        JLabel title = new JLabel("ADD PRODUCT", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Consolas", Font.BOLD, 20));
        title.setBounds(250, 10, 200, 30);

        // Fields
        JTextField nameField = new JTextField();
        JTextField quantityField = new JTextField();
        JTextField priceField = new JTextField();
        nameField.setBounds(150, 100, 200, 30);
        quantityField.setBounds(150, 150, 200, 30);
        priceField.setBounds(150, 200, 200, 30);

        JLabel nameLabel = new JLabel("Name:");
        JLabel quantityLabel = new JLabel("Quantity:");
        JLabel priceLabel = new JLabel("Price:");

        nameLabel.setForeground(Color.WHITE);
        quantityLabel.setForeground(Color.WHITE);
        priceLabel.setForeground(Color.WHITE);

        nameLabel.setBounds(50, 100, 100, 30);
        quantityLabel.setBounds(50, 150, 100, 30);
        priceLabel.setBounds(50, 200, 100, 30);

        // Category and dynamic fields
        String[] categories = {"Food", "Drink", "Dessert"};
        JComboBox<String> categoryBox = new JComboBox<>(categories);
        categoryBox.setBounds(150, 250, 200, 30);
        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setForeground(Color.WHITE);
        categoryLabel.setBounds(50, 250, 100, 30);

        // Food
        String[] foodTypes = {"Snack", "Meal"};
        JComboBox<String> foodTypeBox = new JComboBox<>(foodTypes);
        foodTypeBox.setBounds(150, 300, 200, 30);
        JLabel foodTypeLabel = new JLabel("Food Type:");
        foodTypeLabel.setForeground(Color.WHITE);
        foodTypeLabel.setBounds(50, 300, 100, 30);

        // Drink
        String[] drinkTypes = {"Soda", "Milk Tea", "Juice"};
        JComboBox<String> drinkTypeBox = new JComboBox<>(drinkTypes);
        drinkTypeBox.setBounds(150, 300, 200, 30);
        String[] drinkSizes = {"Small", "Medium", "Large"};
        JComboBox<String> drinkSizeBox = new JComboBox<>(drinkSizes);
        drinkSizeBox.setBounds(150, 350, 200, 30);
        JLabel drinkTypeLabel = new JLabel("Drink Type:");
        drinkTypeLabel.setForeground(Color.WHITE);
        drinkTypeLabel.setBounds(50, 300, 100, 30);
        JLabel drinkSizeLabel = new JLabel("Size:");
        drinkSizeLabel.setForeground(Color.WHITE);
        drinkSizeLabel.setBounds(50, 350, 100, 30);

        // Dessert
        String[] dessertTypes = {"Cake", "Pudding", "Ice Cream"};
        JComboBox<String> dessertTypeBox = new JComboBox<>(dessertTypes);
        dessertTypeBox.setBounds(150, 300, 200, 30);
        String[] dessertServings = {"1 Person", "2 Person", "4 Person"};
        JComboBox<String> dessertServingBox = new JComboBox<>(dessertServings);
        dessertServingBox.setBounds(150, 350, 200, 30);
        JLabel dessertTypeLabel = new JLabel("Dessert Type:");
        dessertTypeLabel.setForeground(Color.WHITE);
        dessertTypeLabel.setBounds(50, 300, 100, 30);
        JLabel dessertServingLabel = new JLabel("Serving:");
        dessertServingLabel.setForeground(Color.WHITE);
        dessertServingLabel.setBounds(50, 350, 100, 30);

        // Buttons
        JButton addButton = createStyledButton("Add Product", 450, 200);
        JButton backButton = createStyledButton("Back", 450, 250);
        viewProductButton = createStyledButton("View Products", 450, 300);

        // Category visibility
        ActionListener categoryListener = e -> {
            String cat = (String) categoryBox.getSelectedItem();

            foodTypeBox.setVisible(false);
            foodTypeLabel.setVisible(false);
            drinkTypeBox.setVisible(false);
            drinkSizeBox.setVisible(false);
            drinkTypeLabel.setVisible(false);
            drinkSizeLabel.setVisible(false);
            dessertTypeBox.setVisible(false);
            dessertServingBox.setVisible(false);
            dessertTypeLabel.setVisible(false);
            dessertServingLabel.setVisible(false);

            switch (cat) {
                case "Food" -> { foodTypeBox.setVisible(true); foodTypeLabel.setVisible(true); }
                case "Drink" -> { drinkTypeBox.setVisible(true); drinkSizeBox.setVisible(true); drinkTypeLabel.setVisible(true); drinkSizeLabel.setVisible(true); }
                case "Dessert" -> { dessertTypeBox.setVisible(true); dessertServingBox.setVisible(true); dessertTypeLabel.setVisible(true); dessertServingLabel.setVisible(true); }
            }
        };
        categoryBox.addActionListener(categoryListener);
        categoryListener.actionPerformed(null);

        addButton.addActionListener(e -> {
            try {
                String category = (String) categoryBox.getSelectedItem();
                String name = nameField.getText().trim();
                int quantity = Integer.parseInt(quantityField.getText().trim());
                double price = Double.parseDouble(priceField.getText().trim());
                boolean invalid = false;
                String errorMsg = "";

                switch (category) {
                    case "Food" -> {

                        if (quantity <= 10){
                            invalid = true;
                            errorMsg = "Product must be greater than 10.";
                            break;
                        }

                        if (price <= 10){
                            invalid = true;
                            errorMsg = "Product Price must be greater than Php 10.00.";
                            break;
                        }

                        for (Product p : productManager.getProducts()) {
                            if (p.getName().equalsIgnoreCase(name)) {
                                invalid = true;
                                errorMsg = "Food name already exists in the system!";
                                break;
                            }
                        }
                        if (!invalid) {
                            Product product = new Food(name, quantity, price, (String) foodTypeBox.getSelectedItem());
                            productManager.addProduct(product);
                        }
                    }
                    case "Drink" -> {
                        String type = (String) drinkTypeBox.getSelectedItem();
                        String size = (String) drinkSizeBox.getSelectedItem();

                        if (quantity <= 10) {
                            invalid = true;
                            errorMsg = "Drink quantity must be greater than Php 10.00.";
                        }

                        if (price <= 10) {
                            invalid = true;
                            errorMsg = "Drink price must be greater than Php 10.00.";
                        }

                        for (Product p : productManager.getProducts()) {
                            if (p instanceof Drink d) {
                                if (d.getName().equalsIgnoreCase(name)) {

                                    if (!d.getDrinkType().equals(type)) {
                                        invalid = true;
                                        errorMsg = "A drink with this name exists but with a different type!";
                                        break;
                                    }
                                    if (d.getDrinkType().equals(type) && d.getDrinkSizes().equals(size)) {
                                        invalid = true;
                                        errorMsg = "A drink with this name, type, and size already exists!";
                                        break;
                                    }
                                    if (d.getDrinkType().equals(type) && !d.getDrinkSizes().equals(size) && d.getPrice() == price) {
                                        invalid = true;
                                        errorMsg = "A drink with the same name and price but different size is invalid!";
                                        break;
                                    }
                                }
                            } else if (!(p instanceof Drink) && p.getName().equalsIgnoreCase(name)) {
                                invalid = true;
                                errorMsg = "This name already exists in another category!";
                                break;
                            }
                        }

                        if (!invalid) {
                            Product product = new Drink(name, quantity, price, type, size);
                            productManager.addProduct(product);
                        }
                    }
                    case "Dessert" -> {
                        String type = (String) dessertTypeBox.getSelectedItem();
                        String serving = (String) dessertServingBox.getSelectedItem();

                        if (quantity <= 10) {
                            invalid = true;
                            errorMsg = "Dessert quantity must be greater than Php 10.00.";
                        }

                        if (price <= 10) {
                            invalid = true;
                            errorMsg = "Dessert price must be greater than Php 10.00.";
                        }

                        for (Product p : productManager.getProducts()) {
                            if (p instanceof Dessert d) {
                                if (d.getName().equalsIgnoreCase(name)) {
                                    if (!d.getDessertType().equals(type)) {
                                        invalid = true;
                                        errorMsg = "A dessert with this name exists but with a different type!";
                                        break;
                                    }
                                    if (d.getDessertType().equals(type) && d.getServingSize().equals(serving)) {
                                        invalid = true;
                                        errorMsg = "A dessert with this name, type, and serving already exists!";
                                        break;
                                    }
                                    if (d.getDessertType().equals(type) && !d.getServingSize().equals(serving) && d.getPrice() == price) {
                                        invalid = true;
                                        errorMsg = "A dessert with the same name and price but different serving is invalid!";
                                        break;
                                    }
                                }
                            } else if (!(p instanceof Dessert) && p.getName().equalsIgnoreCase(name)) {
                                invalid = true;
                                errorMsg = "This name already exists in another category!";
                                break;
                            }
                        }

                        if (!invalid) {
                            Product product = new Dessert(name, quantity, price, type, serving);
                            productManager.addProduct(product);
                        }
                    }
                }

                if (invalid) {
                    JOptionPane.showMessageDialog(this, errorMsg, "Invalid Product", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, category + " " + name + " added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                    if (category.equals("Food")) {
                        nameField.setText("");
                        quantityField.setText("");
                        priceField.setText("");
                        foodTypeBox.setSelectedIndex(0);
                    }
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numeric inputs for quantity and price.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        viewProductButton.addActionListener(e -> showViewItems());

        backButton.addActionListener(e -> cards.show(cardPanel, "MAIN"));

        // Add all components
        boxPanel.add(title);
        boxPanel.add(nameLabel);
        boxPanel.add(nameField);
        boxPanel.add(quantityLabel);
        boxPanel.add(quantityField);
        boxPanel.add(priceLabel);
        boxPanel.add(priceField);
        boxPanel.add(categoryLabel);
        boxPanel.add(categoryBox);
        boxPanel.add(foodTypeLabel);
        boxPanel.add(foodTypeBox);
        boxPanel.add(drinkTypeLabel);
        boxPanel.add(drinkTypeBox);
        boxPanel.add(drinkSizeLabel);
        boxPanel.add(drinkSizeBox);
        boxPanel.add(dessertTypeLabel);
        boxPanel.add(dessertTypeBox);
        boxPanel.add(dessertServingLabel);
        boxPanel.add(dessertServingBox);
        boxPanel.add(addButton);
        boxPanel.add(backButton);
        boxPanel.add(viewProductButton);

        panel.add(boxPanel);
        return panel;
    }

    private JPanel createEquipmentPanel() {
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
        boxPanel.setBounds(50, 50, 700, 450);

        JLabel title = new JLabel("ADD EQUIPMENT", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Consolas", Font.BOLD, 20));
        title.setBounds(250, 10, 200, 30);

        JTextField nameField = new JTextField();
        JTextField descField = new JTextField();

        nameField.setBounds(300, 100, 200, 30);
        descField.setBounds(300, 150, 200, 30);

        JLabel nameLabel = new JLabel("Name:");
        JLabel descLabel = new JLabel("Description:");

        nameLabel.setForeground(Color.WHITE);
        descLabel.setForeground(Color.WHITE);

        nameLabel.setBounds(220, 100, 100, 30);
        descLabel.setBounds(220, 150, 100, 30);

        JButton addButton = createStyledButton("Add Equipment", 250, 200);
        viewEquipmentButton = createStyledButton("View Equipment", 250, 250);
        JButton backButton = createStyledButton("Back", 250, 300);

        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String description = descField.getText();
            boolean invalid = false;
            String errorMsg = "";

            for (Equipment equip: equipmentManager.getEquipments()) {
                if (equip.getName().equalsIgnoreCase(name)) {
                        invalid = true;
                        errorMsg = "Equipment name already exists in the system!";
                        break;
                }
            }
            if (invalid) {
                JOptionPane.showMessageDialog(this, errorMsg, "Invalid Equipment", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a valid equipment name.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Equipment equipment = new Equipment(name, description);

            equipmentManager.addEquipment(equipment);

            nameField.setText("");
            descField.setText("");

            JOptionPane.showMessageDialog(this, "Equipment " + name + " added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        viewEquipmentButton.addActionListener(e -> showViewItems());

        backButton.addActionListener(e -> cards.show(cardPanel, "MAIN"));

        boxPanel.add(title);
        boxPanel.add(nameLabel);
        boxPanel.add(nameField);
        boxPanel.add(descLabel);
        boxPanel.add(descField);
        boxPanel.add(addButton);
        boxPanel.add(viewEquipmentButton);
        boxPanel.add(backButton);

        panel.add(boxPanel);
        return panel;
    }

    private JPanel createMembershipPanel() {
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
        boxPanel.setBounds(50, 50, 700, 450);

        JLabel title = new JLabel("ADD MEMBERSHIP", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Consolas", Font.BOLD, 20));
        title.setBounds(250, 10, 200, 30);

        JTextField nameField = new JTextField();
        JTextField valueField = new JTextField();
        JTextField descField = new JTextField();
        nameField.setBounds(300, 100, 200, 30);
        valueField.setBounds(300, 150, 200, 30);
        descField.setBounds(300, 200, 200, 30);

        JLabel nameLabel = new JLabel("Name:");
        JLabel valueLabel = new JLabel("Value:");
        JLabel descLabel = new JLabel("Description:");

        nameLabel.setForeground(Color.WHITE);
        valueLabel.setForeground(Color.WHITE);
        descLabel.setForeground(Color.WHITE);

        nameLabel.setBounds(220, 100, 100, 30);
        valueLabel.setBounds(220, 150, 100, 30);
        descLabel.setBounds(220, 200, 100, 30);

        JButton addButton = createStyledButton("Add Membership", 250, 250);
        viewMembershipButton = createStyledButton("View Membership", 250, 300);
        JButton backButton = createStyledButton("Back", 250, 350);

        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String description = descField.getText();
            double value = Double.parseDouble(valueField.getText());

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a valid membership name.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Membership membership = new Membership(name, value, description);

            membershipManager.addMembership(membership);

            nameField.setText("");
            valueField.setText("");
            descField.setText("");
            JOptionPane.showMessageDialog(this, "Membership " + name + " added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        viewMembershipButton.addActionListener(e -> {
            productArea.setText(productManager.display());
            equipmentArea.setText(equipmentManager.display());
            membershipArea.setText(membershipManager.display());
            cards.show(cardPanel, "VIEW ITEMS");
        });

        backButton.addActionListener(e -> cards.show(cardPanel, "MAIN"));

        boxPanel.add(title);
        boxPanel.add(nameLabel);
        boxPanel.add(nameField);
        boxPanel.add(valueLabel);
        boxPanel.add(valueField);
        boxPanel.add(descLabel);
        boxPanel.add(descField);
        boxPanel.add(addButton);
        boxPanel.add(viewMembershipButton);
        boxPanel.add(backButton);

        panel.add(boxPanel);
        return panel;
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
        boxPanel.setBounds(50, 50, 700, 500);

        JLabel title = new JLabel("ADD CUSTOMER MEMBERSHIP", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Consolas", Font.BOLD, 20));
        title.setBounds(150, 10, 400, 30);

        // Fields
        JTextField customerNameField = new JTextField();
        JComboBox<Membership> membershipDropdown = new JComboBox<>();
        JTextField startDateField = new JTextField();
        JTextField expirationDateField = new JTextField();
        JTextField contactField = new JTextField();

        customerNameField.setBounds(300, 100, 200, 30);
        membershipDropdown.setBounds(300, 150, 200, 30);
        startDateField.setBounds(300, 200, 200, 30);
        expirationDateField.setBounds(300, 250, 200, 30);
        contactField.setBounds(300, 300, 200, 30);

        // Labels
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

        nameLabel.setBounds(120, 100, 180, 30);
        membershipLabel.setBounds(120, 150, 180, 30);
        startLabel.setBounds(120, 200, 180, 30);
        expLabel.setBounds(120, 250, 180, 30);
        contactLabel.setBounds(120, 300, 180, 30);

        // Buttons
        JButton addButton = createStyledButton("Add Customer Membership", 200, 350);
        JButton backButton = createStyledButton("Back", 400, 350);

        // Load memberships into dropdown
        MembershipDAO membershipDAO = new MembershipDAO();
        List<Membership> memberships = membershipDAO.getAllMemberships();
        for (Membership m : memberships) {
            membershipDropdown.addItem(m);
        }

        // Action: Add Customer Membership
        addButton.addActionListener(e -> {
            String customerName = customerNameField.getText().trim();
            Membership selectedMembership = (Membership) membershipDropdown.getSelectedItem();
            String startDateStr = startDateField.getText().trim();
            String expirationDateStr = expirationDateField.getText().trim();
            String contact = contactField.getText().trim();

            if (customerName.isEmpty() || selectedMembership == null || startDateStr.isEmpty() || expirationDateStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                LocalDate startDate = LocalDate.parse(startDateStr);
                LocalDate expirationDate = LocalDate.parse(expirationDateStr);

                CustomerMembership cm = new CustomerMembership(customerName, selectedMembership, startDate, expirationDate, contact);

                // Add to database
                CustomerMembershipDAO cmDAO = new CustomerMembershipDAO();
                cmDAO.addCustomerMembership(cm);

                // Generate PDF card
                CustomerMembershipCardGenerator.generateCard(cm);

                JOptionPane.showMessageDialog(this, "Customer membership added and card generated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                // Clear fields
                customerNameField.setText("");
                startDateField.setText("");
                expirationDateField.setText("");
                contactField.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        backButton.addActionListener(e -> cards.show(cardPanel, "MAIN"));

        // Add components to boxPanel
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

    private JPanel createViewPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(40, 60, 90));
        panel.setLayout(null);

        JPanel boxPanel = new JPanel();
        boxPanel.setBackground(new Color(60, 80, 110));
        boxPanel.setBorder(new CompoundBorder(new LineBorder(new Color(20, 30, 50), 4), new EmptyBorder(20, 20, 20, 20)));
        boxPanel.setLayout(null);
        boxPanel.setBounds(50, 50, 700, 450);

        JLabel title = new JLabel("VIEW ITEMS", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Consolas", Font.BOLD, 20));
        title.setBounds(250, 10, 200, 30);

        productArea.setEditable(false);
        productArea.setText(productManager.display());
        equipmentArea.setEditable(false);
        equipmentArea.setText(equipmentManager.display());
        membershipArea.setEditable(false);
        membershipArea.setText(membershipManager.display());

        JScrollPane productScroll = new JScrollPane(productArea);
        productScroll.setBounds(50, 70, 300, 150);
        JScrollPane equipmentScroll = new JScrollPane(equipmentArea);
        equipmentScroll.setBounds(370, 70, 300, 150);
        JScrollPane membershipScroll = new JScrollPane(membershipArea);
        membershipScroll.setBounds(50, 250, 300, 150);

        // Labels
        JLabel productLabel = new JLabel("Products:");
        JLabel equipmentLabel = new JLabel("Equipment:");
        JLabel membershipLabel = new JLabel("Memberships:");

        productLabel.setForeground(Color.WHITE);
        equipmentLabel.setForeground(Color.WHITE);
        membershipLabel.setForeground(Color.WHITE);

        productLabel.setBounds(50, 40, 100, 30);
        equipmentLabel.setBounds(370, 40, 100, 30);
        membershipLabel.setBounds(50, 220, 100, 30);

        Font viewFont = new Font("Dialog", Font.PLAIN, 14);
        productArea.setFont(viewFont);
        equipmentArea.setFont(viewFont);
        membershipArea.setFont(viewFont);

        JButton backButton = createStyledButton("Back", 420, 300);
        backButton.addActionListener(e -> cards.show(cardPanel, "MAIN"));

        boxPanel.add(title);
        boxPanel.add(productLabel);
        boxPanel.add(equipmentLabel);
        boxPanel.add(membershipLabel);
        boxPanel.add(productScroll);
        boxPanel.add(equipmentScroll);
        boxPanel.add(membershipScroll);
        boxPanel.add(backButton);
        panel.add(boxPanel);
        return panel;
    }

    private JPanel createDeletePanel() {
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
        boxPanel.setBounds(50, 50, 700, 450);

        JLabel title = new JLabel("DELETE ITEMS", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Consolas", Font.BOLD, 20));
        title.setBounds(250, 10, 200, 30);

        // Labels
        JLabel productLabel = new JLabel("Products:");
        JLabel equipmentLabel = new JLabel("Equipment:");
        JLabel membershipLabel = new JLabel("Memberships:");

        productLabel.setForeground(Color.WHITE);
        equipmentLabel.setForeground(Color.WHITE);
        membershipLabel.setForeground(Color.WHITE);

        productLabel.setBounds(30, 40, 100, 30);
        equipmentLabel.setBounds(250, 40, 100, 30);
        membershipLabel.setBounds(470, 40, 120, 30);

        // Lists
        productList = new JList<>(productModel);
        equipmentList = new JList<>(equipmentModel);
        membershipList = new JList<>(membershipModel);

        JScrollPane productScroll = new JScrollPane(productList);
        productScroll.setBounds(30, 70, 200, 200);

        JScrollPane equipmentScroll = new JScrollPane(equipmentList);
        equipmentScroll.setBounds(250, 70, 200, 200);

        JScrollPane membershipScroll = new JScrollPane(membershipList);
        membershipScroll.setBounds(470, 70, 200, 200);

        // Buttons
        JButton deleteProductBtn = createStyledButton("Delete Product", 30, 280);
        JButton deleteEquipmentBtn = createStyledButton("Delete Equipment", 250, 280);
        JButton deleteMembershipBtn = createStyledButton("Delete Membership", 470, 280);
        JButton refreshBtn = createStyledButton("Refresh", 30, 350);
        JButton backBtn = createStyledButton("Back", 470, 350);

        Font deleteListFont = new Font("Dialog", Font.PLAIN, 15);
        productList.setFont(deleteListFont);
        equipmentList.setFont(deleteListFont);
        membershipList.setFont(deleteListFont);

        // Delete actions
        deleteProductBtn.addActionListener(e -> {
            Product selected = productList.getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(panel, "Select a product to delete.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(panel,
                    "Are you sure you want to delete \"" + selected.getName() + "\"?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                productManager.removeProduct(selected.getId());
                refreshLists();
                JOptionPane.showMessageDialog(panel, "Product deleted successfully!");
            }
        });

        deleteEquipmentBtn.addActionListener(e -> {
            Equipment selected = equipmentList.getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(panel, "Select an equipment to delete.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(panel,
                    "Are you sure you want to delete \"" + selected.getName() + "\"?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                equipmentManager.removeEquipment(selected.getId());
                refreshLists();
                JOptionPane.showMessageDialog(panel, "Equipment deleted successfully!");
            }
        });

        deleteMembershipBtn.addActionListener(e -> {
            Membership selected = membershipList.getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(panel, "Select a membership to delete.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(panel,
                    "Are you sure you want to delete \"" + selected.getName() + "\"?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                membershipManager.removeMembership(selected.getId());

                String pdfPath = "memberships/" + selected.getName().replaceAll("\\s+", "_") + "_Membership.pdf";
                File pdfFile = new File(pdfPath);
                if (pdfFile.exists()) {
                    if (pdfFile.delete()) {
                        System.out.println("PDF deleted: " + pdfPath);
                    } else {
                        System.out.println("Failed to delete PDF: " + pdfPath);
                    }
                }

                refreshLists();
                JOptionPane.showMessageDialog(panel, "Membership deleted successfully!");
            }
        });

        refreshBtn.addActionListener(e -> refreshLists());
        backBtn.addActionListener(e -> cards.show(cardPanel, "MAIN"));

        // Add everything
        boxPanel.add(title);
        boxPanel.add(productLabel);
        boxPanel.add(equipmentLabel);
        boxPanel.add(membershipLabel);
        boxPanel.add(productScroll);
        boxPanel.add(equipmentScroll);
        boxPanel.add(membershipScroll);
        boxPanel.add(deleteProductBtn);
        boxPanel.add(deleteEquipmentBtn);
        boxPanel.add(deleteMembershipBtn);
        boxPanel.add(refreshBtn);
        boxPanel.add(backBtn);

        panel.add(boxPanel);
        return panel;
    }
}
