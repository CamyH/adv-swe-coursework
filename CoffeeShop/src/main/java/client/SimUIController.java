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
 * @author Caelan Mackenzie
 */
public class SimUIController {

    private SimUIModel simModel;
    private SimUIView simView;
    private static SimUIController instance;
    private CoffeeShopLogger coffeeShopLogger;

    /**
     * Constructor to set up the Simulation UI Controller class
     *
     * @param simView The view related to the controller
     * @param simModel The model related to the controller
     */
    public SimUIController(SimUIView simView, SimUIModel simModel) {
        this.simModel = simModel;
        this.simView = simView;
        coffeeShopLogger = CoffeeShopLogger.getInstance();
        simView.addSetListener(new SetListener());
        simView.addSimSpeedChangeListener(e -> updateSimSpeed());
    }

    /**
     * Method opens up new pop up gui window showing staff details
     */
    private void viewStaffDetails() {
        SwingUtilities.invokeLater(() -> {
            try {
                if (!simModel.checkPopup(simView.getCurStaff())) {
                    new StaffPopupController(simModel, simView.getCurStaff());
                }
                else {
                    simView.showPopup("Popup Already Exists");
                }
            } catch (NullPointerException e) {
                simView.showPopup("No staff selected");
            };
        });
    }

    /**
     * Retrieve the current staff details from the View and get the Model to add them to the staffList
     * Utilises a Swing Worker to ensure thread safety
     */
    private void addStaff() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            String name;
            @Override
            protected Void doInBackground() {
                try {
                    name = simView.getStaffName();
                    simModel.addStaff(name, simView.getStaffRole(), simView.getStaffExp());
                    SwingUtilities.invokeLater(() -> simView.showPopup("Added " + name + " to Staff List"));
                    simView.clearCurStaff();
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

    /**
     * Retrieve the selected staff from the View and get the Model to remove it from the staffList
     * Utilises a Swing Worker to ensure thread safety
     */
    private void removeStaff() {

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            String name;
            @Override
            protected Void doInBackground() {
                try {
                    name = simView.getStaffName();
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

    /**
     * Method to update the sim speed from the value shown on the view
     */
    public void updateSimSpeed() {
        SwingUtilities.invokeLater(() -> simModel.setSimSpeed(simView.getSimSliderValue()));
    }

    /**
     * Method to show a pop up message from the Controller Class
     *
     * @param msg Message to be shown on popup window
     */
    public void message(String msg) {
        simView.showPopup(msg);
    }

    /**
     * The set listener to detect button presses on the UI
     * Determines the button by using the defined button names
     */
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
            else if (sourceBtn.getName().equals("PopOrderListBtn")) {
                simModel.populateOrders();
                sourceBtn.setEnabled(false);
            }
        }
    }

    /**
     * Method to close the gui
     */
    public void close() {
        simView.close();
    }
}
