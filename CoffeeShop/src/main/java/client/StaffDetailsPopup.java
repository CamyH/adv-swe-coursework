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

    public void setDetails(String details) {
        detailsArea.append(details);
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
