import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class AdminPanelSystem extends JFrame {
    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS = "1234";

    private CardLayout cards;
    private JPanel cardPanel;
    private ProductManager productManager;
    private EquipmentManager equipmentManager;

    // Main menu buttons
    private JButton viewBtn;
    private JButton deleteBtn;
    private JButton logoutBtn;

    public AdminPanelSystem() {
        setTitle("Admin Panel");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        productManager = new ProductManager();
        equipmentManager = new EquipmentManager();

        cards = new CardLayout();
        cardPanel = new JPanel(cards);

        cardPanel.add(createLoginPanel(), "LOGIN");
        cardPanel.add(createMainMenuPanel(), "MAIN");
        cardPanel.add(createProductPanel(), "PRODUCT");
        cardPanel.add(createEquipmentPanel(), "EQUIPMENT");
        cardPanel.add(createViewPanel(), "VIEW");
        cardPanel.add(createDeletePanel(), "DELETE");

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
                JOptionPane.showMessageDialog(this,
                        "Invalid username or password",
                        "Login Error",
                        JOptionPane.ERROR_MESSAGE);
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

        JButton productBtn = createStyledButton("Manage Products", centerX, 100);
        JButton equipmentBtn = createStyledButton("Manage Equipment", centerX, 170);
        viewBtn = createStyledButton("View Items", centerX, 240);
        deleteBtn = createStyledButton("Delete Items", centerX, 310);
        logoutBtn = createStyledButton("Logout", centerX, 380);

        productBtn.addActionListener(e -> cards.show(cardPanel, "PRODUCT"));
        equipmentBtn.addActionListener(e -> cards.show(cardPanel, "EQUIPMENT"));
        viewBtn.addActionListener(e -> cards.show(cardPanel, "VIEW"));
        deleteBtn.addActionListener(e -> cards.show(cardPanel, "DELETE"));
        logoutBtn.addActionListener(e -> cards.show(cardPanel, "LOGIN"));

        boxPanel.add(title);
        boxPanel.add(productBtn);
        boxPanel.add(equipmentBtn);
        boxPanel.add(viewBtn);
        boxPanel.add(deleteBtn);
        boxPanel.add(logoutBtn);

        panel.add(boxPanel);

        // Initially disable View/Delete if < 3 items
        refreshMainButtons();

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

        // Common fields
        JTextField nameField = new JTextField();
        JTextField idField = new JTextField();
        idField.setEnabled(false);
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

        // Category combo box
        String[] categories = {"Food", "Drink", "Dessert"};
        JComboBox<String> categoryBox = new JComboBox<>(categories);
        categoryBox.setBounds(150, 250, 200, 30);
        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setForeground(Color.WHITE);
        categoryLabel.setBounds(50, 250, 100, 30);

        // Food type
        String[] foodTypes = {"Snack", "Meal"};
        JComboBox<String> foodTypeBox = new JComboBox<>(foodTypes);
        foodTypeBox.setBounds(150, 300, 200, 30);
        JLabel foodTypeLabel = new JLabel("Food Type:");
        foodTypeLabel.setForeground(Color.WHITE);
        foodTypeLabel.setBounds(50, 300, 100, 30);

        // Drink type and size
        String[] drinkTypes = {"Soda", "Milktea", "Juice"};
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

        // Dessert type and serving
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

        // Add & Back buttons
        JButton addButton = createStyledButton("Add Product", 450, 200);
        JButton backButton = createStyledButton("Back", 450, 250);

        // Dynamic visibility depending on category
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
                case "Food":
                    foodTypeBox.setVisible(true);
                    foodTypeLabel.setVisible(true);
                    break;
                case "Drink":
                    drinkTypeBox.setVisible(true);
                    drinkSizeBox.setVisible(true);
                    drinkTypeLabel.setVisible(true);
                    drinkSizeLabel.setVisible(true);
                    break;
                case "Dessert":
                    dessertTypeBox.setVisible(true);
                    dessertServingBox.setVisible(true);
                    dessertTypeLabel.setVisible(true);
                    dessertServingLabel.setVisible(true);
                    break;
            }
        };
        categoryBox.addActionListener(categoryListener);
        categoryListener.actionPerformed(null); // visibility of the boxes

        addButton.addActionListener(e -> {
            try {
                String category = (String) categoryBox.getSelectedItem();
                String name = nameField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                double price = Double.parseDouble(priceField.getText());
                int id = productManager.generateUniqueID();

                Product product = null;
                switch (category) {
                    case "Food":
                        String selectedFoodType = (String) foodTypeBox.getSelectedItem();
                        product = new Food(name, id, quantity, price, selectedFoodType);
                        break;
                    case "Drink":
                        String selectedDrinkType = (String) drinkTypeBox.getSelectedItem();
                        String selectedSize = (String) drinkSizeBox.getSelectedItem();
                        product = new Drink(name, id, quantity, price, selectedDrinkType, selectedSize);
                        break;
                    case "Dessert":
                        String selectedDessertType = (String) dessertTypeBox.getSelectedItem();
                        String selectedServing = (String) dessertServingBox.getSelectedItem();
                        product = new Dessert(name, id, quantity, price, selectedDessertType, selectedServing);
                        break;
                }

                productManager.addProduct(product);
                idField.setText(String.valueOf(id)); // show generated ID

                JOptionPane.showMessageDialog(this,
                        "Product " + name + " added successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);

                if (category.equals("Food")) {
                    nameField.setText("");
                    quantityField.setText("");
                    priceField.setText("");
                    foodTypeBox.setSelectedIndex(0);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Please enter valid numeric inputs.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> cards.show(cardPanel, "MAIN"));

        //Box Panel
        boxPanel.add(title);
        boxPanel.add(nameLabel);
        boxPanel.add(nameField);
        boxPanel.add(quantityLabel);
        boxPanel.add(quantityField);
        boxPanel.add(priceLabel);
        boxPanel.add(priceField);
        boxPanel.add(categoryLabel);
        boxPanel.add(categoryBox);

        // Food Box
        boxPanel.add(foodTypeLabel);
        boxPanel.add(foodTypeBox);

        // Drink Box
        boxPanel.add(drinkTypeLabel);
        boxPanel.add(drinkTypeBox);
        boxPanel.add(drinkSizeLabel);
        boxPanel.add(drinkSizeBox);

        // Dessert Box
        boxPanel.add(dessertTypeLabel);
        boxPanel.add(dessertTypeBox);
        boxPanel.add(dessertServingLabel);
        boxPanel.add(dessertServingBox);

        // Buttons
        boxPanel.add(addButton);
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
        JTextField idField = new JTextField();
        idField.setEnabled(false);
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
        JButton backButton = createStyledButton("Back", 250, 250);

        addButton.addActionListener(e -> {
            String name = nameField.getText();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a valid equipment name.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int id = productManager.generateUniqueID();
            Equipment equipment = new Equipment(name, id);

            equipmentManager.addEquipment(equipment);

            // Clear fields
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

        JTextArea productArea = new JTextArea();
        JTextArea equipmentArea = new JTextArea();
        productArea.setEditable(false);
        equipmentArea.setEditable(false);

        JScrollPane productScroll = new JScrollPane(productArea);
        JScrollPane equipmentScroll = new JScrollPane(equipmentArea);
        productScroll.setBounds(50, 70, 300, 300);
        equipmentScroll.setBounds(370, 70, 300, 300);

        JLabel productLabel = new JLabel("Products:");
        JLabel equipmentLabel = new JLabel("Equipment:");
        productLabel.setForeground(Color.WHITE);
        equipmentLabel.setForeground(Color.WHITE);
        productLabel.setBounds(50, 40, 100, 30);
        equipmentLabel.setBounds(370, 40, 100, 30);

        JButton refreshButton = createStyledButton("Refresh", 50, 380);
        JButton backButton = createStyledButton("Back", 250, 380);

        JLabel note = new JLabel("Please Refresh First");
        note.setForeground(Color.WHITE);
        note.setBounds(500, 380, 200, 30);
        boxPanel.add(note);

        Runnable refreshView = () -> {
            StringBuilder prodStr = new StringBuilder();
            for (Product p : productManager.getProducts()) prodStr.append(p.toString()).append("\n");
            productArea.setText(prodStr.toString());

            StringBuilder equipStr = new StringBuilder();
            for (Equipment eq : equipmentManager.getEquipments()) equipStr.append(eq.toString()).append("\n");
            equipmentArea.setText(equipStr.toString());

            refreshMainButtons();
        };

        refreshButton.addActionListener(e -> refreshView.run());
        backButton.addActionListener(e -> cards.show(cardPanel, "MAIN"));
        refreshView.run();

        boxPanel.add(title);
        boxPanel.add(productLabel);
        boxPanel.add(equipmentLabel);
        boxPanel.add(productScroll);
        boxPanel.add(equipmentScroll);
        boxPanel.add(refreshButton);
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

        DefaultListModel<Product> productModel = new DefaultListModel<>();
        JList<Product> productList = new JList<>(productModel);
        JScrollPane productScroll = new JScrollPane(productList);
        productScroll.setBounds(50, 70, 300, 300);

        DefaultListModel<Equipment> equipmentModel = new DefaultListModel<>();
        JList<Equipment> equipmentList = new JList<>(equipmentModel);
        JScrollPane equipmentScroll = new JScrollPane(equipmentList);
        equipmentScroll.setBounds(370, 70, 300, 300);

        JLabel productLabel = new JLabel("Products:");
        JLabel equipmentLabel = new JLabel("Equipment:");
        productLabel.setForeground(Color.WHITE);
        equipmentLabel.setForeground(Color.WHITE);
        productLabel.setBounds(50, 40, 100, 30);
        equipmentLabel.setBounds(370, 40, 100, 30);

        JButton refreshBtn = createStyledButton("Refresh", 100, 410);
        JButton delProductBtn = createStyledButton("Delete Product", 100, 370);
        JButton delEquipBtn = createStyledButton("Delete Equipment", 420, 370);
        JButton backBtn = createStyledButton("Back", 420, 410);

        JLabel note = new JLabel("Please Refresh first");
        note.setForeground(Color.WHITE);
        note.setBounds(300, 410, 200, 30);
        boxPanel.add(note);

        Runnable refreshDelete = () -> {
            productModel.clear();
            for (Product p : productManager.getProducts()) productModel.addElement(p);

            equipmentModel.clear();
            for (Equipment eq : equipmentManager.getEquipments()) equipmentModel.addElement(eq);

            refreshMainButtons();
        };

        refreshBtn.addActionListener(e -> refreshDelete.run());

        delProductBtn.addActionListener(e -> {
            Product sel = productList.getSelectedValue();
            if (sel != null) {
                int ok = JOptionPane.showConfirmDialog(this, "Delete selected product?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (ok == JOptionPane.YES_OPTION) {
                    productManager.removeProduct(sel);
                    refreshDelete.run();
                }
            } else
                JOptionPane.showMessageDialog(this, "Select a product to delete", "Info", JOptionPane.INFORMATION_MESSAGE);
        });

        delEquipBtn.addActionListener(e -> {
            Equipment sel = equipmentList.getSelectedValue();
            if (sel != null) {
                int ok = JOptionPane.showConfirmDialog(this, "Delete selected equipment?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (ok == JOptionPane.YES_OPTION) {
                    equipmentManager.removeEquipment(sel);
                    refreshDelete.run();
                }
            } else
                JOptionPane.showMessageDialog(this, "Select equipment to delete", "Info", JOptionPane.INFORMATION_MESSAGE);
        });

        backBtn.addActionListener(e -> cards.show(cardPanel, "MAIN"));
        refreshDelete.run();

        boxPanel.add(title);
        boxPanel.add(productLabel);
        boxPanel.add(equipmentLabel);
        boxPanel.add(productScroll);
        boxPanel.add(equipmentScroll);
        boxPanel.add(refreshBtn);
        boxPanel.add(delProductBtn);
        boxPanel.add(delEquipBtn);
        boxPanel.add(backBtn);

        panel.add(boxPanel);
        return panel;
    }

    // Single refreshMainButtons method
    private void refreshMainButtons() {
        boolean allow = productManager.getProducts().size() >= 2 &&
                equipmentManager.getEquipments().size() >= 2;

        if (viewBtn != null) viewBtn.setEnabled(allow);
        if (deleteBtn != null) deleteBtn.setEnabled(allow);
        if (logoutBtn != null) logoutBtn.setEnabled(allow);
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

    private class ProductManager {
        private List<Product> products = new ArrayList<>();

        public void addProduct(Product product) {
            products.add(product);
            refreshMainButtons();
        }

        public List<Product> getProducts() {
            return new ArrayList<>(products);
        }

        public void removeProduct(Product product) {
            products.remove(product);
            refreshMainButtons();
        }

        public boolean hasProductID(int id) {
            for (Product p : products) {
                if (p.getId() == id) {
                    return true; // ID already exists
                }
            }
            return false; // ID is unique
        }

        public int generateUniqueID() {
            int id = 1;
            while (hasProductID(id)) id++;
            return id;
        }


    }

    private class EquipmentManager {
        private List<Equipment> equipments = new ArrayList<>();

        public void addEquipment(Equipment equipment) {
            equipments.add(equipment);
            refreshMainButtons();
        }

        public List<Equipment> getEquipments() {
            return new ArrayList<>(equipments);
        }

        public void removeEquipment(Equipment equipment) {
            equipments.remove(equipment);
            refreshMainButtons();
        }
    }
}

