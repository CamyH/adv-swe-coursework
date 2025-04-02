package client;

import exceptions.DuplicateOrderException;
import exceptions.InvalidItemIDException;
import exceptions.InvalidOrderException;
import item.ItemList;
import order.Order;
import order.OrderList;

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
}