package client;

import exceptions.StaffNullNameException;
import interfaces.Observer;
import logs.CoffeeShopLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

/**
 * The simulation UI controller
 * Uses action listener to watch for any button presses on the UI
 */
public class SimUIController {

    private SimUIModel simModel;
    private SimUIView simView;
    private static SimUIController instance;
    private CoffeeShopLogger coffeeShopLogger;

    public SimUIController(SimUIView simView, SimUIModel simModel) {

        System.out.println("SimUIController()");
        System.out.println("-----------------------------------");
        this.simModel = simModel;
        this.simView = simView;
        coffeeShopLogger = CoffeeShopLogger.getInstance();
        simView.addSetListener(new SetListener());

        simView.addSimSpeedChangeListener(e -> updateSimSpeed());
    }

    private void viewStaffDetails() {
        StaffPopupController staffPopupController;
        try {
            staffPopupController = new StaffPopupController(simModel,simView.getCurStaff());
        } catch (NullPointerException e) {
            SwingUtilities.invokeLater(() -> simView.showPopup("No Staff Found"));
        }

    }

    private void addStaff() {

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            String name;
            @Override
            protected Void doInBackground() {
                try {
                    name = simView.getStaffName();
                    simModel.addStaff(name, simView.getStaffRole(), simView.getStaffExp());
                    SwingUtilities.invokeLater(() -> simView.showPopup("Added " + name + " to Staff List"));
                } catch (StaffNullNameException e) {
                    SwingUtilities.invokeLater(() -> simView.showPopup("Please insert a staff name"));
                }
                return null;
            }

            @Override
            protected void done() {
                simModel.notifyObservers();
            }
        };
        worker.execute();
    }

    private void removeStaff() {

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            String name;
            @Override
            protected Void doInBackground() {
                try {
                    name = simView.getStaffName();
                    System.out.println(name);
                    simModel.removeStaff(simView.getCurStaff());
                    simView.showPopup("Removed " + name + " from Staff List");
                } catch (NullPointerException e) {
                    simView.showPopup("No Staff to Remove");
                }
                return null;
            }

            @Override
            protected void done() {
                simModel.notifyObservers();
            }
        };
        worker.execute();
    }

    public void updateSimSpeed() {
        SwingUtilities.invokeLater(() -> simModel.setSimSpeed(simView.getSimSliderValue()));
        simModel.notifyObservers();
    }

    public void message(String msg) {
        simView.showPopup(msg);
    }

    public class SetListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            JButton sourceBtn = (JButton) e.getSource();

            if (sourceBtn.getName().equals("RemoveStaffBtn")) {
                removeStaff();
            } else if (sourceBtn.getName().equals("AddStaffBtn")) {
                addStaff();
            } else if (sourceBtn.getName().equals("ViewDetailsBtn")) {
                viewStaffDetails();
            }
        }
    }

    public void close() {
        simView.close();
    }
}
