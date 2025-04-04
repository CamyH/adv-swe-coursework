package client;

import exceptions.StaffNullNameException;
import interfaces.Observer;
import interfaces.Subject;
import item.ItemList;
import order.OrderList;
import workers.*;

import java.util.ArrayList;
import java.util.UUID;

public class SimUIModel extends Subject implements Observer {

    private OrderList orderList;
    private final ItemList menu;
    private final ArrayList<String> roles;
    private final StaffList staffList;

    private static SimUIModel instance;

    private static Integer simSpeed = 50;

    public SimUIModel() {
        this.menu = ItemList.getInstance();
        this.staffList = StaffList.getInstance();
        this.orderList = OrderList.getInstance();

        orderList.registerObserver(this);

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

    /**
     *
     * @param ID The ID of the staff whose details we are collecting
     * @return An array list of strings in the form (staff name,customer ID, item 1, ..., item n, order total cost, order discounted cost)
     */
    public String getStaffDetails(UUID ID) {
        return staffList.getStaff(ID).getCurrentOrderDetails();
    }


    // Setter methods

    public void setSimSpeed(int speed) {
        this.simSpeed = speed;
        StaffList.getInstance().setDefaultDelay(simSpeed);
    }

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

    public void removeStaff(UUID ID) {
        staffList.remove(ID);
    }

}
