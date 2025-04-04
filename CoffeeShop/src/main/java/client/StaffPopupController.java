package client;

import interfaces.Observer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.UUID;

/**
 * The simulation UI controller
 * Uses action listener to watch for any button presses on the UI
 */
public class StaffPopupController implements Observer {

    private StaffPopupModel staffPopupModel;
    private StaffDetailsPopup staffPopup;
    private final SimUIModel simModel;

    public StaffPopupController(SimUIModel SM, UUID staffID) {

        this.simModel = SM;
        staffPopupModel = new StaffPopupModel(simModel,staffID);
        staffPopupModel.registerObserver(this);
        staffPopup = new StaffDetailsPopup(staffPopupModel);

        staffPopup.addSetListener(new SetListener());
        // Use WindowAdapter to override only windowClosing()
        staffPopup.addWindowCloseListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });

        update();
        simModel.addPopup(this);
    }

    public void update() {
        String updatedDetails = staffPopupModel.getDetails();
        SwingUtilities.invokeLater(() -> staffPopup.setDetails(updatedDetails));
    }

    public class SetListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton sourceBtn = (JButton)e.getSource();
            if (sourceBtn.getName().equals("ExitBtn")) {
                close();
            }
        }
    }

    public void close() {
        staffPopupModel.removeThisObserver();
        simModel.removePopup(this);
        staffPopup.close();
    }
}
