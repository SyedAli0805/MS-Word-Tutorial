import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class OrderPage extends JPanel {
    private Map<String, Table[]> diningOptions;
    private JPanel tablesPanel;

    public OrderPage(Dashboard dashboard) {
        setLayout(new BorderLayout());

        // Initialize dining options with 25 tables each
        diningOptions = new HashMap<>();
        String[] options = {"Dine In", "Family Hall", "Take Away", "Home Delivery", "Car Parking"};
        for (String option : options) {
            Table[] tables = new Table[25];
            for (int i = 0; i < 25; i++) {
                tables[i] = new Table(i + 1);
            }
            diningOptions.put(option, tables);
        }

        // Create a panel for order types
        JPanel orderTypePanel = new JPanel(new GridLayout(1, 5));
        for (String option : options) {
            JButton optionButton = new JButton(option);
            optionButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    showTables(option);
                }
            });
            orderTypePanel.add(optionButton);
        }
        add(orderTypePanel, BorderLayout.NORTH);

        // Create a panel to display tables
        tablesPanel = new JPanel(new GridLayout(5, 5));
        add(tablesPanel, BorderLayout.CENTER);

        // Create a panel for product categories
        JPanel categoriesPanel = new JPanel(new GridLayout(0, 2));
        categoriesPanel.add(new JLabel("Product Category 1"));
        categoriesPanel.add(new JLabel("Product Category 2"));
        categoriesPanel.add(new JLabel("Product Category 3"));
        add(categoriesPanel, BorderLayout.EAST);

        // Show tables for the first option by default
        showTables("Dine In");
    }

    private void showTables(String option) {
        tablesPanel.removeAll();

        Table[] tables = diningOptions.get(option);
        for (Table table : tables) {
            JButton tableButton = table.getButton();
            tableButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    handleTableAction(table);
                }
            });
            tablesPanel.add(tableButton);
        }

        tablesPanel.revalidate();
        tablesPanel.repaint();
    }

    private void handleTableAction(Table table) {
        String[] options = {"Place Order", "Hold Order", "End Order", "Cancel"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Choose an action for " + table.getButton().getText(),
                "Order Management",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        switch (choice) {
            case 0: // Place Order
                table.placeOrder();
                break;
            case 1: // Hold Order
                table.holdOrder();
                break;
            case 2: // End Order
                table.endOrder();
                break;
            default:
                break;
        }
    }
}
