package workers;

import exceptions.DuplicateOrderException;
import exceptions.InvalidItemIDException;
import exceptions.InvalidOrderException;
import interfaces.INotificationService;
import item.ItemCategory;
import item.ItemList;
import order.*;

import java.util.*;

import logs.CoffeeShopLogger;

/**
 * Class represents how a Waiter functions in the Coffee Shop Simulation
 * This class uses two design patterns:
 * 1. Factory Design Pattern (with Staff and StaffFactory)
 * 2. Observer Design Pattern (between this class and OrderList)
 * Tasks:
 * 1. Check for Order by checking incomplete orders in OrderList
 * 2. Wait specified amount of time to complete order
 * 3. Add order to complete orders in OrderList
 *
 * @author Fraser Holman
 */
public class Waiter extends Staff<Order> {
    private final INotificationService notificationService;
    private final OrderList orderList;
    private Order currentOrder;
    private boolean active = true;

    /** Whether the waiter prioritises online or in person order
     * 0-1 = the smaller the number the more it prioritises online orders
     * 1 = prioritises neither as much as the other
     * >1 = larger the positive integer the more in person orders are prioritised
     * */
    private double priority = 0;
    private static final List<Waiter> waiterList = new ArrayList<>();
    private static final CoffeeShopLogger logger = CoffeeShopLogger.getInstance();
    private ArrayList<String> thisOrder;

    /**
     * Constructor to instantiate a new staff member
     *
     * @param name Name of the staff member
     * @param experience Experience level of the staff member
     */
    public Waiter(String name, int experience, INotificationService notificationService) {
        super(name, experience);
        this.notificationService = notificationService;
        orderList = OrderList.getInstance();
        orderList.registerObserver(this);
        logger = CoffeeShopLogger.getInstance();
        waiterList.add(this);
        thisOrder = new ArrayList<>();
        StaffList staffList = StaffList.getInstance();
        staffList.add(this);
        updatePriority();
    }

    /**
     * Method gets next order in the OrderList queue
     *
     * If there are no orders left the Staff member thread will be left in the waiting state until notified
     */
    @Override
    public void getOrders() {
        if (orderList.getQueueSize(false) * priority > orderList.getQueueSize(true)) {
            currentOrder = orderList.remove();
        }
        else {
            currentOrder = orderList.removeOnline();
        }

        if (currentOrder == null) {
            try {
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException e) {
                logger.logSevere(e.getMessage());
            }
        }
        else {
            for (String item : currentOrder.getDetails()) {
                ItemList itemList = ItemList.getInstance();
                ItemCategory category = itemList.getCategory(item);

                if (isFoodCategory(category)) {
                    FoodList foodList = FoodList.getInstance();
                    FoodItem foodItem = new FoodItem(currentOrder.getOrderID(), item, currentOrder.getClientService());
                    foodList.add(new AbstractMap.SimpleEntry<>(this, foodItem));
                }

                if (isDrinkCategory(category)) {
                    DrinkList drinkList = DrinkList.getInstance();
                    DrinkItem drinkItem = new DrinkItem(currentOrder.getOrderID(), item, currentOrder.getClientService());
                    drinkList.add(new AbstractMap.SimpleEntry<>(this, drinkItem));
                }
            }
        }
    }

    /**
     * Getter method to determine if the item is of type food or not
     *
     * @param category Item category to be checked
     * @return whether the item is a food item or not
     */
    private boolean isFoodCategory(ItemCategory category) {
        return category == ItemCategory.ROLL || category == ItemCategory.FOOD || category == ItemCategory.PASTRY || category == ItemCategory.SNACK;
    }

    /**
     * Getter method to determine if the item is of type drink or not
     *
     * @param category Item category to be checked
     * @return whether the item is a drink item or not
     */
    private boolean isDrinkCategory(ItemCategory category) {
        return category == ItemCategory.HOTDRINK || category == ItemCategory.SOFTDRINK;
    }

    /**
     * Method to show the current order by the staff member has been complete
     *
     * @return Boolean representing whether completion was a success
     */
    @Override
    public boolean completeCurrentOrder() {
        if (currentOrder == null) return false;
        
        orderList.completeOrder(currentOrder);
        logger.logInfo("Waiter " + getWorkerName() + " completed order: " + currentOrder.getOrderID());

        if (hasClientService(currentOrder)) {
            notificationService.sendOrderCompleteNotification(currentOrder.getOrderID(), currentOrder.getClientService());
            notificationService.removeObserver(currentOrder.getClientService());
        }

        currentOrder = null;

        thisOrder = new ArrayList<>();
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
        StringBuilder orderDetails = new StringBuilder();

        orderDetails.append("Staff Name : ").append(this.getWorkerName()).append("\n");
        orderDetails.append("Staff Type : ").append("Waiter").append("\n");
        orderDetails.append("Staff Experience Level : ").append(this.getExperience()).append("\n");

        if (currentOrder == null) {
            orderDetails.append("Staff is Currently Idle").append("\n");
            return orderDetails.toString();
        }

        orderDetails.append("Order ID : ").append(currentOrder.getOrderID()).append("\n");
        orderDetails.append("Customer ID : ").append(currentOrder.getCustomerID()).append("\n");

        for (String itemID : currentOrder.getDetails()) {
            try {
                ItemList itemList = ItemList.getInstance();
                orderDetails.append(itemList.getDescription(itemID)).append("\n");
            } catch (InvalidItemIDException e) {
                logger.logSevere(e.getCause() + " " + e.getMessage());
            }
        }

        orderDetails.append("Total Cost : £").append(currentOrder.getTotalCost()).append("\n");
        orderDetails.append("Discounted Cost : £").append(currentOrder.getDiscountedCost());

        return orderDetails.toString();
    }

