package client;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * The simulation UI controller
 * Uses action listener to watch for any button presses on the UI
 */
public class SimUIController implements ActionListener, ChangeListener {

    SimUIModel simModel;
    SimulationUI simView;

    public SimUIController() {
        simModel = new SimUIModel();
        simView = new SimulationUI(simModel);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == "RemoveStaffBtn") {
            removeStaff();
        }

        else if (e.getSource() == "AddStaffBtn") {
            addStaff();
        }

        else if (e.getSource() == "ViewDetailsBtn") {
            viewStaffDetails();
        }
    }

    public void stateChanged(ChangeEvent e) {

        if (e.getSource() == "SimSpdSlider") {
            updateSimSpd();
        }
    }

    private void viewStaffDetails() {
    }

    private void addStaff() {
    }

    private void removeStaff() {
    }

    private void updateSimSpd() {
    }

    public static void main(final String[] args) {
        SimUIController controller = new SimUIController();
    }
}
