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

public class CustomerModel {
    private Order currentOrder;
    private final OrderList orderList;
    private final ItemList itemList;

    public CustomerModel() {
        orderList = OrderList.getInstance();
        itemList = ItemList.getInstance();
        createNewOrder();
    }

    private void createNewOrder() {
        try {
            currentOrder = new Order();
        } catch (InvalidOrderException e) {
            throw new RuntimeException("Failed to create a new order", e);
        }
    }

    public void addItem(String itemID) throws InvalidItemIDException {
        currentOrder.addItem(itemID.toUpperCase());
    }

    public boolean removeLastItem() {
        return currentOrder.removeLastItem();
    }

    public boolean removeItem(String itemID) {
        return currentOrder.removeItem(itemID.toUpperCase());
    }

    public boolean submitOrder() throws InvalidOrderException, DuplicateOrderException {
        boolean added = orderList.add(currentOrder);
        if (added) {
            createNewOrder();
        }
        return added;
    }

    public void cancelOrder() {
        createNewOrder();
    }

    public String[] getMenuDetails() {
        return itemList.getMenuDetails();
    }

    public List<String> getOrderDetails() {
        return currentOrder.getDetails();
    }

    public double getTotalCost() {
        return currentOrder.getTotalCost();
    }

    public double getDiscountedCost() {
        return currentOrder.getDiscountedCost();
    }

    /**
     * Display about daily special offer. 
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
