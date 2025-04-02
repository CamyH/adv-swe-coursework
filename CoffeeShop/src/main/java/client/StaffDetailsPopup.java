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

    public void setDetails(String details) {
        detailsArea.append(details);
    }

    public void close() {
        StaffDetailsPopup.this.dispose();
        System.out.println("Server has been closed");
    }
}
