package client;

import exceptions.StaffNullorderException;
import interfaces.Observer;
import interfaces.Subject;
import item.Item;
import item.ItemList;
import order.Order;
import order.OrderList;
import workers.Barista;
import workers.Staff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class SimUIModel implements Subject {

    private ArrayList<Observer> observers = new ArrayList<Observer>();

    private OrderList orderList;
    private ItemList menu;

    private ArrayList<String> roles;

    private HashMap<UUID, Staff> staffList =  new HashMap<>();
    // PLACEHOLDER
    // private StaffList staffList;

    private Integer simSpd = 2000;

        public SimUIModel() {
            this.orderList = OrderList.getInstance();
            this.menu = ItemList.getInstance();
            roles = new ArrayList<>();

            // Populate roles
            roles.add("Barista");

            // Populate staff list
            for (int i = 1; i <= 5; i++) {
                Staff curStaff = new Barista("Staffname" + i, i); {
                }
                staffList.put(UUID.randomUUID(),curStaff);
            }
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

    public HashMap<UUID, Staff> getStaffList() {
        return staffList;
    }

    /**
     *
     * @param ID The ID of the staff whose details we are collecting
     * @return An array list of strings in the form (staff name,customer ID, item 1, ..., item n, order total cost, order discounted cost)
     */
    public ArrayList<String> getStaffDetails(UUID ID) throws StaffNullorderException {
            Staff curStaff = staffList.get(ID);

            if (curStaff.getCurrentOrder() == null) {
                throw new StaffNullorderException("Selected staff has no order");
            }

            Order curOrder = curStaff.getCurrentOrder();
            String name = curStaff.getWorkerName();
            ArrayList<String> orderDetails = new ArrayList<>();

            // Add the staff name to the list
            orderDetails.add(name);

            // Add the order's customer ID to the list
            orderDetails.add(String.valueOf(curOrder.getCustomerID()));

            // Add the item names to the list
            for (String itemID : curOrder.getDetails()) {
                Item item = menu.getMenu().get(itemID);
                if (item != null) {
                    orderDetails.add(item.getDescription());
                }
            }

            // Add the total cost and discounted cost to the last two values in the list
            orderDetails.add(String.valueOf(curOrder.getTotalCost()));
            orderDetails.add(String.valueOf(curOrder.getDiscountedCost()));
            return orderDetails;
    }


    // Setter methods

    public void setSimSpd(int speed) {
            this.simSpd = Math.round(speed/ 100.0f) * 100;
    }

    public void addStaff(String name, String role, int experience) throws StaffNullorderException{
        if (name.isEmpty()) {
            throw new StaffNullorderException("Staff name is empty");
        }

        if (role.equals("Barista")) {
            Barista curStaff = new Barista(name, experience);
            staffList.put(curStaff.getID(), curStaff);
            System.out.println(curStaff.getWorkerName());
        }
    }

    public void removeStaff(UUID ID) {
        staffList.get(ID).removeStaff();
        staffList.remove(ID);
    }

}
