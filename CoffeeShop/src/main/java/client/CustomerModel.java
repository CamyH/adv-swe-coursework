package client;

import exceptions.DuplicateOrderException;
import exceptions.InvalidItemIDException;
import exceptions.InvalidOrderException;
import item.Item;
import item.ItemList;
import order.Order;
import order.OrderList;
import utils.Discount;

import java.util.List;

/**
 * Class represents the Model for the customer GUI MVC
 *
 * @author Akash Poonia
 */
public class CustomerModel {
    private Order currentOrder;
    private final OrderList orderList;
    private final ItemList itemList;

    private boolean isOnlineOrder = false;

    /**
     * Constructs a new CustomerModel instance, initializing order and item lists.
     */
    public CustomerModel() {
        orderList = OrderList.getInstance();
        itemList = ItemList.getInstance();
        createNewOrder();
    }

    /**
     * Sets whether the order is an online order.
     *
     * @param isOnlineOrder true if the order is online, false otherwise
     */
    public void setOnlineOrder(boolean isOnlineOrder) {
        this.isOnlineOrder = isOnlineOrder;
    }

    /**
     * Checks if the current order is an online order.
     *
     * @return true if the order is online, false otherwise
     */
    public boolean isOnlineOrder() {
        return isOnlineOrder;
    }

    /**
     * Creates a new order for the customer.
     * If the order creation fails, a runtime exception is thrown.
     */
    private void createNewOrder() {
        try {
            currentOrder = new Order();
        } catch (InvalidOrderException e) {
            throw new RuntimeException("Failed to create a new order", e);
        }
    }

    /**
     * Adds an item to the current order.
     *
     * @param itemID the ID of the item to be added
     * @throws InvalidItemIDException if the item ID is invalid
     */
    public void addItem(String itemID) throws InvalidItemIDException {
        currentOrder.addItem(itemID.toUpperCase());
    }

    /**
     * Removes the last item added to the current order.
     *
     * @return true if an item was removed, false if the order was empty
     */
    public boolean removeLastItem() {
        return currentOrder.removeLastItem();
    }

    /**
     * Removes a specific item from the order.
     *
     * @param itemID the ID of the item to be removed
     * @return true if the item was removed, false if it was not found
     */
    public boolean removeItem(String itemID) {
        return currentOrder.removeItem(itemID.toUpperCase());
    }

    /**
     * Submits the current order to the order list.
     * If the order is an online order, it sets its online status.
     *
     * @return true if the order was successfully submitted, false otherwise
     * @throws InvalidOrderException if the order is invalid
     * @throws DuplicateOrderException if the order already exists in the list
     */
    public boolean submitOrder() throws InvalidOrderException, DuplicateOrderException {
        if (isOnlineOrder) currentOrder.setOnlineStatus();

        boolean added = orderList.add(currentOrder);
        if (added) {
            createNewOrder();
        }
        return added;
    }

    /**
     * Cancels the current order and starts a new one.
     */
    public void cancelOrder() {
        createNewOrder();
    }

    /**
     * Retrieves menu details as an array of strings.
     *
     * @return an array of strings containing menu details
     */
    public String[] getMenuDetails() {
        return itemList.getMenuDetails();
    }

    /**
     * Retrieves details of the current order as a list of strings.
     *
     * @return a list containing order details
     */
    public List<String> getOrderDetails() {
        return currentOrder.getDetails();
    }

    /**
     * Gets the total cost of the current order.
     *
     * @return the total cost as a double
     */
    public double getTotalCost() {
        return currentOrder.getTotalCost();
    }

    /**
     * Gets the discounted total cost of the order.
     *
     * @return the discounted total cost as a double
     */
    public double getDiscountedCost() {
        return currentOrder.getDiscountedCost();
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
