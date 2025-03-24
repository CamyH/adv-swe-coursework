package client;

import interfaces.Observer;
import workers.Staff;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class SimulationUI extends JFrame implements Observer {

    private JPanel contentPanel;
    private JTextArea OrderListArea;
    private JTextArea OnlineOrderArea;
    private JComboBox<String> StaffRoleCombo;
    private JComboBox<Integer> StaffExpCombo;
    private JTextField StaffNameField;
    private JButton AddStaffBtn;
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
    private JButton SimSpdBtn;

    private SimUIModel simModel;

    public SimulationUI(SimUIModel model) {
        model.registerObserver(this);

        simModel = model;

        setContentPane(contentPanel);
        setTitle("Coffee Shop Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,300);

        // Ensure the UI window is shown in the center of the screen
        setLocationRelativeTo(null);
        setVisible(true);

        // Make all non-editable fields un-editable
        OrderListArea.setEnabled(false);
        OnlineOrderArea.setEnabled(false);
        SimSpdField.setEnabled(false);

        // Fill the experience combo box with options
        for (int i = 1; i <= 5; i++) {
            StaffExpCombo.addItem(i);
        }

        // Initial update to populate fields
        update();
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

    public UUID getCurStaff() throws NullPointerException {
        String[] curStaffParts = SelectStaffCombo.getSelectedItem().toString().split(",", 2);
        return UUID.fromString(curStaffParts[1]);
    }

    public int getSimSliderValue() {
        return SimSpdSlider.getValue();
    }

    public void setOrderLists(ArrayList<String> orders, ArrayList<String> onlineOrders) {
        for (String order : orders) {
            OrderListArea.append(order + "\n");
        }
        for (String onlineOrder : onlineOrders) {
            OnlineOrderArea.append(onlineOrder + "\n");
        }
    }

    private void setSimSpeed() {
        // Refresh the sim speed text field
        SimSpdField.setText(String.valueOf(simModel.getSimSpd()));

        // Refresh the sim speed slider
        SimSpdSlider.setValue(simModel.getSimSpd());
    }

    private void setRoles(ArrayList<String> roles) {
        StaffRoleCombo.removeAllItems();
        if (roles != null){
            for (String role : roles) {
                StaffRoleCombo.addItem(role);
            }
        }
    }

    private void setStaffList(HashMap<UUID, Staff> staffList) {
        SelectStaffCombo.removeAllItems();

        // Should work with Stafflist
        staffList.forEach((uuid, curStaff) -> {
            SelectStaffCombo.addItem((curStaff.getWorkerName()) + "," + uuid);
        });
    }

    public void clearCurStaff() {
        StaffNameField.setText("");
    }

    public void addSetListener(ActionListener al) {

        RemoveStaffBtn.setName("RemoveStaffBtn");
        RemoveStaffBtn.addActionListener(al);

        AddStaffBtn.setName("AddStaffBtn");
        AddStaffBtn.addActionListener(al);

        ViewDetailsBtn.setName("ViewDetailsBtn");
        ViewDetailsBtn.addActionListener(al);

        SimSpdBtn.setName("SimSpdBtn");
        SimSpdBtn.addActionListener(al);
    }

    public void update() {

        // Refresh sim speed related fields
        setSimSpeed();

        // Refresh the roles list
        setRoles(simModel.getRoles());

        // Refresh the Staff list
        setStaffList(simModel.getStaffList());

        // Refresh the Order lists
    }

    public void showPopup(String message) {
        JOptionPane.showMessageDialog(SimulationUI.this, message);
    }

    public void close() {
        // close the window
        SimulationUI.this.dispose();
    }
}


