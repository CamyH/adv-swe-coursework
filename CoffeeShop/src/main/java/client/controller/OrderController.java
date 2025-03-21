package client.controller;

import order.Order;
import order.OrderList;
import exceptions.DuplicateOrderException;
import exceptions.InvalidOrderException;
import client.view.OrderView;
import java.util.UUID;

public class OrderController {
    private OrderList orderList;
    private OrderView view;

    public OrderController(OrderList orderList, OrderView view) {
        this.orderList = orderList;
        this.view = view;
    }

    public void placeOrder(Order order) {
        try {
            orderList.add(order);
            view.updateOrderList(orderList.getOrderList());
        } catch (InvalidOrderException | DuplicateOrderException e) {
            view.showErrorMessage(e.getMessage());
        }
    }

    public void removeOrder(UUID orderID) {
        try {
            boolean removed = orderList.remove(orderID);
            if (removed) {
                view.updateOrderList(orderList.getOrderList());
            } else {
                view.showErrorMessage("Order not found!");
            }
        } catch (InvalidOrderException e) {
            view.showErrorMessage(e.getMessage());
        }
    }
}
