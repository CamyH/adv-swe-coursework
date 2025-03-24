package client;

import exceptions.DuplicateOrderException;
import exceptions.InvalidItemIDException;
import exceptions.InvalidOrderException;
import item.Item;
import item.ItemList;
import order.Order;
import order.OrderList;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

/**
 * Controller Class (Refactored for MVC by Akash)
 * Handles user interactions and updates the Model and View.
 * @author Caelan Mackenzie
 */
public class CustomerController implements ActionListener {

    private final CustomerView view;
    private final OrderList orders;
    private final ItemList menu;
    private Order currentOrder;

    /**
     * Initializes the Controller
     *
     * @param view The View (GUI)
     */
    public CustomerController(CustomerView view) {
        this.view = view;
        orders = OrderList.getInstance();
        menu = ItemList.getInstance();

        // Initialize the current order
        try {
            currentOrder = new Order();
            // Initialize and display daily special
            Item dailySpecial = findDailySpecial();
            if (dailySpecial != null) {
                view.setDailySpecial(dailySpecial.getDescription());
            }
        } catch (InvalidOrderException e) {
            throw new RuntimeException(e);
        }

        // Set up action listeners for buttons
        view.getSubmitOrderButton().addActionListener(this);
        view.getCancelOrderButton().addActionListener(this);
        view.getAddItemButton().addActionListener(this);
        view.getRemoveLastItemButton().addActionListener(this);
        view.getRemoveItemButton().addActionListener(this);
        view.getExitButton().addActionListener(this);

        // Display the menu in the View
        view.displayMenu(menu.getMenuDetails());
    }

    /**
     * Handles button clicks
     *
     * @param e The ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getSubmitOrderButton()) {
            submitOrder();
        } else if (e.getSource() == view.getCancelOrderButton()) {
            cancelOrder();
        } else if (e.getSource() == view.getAddItemButton()) {
            addItem();
        } else if (e.getSource() == view.getRemoveLastItemButton()) {
            removeLastItem();
        } else if (e.getSource() == view.getRemoveItemButton()) {
            removeItem();
        } else if (e.getSource() == view.getExitButton()) {
            JOptionPane.showMessageDialog(view, "Good Bye!");
            exit();
        }
    }

    /**
     * Submits the current order
     */
    private void submitOrder() {
        try {

            if (!orders.add(currentOrder)) {
                JOptionPane.showMessageDialog(view, "Order could not be placed - Please Try Again Later");
                return;
            }
            Demo.demoWriteOrders();
            JOptionPane.showMessageDialog(view, "Order has been submitted");
        } catch (InvalidOrderException | DuplicateOrderException e) {
            JOptionPane.showMessageDialog(view, e.getMessage());
        }
        try {
            currentOrder = new Order();
            updateView();
        } catch (InvalidOrderException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Cancels the current order
     */
    private void cancelOrder() {
        JOptionPane.showMessageDialog(view, "Order Cancelled");
        try {
            currentOrder = new Order();
            updateView();
        } catch (InvalidOrderException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Adds an item to the current order
     */
    private void addItem() {
        String itemID = view.getItemIDField().getText();
        try {
            currentOrder.addItem(itemID.toUpperCase());
        } catch (InvalidItemIDException e) {
            JOptionPane.showMessageDialog(view, itemID.toUpperCase() + " is not a valid item ID");
        }
        view.getItemIDField().setText("");
        updateView();
    }

    /**
     * Removes the last item from the current order
     */
    private void removeLastItem() {
        if (!currentOrder.removeLastItem()) {
            JOptionPane.showMessageDialog(view, "No Items in this Order");
        }
        updateView();
    }

    /**
     * Removes a specific item from the current order
     */
    private void removeItem() {
        String itemID = view.getItemIDField().getText();
        if (!currentOrder.removeItem(itemID.toUpperCase())) {
            JOptionPane.showMessageDialog(view, itemID.toUpperCase() + " is not a valid item ID");
        }
        view.getItemIDField().setText("");
        updateView();
    }

    /**
     * Exits the application
     */
    private void exit() {
        Demo.demoCloseGUI();
    }

    /**
     * Updates the View with the current order details
     */
    private void updateView() {
        StringBuilder orderDetails = new StringBuilder();
        for (String entry : currentOrder.getDetails()) {
            orderDetails.append(entry).append("\n");
        }
        view.updateUI(orderDetails.toString(), currentOrder.getTotalCost(), currentOrder.getDiscountedCost());
    }

    private Item findDailySpecial() {
        // Get the values collection from the map which is iterable
        Collection<Item> items = menu.getMenu().values();
        for (Item item : items) {
            if (item.isDailySpecial()) {
                return item;
            }
        }
        return null;
    }
}