package client;

import exceptions.StaffNullNameException;
import interfaces.Subject;
import item.ItemList;
import order.OrderList;
import workers.*;

import java.util.ArrayList;
import java.util.UUID;

public class SimUIModel extends Subject {

    private OrderList orderList;
    private final ItemList menu;
    private final ArrayList<String> roles;
    private final StaffList staffList;

    private static SimUIModel instance;

    private Integer simSpd = 2000;

    private SimUIModel() {
        this.menu = ItemList.getInstance();
        this.staffList = StaffList.getInstance();
        roles = new ArrayList<>();

        // Populate roles
        roles.add("Waiter");
        roles.add("Barista");
        roles.add("Chef");
    }

    public static SimUIModel getInstance() {
        if (instance == null) {
            instance = new SimUIModel();
        }
        return instance;
    }

    // Getter methods

    public int getSimSpd() {
        return simSpd;
    }

    public String getOrderList(boolean online) {
        orderList = OrderList.getInstance();
        return orderList.getOrdersForDisplay(online);
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

    public void setSimSpd(int speed) {
        this.simSpd = Math.round(speed/ 100.0f) * 100;
        StaffList.getInstance().setDefaultDelay(this.simSpd);
    }

    public void addStaff(String name, String role, int experience) throws StaffNullNameException {
        if (name.isEmpty()) {
            throw new StaffNullNameException("Staff name is empty");
        }

        if (role.equals("Waiter")) {
            StaffFactory.getStaff("Waiter", name, experience).start();
        } else if (role.equals("Barista")) {
            StaffFactory.getStaff("Barista", name, experience).start();
        } else if (role.equals("Chef")) {
            StaffFactory.getStaff("Chef", name, experience).start();
        }
        notifyObservers();

    }

    public void removeStaff(UUID ID) {
        staffList.remove(ID);
    }

}
