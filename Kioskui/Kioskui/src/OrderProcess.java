import javax.swing.*;
import java.util.List;

public class OrderProcess {

    public static boolean placeOrder(JFrame parent,
                                     List<Product> products,
                                     List<Integer> quantities,
                                     List<Equipment> equipments,
                                     DefaultListModel<String> orderModel,
                                     JButton refreshButton) {

        if (orderModel.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Order is empty. Add products first.",
                    "Empty Order", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Build order summary and validate quantities
        StringBuilder summary = new StringBuilder();
        summary.append("Order Items:\n");
        boolean invalidQty = false;

        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            int q = quantities.get(i);

            if (q > p.getQuantity()) {
                invalidQty = true;
                summary.append("- ").append(p.getName())
                        .append(": Requested ").append(q)
                        .append(", Available ").append(p.getQuantity())
                        .append("\n");
            } else {
                summary.append("- ").append(q).append(" x ").append(p.getName())
                        .append(" - P").append(String.format("%.2f", p.getPrice()))
                        .append(" each\n");
            }
        }

        if (invalidQty) {
            JOptionPane.showMessageDialog(parent, "Some items exceed available stock:\n" + summary,
                    "Invalid Quantity", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Calculate total price
        double total = 0.0;
        for (int i = 0; i < products.size(); i++) {
            total += products.get(i).getPrice() * quantities.get(i);
        }

        // Include equipments in summary (free)
        for (Equipment eq : equipments) {
            summary.append("- ").append(eq.getName()).append(" - Free (Borrowed)\n");
        }

        summary.append(String.format("\nTotal Price: P%.2f\n", total));

        // Confirm order
        int confirm = JOptionPane.showConfirmDialog(parent, summary.toString(),
                "Confirm Order", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            // Deduct purchased quantities
            for (int i = 0; i < products.size(); i++) {
                products.get(i).reduceQuantity(quantities.get(i));
            }

            JOptionPane.showMessageDialog(parent, "Order placed successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            // Clear order lists
            orderModel.clear();
            products.clear();
            equipments.clear();
            quantities.clear();

            // Refresh the product list
            refreshButton.doClick();

            return true;
        }

        return false;
    }
}
