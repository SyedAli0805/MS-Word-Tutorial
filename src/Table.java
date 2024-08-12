import javax.swing.JButton;
import java.awt.Color;

public class Table {
    private int number;
    private JButton button;
    private String state;

    public Table(int number) {
        this.number = number;
        this.button = new JButton("Table " + number);
        this.state = "empty"; // Possible states: empty, busy, unpaid, paid
        updateButtonColor();
    }

    public JButton getButton() {
        return button;
    }

    public void placeOrder() {
        this.state = "busy";
        updateButtonColor();
    }

    public void holdOrder() {
        this.state = "unpaid";
        updateButtonColor();
    }

    public void endOrder() {
        this.state = "paid";
        updateButtonColor();
    }

    public void resetTable() {
        this.state = "empty";
        updateButtonColor();
    }

    private void updateButtonColor() {
        switch (state) {
            case "busy":
                button.setBackground(Color.YELLOW);
                break;
            case "unpaid":
                button.setBackground(Color.ORANGE);
                break;
            case "paid":
                button.setBackground(Color.GREEN);
                break;
            case "empty":
            default:
                button.setBackground(null);
                break;
        }
    }
}
