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
    private ArrayList<StaffPopupController> popupList;
    private static int simSpeed;

    /**
     * SimUIModel constructor method
     */
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

    /**
     * Getter method to return simulation speed of the program
     *
     * @return The simulation speed as an integer
     */
    public static int getSimSpeed() {
        return simSpeed;
    }

    /**
     * Method that returns the order list to be displayed in each scroll pane
     *
     * @param state Which order list the simulation should return? Online, In person or Complete Orders
     * @return a string to be displayed in each scroll pane representing each type of order list
     */
    public String getOrderList(int state) {
        return orderList.getOrdersForDisplay(state);
    }

    /**
     * Method to return the currently processed orders to be displayed
     *
     * @return The current orders that are being processed to be displayed in the GUI
     */
    public String getCurrentOrders() {
        return Waiter.getCurrentOrdersForDisplay();
    }

    /**
     * Method to return all the available staff roles to be displayed
     *
     * @return an Array List of all available staff roles
     */
    public ArrayList<String> getRoles() {
        return roles;
    }

    /**
     * Method to return the list of staff members
     *
     * @return the StaffList object which holds information about each staff member
     */
    public StaffList getStaffList() {
        return staffList;
    }

    /**
     * Method that returns the current order details that the staff member is currently processing
     *
     * @param ID The ID of the staff the data is received from
     * @return String representing the current order details
     */
    public String getStaffDetails(UUID ID) {
        synchronized (staffList) {
            return staffList.getStaff(ID).getCurrentOrderDetails();
        }
    }

    /**
     * Method to set the simulation speed
     *
     * @param speed The speed to be set for the simulation
     */
    public void setSimSpeed(int speed) {
        simSpeed = speed;
        StaffList.getInstance().setDefaultDelay(simSpeed);
        notifyObservers();
    }

    /**
     * Adds the selected popup controller to the list of current staff details popups
     * @param popup the Controller of the popup UI that is to be added
     */
    public void addPopup(StaffPopupController popup) {
        popupList.add(popup);
    }

    /**
     * Check to see id the given staff ID has a related staff details popup currently open
     * @param ID the ID of the selected staff
     * @return true if the staff has a related popup open, false if not
     */
    public boolean checkPopup(UUID ID) {
        for (StaffPopupController p : popupList) {
            if (p.getID().equals(ID)) {
                System.out.println(true);
                return true;
            }
        }
        System.out.println(false);
        return false;
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

    /**
     * Remove the selected staff from the staffList, closes their details popup if one exists and then gets rid of the staff object
     * @param ID the ID of the staff to be removed
     */
    public void removeStaff(UUID ID) {

        // If the staff has a details popup open, close the details popup
        for (StaffPopupController p : popupList) {
            if (p.getID().equals(ID)) {
                popupList.remove(p);
                p.close();
                staffList.remove(ID);
                return;
            }
        }
        staffList.remove(ID);
    }

}
