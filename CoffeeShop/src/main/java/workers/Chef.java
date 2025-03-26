package workers;

import item.Item;
import logs.CoffeeShopLogger;
import order.DrinkList;
import order.FoodList;
import order.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Chef extends Staff<Item> {
    FoodList foodList;

    Map.Entry<Waiter, Item> currentItem;

    /** Tells if the staff member is currently active (ie not fired) */
    private boolean active = true;

    /** Logger instance */
    private CoffeeShopLogger logger;

    public Chef(String name, double experience) {
        super(name, experience);
        foodList = FoodList.getInstance();
        logger = CoffeeShopLogger.getInstance();
        foodList.registerObserver(this);
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
        return false;
    }

    /**
     * This method will be used to display current order details on the GUI
     *
     * Will return a list of items in the order. This can be further customised depending on what we want to show
     *
     * @return ArrayList of Current Order Details
     */
    @Override
    public ArrayList<String> getCurrentOrderDetails() {
        if (currentItem == null) return null;

        return new ArrayList<>(List.of(currentItem.getValue().getDescription()));
    }

    /**
     * Method to return current order that is being processed
     *
     * @return the current order that is being processed
     */
    public Item getCurrentOrder() {
        return currentItem.getValue();
    }

    /**
     * Method used to remove staff member from the simulation
     */
    @Override
    public synchronized void removeStaff() {
        foodList.removeObserver(this);
        active = false;
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

        }
    }
}
