import javax.swing.JPanel;
import javax.swing.JLabel;

public class RawMaterialPage extends JPanel {
    public RawMaterialPage(Dashboard dashboard, String action) {
        add(new JLabel("Raw Material Page - " + action));
    }
}
