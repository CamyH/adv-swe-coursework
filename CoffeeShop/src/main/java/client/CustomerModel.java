package client;

import exceptions.DuplicateOrderException;
import exceptions.InvalidItemIDException;
import exceptions.InvalidOrderException;
import item.ItemList;
import order.Order;
import order.OrderList;

import java.util.List;

/**
 * CustomerModel represents the model layer for managing customer orders
 * and interacting with the item menu. It allows adding/removing items from orders,
 * submitting orders, and canceling orders.
 * The model ensures that the order is correctly managed and interacts with the
 * shared order list and item list.
 *
 * @author Akash Poonia
 */
public class CustomerModel {
    private Order currentOrder;
    private final OrderList orderList;
    private final ItemList itemList;

    /**
     * Constructs a new CustomerModel, initializes the order list and item list,
     * and creates a new order for the customer.
     */
    public CustomerModel() {
        orderList = OrderList.getInstance();
        itemList = ItemList.getInstance();
        createNewOrder();
    }

    /**
     * Creates a new order and sets it as an online order.
     *
     * @throws RuntimeException if there is an error creating the order
     */
    private void createNewOrder() {
        try {
            currentOrder = new Order();
            currentOrder.setOnlineStatus();
        } catch (InvalidOrderException e) {
            throw new RuntimeException("Failed to create a new order", e);
        }
    }

    /**
     * Adds an item to the current order using the provided item ID.
     *
     * @param itemID the ID of the item to add
     * @throws InvalidItemIDException if the item ID is invalid
     */
    public void addItem(String itemID) throws InvalidItemIDException {
        currentOrder.addItem(itemID.toUpperCase());
    }

    /**
     * Removes the last item added to the current order.
     *
     * @return true if the item was removed successfully, false otherwise
     */
    public boolean removeLastItem() {
        return currentOrder.removeLastItem();
    }

    /**
     * Removes a specific item from the current order using the provided item ID.
     *
     * @param itemID the ID of the item to remove
     * @return true if the item was removed successfully, false otherwise
     */
    public boolean removeItem(String itemID) {
        return currentOrder.removeItem(itemID.toUpperCase());
    }

    /**
     * Submits the current order to the order list.
     * If the order is successfully added, a new order is created.
     *
     * @return true if the order was added to the list, false otherwise
     * @throws InvalidOrderException if the order is invalid
     * @throws DuplicateOrderException if the order is a duplicate
     */
    public boolean submitOrder() throws InvalidOrderException, DuplicateOrderException {
        boolean added = orderList.add(currentOrder);
        if (added) {
            createNewOrder();
        }
        return added;
    }

    /**
     * Cancels the current order by creating a new one.
     */
    public void cancelOrder() {
        createNewOrder();
    }

    /**
     * Retrieves the details of the menu items.
     *
     * @return an array of menu item details
     */
    public String[] getMenuDetails() {
        return itemList.getMenuDetails();
    }

    /**
     * Retrieves the details of the current order.
     *
     * @return a list of order details (items, total cost, etc.)
     */
    public List<String> getOrderDetails() {
        return currentOrder.getDetails();
    }

    /**
     * Retrieves the total cost of the current order.
     *
     * @return the total cost of the order
     */
    public double getTotalCost() {
        return currentOrder.getTotalCost();
    }

    /**
     * Retrieves the discounted cost of the current order.
     *
     * @return the discounted cost of the order
     */
    public double getDiscountedCost() {
        return currentOrder.getDiscountedCost();
    }
}