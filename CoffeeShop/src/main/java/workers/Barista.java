package workers;

import exceptions.InvalidItemIDException;
import interfaces.INotificationService;
import item.ItemList;
import logs.CoffeeShopLogger;
import order.DrinkItem;
import order.DrinkList;
import order.Order;
import order.OrderList;
import server.ClientService;

import java.util.Map;

/**
 * Class represents how a Barista functions in the Coffee Shop Simulation
 *
 * This class uses two design patterns:
 * 1. Factory Design Pattern (with Staff and StaffFactory)
 * 2. Observer Design Pattern (between this class and DrinkList)
 *
 * Tasks:
 * 1. Check for Item by checking for items in DrinkList
 * 2. Wait specified amount of time to complete Item
 * 3. Add Item to waiters list for processing
 *
 * @author Fraser Holman
 */
public class Barista extends Staff<String> {
    private final DrinkList drinkList;

    private final ItemList itemList;

    private final StaffList staffList;

    /** Holds a single entry representing the current processed item and the corresponding waiter */
    private Map.Entry<Waiter, DrinkItem> currentItem;

    /**
     * Tells if the staff member is currently active (ie not fired)
     */
    private boolean active = true;

    /**
     * Logger instance
     */
    private final CoffeeShopLogger logger;

    /**
     * Constructor to set up barista
     *
     * @param name       Name of Barista
     * @param experience Experience level of barista
     */
    public Barista(String name, int experience) {
        super(name, experience);
        drinkList = DrinkList.getInstance();
        itemList = ItemList.getInstance();
        logger = CoffeeShopLogger.getInstance();
        drinkList.registerObserver(this);
        staffList = StaffList.getInstance();
        staffList.add(this);
    }

    /**
     * Method gets next drink in drink list
     * If there are no drinks left to process the Staff member thread will be left in the waiting state until notified
     */
    @Override
    public void getOrders() {
        currentItem = drinkList.remove();

        if (currentItem == null) {
            try {
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException e) {
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
    public boolean completeCurrentOrder() {
        currentItem.getKey().addItem(currentItem.getValue().drinkItem());
        currentItem = null;
        return true;
    }

    /**
     * Method to return current order that is being processed
     *
     * @return the current order that is being processed
     */
    @Override
    public String getCurrentOrder() {
        return currentItem.getValue().drinkItem();
    }

    /**
     * This method will be used to display current order details on the GUI
     * <p>
     * Will return a list of items in the order. This can be further customised depending on what we want to show
     *
     * @return ArrayList of Current Order Details
     */
    @Override
    public String getCurrentOrderDetails() {
        StringBuilder itemDetails = new StringBuilder();

        itemDetails.append("Staff Name : ").append(this.getWorkerName()).append("\n");
        itemDetails.append("Staff Type : ").append("Barista").append("\n");
        itemDetails.append("Staff Experience Level : ").append(this.getExperience()).append("\n");

        if (currentItem == null) {
            itemDetails.append("Staff is Currently Idle").append("\n");
            return itemDetails.toString();
        }

        itemDetails.append("Item ID : ").append(currentItem.getValue()).append("\n");

        try {
            itemDetails.append("Item Description : ").append(itemList.getDescription(currentItem.getValue().drinkItem())).append("\n");
        } catch (InvalidItemIDException e) { // this will never happen
            itemDetails.append("Item Description : ").append("ERROR LOADING ITEMS").append("\n");
            System.out.println(e.getMessage());
        }

        return itemDetails.toString();
    }

    /**
     * Method to return the role of the staff object in this case "barista"
     *
     * @return String representing this staff's role
     */
    public String getRole() {
        return "Barista";
    }

    /**
     * Method used to remove staff member from the simulation
     */
    @Override
    public synchronized void removeStaff() {
        drinkList.removeObserver(this);
        active = false;
        staffList.remove(this.getID());
        notifyAll(); // wakes up thread
        logger.logInfo("Barista " + getWorkerName() + " removed from the simulation.");
    }

    /**
     * Method used by the Subject (DrinkList) to tell the Staff member that an order has been added
     */
    public void update() {
        synchronized (this) {
            notifyAll();
        }
    }

    /**
     * This method is the Barista's thread
     */
    @Override
    public void run() {
        while (active) {
            getOrders();
            staffList.notifyObservers();

            if (currentItem == null) continue;

            delay(logger);

            try {
                logger.logInfo(getWorkerName() + " completed item " + itemList.getDescription(currentItem.getValue().drinkItem()));
            }
            catch (InvalidItemIDException e) {
                System.out.println(e.getMessage());
            }

            completeCurrentOrder();
            staffList.notifyObservers();
        }
    }
}
