package client;

import interfaces.Observer;
import interfaces.Subject;
import workers.StaffList;

import java.util.ArrayList;
import java.util.UUID;

public class StaffPopupModel extends Subject implements Observer {

    private final ArrayList<Observer> observers = new ArrayList<Observer>();

    UUID staffID;
    private final SimUIModel simModel;
    private String details;
    private final StaffList staffList;

    public StaffPopupModel(SimUIModel simModel, UUID staffID) {
        this.simModel = simModel;
        this.details = simModel.getStaffDetails(staffID);
        this.staffID = staffID;
        this.staffList = StaffList.getInstance();
        staffList.registerObserver(this);

    }

    public String getDetails() {
        return details;
    }

    public void update() {
        details = simModel.getStaffDetails(staffID);
        notifyObservers();
    }

    public void removeThisObserver() {
        staffList.removeObserver(this);
    }

}
