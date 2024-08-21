import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

public class ProductsPage extends JPanel {
    private JTable productsTable;
    private DefaultTableModel tableModel;
    private JButton addButton, editButton, deleteButton;

    public ProductsPage(Dashboard dashboard, String action) {
        setLayout(new BorderLayout());

        // Initialize table model and JTable
        tableModel = new DefaultTableModel(new Object[]{"Product Name", "Category", "Price"}, 0);
        productsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(productsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add New Product");
        editButton = new JButton("Edit Product");
        deleteButton = new JButton("Delete Product");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Add Action Listeners
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showProductForm("Add", null);
            }
        });

        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = productsTable.getSelectedRow();
                if (selectedRow != -1) {
                    String productName = (String) tableModel.getValueAt(selectedRow, 0);
                    String category = (String) tableModel.getValueAt(selectedRow, 1);
                    String price = (String) tableModel.getValueAt(selectedRow, 2);
                    showProductForm("Edit", new String[]{productName, category, price});
                } else {
                    JOptionPane.showMessageDialog(dashboard, "Please select a product to edit.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = productsTable.getSelectedRow();
                if (selectedRow != -1) {
                    tableModel.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(dashboard, "Please select a product to delete.");
                }
            }
        });
    }

    private void showProductForm(String action, String[] data) {
        JTextField productNameField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextField priceField = new JTextField();

        if (data != null) {
            productNameField.setText(data[0]);
            categoryField.setText(data[1]);
            priceField.setText(data[2]);
        }

        Object[] message = {
                "Product Name:", productNameField,
                "Category:", categoryField,
                "Price:", priceField
        };

        int option = JOptionPane.showConfirmDialog(this, message, action + " Product", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String productName = productNameField.getText();
            String category = categoryField.getText();
            String price = priceField.getText();

            if (action.equals("Add")) {
                tableModel.addRow(new Object[]{productName, category, price});
            } else if (action.equals("Edit")) {
                int selectedRow = productsTable.getSelectedRow();
                tableModel.setValueAt(productName, selectedRow, 0);
                tableModel.setValueAt(category, selectedRow, 1);
                tableModel.setValueAt(price, selectedRow, 2);
            }
        }
    }
}
