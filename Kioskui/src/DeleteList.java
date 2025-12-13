import javax.swing.*;
import java.awt.*;

public class DeleteList extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(
            JList<?> list, Object value, int index,
            boolean isSelected, boolean cellHasFocus) {

        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        try {
            var method = value.getClass().getMethod("toListString");
            setText(method.invoke(value).toString());
        } catch (Exception e) {
            setText(value.toString());
        }
        return this;
    }
}
