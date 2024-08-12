import javax.swing.JPanel;
import javax.swing.JLabel;

public class ProductsPage extends JPanel {
    public ProductsPage(Dashboard dashboard, String action) {
        add(new JLabel("Products Page - " + action));
    }
}
