package client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StaffDetailsPopup extends JFrame implements ActionListener  {

    private JPanel contentPanel;
    private JTextArea detailsArea;
    private JScrollPane detailsScrollPane;
    private JButton exitButton;

    public StaffDetailsPopup()  {
        // Ensure the UI window is shown in the center of the screen
        setContentPane(contentPanel);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("Server Details");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(300,200);
        exitButton.addActionListener(this);
    }

    public void setDetails(ArrayList<String> details) {
        int size = details.size();
        detailsArea.append("Server: " + details.getFirst() + "\n" + "Experience: " + details.get(1) + "\n");
        if (details.size() < 5) {
            detailsArea.append("Staff is currently waiting for an order");
        } else {
            detailsArea.append("\n" + "Order Customer ID: " + details.get(2));
            for (int i = 3; i < size-2; i++) {
                detailsArea.append("\n" + "Item " + i + ": " + details.get(i));
            }
            detailsArea.append("\n" + "Total: " + details.get((size-2)) + ", " + details.getLast());
        }

    }

    public void exit() {
        StaffDetailsPopup.this.dispose();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exitButton) {
            exit();
        }
    }
}
