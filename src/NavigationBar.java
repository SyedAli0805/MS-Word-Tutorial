import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NavigationBar extends JMenuBar {
    public NavigationBar(Dashboard dashboard) {
        // Home menu
        JMenu homeMenu = new JMenu("Home");
        JMenuItem homeItem = new JMenuItem("Home");
        homeItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dashboard.showPage(new HomePage(dashboard));
            }
        });
        homeMenu.add(homeItem);
        add(homeMenu);

        // Order menu
        JMenu orderMenu = new JMenu("Order");
        JMenuItem orderItem = new JMenuItem("Order");
        orderItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dashboard.showPage(new OrderPage(dashboard));
            }
        });
        orderMenu.add(orderItem);
        add(orderMenu);

        // Products menu
        JMenu productsMenu = new JMenu("Products");
        JMenuItem showProductsItem = new JMenuItem("Show All Products");
        showProductsItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dashboard.showPage(new ProductsPage(dashboard, "Show All Products"));
            }
        });
        productsMenu.add(showProductsItem);
        JMenuItem addProductItem = new JMenuItem("Add New Product");
        addProductItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dashboard.showPage(new ProductsPage(dashboard, "Add New Product"));
            }
        });
        productsMenu.add(addProductItem);
        add(productsMenu);

        // Raw Material menu
        JMenu rawMaterialMenu = new JMenu("Raw Material");
        JMenuItem showRawMaterialItem = new JMenuItem("Show All Raw Material");
        showRawMaterialItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dashboard.showPage(new RawMaterialPage(dashboard, "Show All Raw Material"));
            }
        });
        rawMaterialMenu.add(showRawMaterialItem);
        JMenuItem addRawMaterialItem = new JMenuItem("Add New Raw Material");
        addRawMaterialItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dashboard.showPage(new RawMaterialPage(dashboard, "Add New Raw Material"));
            }
        });
        rawMaterialMenu.add(addRawMaterialItem);
        add(rawMaterialMenu);

        // Recipe menu
        JMenu recipeMenu = new JMenu("Recipe");
        JMenuItem showRecipesItem = new JMenuItem("Show All Recipes");
        showRecipesItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dashboard.showPage(new RecipePage(dashboard, "Show All Recipes"));
            }
        });
        recipeMenu.add(showRecipesItem);
        JMenuItem addRecipeItem = new JMenuItem("Add New Recipe");
        addRecipeItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dashboard.showPage(new RecipePage(dashboard, "Add New Recipe"));
            }
        });
        recipeMenu.add(addRecipeItem);
        add(recipeMenu);

        // File menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem orderReport = new JMenuItem("Show Order Report");
        orderReport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dashboard.showPage(new FilePage(dashboard, "Show Order Report"));
            }
        });

        fileMenu.add(orderReport);

        JMenuItem salesReport = new JMenuItem("Show Sales Report");
        salesReport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dashboard.showPage(new FilePage(dashboard,"Show Sales Report"));
            }
        });

        fileMenu.add(salesReport);

        JMenuItem formulatedConsumptionReport = new JMenuItem("Show Formulated Consumption Report");
        formulatedConsumptionReport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dashboard.showPage(new FilePage(dashboard,"Show Formulated Consumption Report"));
            }
        });

        fileMenu.add(formulatedConsumptionReport);

        add(fileMenu);
    }
}
