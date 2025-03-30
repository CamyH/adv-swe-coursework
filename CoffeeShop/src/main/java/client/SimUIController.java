package client;

import exceptions.StaffNullNameException;
import interfaces.Observer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

/**
 * The simulation UI controller
 * Uses action listener to watch for any button presses on the UI
 */
public class SimUIController implements Observer {

    private SimUIModel simModel;
    private SimUIView simView;
    private static SimUIController instance;

    private SimUIController() {

        System.out.println("SimUIController()");
        System.out.println("-----------------------------------");
        simModel = SimUIModel.getInstance();
        simModel.registerObserver(this);
        simView = SimUIView.getInstance();
        simView.addSetListener(new SetListener());

        simView.addSimSpeedChangeListener(e -> updateSimSpeed());
    }

    public static SimUIController getInstance() {
        if (instance == null) {
            instance = new SimUIController();
            System.out.println(instance.toString());
        }
        return instance;
    }

    private void viewStaffDetails() {
        StaffDetailsPopup staffDetailsPopup = new StaffDetailsPopup();
        try {
            UUID curStaffID = simView.getCurStaff();
            staffDetailsPopup.setDetails(simModel.getStaffDetails(curStaffID));
        } catch (NullPointerException e) {
            staffDetailsPopup.exit();
            simView.showPopup("No Staff Found");
        }

    }

    private void addStaff() {
        try {
            String name = simView.getStaffName();
            simView.clearCurStaff();
            simModel.addStaff(name, simView.getStaffRole(), Integer.parseInt(simView.getStaffExp()));
            simView.showPopup("Added " + name + " to Staff List");
        } catch (StaffNullNameException e) {
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

    public void updateSimSpeed() {
        simModel.setSimSpeed(simView.getSimSliderValue());
        simModel.notifyObservers();
    }

    public void update() {
        // get the order list and send it to the view
        simView.setOrderLists(simModel.getOrderList(false),simModel.getOrderList(true));
    }

    public void message(String msg) {
        simView.showPopup(msg);
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
        }
    }

    public void close() {
        simView.close();
    }
}
