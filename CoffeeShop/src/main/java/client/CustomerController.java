package client;

import exceptions.DuplicateOrderException;
import exceptions.InvalidItemIDException;
import exceptions.InvalidOrderException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller Class (Refactored for MVC)
 * Handles user interactions and coordinates between View and Model
 * @author Caelan Mackenzie
 */
public class CustomerController implements ActionListener {

    private final CustomerView view;
    private final CustomerModel model;

    /**
     * Initializes the Controller with View and Model
     * @param view The View component
     */
    public CustomerController(CustomerView view) {
        this.view = view;
        this.model = new CustomerModel();

        // Set up action listeners
        view.getSubmitOrderButton().addActionListener(this);
        view.getCancelOrderButton().addActionListener(this);
        view.getAddItemButton().addActionListener(this);
        view.getRemoveLastItemButton().addActionListener(this);
        view.getRemoveItemButton().addActionListener(this);
        view.getExitButton().addActionListener(this);
        view.getOnlineOrderCheckBox().addActionListener(this);

        // Initialize view with menu data
        view.displayMenu(model.getMenuDetails());
        view.displayDailySpecial(model.getDailySpecialInfo());
    }

    /**
     * Handles button click events
     * @param e The ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getSubmitOrderButton()) {
            handleSubmitOrder();
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
        }else if (e.getSource() == view.getOnlineOrderCheckBox()) {
            handleOnlineOrderToggle();
        }

    }

    /**
     * Handles order submission
     */
    private void handleSubmitOrder() {
        try {
            boolean isOnline = model.isOnlineOrder();
            boolean orderAdded = model.submitOrder();

            if (orderAdded) {
                String message = isOnline ?
                        "Online order has been submitted for delivery" :
                        "In-store order has been submitted";
                view.showPopup(message);
                updateView();
            } else {
                view.showPopup("Order could not be placed - Please Try Again Later");
            }
        } catch (InvalidOrderException | DuplicateOrderException e) {
            view.showPopup(e.getMessage());
        }
    }

    /**
     * Handles order cancellation
     */
    private void handleCancelOrder() {
        model.cancelOrder();
        view.getOnlineOrderCheckBox().setSelected(false);
        view.showPopup("Order Cancelled");
        updateView();
    }

    /**
     * Handles item addition
     */
    private void handleAddItem() {
        String itemID = view.getItemIDField().getText().trim();

        // Guard clause
        if (itemID.isEmpty()) {
            view.showPopup("Please Input an Item ID");
            return;
        }

        try {
            model.addItem(itemID.toUpperCase());
        } catch (InvalidItemIDException e) {
            view.showPopup(itemID.toUpperCase() + " is not a valid item ID");
        }
        view.getItemIDField().setText("");
        updateView();
    }

    /**
     * Handles removal of last item
     */
    private void handleRemoveLastItem() {
        if (!model.removeLastItem()) {
            view.showPopup("No Items in this Order");
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
                view.showPopup(itemID.toUpperCase() + " is not in the current order");
            }
        }
    }

    /**
     * Handles application exit
     */
    private void handleExit() {
        Demo.demoCloseGUI();  // Existing demo functionality
        view.showPopup("Good Bye!");
        view.closeGUI();
    }

    /**
     * Handles online order checkbox toggle
     */
    private void handleOnlineOrderToggle() {
        boolean isSelected = view.getOnlineOrderCheckBox().isSelected();
        model.setOnlineOrder(isSelected);

        // Update UI based on order type
        if (isSelected) {
            view.showPopup("Online order selected. Delivery charges may apply.");
        }
        updateView(); // Refresh to show any price changes
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