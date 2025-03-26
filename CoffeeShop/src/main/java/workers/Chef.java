package workers;

import exceptions.InvalidItemIDException;
import item.Item;
import item.ItemList;
import logs.CoffeeShopLogger;
import order.DrinkList;
import order.FoodList;
import order.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Chef extends Staff<String> {
    FoodList foodList;

    ItemList itemList;

    Map.Entry<Waiter, String> currentItem;

    /** Tells if the staff member is currently active (ie not fired) */
    private boolean active = true;

    /** Logger instance */
    private CoffeeShopLogger logger;

    public Chef(String name, double experience) {
        super(name, experience);
        foodList = FoodList.getInstance();
        itemList = ItemList.getInstance();
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
        currentItem.getKey().addItem(currentItem.getValue());
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
    public ArrayList<String> getCurrentOrderDetails() {
        if (currentItem == null) return null;

        String description;
        try {
            description = itemList.getDescription(currentItem.getValue());
        } catch (InvalidItemIDException e) { // this will never happen
            description = "ERROR";
            System.out.println(e.getMessage());
        }

        return new ArrayList<>(List.of(
            this.getWorkerName(),
            currentItem.getValue(),
            description,
            currentItem.getKey().getWorkerName()
            ));
    }

    /**
     * Method to return current order that is being processed
     *
     * @return the current order that is being processed
     */
    public String getCurrentOrder() {
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
            getOrders();

            if (currentItem != null) {
                try {
                    sleep((int) (defaultDelay * ((6 - getExperience()) / 5)));
                } catch (InterruptedException e) {
                    logger.logSevere("InterruptedException in Waiter.run: " + e.getMessage());
                }

                try {
                    System.out.println(getWorkerName() + " completed order " + itemList.getDescription(currentItem.getValue()));
                }
                catch (InvalidItemIDException e) {
                    System.out.println(e.getMessage());
                }
                completeCurrentOrder();
            }

        }
    }
}
