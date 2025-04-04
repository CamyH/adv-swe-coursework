package client;

import exceptions.StaffNullNameException;
import interfaces.Observer;
import interfaces.Subject;
import order.OrderList;
import workers.*;

import java.util.ArrayList;
import java.util.UUID;

/**
 * The simulation UI Model
 * @author Caelan Mackenzie
 */
public class SimUIModel extends Subject implements Observer {

    // Declare the Model's data
    private final OrderList orderList;
    private final ArrayList<String> roles;
    private final StaffList staffList;
    private ArrayList<UUID> popupList;
    private static int simSpeed;

    /** SimUIModel constructor method */
    public SimUIModel() {

        // Get the singleton instances of staffList and orderList
        this.staffList = StaffList.getInstance();
        this.orderList = OrderList.getInstance();

        orderList.registerObserver(this);

        // initialise the data for the UI
        simSpeed = 50;
        popupList = new ArrayList<>();
        roles = new ArrayList<>();

        // Populate roles
        roles.add("Waiter");
        roles.add("Barista");
        roles.add("Chef");
    }

    // Getter methods

    public static int getSimSpeed() {
        return simSpeed;
    }

    public String getOrderList(int state) {
        return orderList.getOrdersForDisplay(state);
    }

    public String getCurrentOrders() {
        return Waiter.getCurrentOrdersForDisplay();
    }

    public ArrayList<String> getRoles() {
        return roles;
    }

    public StaffList getStaffList() {
        return staffList;
    }

    public String getStaffDetails(UUID ID) {
        synchronized (staffList) {
            return staffList.getStaff(ID).getCurrentOrderDetails();
        }
    }


    // Setter methods

    public void setSimSpeed(int speed) {
        simSpeed = speed;
        StaffList.getInstance().setDefaultDelay(simSpeed);
        notifyObservers();
    }

    public void addPopup(UUID popup) {
        popupList.add(popup);
    }
    public boolean checkPopup(UUID popup) {
        return popupList.contains(popup);
    }

    /**
     * Add a new staff to the staffList
     * @param name staff name
     * @param role staff role
     * @param experience staff experience
     * @throws StaffNullNameException thrown when the staff nae field is empty
     */
    public void addStaff(String name, String role, int experience) throws StaffNullNameException {
        if (name.isEmpty()) {
            throw new StaffNullNameException("Staff name is empty");
        }

        StaffFactory.getStaff(role, name, experience).start();

        notifyObservers();
    }

    public void populateOrders() {
        Thread orders = new Thread(orderList);
        orders.start();
    }

    public void update() {
        notifyObservers();
    }

    public void removePopup(UUID popup) {
        popupList.remove(popup);
    }

    public void removeStaff(UUID ID) {
        staffList.remove(ID);
    }

}
