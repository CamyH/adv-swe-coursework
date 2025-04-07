package order;

import client.SimUIController;
import client.SimUIModel;
import exceptions.DuplicateOrderException;
import exceptions.InvalidOrderException;
import interfaces.EntityList;
import interfaces.Subject;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import logs.CoffeeShopLogger;
import utils.SoundPlayer;

import static java.lang.Thread.sleep;


/**
 * Singleton class and uses Observer Design Pattern (this class is the subject)
 *
 * Class represents a list of current orders
 *
 * Contains a queue of different orders created by customers
 *
 * @author Fraser Holman
 */

public class OrderList extends Subject implements EntityList<Order, UUID>, Serializable, Runnable {
    /** A queue to hold completed Order objects
     * This will be implemented in Stage 2 */
    private ArrayList<Order> completeOrders;

    private ArrayList<Queue<Order>> allOrders;

    private ArrayList<Order> simulationOrders;

    /** Private instance of OrderList */
    private static OrderList instance;

    /** Integer to check max queue size */
    private int maxQueueSize = 50;

    /** Logger instance */
    private final CoffeeShopLogger logger;

    /**
     * Initialises the queue to contain all the orders
     */
    private OrderList() {
        allOrders = new ArrayList<>();
        simulationOrders = new ArrayList<>();
        allOrders.add(new ArrayDeque<Order>());
        allOrders.add(new ArrayDeque<Order>());
        logger = CoffeeShopLogger.getInstance();
        completeOrders = new ArrayList<>();
    }

    /**
     * Method to set the maximum queue size
     *
     * Only used in tests to set max queue size to a reset instance of OrderList
     *
     * This method can be used within operation but if the queue was initially a larger size it will still contain those extra orders
     *
     * @param maxQueueSize Sets the maximum size of the queue before customers can no longer order
     */
    public void setMaxQueueSize(int maxQueueSize) {
        this.maxQueueSize = maxQueueSize;
    }

    /**
     * Adds an order to the queue of orders
     * Eg will be used when a new Order has been placed
     *
     * @param order The order to be added to the queue
     * @return True if order successfully added, false otherwise
     * @throws InvalidOrderException if the order is incorrect
     * @throws DuplicateOrderException if the order already exists
     */
    @Override
    public synchronized boolean add(Order order) throws InvalidOrderException, DuplicateOrderException {
        if (allOrders.getFirst().size() + allOrders.getLast().size() >= maxQueueSize) {
            logger.logWarning("Order queue is full. Cannot add new order.");
            return false;
        }

        if (Order.isInvalidOrder(order)) {
            logger.logSevere("Invalid order: Order or Order ID cannot be null");
            throw new InvalidOrderException("Order or Order ID cannot be null");
        }

        if (Order.isOrderDetailsNullOrEmpty(order)) {
            logger.logSevere("Invalid order: Order must contain at least one item");
            throw new InvalidOrderException("Order must contain at least one item");
        }

        if (allOrders.stream().anyMatch(queue -> queue.contains(order)) || completeOrders.contains(order)) {
            logger.logWarning("Duplicate order detected: " + order.getOrderID());
            throw new DuplicateOrderException("Duplicate Order");
        }

        logger.logInfo("Order added to queue: " + order.getOrderID());

        boolean success = order.getOnlineStatus() ? allOrders.getLast().offer(order) : allOrders.getFirst().offer(order);

        notifyObservers();

        return success;
    }

    /**
     * Method to add orders from order.txt file into the simulation before being displayed
     *
     * @param order The order to be added
     * @return True if successfully added, False if not
     * @throws InvalidOrderException If order is missing details
     * @throws DuplicateOrderException If order already exists
     */
    public boolean addSimulation(Order order) throws InvalidOrderException, DuplicateOrderException {
        if (order == null) {
            logger.logSevere("Invalid order: Order details cannot be null");
            throw new InvalidOrderException("Order details cannot be null");
        }

        if (order.getDetails().isEmpty()) {
            logger.logSevere("Invalid order: Order details cannot be empty");
            throw new InvalidOrderException("Order details cannot be empty");
        }

        if (allOrders.stream().anyMatch(queue -> queue.contains(order)) || completeOrders.contains(order)) {
            logger.logWarning("Duplicate order detected: " + order.getOrderID());
            throw new DuplicateOrderException("Duplicate Order");
        }

        logger.logInfo("Order added to queue: " + order.getOrderID());

        return simulationOrders.add(order);
    }

