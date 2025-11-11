import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AdminPanelSystem extends JFrame {
    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS = "1234";
    
    private CardLayout cards;
    private JPanel cardPanel;
    private ProductManager productManager;
    private EquipmentManager equipmentManager;

    public AdminPanelSystem() {
        setTitle("Admin Panel");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Initialize managers
        productManager = new ProductManager();
        equipmentManager = new EquipmentManager();

        // Setup card layout
        cards = new CardLayout();
        cardPanel = new JPanel(cards);
        
        // Add all panels
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
        // Main background color
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(40, 60, 90));
        mainPanel.setLayout(null);

        // Panel border style 
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

        // Add hover effect
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

    // Manager classes
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
        
        // Product input fields
        JTextField nameField = new JTextField();
        JTextField idField = new JTextField();
        JTextField priceField = new JTextField();
        String[] categories = {"Food", "Drink", "Dessert"};
        JComboBox<String> categoryBox = new JComboBox<>(categories);
        
        // Style input fields
        nameField.setBounds(150, 100, 200, 30);
        idField.setBounds(150, 150, 200, 30);
        priceField.setBounds(150, 200, 200, 30);
        categoryBox.setBounds(150, 250, 200, 30);
        
        // Labels
        JLabel nameLabel = new JLabel("Name:");
        JLabel idLabel = new JLabel("ID:");
        JLabel priceLabel = new JLabel("Price:");
        JLabel categoryLabel = new JLabel("Category:");
        
        nameLabel.setForeground(Color.WHITE);
        idLabel.setForeground(Color.WHITE);
        priceLabel.setForeground(Color.WHITE);
        categoryLabel.setForeground(Color.WHITE);
        
        nameLabel.setBounds(50, 100, 100, 30);
        idLabel.setBounds(50, 150, 100, 30);
        priceLabel.setBounds(50, 200, 100, 30);
        categoryLabel.setBounds(50, 250, 100, 30);
        
        // Buttons
        JButton addButton = createStyledButton("Add Product", 150, 300);
        JButton backButton = createStyledButton("Back", 150, 350);
        
        addButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String id = idField.getText();
                double price = Double.parseDouble(priceField.getText());
                String category = (String) categoryBox.getSelectedItem();
                
                Product product;
                switch (category) {
                    case "Food":
                        product = new Food(name, id, price);
                        break;
                    case "Drink":
                        product = new Drink(name, id, price);
                        break;
                    case "Dessert":
                        product = new Dessert(name, id, price);
                        break;
                    default:
                        return;
                }
                productManager.addProduct(product);
                
                // Clear fields
                nameField.setText("");
                idField.setText("");
                priceField.setText("");
                
                JOptionPane.showMessageDialog(this, 
                    "Product added successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                    "Please enter a valid price",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        backButton.addActionListener(e -> cards.show(cardPanel, "MAIN"));
        
        // Add components
        boxPanel.add(title);
        boxPanel.add(nameLabel);
        boxPanel.add(nameField);
        boxPanel.add(idLabel);
        boxPanel.add(idField);
        boxPanel.add(priceLabel);
        boxPanel.add(priceField);
        boxPanel.add(categoryLabel);
        boxPanel.add(categoryBox);
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
        
        // Equipment input fields
        JTextField nameField = new JTextField();
        JTextField descField = new JTextField();
        
        // Style input fields
        nameField.setBounds(150, 100, 200, 30);
        descField.setBounds(150, 150, 200, 30);
        
        // Labels
        JLabel nameLabel = new JLabel("Name:");
        JLabel descLabel = new JLabel("Description:");
        
        nameLabel.setForeground(Color.WHITE);
        descLabel.setForeground(Color.WHITE);
        
        nameLabel.setBounds(50, 100, 100, 30);
        descLabel.setBounds(50, 150, 100, 30);
        
        // Buttons
        JButton addButton = createStyledButton("Add Equipment", 150, 200);
        JButton backButton = createStyledButton("Back", 150, 250);
        
        addButton.addActionListener(e -> {
            String name = nameField.getText();
            Equipment equipment = new Equipment(name);
            equipmentManager.addEquipment(equipment);
            
            // Clear fields
            nameField.setText("");
            descField.setText("");
            
            JOptionPane.showMessageDialog(this,
                "Equipment added successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
        });
        
        backButton.addActionListener(e -> cards.show(cardPanel, "MAIN"));
        
        // Add components
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
        
        // Create text areas for products and equipment
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
        
        refreshButton.addActionListener(e -> {
            // Update product list
            StringBuilder prodStr = new StringBuilder();
            for (Product p : productManager.getProducts()) {
                prodStr.append(p.toString()).append("\n");
            }
            productArea.setText(prodStr.toString());
            
            // Update equipment list
            StringBuilder equipStr = new StringBuilder();
            for (Equipment eq : equipmentManager.getEquipments()) {
                equipStr.append(eq.toString()).append("\n");
            }
            equipmentArea.setText(equipStr.toString());
        });
        
        backButton.addActionListener(e -> cards.show(cardPanel, "MAIN"));
        
        // Add components
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
        )) ;
        boxPanel.setLayout(null);
        boxPanel.setBounds(50, 50, 700, 450);

        JLabel title = new JLabel("DELETE ITEMS", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Consolas", Font.BOLD, 20));
        title.setBounds(250, 10, 200, 30);

        // Product list
        DefaultListModel<Product> productModel = new DefaultListModel<>();
        JList<Product> productList = new JList<>(productModel);
        JScrollPane productScroll = new JScrollPane(productList);
        productScroll.setBounds(50, 70, 300, 300);

        // Equipment list
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

        // Buttons
        JButton refreshBtn = createStyledButton("Refresh", 100, 410);
        JButton delProductBtn = createStyledButton("Delete Product", 100, 370);
        JButton delEquipBtn = createStyledButton("Delete Equipment", 420, 370);
        JButton backBtn = createStyledButton("Back", 420, 410);

        // Refresh action
        refreshBtn.addActionListener(e -> {
            productModel.clear();
            for (Product p : productManager.getProducts()) productModel.addElement(p);

            equipmentModel.clear();
            for (Equipment eq : equipmentManager.getEquipments()) equipmentModel.addElement(eq);
        });

        delProductBtn.addActionListener(e -> {
            Product sel = productList.getSelectedValue();
            if (sel != null) {
                int ok = JOptionPane.showConfirmDialog(this, "Delete selected product?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (ok == JOptionPane.YES_OPTION) {
                    productManager.removeProduct(sel);
                    productModel.removeElement(sel);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Select a product to delete", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        delEquipBtn.addActionListener(e -> {
            Equipment sel = equipmentList.getSelectedValue();
            if (sel != null) {
                int ok = JOptionPane.showConfirmDialog(this, "Delete selected equipment?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (ok == JOptionPane.YES_OPTION) {
                    equipmentManager.removeEquipment(sel);
                    equipmentModel.removeElement(sel);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Select equipment to delete", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        backBtn.addActionListener(e -> cards.show(cardPanel, "MAIN"));

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
    
    private class ProductManager {
        private List<Product> products = new ArrayList<>();
        
        public void addProduct(Product product) {
            products.add(product);
        }
        
        public List<Product> getProducts() {
            return new ArrayList<>(products);
        }
        
        public void removeProduct(Product product) {
            products.remove(product);
        }
    }
    
    private class EquipmentManager {
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
        
    // Create menu buttons
    JButton productBtn = createStyledButton("Manage Products", 50, 100);
    JButton equipmentBtn = createStyledButton("Manage Equipment", 50, 170);
    JButton viewBtn = createStyledButton("View Items", 50, 240);
    JButton deleteBtn = createStyledButton("Delete Items", 50, 310);
    JButton logoutBtn = createStyledButton("Logout", 50, 380);
        
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminPanelSystem().setVisible(true));
    }
}
