package client;

import interfaces.Observer;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionListener;

public class SimulationUI extends JFrame implements Observer {

    private JPanel contentPanel;
    private JTextArea OrderListArea;
    private JTextArea OnlineOrderArea;
    private JComboBox StaffRoleCombo;
    private JComboBox StaffExpCombo;
    private JTextField StaffNameField;
    private JButton StaffSubmitBtn;
    private JSlider SimSpdSlider;
    private JTextField SimSpdField;
    private JLabel SimSpdLabel;
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

    private SimUIModel simModel;

    public SimulationUI(SimUIModel model) {
        simModel = model;

        setContentPane(contentPanel);
        setTitle("Coffee Shop Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,300);
        // Ensure the UI window is shown in the center of the screen
        setLocationRelativeTo(null);
        setVisible(true);
        SimSpdSlider.setValue(2000);
    }

    public String getStaffName() {
        return StaffNameField.getText();
    }

    public String getStaffRole() {
        return StaffRoleCombo.getSelectedItem().toString();
    }

    public String getStaffExp() {
        return StaffExpCombo.getSelectedItem().toString();
    }

    public String getSelectedStaff() {
        return SelectStaffCombo.getSelectedItem().toString();
    }

    public int getSimSpd() {
        return SimSpdSlider.getValue();
    }

    public void addSetListener(ActionListener al, ChangeListener cl) {
        RemoveStaffBtn.addActionListener(al);
        StaffSubmitBtn.addActionListener(al);
        ViewDetailsBtn.addActionListener(al);
        SimSpdSlider.addChangeListener(cl);
    }

    public void update() {

    }
}


