package client;

import exceptions.InvalidItemIDException;
import exceptions.InvalidOrderException;
import item.Item;
import item.ItemList;
import logs.CoffeeShopLogger;
import message.Message;
import order.Order;
import order.OrderList;
import utils.Discount;

import javax.swing.*;
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
    private final CoffeeShopLogger logger = CoffeeShopLogger.getInstance();

    private boolean isOnlineOrder = false;

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
     */
    public void setOnlineStatus() {
        if (isOnlineOrder) currentOrder.setOnlineStatus();
    }

    /**
     * Method to create a new empty order
     */
    public void clearOrder() {
        createNewOrder();
    }

    /**
     * Cancels the current order by creating a new one.
     */
    public void cancelOrder() {
        createNewOrder();
    }

    /**
     * Retrieves menu details as an array of strings.
     *
     * @return an array of strings containing menu item details
     */
    public String[] getMenuDetails() {
        return itemList.getMenuDetails();
    }

    /**
     * Retrieves details of the current order as a list of strings.
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

    public void setOnlineOrder(boolean isOnlineOrder) {
        this.isOnlineOrder = isOnlineOrder;
    }

    public boolean isOnlineOrder() {
        return isOnlineOrder;
    }

    /**
     * Updates the item list with the given updated item list.
     *
     * @param updatedItemList the new item list to update
     */
    public void updateItemList(ItemList updatedItemList) {
        itemList.updateItems(updatedItemList.getMenu());
    }

    /**
     * Retrieves the current order.
     *
     * @return the current order object
     */
    public Order getCurrentOrder() {
        return currentOrder;
    }

    /**
     * Display in Customer GUI about daily special offer.
     *
     * @return formatted daily special item details and discount percentage, or "No daily special today" if none exists
     */
    public String getDailySpecialInfo() {
        Item dailySpecial = Discount.getDailySpecialItem();
        if (dailySpecial == null) {
            return "No daily special today";
        }

        return String.format(" \uD83D\uDD25 Today's Special \uD83D\uDD25 \n\n" +
                "Item: %s\n" +
                "Original Price: £%.2f\n" +
                "Special Price: £%.2f\n" +
                "Discount: %d%% OFF\n\n" +
                "Add this to your order:\n%s",
        dailySpecial.getDescription(),
        dailySpecial.getCost(),
        Discount.DAILY_SPECIAL.calculateDiscount(dailySpecial.getCost()),
        Discount.DAILY_SPECIAL.getValue(),
        dailySpecial.getItemID());
    }
}
