import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RawMaterialPage extends JPanel {
    private JTable rawMaterialTable;
    private DefaultTableModel tableModel;
    private JButton addButton, editButton, deleteButton;
    public RawMaterialPage(Dashboard dashboard, String action) {
        setLayout(new BorderLayout());

        // Initialize table model and JTable
        tableModel = new DefaultTableModel(new Object[]{"Material Name", "Current Quantity", "Used Quantity"}, 0);
        rawMaterialTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(rawMaterialTable);
        add(scrollPane, BorderLayout.CENTER);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add New Raw Material");
        editButton = new JButton("Edit Raw Material");
        deleteButton = new JButton("Delete Raw Material");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Add Action Listeners
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showRawMaterialForm("Add", null);
            }
        });

        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = rawMaterialTable.getSelectedRow();
                if (selectedRow != -1) {
                    String productName = (String) tableModel.getValueAt(selectedRow, 0);
                    String category = (String) tableModel.getValueAt(selectedRow, 1);
                    String price = (String) tableModel.getValueAt(selectedRow, 2);
                    showRawMaterialForm("Edit", new String[]{productName, category, price});
                } else {
                    JOptionPane.showMessageDialog(dashboard, "Please select a raw material to edit.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = rawMaterialTable.getSelectedRow();
                if (selectedRow != -1) {
                    tableModel.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(dashboard, "Please select a raw material to delete.");
                }
            }
        });
    }
    private void showRawMaterialForm(String action, String[] data) {
        JTextField rawMaterialNameField = new JTextField();
        JTextField currentQuantityField = new JTextField();
        JTextField usedQuantityField = new JTextField();

        if (data != null) {
            rawMaterialNameField.setText(data[0]);
            currentQuantityField.setText(data[1]);
            usedQuantityField.setText(data[2]);
        }

        Object[] message = {
                "Raw Material Name:", rawMaterialNameField,
                "Current Quantity", currentQuantityField,
                "Used Quantity:", usedQuantityField
        };

        int option = JOptionPane.showConfirmDialog(this, message, action + " Raw Material", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String productName = rawMaterialNameField.getText();
            String category = currentQuantityField.getText();
            String price = usedQuantityField.getText();

            if (action.equals("Add")) {
                tableModel.addRow(new Object[]{productName, category, price});
            } else if (action.equals("Edit")) {
                int selectedRow = rawMaterialTable.getSelectedRow();
                tableModel.setValueAt(productName, selectedRow, 0);
                tableModel.setValueAt(category, selectedRow, 1);
                tableModel.setValueAt(price, selectedRow, 2);
            }
        }
    }
}
