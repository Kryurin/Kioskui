import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class KitchenPanelGUI extends JFrame {

    private OrderQueue orderQueue;
    private JList<String> queueList;
    private DefaultListModel<String> queueModel;
    private JLabel queueStatusLabel;
    private JButton deliverButton;
    private JTextArea detailsArea;
    private Timer refreshTimer;

    public KitchenPanelGUI(OrderQueue orderQueue) {
        this.orderQueue = orderQueue;

        setTitle("Kitchen Panel");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setBackground(new Color(40, 60, 90));

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(40, 60, 90));
        mainPanel.setLayout(null);

        // Title
        JLabel titleLabel = new JLabel("KITCHEN PANEL", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Consolas", Font.BOLD, 28));
        titleLabel.setBounds(50, 20, 700, 40);
        mainPanel.add(titleLabel);

        // Main content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(new Color(60, 80, 110));
        contentPanel.setBorder(new CompoundBorder(
                new LineBorder(new Color(20, 30, 50), 4),
                new EmptyBorder(20, 20, 20, 20)
        ));
        contentPanel.setLayout(null);
        contentPanel.setBounds(50, 80, 700, 450);

        // Queue label
        JLabel queueLabel = new JLabel("ORDER QUEUE", SwingConstants.CENTER);
        queueLabel.setForeground(Color.WHITE);
        queueLabel.setFont(new Font("Consolas", Font.BOLD, 18));
        queueLabel.setBounds(20, 10, 330, 30);
        contentPanel.add(queueLabel);

        // Queue list
        queueModel = new DefaultListModel<>();
        queueList = new JList<>(queueModel);
        queueList.setFont(new Font("Consolas", Font.PLAIN, 12));
        queueList.setBackground(new Color(80, 100, 130));
        queueList.setForeground(Color.WHITE);
        queueList.setSelectionBackground(new Color(100, 120, 150));

        JScrollPane scrollPane = new JScrollPane(queueList);
        scrollPane.setBounds(20, 50, 330, 240);
        scrollPane.setBackground(new Color(80, 100, 130));
        contentPanel.add(scrollPane);

        queueList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedIndex = queueList.getSelectedIndex();
                if (selectedIndex >= 0 && selectedIndex < orderQueue.getAllOrders().size()) {
                    Order order = orderQueue.getAllOrders().get(selectedIndex);
                    displayOrderDetails(order);
                }
            }
        });

        // Details label
        JLabel detailsLabel = new JLabel("ORDER DETAILS", SwingConstants.CENTER);
        detailsLabel.setForeground(Color.WHITE);
        detailsLabel.setFont(new Font("Consolas", Font.BOLD, 18));
        detailsLabel.setBounds(370, 10, 310, 30);
        contentPanel.add(detailsLabel);

        // Details area
        detailsArea = new JTextArea();
        detailsArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        detailsArea.setBackground(new Color(80, 100, 130));
        detailsArea.setForeground(Color.WHITE);
        detailsArea.setEditable(false);
        detailsArea.setLineWrap(true);
        detailsArea.setWrapStyleWord(true);

        JScrollPane detailsScroll = new JScrollPane(detailsArea);
        detailsScroll.setBounds(370, 50, 310, 240);
        contentPanel.add(detailsScroll);

        // Queue status label
        queueStatusLabel = new JLabel("Queue: 0/" + orderQueue.getMaxSize());
        queueStatusLabel.setForeground(Color.WHITE);
        queueStatusLabel.setFont(new Font("Consolas", Font.BOLD, 14));
        queueStatusLabel.setBounds(20, 305, 200, 25);
        contentPanel.add(queueStatusLabel);

        // Deliver button
        deliverButton = createStyledButton("Deliver Order", 450, 305);
        deliverButton.addActionListener(e -> deliverOrder());
        contentPanel.add(deliverButton);

        mainPanel.add(contentPanel);


        setContentPane(mainPanel);

        // Start refresh timer
        startRefreshTimer();
    }

    private JButton createStyledButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setBackground(new Color(200, 200, 200));
        button.setForeground(Color.DARK_GRAY);
        button.setFont(new Font("Consolas", Font.BOLD, 14));
        button.setBorder(new LineBorder(Color.DARK_GRAY, 4));
        button.setFocusPainted(false);
        button.setBounds(x, y, 150, 35);

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

    private void displayOrderDetails(Order order) {
        StringBuilder details = new StringBuilder();
        details.append("ORDER #").append(order.getOrderId()).append("\n");
        details.append("================\n\n");
        details.append("Items:\n");
        
        List<String> items = order.getItems();
        for (String item : items) {
            details.append("• ").append(item).append("\n");
        }
        
        details.append("\n================\n");
        details.append("Total: P").append(String.format("%.2f", order.getTotalPrice()));
        
        detailsArea.setText(details.toString());
    }

    private void deliverOrder() {
        Order delivered = orderQueue.deliverFirstOrder();
        if (delivered != null) {
            JOptionPane.showMessageDialog(this, 
                    "Order #" + delivered.getOrderId() + " delivered!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            updateQueueDisplay();
            detailsArea.setText("");
        } else {
            JOptionPane.showMessageDialog(this, 
                    "Queue is empty!", 
                    "No Orders", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void updateQueueDisplay() {
        queueModel.clear();
        List<Order> orders = orderQueue.getAllOrders();
        
        if (orders.isEmpty()) {
            queueModel.addElement("--- Queue is empty ---");
        } else {
            for (int i = 0; i < orders.size(); i++) {
                Order order = orders.get(i);
                String status = (i == 0) ? "[NEXT]" : "[" + (i) + "]";
                queueModel.addElement(status + " Order #" + order.getOrderId() + 
                        " - Items: " + order.getItemCount());
            }
        }

        queueStatusLabel.setText("Queue: " + orderQueue.getQueueSize() + "/" + orderQueue.getMaxSize());
        deliverButton.setEnabled(!orders.isEmpty());
        queueList.clearSelection();
    }

    private void startRefreshTimer() {
        refreshTimer = new Timer(500, e -> updateQueueDisplay());
        refreshTimer.start();
    }

    private void stopRefreshTimer() {
        if (refreshTimer != null) {
            refreshTimer.stop();
        }
    }

    @Override
    public void dispose() {
        stopRefreshTimer();
        super.dispose();
    }
}