    /**
     * Removes an order from the queue of orders for processing
     * Eg will be used when an Order has been processed
     *
     * @param ID The ID used to find the order to be removed
     */
    @Override
    public synchronized boolean remove(UUID ID) throws InvalidOrderException {
        completeOrders.add(this.getOrder(ID));
        
        logger.logInfo("Order processed and moved to completed orders: " + ID);

        if (allOrders.getFirst().removeIf(order -> order.getOrderID().equals(ID))) return true;

        return allOrders.getLast().removeIf(order -> order.getOrderID().equals(ID));
    }

    /**
     * Method to remove and return the first order in the in person queue
     *
     * the queue or null if in person orders queue is empty
     *
     * @return Order object to be processed by staff
     */
    public synchronized Order remove() {
        Order o = allOrders.getFirst().poll();
        if (o != null) {
            notifyObservers();
            notifyAll();
        }
        return o;
    }

    /**
     * Method to remove and return the first order in the online queue
     *
     * the queue or null if online orders is empty
     *
     * @return Order object to be processed by staff
     */
    public synchronized Order removeOnline() {
        Order o = allOrders.getLast().poll();

        if (o == null) {
            return null;
        }

        notifyObservers();
        notifyAll();

        return o;
    }

    /**
     * Completes an order from the queue of orders and plays a sound
     */
    public synchronized void completeOrder(Order order) {
        completeOrders.add(order);
        notifyObservers();
        logger.logInfo("Order completed: " + order.getOrderID());
        SoundPlayer.playSound(SoundPlayer.SoundType.ORDER_COMPLETE);
    }

    /**
     * Removes an order from the queue of orders for processing
     */
    public Order getOrder(boolean online) {
        if (online) return allOrders.getLast().peek();

        return allOrders.getFirst().peek();
    }

    /**
     * Method to return the number of orders in the orderlist queue
     *
     * @param online Checks whether the caller wants the size of the online or in person order queue
     * @return an integer representing the size of the queue
     */
    public synchronized int getQueueSize(boolean online) {
        if (online) return allOrders.getLast().size();

        return allOrders.getFirst().size();
    }

    /**
     * Get method to return an Order object from queue
     *
     * @param orderID The UUID of the order to be retrieved
     * @return An Order Object
     */
    public synchronized Order getOrder(UUID orderID) throws InvalidOrderException {
        /**
         * Combines the two queues from the array list into one stream
         * Saves having to use nested for loops
         */
        for (Order o : allOrders.stream().flatMap(Collection::stream).toList()) {
            if (o.getOrderID().equals(orderID)) {
                return o;
            }
        }
        logger.logWarning("Invalid order ID: " + orderID);
        throw new InvalidOrderException(orderID + " is not a valid order ID");
    }

    /**
     * Get method to return the entire queue of Order Objects
     *
     * @return The queue of orders
     */
    public Queue<Order> getOrderList() {
        return new ArrayDeque<>(allOrders.stream().flatMap(Collection::stream).collect(Collectors.toCollection(ArrayDeque::new)));
    }

    /**
     * Method used to return details of either all or completed orders as a string array
     *
     * @param completed represents whether to return a string array of completed or incomplete orders
     * @return a String array with each entry formatted as below
     * (Order ID,Customer ID,Timestamp,Order Details Array [Item ID],Total Cost,Discounted Cost) e.g.
     */
    public String[] getOrdersToString(boolean completed) {
        Collection<Order> c = completeOrders;

        if (!completed) {
            c = Stream.of(
                            simulationOrders.stream(),
                            completeOrders.stream(),
                            allOrders.stream().flatMap(Collection::stream)
                    )
                    .flatMap(s -> s)
                    .toList();
        }

        String[] orderString = new String[c.size()];

        int count = 0;

        for (Order o : c) {
            String s = String.format("%s,%s,%s,%s,%b",
                o.getOrderID().toString(),
                o.getCustomerID(),
                o.getTimestamp().toString(),
                String.join(";", o.getDetails()),
                o.getOnlineStatus()
            );

            orderString[count] = s;

            count++;
        }

        return orderString;
    }

