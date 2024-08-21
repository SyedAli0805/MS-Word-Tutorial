import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RecipePage extends JPanel {
    private JTable recipeTable;
    private DefaultTableModel tableModel;
    private JButton addButton, editButton, deleteButton;

    public RecipePage(Dashboard dashboard, String action) {
        setLayout(new BorderLayout());

        // Initialize table model and JTable
        tableModel = new DefaultTableModel(new Object[]{"Recipe Name", "Materials", "Quantities"}, 0);
        recipeTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(recipeTable);
        add(scrollPane, BorderLayout.CENTER);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add New Recipe");
        editButton = new JButton("Edit Recipe");
        deleteButton = new JButton("Delete Recipe");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Add Action Listeners
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showRecipeForm("Add", null);
            }
        });

        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = recipeTable.getSelectedRow();
                if (selectedRow != -1) {
                    String recipeName = (String) tableModel.getValueAt(selectedRow, 0);
                    String materials = (String) tableModel.getValueAt(selectedRow, 1);
                    String quantities = (String) tableModel.getValueAt(selectedRow, 2);
                    showRecipeForm("Edit", new String[]{recipeName, materials, quantities});
                } else {
                    JOptionPane.showMessageDialog(dashboard, "Please select a recipe to edit.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = recipeTable.getSelectedRow();
                if (selectedRow != -1) {
                    tableModel.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(dashboard, "Please select a recipe to delete.");
                }
            }
        });
    }

    private void showRecipeForm(String action, String[] data) {
        JTextField recipeNameField = new JTextField();
        DefaultTableModel materialsModel = new DefaultTableModel(new Object[]{"Material", "Quantity"}, 0);
        JTable materialsTable = new JTable(materialsModel);

        // If editing, populate the fields
        if (data != null) {
            recipeNameField.setText(data[0]);

            String[] materials = data[1].split(",");
            String[] quantities = data[2].split(",");
            for (int i = 0; i < materials.length; i++) {
                materialsModel.addRow(new Object[]{materials[i], quantities[i]});
            }
        }

        // Panel for adding materials
        JPanel materialPanel = new JPanel(new BorderLayout());
        materialPanel.add(new JScrollPane(materialsTable), BorderLayout.CENTER);

        JButton addMaterialButton = new JButton("Add Material");
        addMaterialButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                materialsModel.addRow(new Object[]{"", ""});
            }
        });

        materialPanel.add(addMaterialButton, BorderLayout.SOUTH);

        Object[] message = {
                "Recipe Name:", recipeNameField,
                "Materials and Quantities:", materialPanel
        };

        int option = JOptionPane.showConfirmDialog(this, message, action + " Recipe", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String recipeName = recipeNameField.getText();

            ArrayList<String> materialsList = new ArrayList<>();
            ArrayList<String> quantitiesList = new ArrayList<>();

            for (int i = 0; i < materialsModel.getRowCount(); i++) {
                String material = (String) materialsModel.getValueAt(i, 0);
                String quantity = (String) materialsModel.getValueAt(i, 1);
                materialsList.add(material);
                quantitiesList.add(quantity);
            }

            String materials = String.join(",", materialsList);
            String quantities = String.join(",", quantitiesList);

            if (action.equals("Add")) {
                tableModel.addRow(new Object[]{recipeName, materials, quantities});
            } else if (action.equals("Edit")) {
                int selectedRow = recipeTable.getSelectedRow();
                tableModel.setValueAt(recipeName, selectedRow, 0);
                tableModel.setValueAt(materials, selectedRow, 1);
                tableModel.setValueAt(quantities, selectedRow, 2);
            }
        }
    }
}
