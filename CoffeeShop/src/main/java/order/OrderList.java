package order;

import exceptions.DuplicateOrderException;
import exceptions.InvalidOrderException;
import interfaces.EntityList;
import interfaces.Subject;

import java.io.Serializable;
import java.util.*;

import interfaces.Observer;


/**
 * Singleton class and uses Observer Design Pattern (this class is the subject)
 *
 * Class represents a list of current orders
 *
 * Contains a queue of different orders created by customers
 *
 * @author Fraser Holman
 */

public class OrderList implements EntityList<Order, UUID>, Subject, Serializable {
    /** A queue to hold existing Order objects */
    private Queue<Order> inCompleteOrders;

    /** A queue to hold completed Order objects
     * This will be implemented in Stage 2 */
    private ArrayList<Order> completeOrders;

    /** Private instance of OrderList */
    private static OrderList instance = new OrderList();

    /** Linked list to hold observer details */
    private List<Observer> registeredObservers = new LinkedList<Observer>();

    /** Integer to check max queue size */
    private int maxQueueSize = 50;

    /**
     * Initialises the queue to contain all the orders
     */
    private OrderList() {
        inCompleteOrders = new ArrayDeque<Order>();
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
     */
    @Override
    public synchronized boolean add(Order order) throws InvalidOrderException, DuplicateOrderException {
        if (inCompleteOrders.size() >= maxQueueSize) {
            return false;
        }

        if (order.getDetails().isEmpty()) {
            throw new InvalidOrderException("Order details cannot be null or empty");
        }

        if (inCompleteOrders.contains(order) || completeOrders.contains(order)) {
            throw new DuplicateOrderException("Duplicate Order");
        }

        notifyObservers();
        return inCompleteOrders.offer(order);
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

        return inCompleteOrders.removeIf(order -> order.getOrderID().equals(ID));
    }

    /**
     * Method to remove and return the first order in the queue
     *
     * the queue or null if inCompleteOrders is empty
     *
     * @return Order object to be processed by staff
     */
    public synchronized Order remove() {
        return inCompleteOrders.poll();
    }

    public void completeOrder(Order order) {
        completeOrders.add(order);
    }

    /**
     * Removes an order from the queue of orders for processing
     */
    public Order getOrder() {
        return inCompleteOrders.peek();
    }

    /**
     * Get method to return an Order object from queue
     *
     * @param orderID The UUID of the order to be retrieved
     * @return An Order Object
     */
    public Order getOrder(UUID orderID) throws InvalidOrderException {
        for (Order o : inCompleteOrders) {
            if (o.getOrderID().equals(orderID)) {
                return o;
            }
        }
        throw new InvalidOrderException(orderID + " is not a valid order ID");
    }

    /**
     * Get method to return the entire queue of Order Objects
     *
     * @return The queue of orders
     */
    public Queue<Order> getOrderList() {
        return new LinkedList<>(inCompleteOrders);
    }

    /**
     * Method used to return details of uncompleted orders as a string array
     *
     * @return a String array with each entry formatted as below
     * (Order ID,Customer ID,Timestamp,Order Details Array [Item ID],Total Cost,Discounted Cost) e.g.
     */
    public String[] getOrdersToString(Boolean completed) {
        Collection<Order> c = completeOrders;

        if (!completed) {
            c = inCompleteOrders;
        }

        String[] uncompletedOrderString = new String[c.size()];

        int count = 0;

        for (Order o : c) {
            String s = String.format("%s,%s,%s,%s",
                o.getOrderID().toString(),
                o.getCustomerID(),
                o.getTimestamp().toString(),
                String.join(";", o.getDetails())
            );

            uncompletedOrderString[count] = s;

            count++;
        }

        return uncompletedOrderString;
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

        for (Order o : inCompleteOrders) {
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
            c = inCompleteOrders;
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
        return instance;
    }

    /**
     * Reset the OrderList singleton instance
     * Used by tests
     */
    public static void resetInstance() {
        instance = new OrderList();
    }

    /**
     * Method used to register observers
     *
     * @param obs The observer to be added to the list of observers
     */
    public void registerObserver(Observer obs) {
        registeredObservers.add(obs);
    }

    /**
     * Method used to remove observers
     *
     * @param obs The observer to be removed from the list of observers
     */
    public void removeObserver(Observer obs) {
        registeredObservers.remove(obs);
    }

    /**
     * Method used to notify observers
     */
    public void notifyObservers() {
        for(Observer obs : registeredObservers) obs.update();
    }
}
