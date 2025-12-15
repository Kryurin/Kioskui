import javax.swing.*;
import javax.swing.border.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
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

    private JList<Product> editProductList;
    private JList<Product> deleteProductList;

    private JList<Equipment> editEquipmentList;
    private JList<Equipment> deleteEquipmentList;

    private JList<Membership> editMembershipList;
    private JList<Membership> deleteMembershipList;

    private JButton viewProductButton = new JButton();
    private JButton viewEquipmentButton = new JButton();
    private JButton editProductButton = new JButton();
    private JButton editEquipmentButton = new JButton();
    private JButton editMembershipButton = new JButton();
    private JButton customerMembershipButton = new JButton();
    private JButton deleteButton;
    private JButton logoutButton;

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
        cardPanel.add(createMembershipPanel(), "MEMBERSHIP");
        cardPanel.add(createViewProductPanel(), "VIEW PRODUCTS");
        cardPanel.add(createViewEquipmentPanel(), "VIEW EQUIPMENT");
        cardPanel.add(createViewMembershipPanel(), "VIEW MEMBERSHIPS");
        cardPanel.add(createViewPanel(), "VIEW");
        cardPanel.add(createEditProductPanel(), "EDIT PRODUCT");
        cardPanel.add(createEditEquipmentPanel(), "EDIT EQUIPMENT");
        cardPanel.add(createEditMembershipPanel(), "EDIT MEMBERSHIP");
        cardPanel.add(createDeletePanel(), "DELETE");
        cardPanel.add(createCustomerMembershipPanel(), "CUSTOMER MEMBERSHIP");

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

        loginBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                loginBtn.setBackground(new Color(180, 180, 180));
            }
            public void mouseExited(MouseEvent evt) {
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

    private void refreshLists() {
        ProductDAO productDAO = new ProductDAO();
        productManager.setProducts(productDAO.getAllProducts());

        List<Product> sortedProducts = otherMethods.sortByQuantity(productManager.getProducts());
        productModel.clear();
        for (Product p : sortedProducts) productModel.addElement(p);

        EquipmentDAO equipmentDAO = new EquipmentDAO();
        equipmentManager.setEquipments(equipmentDAO.getAllEquipments());

        equipmentModel.clear();
        for (Equipment eq : equipmentManager.getEquipments()) equipmentModel.addElement(eq);

        MembershipDAO membershipDAO = new MembershipDAO();
        membershipManager.setMemberships(membershipDAO.getAllMemberships());

        membershipModel.clear();
        for (Membership m : membershipManager.getMemberships()) membershipModel.addElement(m);
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

    private void showViewProducts() {
        if (canProceed("view")) return;

        productArea.setText(productManager.display());
        cards.show(cardPanel, "VIEW PRODUCTS");
    }

    private void showViewEquipment() {
        if (canProceed("view")) return;

        equipmentArea.setText(equipmentManager.display());
        cards.show(cardPanel, "VIEW EQUIPMENT");
    }

    private void showViewMembership() {
        membershipArea.setText(membershipManager.display());
        cards.show(cardPanel, "VIEW MEMBERSHIPS");
    }

    private void showViewPanel() {
        if (canProceed("view")) return;

        refreshLists();
        cards.show(cardPanel, "VIEW");
    }

    private void showEditProducts() {
        if (canProceed("edit")) return;

        refreshLists();
        cards.show(cardPanel, "EDIT PRODUCT");
    }

    private void showEditEquipment() {
        if (canProceed("edit")) return;

        refreshLists();
        cards.show(cardPanel, "EDIT EQUIPMENT");
    }

    private void showEditMembership() {
        refreshLists();
        cards.show(cardPanel, "EDIT MEMBERSHIP");
    }

    private void showCustomerMemberships() {
        refreshLists();
        cards.show(cardPanel, "CUSTOMER MEMBERSHIP");
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
                "Are you sure the added items are correct?\nClick Yes to proceed to the Cashier Panel, No to stay in Main Menu.",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            StartMenu orderPanel = new StartMenu();
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
        JButton equipmentBtn = createStyledButton("Create Equipment", centerX, 150);
        JButton membershipBtn = createStyledButton("Manage Memberships", centerX, 200);
        JButton viewBtn = createStyledButton("View Items", centerX, 250);
        deleteButton = createStyledButton("Delete Items", centerX, 300);
        logoutButton = createStyledButton("Log Out", centerX, 350);

        viewProductButton.addActionListener(e -> showViewProducts());
        viewEquipmentButton.addActionListener(e -> showViewEquipment());
        customerMembershipButton.addActionListener(e -> showCustomerMemberships());
        editProductButton.addActionListener(e -> cards.show(cardPanel, "EDIT PRODUCT"));
        editEquipmentButton.addActionListener(e -> cards.show(cardPanel, "EDIT EQUIPMENT"));
        editMembershipButton.addActionListener(e -> cards.show(cardPanel, "EDIT MEMBERSHIP"));

        productBtn.addActionListener(e -> cards.show(cardPanel, "PRODUCT"));
        equipmentBtn.addActionListener(e -> cards.show(cardPanel, "EQUIPMENT"));
        membershipBtn.addActionListener(e -> cards.show(cardPanel, "MEMBERSHIP"));
        viewBtn.addActionListener(e -> showViewPanel());
        deleteButton.addActionListener(e -> deleteItems());
        logoutButton.addActionListener(e -> logout());

        boxPanel.add(title);
        boxPanel.add(productBtn);
        boxPanel.add(equipmentBtn);
        boxPanel.add(membershipBtn);
        boxPanel.add(viewBtn);
        boxPanel.add(deleteButton);
        boxPanel.add(logoutButton);

        panel.add(boxPanel);
        return panel;
    }

    private JPanel createProductPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(40, 60, 90));

        JPanel boxPanel = new JPanel(null);
        boxPanel.setBackground(new Color(60, 80, 110));
        boxPanel.setBorder(new CompoundBorder(
                new LineBorder(new Color(20, 30, 50), 4),
                new EmptyBorder(20, 20, 20, 20)
        ));
        boxPanel.setBounds(50, 50, 700, 450);

        JLabel title = new JLabel("ADD PRODUCT", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Consolas", Font.BOLD, 20));
        title.setBounds(250, 10, 200, 30);

        // -------- Fields --------
        JTextField nameField = new JTextField();
        JTextField quantityField = new JTextField();
        JTextField priceField = new JTextField();
        JComboBox<String> availabilityBox = new JComboBox<>(new String[]{"Available", "Not Available"});
        availabilityBox.setVisible(false);

        nameField.setBounds(150, 100, 200, 30);
        quantityField.setBounds(150, 150, 200, 30);
        availabilityBox.setBounds(150, 150, 200, 30);
        priceField.setBounds(150, 200, 200, 30);

        JLabel nameLabel = new JLabel("Name:");
        JLabel qtyAvailLabel = new JLabel("Quantity:"); // dynamically changes
        JLabel priceLabel = new JLabel("Price:");

        nameLabel.setBounds(50, 100, 100, 30);
        qtyAvailLabel.setBounds(50, 150, 100, 30);
        priceLabel.setBounds(50, 200, 100, 30);

        nameLabel.setForeground(Color.WHITE);
        qtyAvailLabel.setForeground(Color.WHITE);
        priceLabel.setForeground(Color.WHITE);

        // -------- Category & Dynamic Fields --------
        JComboBox<String> categoryBox = new JComboBox<>(new String[]{"Food", "Drink", "Dessert"});
        categoryBox.setBounds(150, 250, 200, 30);
        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setForeground(Color.WHITE);
        categoryLabel.setBounds(50, 250, 100, 30);

        // Food
        JComboBox<String> foodTypeBox = new JComboBox<>(new String[]{"Snack", "Meal"});
        foodTypeBox.setBounds(150, 300, 200, 30);
        JLabel foodTypeLabel = new JLabel("Food Type:");
        foodTypeLabel.setForeground(Color.WHITE);
        foodTypeLabel.setBounds(50, 300, 100, 30);

        // Drink
        JComboBox<String> drinkTypeBox = new JComboBox<>(new String[]{"Soda", "Milk Tea", "Juice"});
        drinkTypeBox.setBounds(150, 300, 200, 30);
        JComboBox<String> drinkSizeBox = new JComboBox<>(new String[]{"Small", "Medium", "Large"});
        drinkSizeBox.setBounds(150, 350, 200, 30);
        JLabel drinkTypeLabel = new JLabel("Drink Type:");
        drinkTypeLabel.setForeground(Color.WHITE);
        drinkTypeLabel.setBounds(50, 300, 100, 30);
        JLabel drinkSizeLabel = new JLabel("Size:");
        drinkSizeLabel.setForeground(Color.WHITE);
        drinkSizeLabel.setBounds(50, 350, 100, 30);

        // Dessert
        JComboBox<String> dessertTypeBox = new JComboBox<>(new String[]{"Cake", "Pudding", "Ice Cream"});
        dessertTypeBox.setBounds(150, 300, 200, 30);
        JComboBox<String> dessertServingBox = new JComboBox<>(new String[]{"1 Person", "2 Person", "4 Person"});
        dessertServingBox.setBounds(150, 350, 200, 30);
        JLabel dessertTypeLabel = new JLabel("Dessert Type:");
        dessertTypeLabel.setForeground(Color.WHITE);
        dessertTypeLabel.setBounds(50, 300, 100, 30);
        JLabel dessertServingLabel = new JLabel("Serving:");
        dessertServingLabel.setForeground(Color.WHITE);
        dessertServingLabel.setBounds(50, 350, 100, 30);

        // -------- Buttons --------
        JButton addButton = createStyledButton("Add Product", 450, 200);
        JButton viewProductsBtn = createStyledButton("View Products", 450, 250);
        viewProductsBtn.addActionListener(e -> showViewProducts());
        JButton editProductButton = createStyledButton("Edit Products", 450, 300);
        editProductButton.addActionListener(e -> showEditProducts());
        JButton backButton = createStyledButton("Back", 450, 350);

        Runnable showQuantity = () -> {
            quantityField.setVisible(true);
            availabilityBox.setVisible(false);
            qtyAvailLabel.setText("Quantity:");
        };
        Runnable showAvailability = () -> {
            quantityField.setVisible(false);
            availabilityBox.setVisible(true);
            qtyAvailLabel.setText("Availability:");
        };

        ActionListener refreshUI = e -> {
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

            String cat = (String) categoryBox.getSelectedItem();
            switch (cat) {
                case "Food" -> {
                    foodTypeBox.setVisible(true);
                    foodTypeLabel.setVisible(true);
                    showAvailability.run();
                }
                case "Drink" -> {
                    drinkTypeBox.setVisible(true);
                    drinkTypeLabel.setVisible(true);
                    drinkSizeBox.setVisible(true);
                    drinkSizeLabel.setVisible(true);
                    if ("Milk Tea".equals(drinkTypeBox.getSelectedItem())) showAvailability.run();
                    else showQuantity.run();
                }
                case "Dessert" -> {
                    dessertTypeBox.setVisible(true);
                    dessertServingBox.setVisible(true);
                    dessertTypeLabel.setVisible(true);
                    dessertServingLabel.setVisible(true);
                    showQuantity.run(); }
            }
        };
        categoryBox.addActionListener(refreshUI);
        drinkTypeBox.addActionListener(refreshUI);
        refreshUI.actionPerformed(null);

        // -------- Add Button Logic --------
        addButton.addActionListener(e -> {
            try {
                String category = (String) categoryBox.getSelectedItem();
                String name = nameField.getText().trim();
                String priceText = priceField.getText().trim();

                if (name.isEmpty() || priceText.isEmpty() || (quantityField.isVisible() && quantityField.getText().trim().isEmpty())) {
                    JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double price = Double.parseDouble(priceText);
                int quantity;
                String availability;

                if (availabilityBox.isVisible()) {
                    availability = availabilityBox.getSelectedItem().toString();
                    quantity = availability.equals("Available") ? 50 : 0;
                } else {
                    availability = "Available";
                    quantity = Integer.parseInt(quantityField.getText().trim());
                }

                boolean invalid = false;
                String errorMsg = "";

                switch (category) {
                    case "Food" -> {
                        if (quantity <= 10) { invalid = true; errorMsg = "Product must be greater than 10."; break; }
                        if (price <= 10) { invalid = true; errorMsg = "Product Price must be greater than Php 10.00."; break; }
                        for (Product p : productManager.getProducts()) {
                            if (p.getName().equalsIgnoreCase(name)) { invalid = true; errorMsg = "Food name already exists!"; break; }
                        }
                        if (!invalid) productManager.addProduct(new Food(name, quantity, price, availability, (String) foodTypeBox.getSelectedItem()));
                    }
                    case "Drink" -> {
                        String type = (String) drinkTypeBox.getSelectedItem();
                        String size = (String) drinkSizeBox.getSelectedItem();

                        if (quantity <= 10) { invalid = true; errorMsg = "Drink quantity must be greater than 10."; }
                        if (price <= 10) { invalid = true; errorMsg = "Drink price must be greater than Php 10.00."; }

                        for (Product p : productManager.getProducts()) {
                            if (p instanceof Drink d && d.getName().equalsIgnoreCase(name)) {
                                if (!d.getDrinkType().equals(type)) { invalid = true; errorMsg = "A drink with this name exists but with a different type!"; break; }
                                if (d.getDrinkType().equals(type) && d.getDrinkSizes().equals(size)) { invalid = true; errorMsg = "A drink with this name, type, and size already exists!"; break; }
                                if (d.getDrinkType().equals(type) && !d.getDrinkSizes().equals(size) && d.getPrice() == price) { invalid = true; errorMsg = "A drink with the same name and price but different size is invalid!"; break; }
                            } else if (!(p instanceof Drink) && p.getName().equalsIgnoreCase(name)) { invalid = true; errorMsg = "This name already exists in another category!"; break; }
                        }

                        // ===== SIZE PRICE LOGIC =====
                        for (Product p : productManager.getProducts()) {
                            if (p instanceof Drink d && d.getDrinkType().equals(type)) {
                                String otherSize = d.getDrinkSizes();
                                double otherPrice = d.getPrice();

                                if (size.equalsIgnoreCase("Small")) {
                                    if ((otherSize.equalsIgnoreCase("Medium") || otherSize.equalsIgnoreCase("Large")) && price >= otherPrice) {
                                        invalid = true;
                                        errorMsg = "Small drink price must be less than Medium and Large!";
                                        break;
                                    }
                                } else if (size.equalsIgnoreCase("Medium")) {
                                    if (otherSize.equalsIgnoreCase("Small") && price <= otherPrice) {
                                        invalid = true;
                                        errorMsg = "Medium drink price must be greater than Small!";
                                        break;
                                    }
                                    if (otherSize.equalsIgnoreCase("Large") && price >= otherPrice) {
                                        invalid = true;
                                        errorMsg = "Medium drink price must be less than Large!";
                                        break;
                                    }
                                } else if (size.equalsIgnoreCase("Large")) {
                                    if ((otherSize.equalsIgnoreCase("Small") || otherSize.equalsIgnoreCase("Medium")) && price <= otherPrice) {
                                        invalid = true;
                                        errorMsg = "Large drink price must be greater than Small and Medium!";
                                        break;
                                    }
                                }
                            }
                        }
                        if (!invalid) productManager.addProduct(new Drink(name, quantity, price, availability, type, size));
                    }
                    case "Dessert" -> {
                        String type = (String) dessertTypeBox.getSelectedItem();
                        String serving = (String) dessertServingBox.getSelectedItem();

                        if (quantity <= 10) { invalid = true; errorMsg = "Dessert quantity must be greater than 10."; }
                        if (price <= 10) { invalid = true; errorMsg = "Dessert price must be greater than Php 10.00."; }

                        for (Product p : productManager.getProducts()) {
                            if (p instanceof Dessert d && d.getName().equalsIgnoreCase(name)) {
                                if (!d.getDessertType().equals(type)) { invalid = true; errorMsg = "A dessert with this name exists but with a different type!"; break; }
                                if (d.getDessertType().equals(type) && d.getServingSize().equals(serving)) { invalid = true; errorMsg = "A dessert with this name, type, and serving already exists!"; break; }
                                if (d.getDessertType().equals(type) && !d.getServingSize().equals(serving) && d.getPrice() == price) { invalid = true; errorMsg = "A dessert with the same name and price but different serving is invalid!"; break; }

                            } else if (!(p instanceof Dessert) && p.getName().equalsIgnoreCase(name)) { invalid = true; errorMsg = "This name already exists in another category!"; break; }

                        }

                        if (!invalid) productManager.addProduct(new Dessert(name, quantity, price, availability, type, serving));
                    }
                }

                if (invalid) JOptionPane.showMessageDialog(this, errorMsg, "Invalid Product", JOptionPane.ERROR_MESSAGE);
                else {
                    JOptionPane.showMessageDialog(this, category + " " + name + " added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    nameField.setText(""); quantityField.setText(""); priceField.setText("");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numeric inputs for quantity and price.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> cards.show(cardPanel, "MAIN"));

        // -------- Add all components --------
        boxPanel.add(title);
        boxPanel.add(nameLabel);
        boxPanel.add(nameField);
        boxPanel.add(qtyAvailLabel);
        boxPanel.add(quantityField);
        boxPanel.add(availabilityBox);
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
        boxPanel.add(editProductButton);
        boxPanel.add(viewProductsBtn);
        boxPanel.add(backButton);

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
        JButton viewEquipmentButton = createStyledButton("View Equipment", 250, 250);
        viewEquipmentButton.addActionListener(e -> showViewEquipment());
        JButton editEquipmentButton = createStyledButton("Edit Equipment", 250, 300);
        editEquipmentButton.addActionListener(e -> showEditEquipment());
        JButton backButton = createStyledButton("Back", 250, 350);

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

            if (name.isEmpty() || description.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a name or description.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Equipment equipment = new Equipment(name, description);

            equipmentManager.addEquipment(equipment);

            nameField.setText("");
            descField.setText("");

            JOptionPane.showMessageDialog(this, "Equipment " + name + " added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        backButton.addActionListener(e -> cards.show(cardPanel, "MAIN"));

        boxPanel.add(title);
        boxPanel.add(nameLabel);
        boxPanel.add(nameField);
        boxPanel.add(descLabel);
        boxPanel.add(descField);
        boxPanel.add(addButton);
        boxPanel.add(viewEquipmentButton);
        boxPanel.add(editEquipmentButton);
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
        nameField.setBounds(300, 50, 200, 30);
        valueField.setBounds(300, 100, 200, 30);
        descField.setBounds(300, 150, 200, 30);

        JLabel nameLabel = new JLabel("Name:");
        JLabel valueLabel = new JLabel("Value:");
        JLabel descLabel = new JLabel("Description:");

        nameLabel.setForeground(Color.WHITE);
        valueLabel.setForeground(Color.WHITE);
        descLabel.setForeground(Color.WHITE);

        nameLabel.setBounds(220, 50, 100, 30);
        valueLabel.setBounds(220, 100, 100, 30);
        descLabel.setBounds(220, 150, 100, 30);

        JButton addButton = createStyledButton("Add Membership", 250, 200);
        JButton customerMembershipButton = createStyledButton("Customer Memberships", 250, 250);
        JButton viewMembershipButton = createStyledButton("View Memberships", 250, 300);
        JButton editMembershipButton = createStyledButton("Edit Memberships", 250, 350);
        JButton backButton = createStyledButton("Back", 250, 400);

        // Add button listeners
        addButton.addActionListener(e -> {
            String name;
            double value;
            String description;

            try {
                name = nameField.getText();
                value = Double.parseDouble(valueField.getText().trim());
                description = descField.getText();

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid membership name.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (value <= 0) {
                    JOptionPane.showMessageDialog(this, "Membership value must be greater than Php 0.00.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (description.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter a description.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid input", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            finally {
                System.out.println("Attempted to add membership");
            }

            Membership membership = new Membership(name, value, description);
            membershipManager.addMembership(membership);

            nameField.setText("");
            valueField.setText("");
            descField.setText("");
            JOptionPane.showMessageDialog(this, "Membership " + name + " added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        viewMembershipButton.addActionListener(e -> showViewMembership());
        editMembershipButton.addActionListener(e -> showEditMembership());
        customerMembershipButton.addActionListener(e -> showCustomerMemberships());
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
        boxPanel.add(customerMembershipButton);
        boxPanel.add(editMembershipButton);
        boxPanel.add(backButton);

        panel.add(boxPanel);
        return panel;
    }

    private JPanel createViewProductPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(40, 60, 90));
        panel.setLayout(null);

        productArea.setEditable(false);
        productArea.setText(productManager.display());
        productArea.setFont(new Font("Dialog", Font.PLAIN, 14));

        JScrollPane scroll = new JScrollPane(productArea);
        scroll.setBounds(50, 70, 600, 300);

        JPanel boxPanel = new JPanel();
        boxPanel.setBackground(new Color(60, 80, 110));
        boxPanel.setBorder(new CompoundBorder(new LineBorder(new Color(20, 30, 50), 4),
                new EmptyBorder(20, 20, 20, 20)));
        boxPanel.setLayout(null);
        boxPanel.setBounds(50, 50, 700, 450);

        JLabel title = new JLabel("VIEW PRODUCTS", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Consolas", Font.BOLD, 24));
        title.setBounds(200, 10, 300, 30);

        JButton backButton = createStyledButton("Back", 270, 380);
        backButton.addActionListener(e -> cards.show(cardPanel, "PRODUCT"));

        refreshLists();
        productArea.setText(productManager.display());

        boxPanel.add(title);
        boxPanel.add(scroll);
        boxPanel.add(backButton);
        panel.add(boxPanel);

        return panel;
    }

    private JPanel createViewEquipmentPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(40, 60, 90));
        panel.setLayout(null);

        JPanel boxPanel = new JPanel();
        boxPanel.setBackground(new Color(60, 80, 110));
        boxPanel.setBorder(new CompoundBorder(new LineBorder(new Color(20, 30, 50), 4),
                new EmptyBorder(20, 20, 20, 20)));
        boxPanel.setLayout(null);
        boxPanel.setBounds(50, 50, 700, 450);

        equipmentArea.setEditable(false);
        equipmentArea.setText(equipmentManager.display());
        equipmentArea.setFont(new Font("Dialog", Font.PLAIN, 14));

        JScrollPane scroll = new JScrollPane(equipmentArea);
        scroll.setBounds(50, 70, 600, 300);

        JLabel title = new JLabel("VIEW EQUIPMENT", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Consolas", Font.BOLD, 24));
        title.setBounds(200, 10, 300, 30);

        JButton backButton = createStyledButton("Back", 270, 380);
        backButton.addActionListener(e -> cards.show(cardPanel, "EQUIPMENT"));

        refreshLists();

        boxPanel.add(title);
        boxPanel.add(scroll);
        boxPanel.add(backButton);
        panel.add(boxPanel);

        return panel;
    }

    private JPanel createViewMembershipPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(40, 60, 90));
        panel.setLayout(null);

        JPanel boxPanel = new JPanel();
        boxPanel.setBackground(new Color(60, 80, 110));
        boxPanel.setBorder(new CompoundBorder(new LineBorder(new Color(20, 30, 50), 4),
                new EmptyBorder(20, 20, 20, 20)));
        boxPanel.setLayout(null);
        boxPanel.setBounds(50, 50, 700, 450);

        membershipArea.setEditable(false);
        membershipArea.setText(membershipManager.display());
        membershipArea.setFont(new Font("Dialog", Font.PLAIN, 14));

        JScrollPane scroll = new JScrollPane(membershipArea);
        scroll.setBounds(50, 70, 600, 300);

        JLabel title = new JLabel("VIEW MEMBERSHIPS", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Consolas", Font.BOLD, 24));
        title.setBounds(200, 10, 300, 30);

        JButton backButton = createStyledButton("Back", 270, 380);
        backButton.addActionListener(e -> cards.show(cardPanel, "MEMBERSHIP"));

        refreshLists();

        boxPanel.add(title);
        boxPanel.add(scroll);
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
        boxPanel.setBorder(new CompoundBorder(
                new LineBorder(new Color(20, 30, 50), 4),
                new EmptyBorder(20, 20, 20, 20)
        ));
        boxPanel.setLayout(null);
        boxPanel.setBounds(50, 50, 700, 450);

        JLabel title = new JLabel("VIEW ITEMS", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Consolas", Font.BOLD, 20));
        title.setBounds(250, 10, 200, 30);

        refreshLists();

        viewProductButton = createStyledButton("View Products", 100, 100);
        viewProductButton.addActionListener(e -> showViewProducts());
        viewEquipmentButton = createStyledButton("View Equipment", 400, 100);
        viewEquipmentButton.addActionListener(e -> showViewEquipment());
        JButton viewMembershipButton = createStyledButton("View Memberships", 100, 250);
        viewMembershipButton.addActionListener(e -> showViewMembership());

        JButton backButton = createStyledButton("Back", 400, 250);
        backButton.addActionListener(e -> cards.show(cardPanel, "MAIN"));

        // Add components to boxPanel
        boxPanel.add(title);
        boxPanel.add(viewProductButton);
        boxPanel.add(viewEquipmentButton);
        boxPanel.add(viewMembershipButton);
        boxPanel.add(backButton);

        panel.add(boxPanel);
        return panel;
    }

    private JPanel createEditProductPanel() {
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

        JLabel title = new JLabel("EDIT PRODUCT", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Consolas", Font.BOLD, 20));
        title.setBounds(200, 10, 300, 30);

        // --- Product list ---
        editProductList = new JList<>(productModel);
        editProductList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane productScroll = new JScrollPane(editProductList);
        productScroll.setBounds(50, 50, 250, 350);

        // --- Labels ---
        JLabel nameLabel = new JLabel("Name:");
        JLabel qtyAvailLabel = new JLabel("Quantity / Availability:");
        JLabel priceLabel = new JLabel("Price:");
        JLabel sizeLabel = new JLabel("Size:");
        nameLabel.setForeground(Color.WHITE);
        qtyAvailLabel.setForeground(Color.WHITE);
        priceLabel.setForeground(Color.WHITE);
        sizeLabel.setForeground(Color.WHITE);
        nameLabel.setBounds(350, 50, 120, 30);
        qtyAvailLabel.setBounds(350, 100, 150, 30);
        priceLabel.setBounds(350, 150, 100, 30);
        sizeLabel.setBounds(350, 200, 100, 30);

        // --- Input fields ---
        JTextField nameField = new JTextField();
        JTextField quantityField = new JTextField();
        JComboBox<String> availabilityBox = new JComboBox<>(new String[]{"Available", "Not Available"});
        JTextField priceField = new JTextField();
        JComboBox<String> sizeCombo = new JComboBox<>();

        nameField.setBounds(450, 50, 200, 30);
        quantityField.setBounds(480, 100, 200, 30);
        availabilityBox.setBounds(480, 100, 200, 30);
        priceField.setBounds(450, 150, 200, 30);
        sizeCombo.setBounds(450, 200, 200, 30);
        availabilityBox.setVisible(false); // hidden by default

        // --- Buttons ---
        JButton saveButton = createStyledButton("Save Changes", 350, 350);
        JButton backButton = createStyledButton("Back", 350, 400);

        refreshLists();

        // --- List selection logic ---
        editProductList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Product selected = editProductList.getSelectedValue();
                if (selected != null) {
                    nameField.setText(selected.getName());
                    priceField.setText(String.valueOf(selected.getPrice()));

                    // Determine whether to show quantity or availability
                    boolean showAvailability = (selected instanceof Food)
                            || (selected instanceof Drink d && d.getDrinkType().equalsIgnoreCase("Milk Tea"));

                    if (showAvailability) {
                        quantityField.setVisible(false);
                        availabilityBox.setVisible(true);
                        availabilityBox.setSelectedItem(selected.getAvailability());
                    } else {
                        quantityField.setVisible(true);
                        availabilityBox.setVisible(false);
                        quantityField.setText(String.valueOf(selected.getQuantity()));
                    }

                    // Handle size combo for Drink/Dessert
                    sizeCombo.removeAllItems();
                    if (selected instanceof Drink drink) {
                        for (String s : new String[]{"Small", "Medium", "Large"}) sizeCombo.addItem(s);
                        sizeCombo.setSelectedItem(drink.getDrinkSizes());
                        sizeCombo.setEnabled(true);
                    } else if (selected instanceof Dessert dessert) {
                        for (String s : new String[]{"1 Person", "2 Person", "4 Person"}) sizeCombo.addItem(s);
                        sizeCombo.setSelectedItem(dessert.getServingSize());
                        sizeCombo.setEnabled(true);
                    } else { // Food
                        sizeCombo.setEnabled(false);
                        sizeCombo.setSelectedItem(null);
                    }
                }
            }
        });

        // --- Save logic ---
        saveButton.addActionListener(e -> {
            Product selected = editProductList.getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(this, "Select a product first.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String newName = nameField.getText().trim();
            double newPrice;
            String newSize = sizeCombo.getItemCount() > 0 ? (String) sizeCombo.getSelectedItem() : null;

            boolean showAvailability = availabilityBox.isVisible();
            int newQuantity = selected.getQuantity();
            String newAvailability = selected.getAvailability();

            for (Product p : productManager.getProducts()) {
                if (!p.equals(selected) && p.getName().equalsIgnoreCase(newName)) {
                    JOptionPane.showMessageDialog(this, "A product with this name already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            if (showAvailability) {
                newAvailability = (String) availabilityBox.getSelectedItem();
                newQuantity = newAvailability.equals("Available") ? 50 : 0;
            } else {
                try {
                    newQuantity = Integer.parseInt(quantityField.getText().trim());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Enter valid numeric quantity.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            try {
                newPrice = Double.parseDouble(priceField.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter valid numeric price.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Handle size updates
            if (selected instanceof Drink drink) {
                if (newSize == null) {
                    JOptionPane.showMessageDialog(this, "Select a size for the drink.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                drink.setDrinkSizes(newSize);
            } else if (selected instanceof Dessert dessert) {
                if (newSize == null) {
                    JOptionPane.showMessageDialog(this, "Select a serving size for the dessert.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                dessert.setServingSize(newSize);
            }

            // Update product fields
            selected.setName(newName);
            selected.setQuantity(newQuantity);
            selected.setAvailability(newAvailability);
            selected.setPrice(newPrice);

            // Update database
            ProductDAO productDAO = new ProductDAO();
            productDAO.updateProduct(selected);
            productManager.setProducts(productDAO.getAllProducts());

            // Refresh list
            productModel.clear();
            for (Product p : otherMethods.sortByQuantity(productManager.getProducts())) {
                productModel.addElement(p);
            }

            JOptionPane.showMessageDialog(this, "Product updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            refreshLists();
        });

        backButton.addActionListener(e -> cards.show(cardPanel, "PRODUCT"));

        // --- Add components ---
        boxPanel.add(title);
        boxPanel.add(productScroll);
        boxPanel.add(nameLabel);
        boxPanel.add(nameField);
        boxPanel.add(qtyAvailLabel);
        boxPanel.add(quantityField);
        boxPanel.add(availabilityBox);
        boxPanel.add(priceLabel);
        boxPanel.add(priceField);
        boxPanel.add(sizeLabel);
        boxPanel.add(sizeCombo);
        boxPanel.add(saveButton);
        boxPanel.add(backButton);

        panel.add(boxPanel);
        return panel;
    }


    private JPanel createEditEquipmentPanel() {
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

        JLabel title = new JLabel("EDIT EQUIPMENT", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Consolas", Font.BOLD, 20));
        title.setBounds(200, 10, 300, 30);

        // --- Product list ---
        editEquipmentList = new JList<>(equipmentModel);
        editEquipmentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane productScroll = new JScrollPane(editEquipmentList);
        productScroll.setBounds(50, 50, 250, 350);

        // --- Labels ---
        JLabel nameLabel = new JLabel("Name:");
        JLabel descriptionLabel = new JLabel("Description:");
        nameLabel.setForeground(Color.WHITE);
        descriptionLabel.setForeground(Color.WHITE);
        nameLabel.setBounds(350, 80, 100, 30);
        descriptionLabel.setBounds(350, 130, 100, 30);

        // --- Input fields ---
        JTextField nameField = new JTextField();
        JTextField descriptionField = new JTextField();
        nameField.setBounds(450, 80, 200, 30);
        descriptionField.setBounds(450, 130, 200, 30);

        // --- Buttons ---
        JButton saveButton = createStyledButton("Save Changes", 350, 250);
        JButton backButton = createStyledButton("Back", 350, 300);

        refreshLists();

        // --- Selection listener ---
        editEquipmentList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Equipment selected = editEquipmentList.getSelectedValue();
                if (selected != null) {
                    nameField.setText(selected.getName());
                    descriptionField.setText(selected.getDescription());
                }
            }
        });

        // --- Save logic ---
        saveButton.addActionListener(e -> {
            Equipment selected = editEquipmentList.getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(this, "Select a Equipment first.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String newName = nameField.getText().trim();
            String newDescription = descriptionField.getText().trim();


            if (newName.isEmpty() || newDescription.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid input. Check all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // --- Update Equipment ---
            selected.setName(newName);
            selected.setDescription(newDescription);

            EquipmentDAO equipmentDAO = new EquipmentDAO();
            equipmentDAO.updateEquipment(selected);

            equipmentManager.setEquipments(equipmentDAO.getAllEquipments());

            JOptionPane.showMessageDialog(this, "Equipment updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            refreshLists();
        });

        backButton.addActionListener(e -> cards.show(cardPanel, "EQUIPMENT"));

        // --- Add components ---
        boxPanel.add(title);
        boxPanel.add(productScroll);
        boxPanel.add(nameLabel);
        boxPanel.add(nameField);
        boxPanel.add(descriptionLabel);
        boxPanel.add(descriptionField);
        boxPanel.add(saveButton);
        boxPanel.add(backButton);

        panel.add(boxPanel);
        return panel;
    }

    private JPanel createEditMembershipPanel() {
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

        JLabel title = new JLabel("EDIT MEMBERSHIP", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Consolas", Font.BOLD, 20));
        title.setBounds(200, 10, 300, 30);

        // --- Membership list ---
        editMembershipList = new JList<>(membershipModel);
        editMembershipList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane membershipScroll = new JScrollPane(editMembershipList);
        membershipScroll.setBounds(50, 50, 250, 350);

        JLabel nameLabel = new JLabel("Name:");
        JLabel valueLabel = new JLabel("Value:");
        JLabel descriptionLabel = new JLabel("Description:");
        nameLabel.setForeground(Color.WHITE);
        valueLabel.setForeground(Color.WHITE);
        descriptionLabel.setForeground(Color.WHITE);
        nameLabel.setBounds(350, 80, 100, 30);
        valueLabel.setBounds(350, 130, 100, 30);
        descriptionLabel.setBounds(350, 180, 100, 30);

        // --- Input fields ---
        JTextField nameField = new JTextField();
        JTextField valueField = new JTextField();
        JTextField descriptionField = new JTextField();
        nameField.setBounds(450, 80, 200, 30);
        valueField.setBounds(450, 130, 200, 30);
        descriptionField.setBounds(450, 180, 200, 30);

        // --- Buttons ---
        JButton saveButton = createStyledButton("Save Changes", 350, 250);
        JButton backButton = createStyledButton("Back", 350, 300);

        refreshLists();

        // --- Selection listener ---
        editMembershipList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Membership selected = editMembershipList.getSelectedValue();
                if (selected != null) {
                    nameField.setText(selected.getName());
                    valueField.setText(String.valueOf(selected.getValue()));
                    descriptionField.setText(selected.getDescription());
                }
            }
        });

        // --- Save logic ---
        saveButton.addActionListener(e -> {
            Membership selected = editMembershipList.getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(this, "Select a Membership plan first.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String newName = nameField.getText().trim();
            int newValue;
            String newDescription = descriptionField.getText().trim();

            try {
                newValue = Integer.parseInt(valueField.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numeric values for value.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (newName.isEmpty() || newValue <= 0 || newDescription.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid input. Check all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // --- Update Membership ---
            selected.setName(newName);
            selected.setValue(newValue);
            selected.setDescription(newDescription);

            MembershipDAO membershipDAO = new MembershipDAO();
            membershipDAO.updateMembership(selected);

            membershipManager.setMemberships(membershipDAO.getAllMemberships());

            JOptionPane.showMessageDialog(this, "Membership updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            refreshLists();
        });

        backButton.addActionListener(e -> cards.show(cardPanel, "MEMBERSHIP"));

        // --- Add components ---
        boxPanel.add(title);
        boxPanel.add(membershipScroll);
        boxPanel.add(nameLabel);
        boxPanel.add(nameField);
        boxPanel.add(valueLabel);
        boxPanel.add(valueField);
        boxPanel.add(descriptionLabel);
        boxPanel.add(descriptionField);
        boxPanel.add(saveButton);
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

        // --- Product List ---
        JLabel productLabel = new JLabel("Products:");
        productLabel.setForeground(Color.WHITE);
        productLabel.setBounds(30, 40, 100, 30);

        deleteProductList = new JList<>(productModel);
        deleteProductList.setCellRenderer(new DeleteList());
        JScrollPane productScroll = new JScrollPane(deleteProductList);
        productScroll.setBounds(30, 70, 200, 250);

        JButton deleteProductBtn = createStyledButton("Delete Product", 30, 330);
        deleteProductBtn.addActionListener(e -> {
            Product selected = deleteProductList.getSelectedValue();
            if (selected == null) return;
            if (JOptionPane.showConfirmDialog(panel,
                    "Are you sure you want to delete \"" + selected.getName() + "\"?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                productManager.removeProduct(selected.getId());
                refreshLists();
            }
        });

        // --- Equipment List ---
        JLabel equipmentLabel = new JLabel("Equipment:");
        equipmentLabel.setForeground(Color.WHITE);
        equipmentLabel.setBounds(250, 40, 100, 30);

        deleteEquipmentList = new JList<>(equipmentModel);
        deleteEquipmentList.setCellRenderer(new DeleteList());
        JScrollPane equipmentScroll = new JScrollPane(deleteEquipmentList);
        equipmentScroll.setBounds(250, 70, 200, 250);

        JButton deleteEquipmentBtn = createStyledButton("Delete Equipment", 250, 330);
        deleteEquipmentBtn.addActionListener(e -> {
            Equipment selected = deleteEquipmentList.getSelectedValue();
            if (selected == null) return;
            if (JOptionPane.showConfirmDialog(panel,
                    "Are you sure you want to delete \"" + selected.getName() + "\"?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                equipmentManager.removeEquipment(selected.getId());
                refreshLists();
            }
        });

        // --- Membership List ---
        JLabel membershipLabel = new JLabel("Memberships:");
        membershipLabel.setForeground(Color.WHITE);
        membershipLabel.setBounds(470, 40, 120, 30);

        deleteMembershipList = new JList<>(membershipModel);
        deleteMembershipList.setCellRenderer(new DeleteList());
        JScrollPane membershipScroll = new JScrollPane(deleteMembershipList);
        membershipScroll.setBounds(470, 70, 200, 250);

        JButton deleteMembershipBtn = createStyledButton("Delete Membership", 470, 330);
        deleteMembershipBtn.addActionListener(e -> {
            Membership selected = deleteMembershipList.getSelectedValue();
            if (selected == null) return;
            if (JOptionPane.showConfirmDialog(panel,
                    "Are you sure you want to delete \"" + selected.getName() + "\"?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                membershipManager.removeMembership(selected.getId());
                refreshLists();
            }
        });

        refreshLists();

        // --- Back Button ---
        JButton backBtn = createStyledButton("Back", 250, 400);
        backBtn.addActionListener(e -> cards.show(cardPanel, "MAIN"));

        // --- Add components ---
        boxPanel.add(title);

        boxPanel.add(productLabel);
        boxPanel.add(productScroll);
        boxPanel.add(deleteProductBtn);

        boxPanel.add(equipmentLabel);
        boxPanel.add(equipmentScroll);
        boxPanel.add(deleteEquipmentBtn);

        boxPanel.add(membershipLabel);
        boxPanel.add(membershipScroll);
        boxPanel.add(deleteMembershipBtn);

        boxPanel.add(backBtn);
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

        LocalDate today = LocalDate.now();
        startDateField.setText(String.valueOf(today));
        expirationDateField.setText(String.valueOf(today.plusYears(1)));

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
        JButton addButton = createStyledButton("Add C.Membership", 100, 350);
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

        backButton.addActionListener(e -> cards.show(cardPanel, "MEMBERSHIP"));

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
}
