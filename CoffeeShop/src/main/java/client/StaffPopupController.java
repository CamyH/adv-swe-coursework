package client;

import exceptions.StaffNullOrderException;
import interfaces.Observer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
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
        System.out.println("SimUIController()");
        System.out.println("-----------------------------------");
        staffPopupModel = new StaffPopupModel(simModel,staffID);
        staffPopupModel.registerObserver(this);
        staffPopup = new StaffDetailsPopup(staffPopupModel);
        staffPopup.addSetListener(new SetListener());
        update();
        simModel.addPopup(this);
    }


    public void update() {
        staffPopup.setDetails(staffPopupModel.getDetails());
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
        simModel.removePopup(this);
        staffPopup.close();
    }
}
