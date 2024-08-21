import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class OrderPage extends JPanel {
    private Map<String, Table[]> diningOptions;
    private JPanel tablesPanel;
    private JPanel categoriesPanel;
    private JPanel productsPanel;
    private JPanel orderSummaryPanel;
    private Map<String, Category> categories;
    private List<String> selectedProducts;
    private Table currentTable;
    private JButton confirmOrderButton;
    private JButton paidButton;
    private JLabel totalAmountLabel;

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
        categoriesPanel = new JPanel(new GridLayout(0, 2));
        add(categoriesPanel, BorderLayout.WEST);

        // Create a panel for displaying products of selected categories
        productsPanel = new JPanel(new GridLayout(0, 2));
        add(productsPanel, BorderLayout.EAST);

        // Create a panel for order summary (receipt)
        orderSummaryPanel = new JPanel(new BorderLayout());
        add(orderSummaryPanel, BorderLayout.SOUTH);

        // Initialize categories and add products
        initializeCategories();

        // Show tables for the first option by default
        showTables("Dine In");

        // Create order buttons
        confirmOrderButton = new JButton("Confirm Order");
        confirmOrderButton.setEnabled(false);
        confirmOrderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                confirmOrder();
            }
        });

        paidButton = new JButton("Paid");
        paidButton.setEnabled(false);
        paidButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                payAndResetTable();
            }
        });

        totalAmountLabel = new JLabel("Total: PKR 0");
        JPanel orderActionsPanel = new JPanel(new GridLayout(3, 1));
        orderActionsPanel.add(totalAmountLabel);
        orderActionsPanel.add(confirmOrderButton);
        orderActionsPanel.add(paidButton);

        orderSummaryPanel.add(orderActionsPanel, BorderLayout.SOUTH);

        // Reset products and categories view
        showCategories();
    }

    private void initializeCategories() {
        categories = new HashMap<>();

        // Example categories and products
        Category pizza = new Category("Pizza");
        pizza.addProduct("Pepperoni Pizza");
        pizza.addProduct("Margherita Pizza");
        pizza.addProduct("BBQ Chicken Pizza");

        Category burgers = new Category("Burgers");
        burgers.addProduct("Cheeseburger");
        burgers.addProduct("Chicken Burger");
        burgers.addProduct("Veggie Burger");

        Category beverages = new Category("Beverages");
        beverages.addProduct("Coca-Cola");
        beverages.addProduct("Pepsi");
        beverages.addProduct("Lemonade");

        Category appetizers = new Category("Appetizers");
        appetizers.addProduct("Mozzarella Sticks");
        appetizers.addProduct("Garlic Bread");
        appetizers.addProduct("Onion Rings");

        Category deals = new Category("Deals");
        deals.addProduct("Family Deal");
        deals.addProduct("Student Deal");
        deals.addProduct("Party Deal");

        categories.put("Pizza", pizza);
        categories.put("Burgers", burgers);
        categories.put("Beverages", beverages);
        categories.put("Appetizers", appetizers);
        categories.put("Deals", deals);
    }

    private void showCategories() {
        categoriesPanel.removeAll();
        productsPanel.removeAll();
        selectedProducts = new ArrayList<>();

        for (Category category : categories.values()) {
            JButton categoryButton = new JButton(category.getName());
            categoryButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    showProducts(category);
                }
            });
            categoriesPanel.add(categoryButton);
        }

        categoriesPanel.revalidate();
        categoriesPanel.repaint();
    }

    private void showProducts(Category category) {
        productsPanel.removeAll();

        for (String product : category.getProducts()) {
            JCheckBox productCheckBox = new JCheckBox(product);
            productCheckBox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (productCheckBox.isSelected()) {
                        selectedProducts.add(product);
                    } else {
                        selectedProducts.remove(product);
                    }
                    updateOrderSummary();
                }
            });
            productsPanel.add(productCheckBox);
        }

        productsPanel.revalidate();
        productsPanel.repaint();
    }

    private void updateOrderSummary() {
        orderSummaryPanel.removeAll();

        JPanel productsListPanel = new JPanel(new GridLayout(0, 1));
        for (String product : selectedProducts) {
            productsListPanel.add(new JLabel(product));
        }
        orderSummaryPanel.add(productsListPanel, BorderLayout.CENTER);

        // Show total amount (example pricing, can be more sophisticated)
        int totalAmount = selectedProducts.size() * 500; // Assuming each product costs PKR 500
        totalAmountLabel.setText("Total: PKR " + totalAmount);

        confirmOrderButton.setEnabled(!selectedProducts.isEmpty());
        orderSummaryPanel.add(confirmOrderButton, BorderLayout.SOUTH);

        orderSummaryPanel.revalidate();
        orderSummaryPanel.repaint();
    }

    private void confirmOrder() {
        if (currentTable != null) {
            currentTable.placeOrder();
            printReceipt("Order Slip", selectedProducts);
            printReceipt("Kitchen Slip", selectedProducts);
            paidButton.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a table first!");
        }
    }

    private void payAndResetTable() {
        if (currentTable != null) {
            printReceipt("Final Receipt", selectedProducts);
            currentTable.markAsPaid();
            resetOrder();
        }
    }

    private void showTables(String option) {
        tablesPanel.removeAll();

        Table[] tables = diningOptions.get(option);
        for (Table table : tables) {
            JButton tableButton = table.getButton();
            tableButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    currentTable = table;
                    showCategories();
                    productsPanel.removeAll(); // Clear products if a new table is selected
                    orderSummaryPanel.removeAll(); // Reset the order summary if a new table is selected
                    orderSummaryPanel.repaint();
                }
            });
            tablesPanel.add(tableButton);
        }

        tablesPanel.revalidate();
        tablesPanel.repaint();
    }

    private void printReceipt(String title, List<String> products) {
        StringBuilder receipt = new StringBuilder(title + "\n");
        for (String product : products) {
            receipt.append(product).append("\n");
        }
        receipt.append("\nThank you for your order!");
        JOptionPane.showMessageDialog(this, receipt.toString());
    }

    private void resetOrder() {
        selectedProducts.clear();
        currentTable = null;
        paidButton.setEnabled(false);
        totalAmountLabel.setText("Total: PKR 0");
        showCategories();
        productsPanel.removeAll();
        orderSummaryPanel.removeAll();
        orderSummaryPanel.repaint();
    }
}
