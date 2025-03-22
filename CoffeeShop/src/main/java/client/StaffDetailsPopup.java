package client;

import javax.swing.*;
import java.util.ArrayList;

public class StaffDetailsPopup extends JFrame {

    private JPanel contentPanel;
    private JTextArea detailsArea;

    public StaffDetailsPopup() {
        // Ensure the UI window is shown in the center of the screen
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void setDetails(ArrayList<String> details) {
        int size = details.size();
        detailsArea.append("Server: " + details.getFirst());
        detailsArea.append("\n" + "Order Customer ID: " + details.get(1));
        for (int i = 2; i < size-2; i++) {
            detailsArea.append("\n" + "Item " + i + ": " + details.get(i));
        }
        detailsArea.append("\n" + "Total: " + details.get((size-2)) + ", " + details.getLast());
    }

    public void exit() {
        StaffDetailsPopup.this.dispose();
    }
}
