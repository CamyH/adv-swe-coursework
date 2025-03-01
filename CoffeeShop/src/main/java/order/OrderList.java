package order;

import exceptions.InvalidOrderException;
import interfaces.EntityList;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.UUID;

/**
 * @author Fraser Holman
 *
 * Class represents a list of current orders
 *
 * Contains a queue of different orders created by customers
 */

public class OrderList implements EntityList<Order, UUID> {
    /** A queue to hold existing Order objects */
    private Queue<Order> queue;

    /**
     * Initialises the queue to contain all the orders
     */
    public OrderList() {
        queue = new ArrayDeque<Order>();
    }

    /**
     * Adds an order to the queue of orders
     * Eg will be used when a new Order has been placed
     *
     * @param order The order to be added to the queue
     */
    @Override
    public Boolean add(Order order) {
        return queue.offer(order);
    }

    /**
     * Removes an order from the queue of orders for processing
     * Eg will be used when an Order has been processed
     *
     * @param ID The ID used to find the order to be removed
     */
    @Override
    public Boolean remove(UUID ID) {
        return queue.removeIf(order -> order.getOrderID().equals(ID));
    }

    /**
     * Get method to return an Order object from queue
     *
     * @param orderID The UUID of the order to be retrieved
     * @return An Order Object
     */
    public Order getOrder(UUID orderID) throws InvalidOrderException {
        for (Order o : queue) {
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
        return queue;
    }

    /**
     * Get method to return the details of an Order
     *
     * @return A string containing
     */
    public String getOrderDetails() {
        return "";
    }
}
