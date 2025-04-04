package workers;

import exceptions.InvalidItemIDException;
import interfaces.INotificationService;
import item.ItemList;
import logs.CoffeeShopLogger;
import order.*;
import server.ClientService;

import java.util.Map;

/**
 * Class represents how a Chef functions in the Coffee Shop Simulation
 * This class uses two design patterns:
 * 1. Factory Design Pattern (with Staff and StaffFactory)
 * 2. Observer Design Pattern (between this class and FoodList)
 * Tasks:
 * 1. Check for Item by checking for items in FoodList
 * 2. Wait specified amount of time to complete Item
 * 3. Add Item to waiters list for processing
 *
 * @author Fraser Holman
 */
public class Chef extends Staff<String> {
    private final INotificationService notificationService;
    private final FoodList foodList;

    private final ItemList itemList;

    private Map.Entry<Waiter, FoodItem> currentItem;

    private final StaffList staffList;

    /** Tells if the staff member is currently active (ie not fired) */
    private boolean active = true;

    /** Logger instance */
    private final CoffeeShopLogger logger;

    /**
     * Constructor to setup Chef
     *
     * @param name Name of Chef
     * @param experience experience level of Chef
     */
    public Chef(String name, int experience, INotificationService notificationService) {
        super(name, experience);
        this.notificationService = notificationService;
        foodList = FoodList.getInstance();
        itemList = ItemList.getInstance();
        logger = CoffeeShopLogger.getInstance();
        foodList.registerObserver(this);
        staffList = StaffList.getInstance();
        staffList.add(this);
    }

    /**
     * Method gets next food item in food list
     *
     * If there is no food left to process the Staff member thread will be left in the waiting state until notified
     */
    @Override
    public synchronized void getOrders() {
        currentItem = foodList.remove();

        if (currentItem == null) {
            try {
                wait();
            }
            catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Method to show the current order by the staff member has been complete
     *
     * @return Boolean representing whether completion was a success
     */
    @Override
    public synchronized boolean completeCurrentOrder() {
        currentItem.getKey().addItem(currentItem.getValue().foodItem());
        return true;
    }

    /**
     * This method will be used to display current order details on the GUI
     *
     * Will return a list of items in the order. This can be further customised depending on what we want to show
     *
     * @return ArrayList of Current Order Details
     */
    @Override
    public String getCurrentOrderDetails() {
        StringBuilder itemDetails = new StringBuilder();

        itemDetails.append("Staff Name : ").append(this.getWorkerName()).append("\n");
        itemDetails.append("Staff Type : ").append("Chef").append("\n");
        itemDetails.append("Staff Experience Level : ").append(this.getExperience()).append("\n");

        if (currentItem == null) {
            itemDetails.append("Staff is Currently Idle").append("\n");
            return itemDetails.toString();
        }

        itemDetails.append("Item ID : ").append(currentItem.getValue()).append("\n");

        try {
            itemDetails.append("Item Description : ").append(itemList.getDescription(currentItem.getValue().foodItem())).append("\n");
        } catch (InvalidItemIDException e) { // this will never happen
            itemDetails.append("Item Description : ").append("ERROR LOADING ITEMS").append("\n");
            System.out.println(e.getMessage());
        }

        return itemDetails.toString();
    }

    public String getRole() {
        return "Chef";
    }

    /**
     * Method to return current order that is being processed
     *
     * @return the current order that is being processed
     */
    public String getCurrentOrder() {
        return currentItem.getValue().foodItem();
    }

    /**
     * Method used to remove staff member from the simulation
     */
    @Override
    public synchronized void removeStaff() {
        foodList.removeObserver(this);
        active = false;
        staffList.remove(this.getID());
        notifyAll(); // wakes up thread
        logger.logInfo("Chef " + getWorkerName() + " removed from the simulation.");
    }

    /**
     * Method used by the Subject (FoodList) to tell the Staff member that an order has been added
     */
    public synchronized void update() {
        notifyAll();
    }

    /**
     * This method is the Chef's thread
     */
    @Override
    public void run() {
        while (active) {
            getOrders();

            if (currentItem == null) continue;

            for (Order item : OrderList.getInstance().getOrderList()) {
                System.out.println("HEY " + item.getOrderID() + " " + item.getClientService());
            }

            try {
                sleep((int) (defaultDelay * ((6.0 - getExperience()) / 5.0)));
            } catch (InterruptedException e) {
                logger.logSevere("InterruptedException in Chef.run: " + e.getMessage());
            }

            try {
                System.out.println(getWorkerName() + " completed item " + itemList.getDescription(currentItem.getValue().foodItem()));
                logger.logInfo(getWorkerName() + " completed item " + itemList.getDescription(currentItem.getValue().foodItem()));
            }
            catch (InvalidItemIDException e) {
                System.out.println(e.getMessage());
            }
            completeCurrentOrder();
        }
    }
}
