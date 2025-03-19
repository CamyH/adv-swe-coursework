package client;

import client.controller.CustomerController;
import client.view.CustomerView;
import exceptions.InvalidOrderException;
import order.OrderList;

/**
 * GUI Class - Initializes the application and connects the MVC components.
 */
public class GUI {
    private static CustomerView view;

    public static void main(String[] args) throws InvalidOrderException {
        view = new CustomerView();
        OrderList orders = new OrderList();
        CustomerController controller = new CustomerController(view, orders);

        view.setVisible(true);
    }

    public static void closeGUI() {
        if (view != null) {
            view.dispose();
        }
    }
}
