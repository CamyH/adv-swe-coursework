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
 * The simulation UI Model
 * NotificationService is injected using Dependency Injection
 * @author Caelan Mackenzie
 */
public class SimUIModel extends Subject implements Observer {
    private final INotificationService notificationService;
    private final OrderList orderList;
    private final ItemList menu;
    private final ArrayList<String> roles;
    private final StaffList staffList;
    private final CoffeeShopLogger logger = CoffeeShopLogger.getInstance();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private ArrayList<StaffPopupController> popupList;
    private static int simSpeed;

    /**
     * Constructor for SimUIModel
     * Initializes shared instances, registers as observer, and starts the server
     *
     * @param notificationService service for sending notifications to clients
     */
    public SimUIModel(INotificationService notificationService) {
        this.notificationService = notificationService;
        this.menu = ItemList.getInstance();
        // Get the singleton instances of staffList and orderList
        this.staffList = StaffList.getInstance();
        this.orderList = OrderList.getInstance();

        startServer();

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
     * @param state whether to display online orders only
     * @return formatted string of current orders
     */
    public String getOrderList(int state) {
        return orderList.getOrdersForDisplay(state);
    }

    public String getCurrentOrders() {
        return Waiter.getCurrentOrdersForDisplay();
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
        synchronized (staffList) {
            return staffList.getStaff(ID).getCurrentOrderDetails();
        }
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
    public void populateOrders() {
        Thread orders = new Thread(orderList);
        orders.start();
    }

    /**
     * Notify the observers
     */
    public void update() {
        notifyObservers();
    }

    /**
     *
     * Remove the selected staff from the staffList, closes their details popup if one exists and then gets rid of the staff object
     *
     * @param ID the ID of the staff to be removed
     */
    public void removeStaff(UUID ID) {

        // If the staff has a details popup open, close the details popup
        for (StaffPopupController p : popupList) {
            if (p.getID().equals(ID)) {
                popupList.remove(p);
                staffList.remove(ID);
                p.close();
            }
        }
        staffList.remove(ID);
    }

    /**
     * Attempts to add a new order to the order list
     *
     * @param order the order to add
     * @return true if added successfully, false otherwise
     */
    public boolean addOrder(Order order) {
        try {
            return orderList.add(order);
        } catch (InvalidOrderException | DuplicateOrderException e) {
            logger.logWarning(e.getClass() + e.getMessage());
        }

        return false;
    }

    /**
     * Starts the simulation server on a separate thread
     */
    private void startServer() {
        executor.submit(() -> {
            Server server = new Server(this);
            server.start();
        });
    }

    /**
     * Stops the simulation server and shuts down the executor
     */
    protected void stopServer() {
        executor.shutdown();
    }

    /**
     * Gets the shared order list instance
     *
     * @return order list
     */
    public OrderList getOrderList() {
        return orderList;
    }

    /**
     * Gets the shared item list (menu)
     *
     * @return menu item list
     */
    public ItemList getMenu() {
        return menu;
    }
}
