package client;

import exceptions.DuplicateOrderException;
import exceptions.InvalidItemIDException;
import exceptions.InvalidOrderException;
import utils.SoundPlayer;

import javax.swing.*;
import logs.CoffeeShopLogger;
import utils.RetryPolicy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller Class (Refactored for MVC)
 * Handles user interactions and coordinates between View and Model
 * @author Caelan Mackenzie
 */
public class CustomerController implements ActionListener {

    private final CustomerView view;
    private final CoffeeShopLogger logger = CoffeeShopLogger.getInstance();
    private final CustomerModel model;
    private final Client client;

    /**
     * Initializes the Controller with View and Model
     * @param view The View component
     */
    public CustomerController(CustomerView view, Client client, CustomerModel customerModel) {
        this.client = client;
        this.view = view;
        this.model = customerModel;

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
        } else if (e.getSource() == view.getOnlineOrderCheckBox()) {
            handleOnlineOrderToggle();
        } else if (e.getSource() == view.getEnterCustomerNameBtn()) {
            handleCustomerNameInput();
        }

    }

    /**
     * Handles changing customer name
     */
    private void handleCustomerNameInput() {
        String customerName = view.getEnterCustomerNameBtn().getText().trim();

        if (customerName.isEmpty()) {
            view.showPopup("Customer Name is Empty");
            return;
        }

        customer.setName(customerName);
    }

    /**
     * Handles order submission
     */
    private void handleSubmitOrder() {
        try {
            if (customer.getName() == null) {
                view.showPopup("Please Enter Name");
                return;
            }

            RetryPolicy.retryOnFailure(() ->
                            client.sendOrder(model.getCurrentOrder()),
                    3);
            model.submitOrder();
            boolean isOnline = model.isOnlineOrder();
            SoundPlayer.playSound(SoundPlayer.SoundType.SUBMIT_ORDER);
            String message = isOnline ?
                        "Online order has been submitted for delivery" :
                        "In-store order has been submitted";
                view.showPopup(message);
                updateView();
        } catch (Exception e) {
            logger.logSevere("Failed to submit order: "
                    + e.getClass() + " "
                    + e.getCause() + " "
                    + e.getMessage());
            view.showPopup("Order could not be placed - Please Try Again Later");
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
        view.showPopup("Good Bye!");
        view.closeGUI();
    }

    /**
     * Handles online order checkbox toggle
     */
    private void handleOnlineOrderToggle() {
        boolean isSelected = view.getOnlineOrderCheckBox().isSelected();
        model.setOnlineOrder(isSelected);

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
