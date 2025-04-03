package client;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.util.UUID;

public class StaffDetailsPopup extends JFrame  {

    private JPanel contentPanel;
    private JTextArea detailsArea;
    private JScrollPane detailsScrollPane;
    private JButton exitButton;
    private UUID ID;

    public StaffDetailsPopup(StaffPopupModel SM)  {
        SwingUtilities.invokeLater(() -> {
            // Ensure the UI window is shown in the center of the screen
            setContentPane(contentPanel);
            setLocationRelativeTo(null);
            setVisible(true);
            setTitle("Server Details");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(300, 200);
        });
    }

    public void addSetListener(ActionListener al) {
        SwingUtilities.invokeLater(() -> {
            exitButton.setName("ExitBtn");
            exitButton.addActionListener(al);
        });
    }

    public void addWindowCloseListener(WindowListener wl) {
        this.addWindowListener(wl);
    }

    public void setDetails(String details) {
        SwingUtilities.invokeLater(() -> detailsArea.append(details));
    }

    public void close() {
        SwingUtilities.invokeLater(StaffDetailsPopup.this::dispose);
    }
}
