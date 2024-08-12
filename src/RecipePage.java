import javax.swing.JPanel;
import javax.swing.JLabel;

public class RecipePage extends JPanel {
    public RecipePage(Dashboard dashboard, String action) {
        add(new JLabel("Recipe Page - " + action));
    }
}
