package client;

import interfaces.Observer;
import workers.StaffList;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.UUID;

public class SimUIView extends JFrame implements Observer {

    private JPanel contentPanel;
    private JTextArea OrderListArea;
    private JTextArea OnlineOrderArea;
    private JComboBox<String> StaffRoleCombo;
    private JComboBox<Integer> StaffExpCombo;
    private JTextField StaffNameField;
    private JButton AddStaffBtn;
    private JSlider SimSpeedSlider;
    private JTextField SimSpeedField;
    private JLabel SimSpeedLabel;
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
    private JPanel SimSpeedPanel;
    private JButton PopOrderListBtn;
    private JScrollPane CompleteOrderScrollPane;
    private JTextArea CompleteOrderArea;
    private JScrollPane ProcessedOrdersScrollPane;
    private JTextArea ProcessedOrderArea;

    private SimUIModel simModel;
    private static SimUIView instance;

    public SimUIView(SimUIModel simModel) {
        this.simModel = simModel;
        simModel.registerObserver(this);
        setContentPane(contentPanel);
        setTitle("Coffee Shop Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,500);

        // Ensure the UI window is shown in the center of the screen
        setLocationRelativeTo(null);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Demo.cleanUp();
            }
        });

        // Make all non-editable fields un-editable
        OrderListArea.setEnabled(false);
        OrderListArea.setDisabledTextColor(Color.BLACK);
        OnlineOrderArea.setEnabled(false);
        OnlineOrderArea.setDisabledTextColor(Color.BLACK);
        SimSpeedField.setEnabled(false);
        SimSpeedField.setDisabledTextColor(Color.BLACK);
        CompleteOrderArea.setEnabled(false);
        CompleteOrderArea.setDisabledTextColor(Color.BLACK);
        ProcessedOrderArea.setEnabled(false);
        ProcessedOrderArea.setDisabledTextColor(Color.BLACK);

        // Fill the experience combo box with options
        for (int i = 1; i <= 5; i++) {
            StaffExpCombo.addItem(i);
        }

        setRoles(simModel.getRoles());

        // Initial update to populate fields
        update();
    }

    public void addSimSpeedChangeListener(ChangeListener listener) {
        SimSpeedSlider.addChangeListener(listener);
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
        String[] curStaffParts = SelectStaffCombo.getSelectedItem().toString().split(", ", 3);
        return UUID.fromString(curStaffParts[2]);
    }

    public int getSimSliderValue() {
        return SimSpeedSlider.getValue();
    }

    public void setOrderLists(String orders, String onlineOrders, String completedOrders, String processedOrders) {
        OrderListArea.setText("");
        OrderListArea.append(orders + "\n");

        OnlineOrderArea.setText("");
        OnlineOrderArea.append(onlineOrders + "\n");

        CompleteOrderArea.setText("");
        CompleteOrderArea.append(completedOrders + "\n");

        ProcessedOrderArea.setText("");
        ProcessedOrderArea.append(processedOrders + "\n");
    }

    private void setSimSpeed() {
        // Refresh the sim speed text field
        SimSpeedField.setText(String.valueOf(simModel.getSimSpeed()));

        // Refresh the sim speed slider
        SimSpeedSlider.setValue(simModel.getSimSpeed());
    }

    private void setRoles(ArrayList<String> roles) {
        StaffRoleCombo.removeAllItems();
        if (roles != null){
            for (String role : roles) {
                StaffRoleCombo.addItem(role);
            }
        }
    }

    private void setStaffList(StaffList staffList) {
        SelectStaffCombo.removeAllItems();

        // Should work with Stafflist

        staffList.getStaffList().forEach((uuid, curStaff) -> {
            SelectStaffCombo.addItem((curStaff.getWorkerName()) + ", " + curStaff.getRole() + ", "+ uuid);
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

        PopOrderListBtn.setName("PopOrderListBtn");
        PopOrderListBtn.addActionListener(al);
    }

    public void update() {
        // Refresh sim speed related fields
        setSimSpeed();

        // Refresh the Staff list
        setStaffList(simModel.getStaffList());

        // Refresh the Order lists
        setOrderLists(simModel.getOrderList(0),simModel.getOrderList(1), simModel.getOrderList(2), simModel.getCurrentOrders());
    }

    public void showPopup(String message) {
        JOptionPane.showMessageDialog(SimUIView.this, message);
    }

    public void close() {
        // close the window
        SimUIView.this.dispose();
    }
}


