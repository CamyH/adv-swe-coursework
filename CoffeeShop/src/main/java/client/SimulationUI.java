package client;

import javax.swing.*;
import java.awt.*;

public class SimulationUI extends JFrame {

    private JPanel contentPanel;
    private JTextArea OrderListArea;
    private JTextArea OnlineOrderArea;
    private JComboBox StaffRoleCombo;
    private JComboBox StaffExpCombo;
    private JTextField StaffNameField;
    private JButton StaffSubmitBtn;
    private JSlider simSpdSlider;
    private JTextField simSpdField;
    private JLabel simSpdLabel;
    private JComboBox SelectStaffCombo;
    private JButton RemoveStaffBtn;
    private JButton ViewDetailsBtn;
    private JLabel OrderListLabel;
    private JLabel OnlineOrderLabel;
    private JScrollPane OrderListScrollPane;
    private JScrollPane OnlineOrderScrollPane;
    private JPanel InputsPanel;
    private JPanel DisplayPanel;
    private JPanel AddStaffPanel;
    private JLabel StaffNameLabel;
    private JLabel StaffRoleLabel;
    private JLabel StaffExpLabel;
    private JPanel SimSpdPanel;

    public SimulationUI() {
        setContentPane(contentPanel);
        setTitle("Coffee Shop Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,300);
        // Ensure the UI window is shown in the center of the screen
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JComboBox getStaffRoleCombo() {
        return StaffRoleCombo;
    }

    public JComboBox getStaffExpCombo() {
        return StaffExpCombo;
    }

    public JTextField getStaffNameField() {
        return StaffNameField;
    }

    public JTextArea getOrderListArea() {
        return OrderListArea;
    }

    public JTextArea getOnlineOrderArea() {
        return OnlineOrderArea;
    }

    public JButton getStaffSubmitBtn() {
        return StaffSubmitBtn;
    }

    public JSlider getSimSpdSlider() {
        return simSpdSlider;
    }

    public JTextField getSimSpdField() {
        return simSpdField;
    }

    public JComboBox getSelectStaffCombo() {
        return SelectStaffCombo;
    }

    public JButton getRemoveStaffBtn() {
        return RemoveStaffBtn;
    }

    public JButton getViewDetailsBtn() {
        return ViewDetailsBtn;
    }
}


