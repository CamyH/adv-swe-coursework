package workers;

import interfaces.EntityList;
import interfaces.Observer;
import interfaces.Subject;
import item.ItemList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class StaffList extends Subject implements EntityList<Staff, UUID> {
    // Hashmap of staff ID as the key, and Staff Object as the value
    private HashMap<UUID, Staff> staffList;

    private final ArrayList<Observer> observers = new ArrayList<Observer>();

    /** Initialise the instance of StaffList */
    private static StaffList instance;

    /**
     * Private constructor as Object is a singleton
     */
    private StaffList() {
        staffList = new HashMap<>();
    }

    /**
     * Method to add staff member to hashmap
     *
     * @param staff the object to be added
     * @return True is the key is unique, False otherwise
     */
    public boolean add(Staff staff) {
        if (staff == null) {
            return false;
        }
        return staffList.putIfAbsent(staff.getID(), staff) == null;
    }

    /**
     * Method to remove staff from staff list
     *
     * @param ID The ID of the staff member to be removed
     * @return true if the removal was successful, false otherwise
     */
    public boolean remove(UUID ID) {
        Staff staff = staffList.remove(ID);

        if (staff == null) {
            return false;
        }

        staff.removeStaff();

        return true;
    }

    public void setDefaultDelay(double defaultDelay) {
        for (Staff staff : staffList.values()) {
            staff.setDefaultDelay(defaultDelay);
        }
    }

    /**
     * Method to return the Staff object related to the ID
     *
     * @param ID The ID of the staff member to return
     * @return The Staff object
     */
    public Staff getStaff(UUID ID) {
        return staffList.get(ID);
    }

    /**
     * Method to return array of staff IDs used to display
     *
     * @return String array containing all Staff IDs
     */
    public UUID[] getStaffIDs() {
        return staffList.keySet().toArray(new UUID[0]);
    }

    /**
     * Method to return the data structure of Staff Objects
     *
     * @return A hashmap of Staff Objects
     */
    public HashMap<UUID, Staff> getStaffList() {
        return new HashMap<>(staffList);
    }

    /**
     * Getter method to return an instance of StaffList
     *
     * @return an instance of StaffList
     */
    public static StaffList getInstance() {
        if (instance == null) instance = new StaffList();
        return instance;
    }

    /**
     * Reset the StaffList singleton instance
     */
    public static void resetInstance() {
        instance = new StaffList();
    }
}
