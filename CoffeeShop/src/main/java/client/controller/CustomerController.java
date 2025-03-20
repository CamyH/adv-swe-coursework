package client.controller;

import client.view.CustomerView;
import exceptions.DuplicateOrderException;
import exceptions.InvalidOrderException;
import order.Order;
import order.OrderList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * CustomerController Class - Handles interactions between CustomerView and the Order Model.
 *
 */
public class CustomerController {

    private final CustomerView view;
    private final OrderList orders;
    private Order curOrder;

    public CustomerController(CustomerView view, OrderList orders) throws InvalidOrderException {
        this.view = view;
        this.orders = orders;
        this.curOrder = new Order();
        addListeners();
    }

    private void addListeners() {
        view.getSubmitOrderButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    submitOrder();
                } catch (InvalidOrderException | DuplicateOrderException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        view.getCancelOrderButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cancelOrder();
                } catch (InvalidOrderException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private void submitOrder() throws InvalidOrderException, DuplicateOrderException {
        orders.add(curOrder);
        view.getTotalCostField().setText("");
        view.getDiscountedCostField().setText("");
        System.out.println("Order submitted.");
    }

    private void cancelOrder() throws InvalidOrderException {
        curOrder = new Order();
        view.getTotalCostField().setText("");
        view.getDiscountedCostField().setText("");
        System.out.println("Order canceled.");
    }
}
