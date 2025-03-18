package workers;

import interfaces.EntityList;
import interfaces.Singleton;

import java.util.HashMap;
import java.util.UUID;

public class StaffList implements EntityList<Staff, UUID>, Singleton {
    // Hashmap of staff ID as the key, and Staff Object as the value
    private HashMap<UUID, Staff> staffList;

    /** Initialise the instance of StaffList */
    private static StaffList instance = new StaffList();

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
        return staffList.putIfAbsent(staff.getID(), staff) == null;
    }

    /**
     * Method to remove staff from staff list
     *
     * @param ID The ID of the staff member to be removed
     * @return true if the removal was successful, false otherwise
     */
    public boolean remove(UUID ID) {
        return staffList.remove(ID) != null;
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
    public String[] getStaffIDs() {
        return staffList.keySet().toArray(new String[0]);
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
        return instance;
    }

    /**
     * Reset the StaffList singleton instance
     */
    public static void resetInstance() {
        instance = new StaffList();
    }
}
