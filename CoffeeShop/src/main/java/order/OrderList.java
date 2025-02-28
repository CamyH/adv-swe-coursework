package order;

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
    private Queue<Order> queue;

    /**
     * Initialises the queue to contain all the orders
     */
    public OrderList() {
        queue = new ArrayDeque<Order>();
    }

    /**
     * Adds an order to the queue of orders
     *
     * @param order The order to be added to the queue
     */
    @Override
    public void add(Order order) {
        queue.add(order);
    }

    /**
     * Removes an order from the queue of orders for processing
     *
     * @param ID The ID used to find the order to be removed
     */
    @Override
    public void remove(UUID ID) {
        for (Order o : queue) {
            if (o.getOrderID().equals(ID)) {
                queue.remove(o);
                return;
            }
        }
        throw new IllegalArgumentException(ID + " is not a valid order ID");
    }

    /**
     *
     * @param orderID
     * @return An Order Object
     */
    public Order getOrder(UUID orderID) {
        for (Order o : queue) {
            if (o.getOrderID().equals(orderID)) {
                return o;
            }
        }
        throw new IllegalArgumentException(orderID + " is not a valid order ID");
    }

    /**
     *
     * @return The queue of orders
     */
    public Queue<Order> getOrderList() {
        return queue;
    }

    /**
     *
     * @return A string containing
     */
    public String getOrderDetails() {
        return "";
    }
}
