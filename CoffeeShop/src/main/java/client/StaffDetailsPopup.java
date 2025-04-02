package client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.UUID;

public class StaffDetailsPopup extends JFrame  {

    private JPanel contentPanel;
    private JTextArea detailsArea;
    private JScrollPane detailsScrollPane;
    private JButton exitButton;
    private UUID ID;

    public StaffDetailsPopup(StaffPopupModel SM)  {

        // Ensure the UI window is shown in the center of the screen
        setContentPane(contentPanel);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("Server Details");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300,200);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                close();
            }
        });
    }

    public void addSetListener(ActionListener al) {
        exitButton.setName("ExitBtn");
        exitButton.addActionListener(al);
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

    public void close() {
        StaffDetailsPopup.this.dispose();
        System.out.println("Server has been closed");
    }
}
