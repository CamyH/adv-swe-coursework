package client;

import exceptions.StaffNullorderException;
import interfaces.Observer;
import order.OrderList;
import workers.Staff;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The simulation UI controller
 * Uses action listener to watch for any button presses on the UI
 */
public class SimUIController implements Observer {

    private SimUIModel simModel;
    private SimulationUI simView;

    // The order list to display
    // Contains both in person and online orders
    private OrderList orders;

    public SimUIController() {

        System.out.println("SimUIController()");
        System.out.println("-----------------------------------");
        simModel = new SimUIModel();
        simModel.registerObserver(this);
        simView = new SimulationUI(simModel);
        simView.addSetListener(new SetListener());
        orders = OrderList.getInstance();

    }

    private void viewStaffDetails() {
        StaffDetailsPopup staffDetailsPopup = new StaffDetailsPopup();
        try {
            staffDetailsPopup.setDetails(simModel.getStaffDetails(simView.getCurStaff()));
        } catch (StaffNullorderException e) {
            staffDetailsPopup.exit();
            simView.showPopup(e.getMessage());
        } catch (NullPointerException e) {
            staffDetailsPopup.exit();
            simView.showPopup("No Staff Found");
        }

        simModel.notifyObservers();
    }

    private void addStaff() {
        try {
            String name = simView.getStaffName();
            simModel.addStaff(name, simView.getStaffRole(), Integer.parseInt(simView.getStaffExp()));
            simView.clearCurStaff();
            simModel.notifyObservers();
            simView.showPopup("Added " + name + " to Staff List");
        } catch (StaffNullorderException e) {
            simView.showPopup(e.getMessage());
        }
    }

    private void removeStaff() {
        try {
            simModel.removeStaff(simView.getCurStaff());
            simModel.notifyObservers();
            simView.showPopup("Removed Selected Staff");
        } catch (NullPointerException e) {
            simView.showPopup("No Staff to Remove");
        }
    }

    private void updateSimSpd() {
        simModel.setSimSpd(simView.getSimSliderValue());
        simModel.notifyObservers();
        simView.showPopup("Updated Simulation Speed");
    }

    private void updateOrders() {
        // get the order list and send it to the view
        // placeholder
        ArrayList<String> list = simModel.getOrderList();
    }

    public void update() {
        updateOrders();
    }

    public class SetListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton sourceBtn = (JButton)e.getSource();
            if (sourceBtn.getName().equals("RemoveStaffBtn")) {
                removeStaff();
            }

            else if (sourceBtn.getName().equals("AddStaffBtn")) {
                addStaff();
            }

            else if (sourceBtn.getName().equals("ViewDetailsBtn")) {
                viewStaffDetails();
            }

            else if (sourceBtn.getName().equals("SimSpdBtn")) {
                updateSimSpd();
            }
        }
    }

    public static void main(final String[] args) {
        SimUIController controller = new SimUIController();
    }

}
