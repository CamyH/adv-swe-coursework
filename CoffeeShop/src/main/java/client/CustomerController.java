package client;

import exceptions.DuplicateOrderException;
import exceptions.InvalidItemIDException;
import exceptions.InvalidOrderException;
import item.ItemList;
import logs.CoffeeShopLogger;
import order.Order;
import order.OrderList;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Controller Class (Refactored for MVC)
 * Handles user interactions and coordinates between View and Model
 * @author Caelan Mackenzie
 */
public class CustomerController implements ActionListener {
    private final CustomerView view;
    private Order currentOrder;
    private final CoffeeShopLogger logger = CoffeeShopLogger.getInstance();
    private final CustomerModel model;
    private final Client client;

    /**
     * Initializes the Controller with View and Model
     * @param view The View component
     */
    public CustomerController(CustomerView view, Client client, CustomerModel customerModel) {
        this.view = view;
        this.model = customerModel;
        this.client = client;

        // Set up action listeners
        view.getSubmitOrderButton().addActionListener(this);
        view.getCancelOrderButton().addActionListener(this);
        view.getAddItemButton().addActionListener(this);
        view.getRemoveLastItemButton().addActionListener(this);
        view.getRemoveItemButton().addActionListener(this);
        view.getExitButton().addActionListener(this);

        // Initialize view with menu data
        view.displayMenu(model.getMenuDetails());
    }

    /**
     * Handles button click events
     * @param e The ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getSubmitOrderButton()) {
            submitOrder(model.getCurrentOrder());
        } else if (e.getSource() == view.getCancelOrderButton()) {
            handleCancelOrder();
        } else if (e.getSource() == view.getAddItemButton()) {
            handleAddItem();
        } else if (e.getSource() == view.getRemoveLastItemButton()) {
            handleRemoveLastItem();
        } else if (e.getSource() == view.getRemoveItemButton()) {
            handleRemoveItem();
        } else if (e.getSource() == view.getExitButton()) {
            handleExit();
        }
    }

    /**
     * Handles order submission
     */
    private void submitOrder(Order order) {
        try {
            //Demo.demoWriteOrders();
            System.out.println(order.getOrderID());
            client.sendOrder(order);
            JOptionPane.showMessageDialog(view, "Order has been submitted");
        } catch (IOException e) {
            // this disgusts me don't forget to refactor
            logger.logWarning(e.getClass() + " Order was not sent, retrying " + e.getMessage());
            try {
                client.sendOrder(order);
            } catch (IOException e1) {
                logger.logSevere("Could not send order, retry failed" + e1.getMessage());
            }
        }
    }

    /**
     * Handles order cancellation
     */
    private void handleCancelOrder() {
        model.cancelOrder();
        JOptionPane.showMessageDialog(view, "Order Cancelled");
        updateView();
    }

    /**
     * Handles item addition
     */
    private void handleAddItem() {
        String itemID = view.getItemIDField().getText().trim();
        if (itemID.isEmpty()) {
            return;
        }
        try {
            model.addItem(itemID.toUpperCase());
        } catch (InvalidItemIDException e) {
            JOptionPane.showMessageDialog(view, itemID.toUpperCase() + " is not a valid item ID");
        }
        view.getItemIDField().setText("");
        updateView();
    }




    /**
     * Handles removal of last item
     */
    private void handleRemoveLastItem() {
        if (!model.removeLastItem()) {
            JOptionPane.showMessageDialog(view, "No Items in this Order");
        }
        updateView();
    }

    /**
     * Handles removal of specific item
     */
    private void handleRemoveItem() {
        String itemID = view.getItemIDField().getText().trim();
        if (!itemID.isEmpty()) {
            if (model.removeItem(itemID)) {
                view.getItemIDField().setText("");
                updateView();
            } else {
                JOptionPane.showMessageDialog(view,
                        itemID.toUpperCase() + " is not in the current order");
            }
        }
    }

    /**
     * Handles application exit
     */
    private void handleExit() {
        Demo.demoCloseGUI();  // Existing demo functionality
        JOptionPane.showMessageDialog(view, "Good Bye!");
        view.closeGUI();
    }

    /**
     * Updates the View with current order data
     */
    private void updateView() {
        StringBuilder orderDetails = new StringBuilder();
        for (String item : model.getOrderDetails()) {
            orderDetails.append(item).append("\n");
        }

        view.updateUI(
                orderDetails.toString(),
                model.getTotalCost(),
                model.getDiscountedCost()
        );
    }
}