import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class AdminPanelExample extends JFrame {
    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS = "1234";

    private CardLayout cards;
    private JPanel menuButton;

    // Managers (SRP - data handling separated from UI)
    private ProductManager productMenu = new ProductManager();
    private EquipmentManager equipmentMenu = new EquipmentManager();

    // Buttons on main menu that need enabling/disabling
    private JButton deleteButton;
    private JButton viewListButton;
    private JButton submitButton;

    public AdminPanelExample() {
        super("Admin Panel");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        cards = new CardLayout();
        menuButton = new JPanel(cards);

        // Create panels
        menuButton.add(new LoginPanel(), "LOGIN");
        menuButton.add(new MainMenuPanel(), "MAIN");
        menuButton.add(new ProductPanel(), "PRODUCT");
        menuButton.add(new EquipmentPanel(), "EQUIPMENT");
        menuButton.add(new DeletePanel(), "DELETE");
        menuButton.add(new ViewListPanel(), "VIEWLIST");
        menuButton.add(new ViewProductPanel(), "VIEWPRODUCT");
        menuButton.add(new ViewEquipmentPanel(), "VIEWEQUIP");

        add(menuButton);
        showCard("LOGIN");
    }

    private void showCard(String name) {
        cards.show(menuButton, name);
        refreshMainButtons();
    }

    private void refreshMainButtons() {
        if (deleteButton != null) {
            boolean show = productMenu.count() >= 2 && equipmentMenu.count() >= 2;
            deleteButton.setEnabled(show);
            viewListButton.setEnabled(show);
            submitButton.setEnabled(show);
        }
    }

    //1.) Login Panel
    class LoginPanel extends JPanel {
        private JTextField userField;
        private JPasswordField passField;

        LoginPanel() {
            setLayout(new GridBagLayout());
            GridBagConstraints label = new GridBagConstraints();
            label.insets = new Insets(8, 12, 5, 8);
            label.gridx = 0; label.gridy = 0;
            add(new JLabel("Username:"), label);
            label.gridx = 1; userField = new JTextField(10); add(userField, label);
            label.gridx = 0; label.gridy = 1; add(new JLabel("Password:"), label);
            label.gridx = 1; passField = new JPasswordField(10); add(passField, label);

            JButton loginButton = new JButton("Log-in");
            label.gridx = 0; label.gridy = 2; label.gridwidth = 2; add(loginButton, label);

            loginButton.addActionListener(e -> {
                String user = userField.getText().trim();
                String pass = new String(passField.getPassword());
                if (ADMIN_USER.equals(user) && ADMIN_PASS.equals(pass)) {
                    showCard("MAIN");
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            });
        }
    }

    // 2) Main Menu Panel (What do you want to do)
    class MainMenuPanel extends JPanel {
        MainMenuPanel() {
            setLayout(new BorderLayout());
            JPanel top = new JPanel(new GridLayout(0, 1, 5,4));

            JButton addProductBtn = new JButton("Add Product");
            JButton addEquipBtn = new JButton("Add Equipment");
            deleteButton = new JButton("Delete Product");
            JButton createMembershipBtn = new JButton("Create Membership Plan");
            viewListButton = new JButton("View List");
            submitButton = new JButton("Submit (Exit)");

            // check if we added 3 prods and 3 equips to enable buttons
            deleteButton.setEnabled(false);
            viewListButton.setEnabled(false);
            submitButton.setEnabled(false);

            top.add(addProductBtn);
            top.add(addEquipBtn);
            top.add(deleteButton);
            top.add(createMembershipBtn);
            top.add(viewListButton);
            top.add(submitButton);

            add(top, BorderLayout.CENTER);

            addProductBtn.addActionListener(e -> showCard("PRODUCT"));
            addEquipBtn.addActionListener(e -> showCard("EQUIPMENT"));
            deleteButton.addActionListener(e -> showCard("DELETE"));

            createMembershipBtn.addActionListener(e -> {
                String name = JOptionPane.showInputDialog(this, "Enter membership plan name:");
                if (name != null && !name.trim().isEmpty()) {
                    String discStr = JOptionPane.showInputDialog(this, "Enter discount percentage (e.g. 10):");
                        int disc = Integer.parseInt(discStr);
                        JOptionPane.showMessageDialog(this, "Membership plan '" + name + "' created with " + disc + "% discount on all Product.");
                }
            });

            viewListButton.addActionListener(e -> showCard("VIEWLIST"));

            submitButton.addActionListener(e -> {
                int ok = JOptionPane.showConfirmDialog(this, "Submit and exit?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (ok == JOptionPane.YES_OPTION) System.exit(0);
            });
        }
    }

    // 3) Product Panel
    class ProductPanel extends JPanel {
        private JTextField nameField, idField, priceField;
        private JComboBox<String> categoryBox;

        ProductPanel() {
            setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.insets = new Insets(6,6,6,6);
            c.gridx = 0; c.gridy = 0; add(new JLabel("Product Name:"), c);
            c.gridx = 1; nameField = new JTextField(20); add(nameField, c);
            c.gridx = 0; c.gridy = 1; add(new JLabel("Product ID:"), c);
            c.gridx = 1; idField = new JTextField(20); add(idField, c);
            c.gridx = 0; c.gridy = 2; add(new JLabel("Price:"), c);
            c.gridx = 1; priceField = new JTextField(20); add(priceField, c);
            c.gridx = 0; c.gridy = 3; add(new JLabel("Category:"), c);
            c.gridx = 1; categoryBox = new JComboBox<>(new String[]{"Food", "Dessert", "Drink"}); add(categoryBox, c);

            JButton addBtn = new JButton("Add product");
            JButton backBtn = new JButton("Back");
            c.gridx = 0; c.gridy = 4; add(addBtn, c);
            c.gridx = 1; add(backBtn, c);

            addBtn.addActionListener(e -> {
                String name = nameField.getText().trim();
                String id = idField.getText().trim();
                String priceStr = priceField.getText().trim();
                String cat = (String)categoryBox.getSelectedItem();
                if (name.isEmpty() || id.isEmpty() || priceStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Missing", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                double price;
                try { price = Double.parseDouble(priceStr); }
                catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Invalid price."); return; }

                Product p;
                // Polymorphism & inheritance: create specific product subtype
                switch (cat) {
                    case "Food": p = new Food(name, id, price); break;
                    case "Dessert": p = new Dessert(name, id, price); break;
                    default: p = new Drink(name, id, price); break;
                }
                productMenu.addProduct(p);

                // Notification: "Product added, add more?" with Yes/No/Exit
                Object[] options = {"Yes", "No", "Exit"};
                int choice = JOptionPane.showOptionDialog(this,
                        "Product added, add more?",
                        "Added",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        options,
                        options[0]);

                if (choice == 0) { // Yes: clear fields for next
                    nameField.setText(""); idField.setText(""); priceField.setText("");
                } else if (choice == 1) { // No: back to main
                    showCard("MAIN");
                } else { // Exit: back to main
                    showCard("MAIN");
                }
                refreshMainButtons();
            });

            backBtn.addActionListener(e -> showCard("MAIN"));
        }
    }

    // 4) Equipment Panel
    class EquipmentPanel extends JPanel {
        private JTextField nameField, idField;
        private JTextField descField;

        EquipmentPanel() {
            setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.insets = new Insets(6,6,6,6);
            c.gridx = 0; c.gridy = 0; add(new JLabel("Equipment Name:"), c);
            c.gridx = 1; nameField = new JTextField(20); add(nameField, c);
            c.gridx = 0; c.gridy = 1; add(new JLabel("Equipment ID:"), c);
            c.gridx = 1; idField = new JTextField(20); add(idField, c);
            c.gridx = 0; c.gridy = 2; add(new JLabel("Description:"), c);
            c.gridx = 1; descField = new JTextField(20); add(descField, c);

            JButton addBtn = new JButton("Add equipment");
            JButton backBtn = new JButton("Back");
            c.gridx = 0; c.gridy = 3; add(addBtn, c);
            c.gridx = 1; add(backBtn, c);

            addBtn.addActionListener(e -> {
                String name = nameField.getText().trim();
                String id = idField.getText().trim();
                String desc = descField.getText().trim();
                if (name.isEmpty() || id.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill in required fields.");
                    return;
                }
                Equipment eq = new Equipment(name, id, desc);
                equipmentMenu.addEquipment(eq);

                Object[] options = {"Yes", "No", "Exit"};
                int choice = JOptionPane.showOptionDialog(this,
                        "Equipment added, add more?",
                        "Added",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        options,
                        options[0]);

                if (choice == 0) { // Yes clear
                    nameField.setText(""); idField.setText(""); descField.setText("");
                } else { // No or Exit: back to main
                    showCard("MAIN");
                }
                refreshMainButtons();
            });

            backBtn.addActionListener(e -> showCard("MAIN"));
        }
    }

    // 5) Delete Panel
    class DeletePanel extends JPanel {
        private DefaultListModel<Product> listModel = new DefaultListModel<>();
        private JList<Product> jList;

        DeletePanel() {
            setLayout(new BorderLayout(8,8));
            JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
            top.add(new JLabel("Products:"));
            add(top, BorderLayout.NORTH);

            jList = new JList<>(listModel);
            JScrollPane sp = new JScrollPane(jList);
            add(sp, BorderLayout.CENTER);

            JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton deleteBtn = new JButton("Delete Selected");
            JButton exitBtn = new JButton("Exit");
            south.add(deleteBtn); south.add(exitBtn);
            add(south, BorderLayout.SOUTH);

            // populate list when shown
            addComponentListener(new ComponentAdapter() {
                public void componentShown(ComponentEvent e) { refreshList(); }
            });

            deleteBtn.addActionListener(e -> {
                Product sel = jList.getSelectedValue();
                if (sel == null) { JOptionPane.showMessageDialog(this, "Select a product first."); return; }
                int ok = JOptionPane.showConfirmDialog(this, "Delete product: " + sel.getName() + "?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (ok == JOptionPane.YES_OPTION) {
                    productMenu.removeProductById(sel.getId());
                    refreshList();
                    refreshMainButtons();
                }
            });

            exitBtn.addActionListener(e -> showCard("MAIN"));
        }

        private void refreshList() {
            listModel.clear();
            for (Product p : productMenu.getAll()) listModel.addElement(p);
        }
    }

    // 6) View List Panel
    class ViewListPanel extends JPanel {
        ViewListPanel() {
            setLayout(new FlowLayout());
            JButton viewProd = new JButton("View Product");
            JButton viewEquip = new JButton("View Equipment");
            JButton exit = new JButton("Exit");
            add(viewProd); add(viewEquip); add(exit);

            viewProd.addActionListener(e -> showCard("VIEWPRODUCT"));
            viewEquip.addActionListener(e -> showCard("VIEWEQUIP"));
            exit.addActionListener(e -> showCard("MAIN"));
        }
    }

    // 7) View Product Panel (sorted by category)
    class ViewProductPanel extends JPanel {
        private JTextArea area;
        ViewProductPanel() {
            setLayout(new BorderLayout());
            area = new JTextArea(); area.setEditable(false);
            add(new JScrollPane(area), BorderLayout.CENTER);
            JButton exit = new JButton("Exit");
            add(exit, BorderLayout.SOUTH);

            addComponentListener(new ComponentAdapter() {
                public void componentShown(ComponentEvent e) { refreshView(); }
            });

            exit.addActionListener(e -> showCard("VIEWLIST"));
        }

        private void refreshView() {
            area.setText("");
            // Group products by category
            Map<String, List<Product>> map = new TreeMap<>();
            for (Product p : productMenu.getAll()) {
                map.computeIfAbsent(p.getCategory(), k -> new ArrayList<>()).add(p);
            }
            if (map.isEmpty()) { area.setText("No products added."); return; }
            StringBuilder sb = new StringBuilder();
            for (String cat : map.keySet()) {
                sb.append("== " + cat + " ==\n");
                for (Product p : map.get(cat)) sb.append(p + "\n");
                sb.append("\n");
            }
            area.setText(sb.toString());
        }
    }

    // 😎 View Equipment Panel
    class ViewEquipmentPanel extends JPanel {
        private JTextArea area;
        ViewEquipmentPanel() {
            setLayout(new BorderLayout());
            area = new JTextArea(); area.setEditable(false);
            add(new JScrollPane(area), BorderLayout.CENTER);
            JButton exit = new JButton("Exit");
            add(exit, BorderLayout.SOUTH);

            addComponentListener(new ComponentAdapter() {
                public void componentShown(ComponentEvent e) { refreshView(); }
            });

            exit.addActionListener(e -> showCard("VIEWLIST"));
        }

        private void refreshView() {
            area.setText("");
            List<Equipment> list = equipmentMenu.getAll();
            if (list.isEmpty()) { area.setText("No equipment added."); return; }
            StringBuilder sb = new StringBuilder();
            for (Equipment eq : list) sb.append(eq + "\n");
            area.setText(sb.toString());
        }
    }

    // --- Data classes & managers ---

    // Abstraction + Inheritance: Product is abstract; specific product types extend it.
    abstract class Product {
        private String name;
        private String id;
        private double price;

        public Product(String name, String id, double price) {
            this.name = name; this.id = id; this.price = price;
        }

        // Encapsulation: private fields with getters
        public String getName() { return name; }
        public String getId() { return id; }
        public double getPrice() { return price; }

        public abstract String getCategory();

        @Override
        public String toString() {
            return String.format("%s (ID:%s) - %.2f", name, id, price);
        }
    }

    class Food extends Product { public Food(String n,String i,double p){super(n,i,p);} public String getCategory(){return "Food";} }
    class Dessert extends Product { public Dessert(String n,String i,double p){super(n,i,p);} public String getCategory(){return "Dessert";} }
    class Drink extends Product { public Drink(String n,String i,double p){super(n,i,p);} public String getCategory(){return "Drink";} }

    // Equipment class (encapsulation)
    class Equipment {
        private String name;
        private String id;
        private String description;
        public Equipment(String n,String i,String d){name=n;id=i;description=d;}
        public String getName(){return name;} public String getId(){return id;} public String getDescription(){return description;}
        @Override public String toString(){ return String.format("%s (ID:%s) - %s", name, id, description==null?"":description); }
    }

    // Managers: handle collections; single responsibility (data management)
    class ProductManager {
        private List<Product> products = new ArrayList<>();
        public void addProduct(Product p){ products.add(p); }
        public List<Product> getAll(){ return Collections.unmodifiableList(products); }
        public void removeProductById(String id){ products.removeIf(p -> p.getId().equals(id)); }
        public Product findById(String id){ for (Product p:products) if (p.getId().equals(id)) return p; return null; }
        public int count(){ return products.size(); }
    }

    class EquipmentManager {
        private List<Equipment> items = new ArrayList<>();
        public void addEquipment(Equipment e){ items.add(e); }
        public List<Equipment> getAll(){ return Collections.unmodifiableList(items); }
        public void removeById(String id){ items.removeIf(e -> e.getId().equals(id)); }
        public int count(){ return items.size(); }
    }

    // --- Main ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminPanelExample app = new AdminPanelExample();
            app.setVisible(true);
        });
    }
}