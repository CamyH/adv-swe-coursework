package client;

import exceptions.DuplicateOrderException;
import exceptions.InvalidOrderException;
import exceptions.StaffNullNameException;
import interfaces.INotificationService;
import interfaces.Observer;
import interfaces.Subject;
import item.ItemList;
import logs.CoffeeShopLogger;
import order.Order;
import order.OrderList;
import server.Server;
import workers.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * SimUIModel acts as the data model for the simulation user interface
 * Handles core logic related to staff management, order management, and server control
 * Implements the Observer pattern to react to order updates and notify observers
 * Dependencies include shared instances of OrderList, ItemList, and StaffList
 * Integrates with the server and staff via notification services and threading
 * NotificationService is injected using Dependency Injection
 *
 * @author Caelan Mackenzie
 */
public class SimUIModel extends Subject implements Observer {
    private final INotificationService notificationService;
    private final OrderList orderList;
    private final ItemList menu;
    private final ArrayList<String> roles;
    private final StaffList staffList;
    private static Integer simSpeed = 50;
    private final CoffeeShopLogger logger = CoffeeShopLogger.getInstance();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    /**
     * Constructor for SimUIModel
     * Initializes shared instances, registers as observer, and starts the server
     *
     * @param notificationService service for sending notifications to clients
     */
    public SimUIModel(INotificationService notificationService) {
        this.notificationService = notificationService;
        this.menu = ItemList.getInstance();
        this.staffList = StaffList.getInstance();
        this.orderList = OrderList.getInstance();

        startServer();

        orderList.registerObserver(this);

        roles = new ArrayList<>();

        // Populate roles
        roles.add("Waiter");
        roles.add("Barista");
        roles.add("Chef");
    }

    // Getter methods
    /**
     * Gets the current simulation speed in milliseconds
     *
     * @return simulation delay in milliseconds
     */
    public static int getSimSpeed() {
        return simSpeed;
    }

    /**
     * Returns a string representation of the current orders
     *
     * @param online whether to display online orders only
     * @return formatted string of current orders
     */
    public String getOrderList(boolean online) {
        return orderList.getOrdersForDisplay(online);
    }

    /**
     * Gets the available staff roles in the simulation
     *
     * @return list of role names
     */
    public ArrayList<String> getRoles() {
        return roles;
    }

    /**
     * Gets the current staff list
     *
     * @return shared StaffList instance
     */
    public StaffList getStaffList() {
        return staffList;
    }

    /**
     * Gets order details currently handled by a staff member
     * @param ID The ID of the staff whose details we are collecting
     * @return An array list of strings in the form (staff name,customer ID, item 1, ..., item n, order total cost, order discounted cost)
     */
    public String getStaffDetails(UUID ID) {
        return staffList.getStaff(ID).getCurrentOrderDetails();
    }


    // Setter methods

    /**
     * Sets the simulation speed and applies it to all staff
     *
     * @param speed the delay in milliseconds
     */
    public void setSimSpeed(int speed) {
        simSpeed = speed;
        StaffList.getInstance().setDefaultDelay(simSpeed);
    }

    /**
     * Attempts to add a staff member with the given details
     *
     * @param name name of the staff member
     * @param role role of the staff member
     * @param experience experience level
     * @throws StaffNullNameException if the name is empty
     */
    public void addStaff(String name, String role, int experience) throws StaffNullNameException {
        if (name.isEmpty()) {
            throw new StaffNullNameException("Staff name is empty");
        }

        // Will create the correct staff class based on the role,
        // and it will only start the staff thread if the staff
        // member was created successfully
        // logs if a staff member cannot be added
        Optional.ofNullable(StaffFactory.getStaff(role, name, experience, notificationService))
        .ifPresentOrElse(Staff::start, () -> logger.logSevere("Could not add staff member"));

        notifyObservers();
    }

    /**
     * Updates all registered observers of this model
     */
    public void update() {
        notifyObservers();
    }

    /**
     * Removes a staff member by ID
     *
     * @param ID UUID of the staff member
     */
    public void removeStaff(UUID ID) {
        staffList.remove(ID);
    }

}
