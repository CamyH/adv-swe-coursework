package client;

import exceptions.StaffNullOrderException;
import interfaces.Observer;
import order.OrderList;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.UUID;

/**
 * The simulation UI controller
 * Uses action listener to watch for any button presses on the UI
 */
public class SimUIController implements Observer {

    private SimUIModel simModel;
    private SimulationUI simView;

    public SimUIController() {

        System.out.println("SimUIController()");
        System.out.println("-----------------------------------");
        simModel = new SimUIModel();
        simModel.registerObserver(this);
        simView = new SimulationUI(simModel);
        simView.addSetListener(new SetListener());

    }

    private void viewStaffDetails() {
        StaffPopupController staffPopupController;
        try {
            staffPopupController = new StaffPopupController(simModel,simView.getCurStaff());
        } catch (NullPointerException e) {
            simView.showPopup("No Staff Found");
        }

    }

    private void addStaff() {
        try {
            String name = simView.getStaffName();
            simView.clearCurStaff();
            simModel.addStaff(name, simView.getStaffRole(), Integer.parseInt(simView.getStaffExp()));
            simView.showPopup("Added " + name + " to Staff List");
        } catch (StaffNullOrderException e) {
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

    public void close() {
        simView.close();
    }
}