    /**
     * Method used by the simulation GUI to get a summary of orders that need to be complete
     *
     * @param state whether to return online or in person orders or complete orders
     * @return a string array of orders to be displayed
     */
    public String getOrdersForDisplay(int state) {
        Collection<Order> c;

        if (state == 0) {
            c = allOrders.getFirst();
        }
        else if (state == 1) {
            c = allOrders.getLast();
        }
        else {
            c = completeOrders;
        }

        StringBuilder orderString = new StringBuilder();

        for (Order o : c) {
            String s = String.format("%s,%s,%s",
                    o.getCustomerName(),
                    o.getTimestamp().toString(),
                    String.join(";", o.getDetails())
            );

            orderString.append(s).append("\n");
        }

        // Remove the last newline if needed
        if (!c.isEmpty()) {
            orderString.setLength(orderString.length() - 1);
        }

        return orderString.toString();
    }

    /**
     * Method to return a summary of the purchased items and quantity
     *
     * @return Hashmap containing what items have been purchased
     */
    public HashMap<String, Double> completedOrderItemCount() {
        HashMap<String, Double> itemCount = new HashMap<>();

        double totalCost = 0;
        double discountCost = 0;
        double numOrders = 0;

        for (Order o : completeOrders) {
            ArrayList<String> string = o.getDetails();

            totalCost += o.getTotalCost();
            discountCost += o.getDiscountedCost();
            numOrders++;

            for (String s : string) {
                itemCount.put(s, itemCount.getOrDefault(s, 0.0) + 1.0);
            }
        }

        itemCount.put("total-cost", totalCost);
        itemCount.put("discount-cost", discountCost);
        itemCount.put("num-orders", numOrders);

        return itemCount;
    }

    /**
     * Method to return a string array of Order IDs to be used on the console
     *
     * @param completed chooses whether to convert completed or incomplete order IDs to string
     * @return String[] returns a string array of IDs that can be printed directly
     */
    public String[] orderIDsToString(boolean completed) {
        Collection<Order> c = completeOrders;

        if (!completed) {
            c = allOrders.stream().flatMap(Collection::stream).toList();
        }

        String[] orderIDsArr = new String[c.size()];

        int count = 0;

        for (Order o : c) {
            String s = String.format("%s,%s",
                    o.getOrderID().toString(),
                    o.getCustomerID()
            );

            orderIDsArr[count] = s;

            count++;
        }

        return orderIDsArr;
    }

    /**
     * Getter method to return an instance of OrderList
     *
     * @return an instance of OrderList
     */
    public static OrderList getInstance() {
        if (instance == null) instance = new OrderList();
        return instance;
    }

    /**
     * Reset the OrderList singleton instance
     * Used by tests
     */
    public static void resetInstance() {
        instance = new OrderList();
    }

    @Override
    public void run() {
        while (!simulationOrders.isEmpty()) {
            try {
                if (!this.add(simulationOrders.removeFirst())) {
                    synchronized (this) {
                        wait();
                    }
                }
            }
            catch (InvalidOrderException | DuplicateOrderException | InterruptedException e) {
                System.err.println("Error in client: " + e.getClass() + " " + e.getCause() + " " + e.getMessage());
            }

            try {
                Thread.sleep((int) ((10000.0 - ( SimUIModel.getSimSpeed() / 100.0 * 10000.0 ) + 100.0)/2.0));
            }
            catch (InterruptedException e) {
                System.err.println("Error in client: " + e.getClass() + " " + e.getCause() + " " + e.getMessage());
            }
        }
    }
}