    /**
     * Method to return the role of the staff object in this case "waiter"
     *
     * @return String representing this staff's role
     */
    public String getRole() {
        return "Waiter";
    }

    /**
     * Method to return current order that is being processed
     *
     * @return the current order that is being processed
     */
    public Order getCurrentOrder() {
        return currentOrder;
    }

    /**
     * Method to add order back to order list if waiter is removed during operation
     */
    public void addBackOrder() {
        try {
            OrderList.getInstance().add(getCurrentOrder());
        } catch (InvalidOrderException | DuplicateOrderException e) {
            logger.logSevere(e.getCause() + " " + e.getMessage());
        }
    }

    public static void addBackAllCurrentOrders() {
        ArrayList<Order> allOrders = new ArrayList<>();

        for (Waiter waiter : waiterList) {
            try {
                if (waiter.getCurrentOrder() != null) {
                    OrderList.getInstance().add(waiter.getCurrentOrder());
                }
            } catch (InvalidOrderException | DuplicateOrderException e) {
                logger.logSevere(e.getCause() + " " + e.getMessage());
            }
        }
    }

    /**
     * Method used by the Subject (OrderList) to tell the Staff member that an order has been added
     */
    public synchronized void update() {
        notifyAll();
    }

    /**
     * Method used to remove staff member from the simulation
     */
    @Override
    public synchronized void removeStaff() {
        addBackOrder();
        orderList.removeObserver(this);
        active = false;
        waiterList.remove(this);
        updatePriority();
        notifyAll();
        logger.logInfo("Waiter " + getWorkerName() + " removed from the simulation.");
    }

    /**
     * Processing order method - waiter waits until all items have been made by chef's / barista's
     */
    public void processingOrder() {
        while (thisOrder.size() != currentOrder.getDetails().size()) {
            try {
                synchronized (this) {
                    wait();
                    notificationService.sendOrderProcessingNotification(currentOrder.getOrderID(), currentOrder.getClientService());
                }
            } catch (InterruptedException e) {
                logger.logSevere(e.getCause() + " " + e.getMessage());
            }
        }
    }

    /**
     * Used by chefs/baristas to give back items to the desired waiter
     *
     * @param item ItemID of completed item
     */
    public synchronized void addItem(String item) {
        thisOrder.add(item);
        notifyAll();
    }

    /**
     * Method will set the Waiter priority between online and in person orders
     *
     * @param priority The priority to be set for the Waiter
     */
    public void setWaiterPriority(double priority) {
        this.priority = priority;
    }

    /**
     * Method to return waiter's priority
     *
     * @return priority of the waiter as a double
     */
    public double getWaiterPriority() {
        return priority;
    }

    /**
     * This will update all the waiters priorities whenever they are added or removed
     */
    private static void updatePriority() {
        // starting the priority at 0.6 this means the waiters will always favour online orders over in person orders
        double middlePriority = 0.8;
        double tempPriority = Math.pow(middlePriority, waiterList.size());
        double addition = waiterList.size() != 1 ? ( ( middlePriority - tempPriority ) * 2 ) / ( waiterList.size() - 1 ) : 0;
        for (Waiter waiter : waiterList) {
            waiter.setWaiterPriority(tempPriority);
            tempPriority += addition;
        }
    }

    /**
     * Method to return every currently being processed order as a string
     *
     * @return String of orders currently being processed
     */
    public static String getCurrentOrdersForDisplay() {
        StringBuilder orderString = new StringBuilder();

        for (Waiter waiter : waiterList) {
            Order o = waiter.getCurrentOrder();

            if (o != null) {
                String s = String.format("%s,%s,%s",
                        o.getOrderID().toString(),
                        o.getTimestamp().toString(),
                        String.join(";", o.getDetails())
                );

                orderString.append(s).append("\n");
            }
        }

        return orderString.toString();
    }

    /**
     * This method is the Waiter's thread
     */
    @Override
    public void run() {
        while (active) {
            getOrders();
            staffList.notifyObservers();

            if (currentOrder == null) continue;

            if (currentOrder.getClientService() != null) {
                notificationService.addObserver(currentOrder.getClientService());
            }

            orderList.notifyObservers();
            processingOrder();

            try {
                sleep((int) (defaultDelay * ((6.0 - getExperience()) / 5.0)));
            } catch (InterruptedException e) {
                logger.logSevere("InterruptedException in Waiter.run: " + e.getMessage());
            }

            logger.logInfo(getWorkerName() + " completed order " + currentOrder.getOrderID());

            if (active) completeCurrentOrder();
            orderList.notifyObservers();
            staffList.notifyObservers();
        }
    }
}
