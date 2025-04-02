package client;

import interfaces.Observer;
import interfaces.Subject;

import java.util.ArrayList;
import java.util.UUID;

public class StaffPopupModel implements Subject, Observer {

    private final ArrayList<Observer> observers = new ArrayList<Observer>();

    UUID staffID;
    private final SimUIModel simModel;
    private ArrayList<String> details;

    public StaffPopupModel(SimUIModel simModel, UUID staffID) {
        this.simModel = simModel;
        this.details = simModel.getStaffDetails(staffID);
        this.staffID = staffID;
    }

    public void registerObserver(Observer obs) {
        observers.add(obs);
    }

    public void removeObserver(Observer obs) {
        observers.remove(obs);
    }

    public void notifyObservers() {
        for (Observer obs : observers) {
            obs.update();
        }
    }

    public ArrayList<String> getDetails() {
        return details;
    }

    public void update() {
        details = simModel.getStaffDetails(staffID);
        notifyObservers();
    }
}
