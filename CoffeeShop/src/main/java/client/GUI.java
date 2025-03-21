package client;

import order.OrderList;
import client.controller.OrderController;
import client.view.OrderView;

public class GUI {
    private static OrderView orderView;

    public static void main(String[] args) {
        OrderList orderList = OrderList.getInstance();
        orderView = new OrderView(null);
        OrderController orderController = new OrderController(orderList, orderView);
        orderView.setController(orderController);
        orderList.registerObserver(orderView);
    }

    public static void closeGUI() {
        if (orderView != null) {
            orderView.dispose();
        }
    }
}
