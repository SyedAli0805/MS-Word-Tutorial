import javax.swing.JButton;
import java.awt.Color;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        markAsPaid(); // Call the new method
    }

    public void markAsPaid() {
        this.state = "paid";
        updateButtonColor();

        // Reset the table state to "empty" after 2 seconds
        Timer timer = new Timer(2000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetTable();
            }
        });
        timer.setRepeats(false); // Only execute once
        timer.start();
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
