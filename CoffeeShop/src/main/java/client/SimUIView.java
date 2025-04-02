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

    private final SimUIModel simModel;
    private static SimUIView instance;

    public SimUIView(SimUIModel simModel) {
        SwingUtilities.invokeLater(() -> {

            simModel.registerObserver(this);
            setContentPane(contentPanel);
            setTitle("Coffee Shop Simulation");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(800,300);

            // Ensure the UI window is shown in the center of the screen
            setLocationRelativeTo(null);
            setVisible(true);

            // Make all non-editable fields un-editable
            OrderListArea.setEnabled(false);
            OrderListArea.setDisabledTextColor(Color.BLACK);
            OnlineOrderArea.setEnabled(false);
            OnlineOrderArea.setDisabledTextColor(Color.BLACK);
            SimSpeedField.setEnabled(false);
            SimSpeedField.setDisabledTextColor(Color.BLACK);

            // Fill the experience combo box with options
            for (int i = 1; i <= 5; i++) {
                StaffExpCombo.addItem(i);
            }
        });

        this.simModel = simModel;

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Demo.cleanUp();
            }
        });

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
        return (String) StaffRoleCombo.getSelectedItem();
    }

    public int getStaffExp() {
        return (Integer) StaffExpCombo.getSelectedItem();
    }

    public UUID getCurStaff() throws NullPointerException {
        String selectedItem = (String) SelectStaffCombo.getSelectedItem();
        if (selectedItem == null) {
            throw new NullPointerException("No staff selected");
        }
        String[] curStaffParts = selectedItem.split(", ", 3);
        return UUID.fromString(curStaffParts[2]);
    }

    public int getSimSliderValue() {
        return SimSpeedSlider.getValue();
    }

    public void setOrderLists(String orders, String onlineOrders) {
        SwingUtilities.invokeLater(() -> {
            OrderListArea.setText("");
            OrderListArea.append(orders + "\n");

            OnlineOrderArea.setText("");
            OnlineOrderArea.append(onlineOrders + "\n");
        });
    }

    private void setSimSpeed() {
        SwingUtilities.invokeLater(() -> {
            // Refresh the sim speed text field
            SimSpeedField.setText(String.valueOf(SimUIModel.getSimSpeed()));

            // Refresh the sim speed slider
            SimSpeedSlider.setValue(SimUIModel.getSimSpeed());
        });
    }

    private void setRoles(ArrayList<String> roles) {
        SwingUtilities.invokeLater(() -> {
            StaffRoleCombo.removeAllItems();
            if (roles != null) {
                for (String role : roles) {
                    StaffRoleCombo.addItem(role);
                }
            }
        });
    }

    private void setStaffList(StaffList staffList) {
        SwingUtilities.invokeLater(() -> {
            SelectStaffCombo.removeAllItems();

            // Should work with Stafflist

            staffList.getStaffList().forEach((uuid, curStaff) -> {
                SelectStaffCombo.addItem((curStaff.getWorkerName()) + ", " + curStaff.getRole() + ", " + uuid);
            });
        });
    }

    public void clearCurStaff() {
        SwingUtilities.invokeLater(() -> StaffNameField.setText(""));
    }

    public void addSetListener(ActionListener al) {
        SwingUtilities.invokeLater(() -> {
            RemoveStaffBtn.setName("RemoveStaffBtn");
            RemoveStaffBtn.addActionListener(al);

            AddStaffBtn.setName("AddStaffBtn");
            AddStaffBtn.addActionListener(al);

            ViewDetailsBtn.setName("ViewDetailsBtn");
            ViewDetailsBtn.addActionListener(al);
        });
    }

    public void update() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                // Refresh sim speed related fields
                setSimSpeed();

                // Refresh the roles list
                setRoles(simModel.getRoles());

                // Refresh the Staff list
                setStaffList(simModel.getStaffList());

                // Refresh the Order lists
                setOrderLists(simModel.getOrderList(false), simModel.getOrderList(true));
                return null;
            }
        };
        worker.execute();
    }

    public void showPopup(String message) {
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(SimUIView.this, message));
    }

    public void close() {
        // close the window
        SwingUtilities.invokeLater(SimUIView.this::dispose);
    }
}


