package client;

import exceptions.StaffNullOrderException;
import interfaces.Observer;
import interfaces.Subject;
import item.Item;
import item.ItemList;
import order.Order;
import order.OrderList;
import workers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class SimUIModel extends Subject {

    private final OrderList orderList;
    private final ItemList menu;
    private final ArrayList<String> roles;
    private final StaffList staffList;

    private Integer simSpd = 2000;

        public SimUIModel() {
            this.orderList = OrderList.getInstance();
            this.menu = ItemList.getInstance();
            this.staffList = StaffList.getInstance();
            roles = new ArrayList<>();

            // Populate roles
            roles.add("Waiter");
            roles.add("Barista");
            roles.add("Chef");
            new Waiter("Manager", 5);
        }

    // Getter methods

    public int getSimSpd() {
            return simSpd;
    }

    public ArrayList<String> getOrderList() {
            ArrayList<String> list = new ArrayList<>(Arrays.asList(orderList.getOrdersToString(true)));
            for (String line : list) {
                System.out.println(line);
            }
            return list;
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
    }

    public void addStaff(String name, String role, int experience) throws StaffNullOrderException {
        if (name.isEmpty()) {
            throw new StaffNullOrderException("Staff name is empty");
        }

        if (role.equals("Waiter")) {
            new Waiter(name, experience);
        } else if (role.equals("Barista")) {
            new Barista(name, experience);
        } else if (role.equals("Chef")) {
            new Chef(name, experience);
        }
        notifyObservers();

    }

    public void removeStaff(UUID ID) {
        staffList.remove(ID);
    }

}
