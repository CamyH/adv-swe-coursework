package workers;

import order.Order;
import order.OrderList;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import logs.CoffeeShopLogger;

/**
 * Class represents how a Barista functions in the Coffee Shop Simulation
 *
 * This class uses two design patterns:
 * 1. Factory Design Pattern (with Staff and StaffFactory)
 * 2. Observer Design Pattern (between this class and OrderList)
 *
 * Tasks:
 * 1. Check for Order by checking incomplete orders in OrderList
 * 2. Wait specified amount of time to complete order
 * 3. Add order to complete orders in OrderList
 *
 * @author Fraser Holman
 */
public class Barista extends Staff {
    /** Stores the List of Orders */
    private OrderList orderList;

    /** Stores the current order this staff member is working on */
    private Order currentOrder;

    /** Tells if the staff member is currently active (ie not fired) */
    private boolean active = true;

    /** Whether the barista prioritises online or in person order
     * 0-1 = the smaller the number the more it prioritises online orders
     * 1 = prioritises neither as much as the other
     * >1 = larger the positive integer the more in person orders are prioritised
     * */
    private double priority = 0;

    /** List of existing Baristas */
    private static List<Barista> baristaList = new ArrayList<>();

    /** Logger instance */
    private CoffeeShopLogger logger = CoffeeShopLogger.getInstance();

    /**
     * Constructor to instantiate a new staff member
     *
     * @param name Name of the staff member
     * @param experience Experience level of the staff member
     */
    public Barista(String name, double experience) {
        super(name, experience);
        orderList = OrderList.getInstance();
        orderList.registerObserver(this);
        baristaList.add(this);
        updatePriority();
    }

    /**
     * Method gets next order in the OrderList queue
     *
     * If there are no orders left the Staff member thread will be left in the waiting state until notified
     */
    @Override
    public synchronized void getOrders() {
        if (orderList.getQueueSize(false) * priority > orderList.getQueueSize(true)) {
            currentOrder = orderList.remove();
        }
        else {
            currentOrder = orderList.removeOnline();
        }

        if (currentOrder == null) {
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
        if (currentOrder == null) return false;
        
        orderList.completeOrder(currentOrder);
        logger.logInfo("Barista " + getWorkerName() + " completed order: " + currentOrder.getOrderID());
        currentOrder = null;
        return true;
    }

    /**
     * This method will be used to display current order details on the GUI
     *
     * Will return a list of items in the order. This can be further customised depending on what we want to show
     *
     * @return ArrayList of Current Order Details
     */
    public ArrayList<String> getCurrentOrderDetails() {
        if (currentOrder == null) return null;
        
        return currentOrder.getDetails();
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
    public synchronized void removeStaff() {
        orderList.removeObserver(this);
        active = false;
        baristaList.remove(this);
        updatePriority();
        notifyAll(); // wakes up thread
        logger.logInfo("Barista " + getWorkerName() + " removed from the simulation.");
    }

    /**
     * Method will set the baristas priority between online and in person orders
     *
     * @param priority The priority to be set for the barista
     */
    public void setBaristaPriority(double priority) {
        this.priority = priority;
    }

    /**
     * Method to return barista's priority
     *
     * @return priority of the barista as a double
     */
    public double getBaristaPriority() {
        return priority;
    }

    /**
     * This will update all the baristas priorities whenever they are added or removed
     */
    private static void updatePriority() {
        // starting the priority at 0.6 this means the baristas will always favour online orders over in person orders
        double middlePriority = 0.8;
        double tempPriority = Math.pow(middlePriority, baristaList.size());
        double addition = baristaList.size() != 1 ? ( ( middlePriority - tempPriority ) * 2 ) / ( baristaList.size() - 1 ) : 0;
        for (Barista barista : baristaList) {
            barista.setBaristaPriority(tempPriority);
            tempPriority += addition;
        }
    }

    /**
     * This method is the Barista's thread
     */
    @Override
    public void run() {
        while (active) {
            getOrders();

            if (currentOrder != null) {
                try {
                    sleep((int) (defaultDelay * ((6 - getExperience()) / 5)));
                } catch (InterruptedException e) {
                    logger.logSevere("InterruptedException in Barista.run: " + e.getMessage());
                }

                System.out.println(getWorkerName() + " completed order " + currentOrder.getOrderID());
                completeCurrentOrder();
            }

        }
    }
}
