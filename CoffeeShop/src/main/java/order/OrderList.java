package order;

import exceptions.InvalidOrderException;
import interfaces.EntityList;
import item.ItemList;

import java.util.*;

/**
 * Singleton class
 *
 * Class represents a list of current orders
 *
 * Contains a queue of different orders created by customers
 *
 * @author Fraser Holman
 */

public class OrderList implements EntityList<Order, UUID> {
    /** A queue to hold existing Order objects */
    private Queue<Order> inCompleteOrders;

    /** A queue to hold completed Order objects
     * This will be implemented in Stage 2 */
    private ArrayList<Order> completeOrders;

    /** Private instance of OrderList */
    private static OrderList instance = new OrderList();

    /**
     * Initialises the queue to contain all the orders
     */
    private OrderList() {
        inCompleteOrders = new ArrayDeque<Order>();
        completeOrders = new ArrayList<>();
    }

    /**
     * Adds an order to the queue of orders
     * Eg will be used when a new Order has been placed
     *
     * @param order The order to be added to the queue
     */
    @Override
    public Boolean add(Order order) throws InvalidOrderException {
        if (order.getDetails().isEmpty()) {
            throw new InvalidOrderException("Order details cannot be null");
        }
        return inCompleteOrders.offer(order);
    }

    /**
     * Removes an order from the queue of orders for processing
     * Eg will be used when an Order has been processed
     *
     * @param ID The ID used to find the order to be removed
     */
    @Override
    public Boolean remove(UUID ID) {
        try {
            completeOrders.add(this.getOrder(ID));
            return inCompleteOrders.removeIf(order -> order.getOrderID().equals(ID));
        }
        catch (InvalidOrderException e) {
            System.out.println(e.getMessage());
        }
        return false;
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

    public static void resetInstance() {
        instance = new OrderList();
    }
}
