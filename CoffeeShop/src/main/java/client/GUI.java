package client;
import exceptions.InvalidItemIDException;
import exceptions.InvalidOrderException;
import item.ItemList;
import order.OrderList;
import order.Order;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * GUI Class
 * @author Caelan Mackenzie
 */

public class GUI extends JFrame {

    // The item list (menu) to be displayed
    private final ItemList menu;

    // The order list to add submitted orders to
    private OrderList orders;

    // An instance of the order object to edit before submitting
    private Order curOrder;

    // GUI components
    private JPanel contentPanel;
    private JPanel detailsPane;
    private JPanel costsPane;
    private JButton submitOrderButton;
    private JButton cancelOrderButton;
    private JPanel totalCostPane;
    private JLabel totalCostLabel;
    private JTextField totalCostField;
    private JPanel discountedCostPane;
    private JLabel discountedCost;
    private JTextField discountedCostField;
    private JPanel itemInsertPane;
    private JLabel enterItemID;
    private JTextField itemIDField;
    private JButton addItemButton;
    private JPanel itemListPane;
    private JScrollPane itemListScrollPane;
    private JTextArea displayMenuField;
    private JScrollPane orderDetailsScrollPane;
    private JTextArea orderDetailsField;
    private JButton exitButton;
    private JButton removeLastItemButton;
    private JButton removeItemButton;


    // Constructor
    public GUI(ItemList itemList, OrderList orderList) {
        
        orders = orderList;
        menu = itemList;
        try {
            curOrder = new Order(menu);
        } catch (InvalidOrderException e) {
            throw new RuntimeException(e);
        }

        // UI parameters
        setContentPane(contentPanel);
        setTitle("Coffee Shop App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,300);
        // Ensure the UI window is shown in the center of the screen
        setLocationRelativeTo(null);
        setVisible(true);

        totalCostField.setEnabled(false);
        totalCostField.setDisabledTextColor(Color.BLACK);
        discountedCostField.setEnabled(false);
        discountedCostField.setDisabledTextColor(Color.BLACK);
        displayMenuField.setEnabled(false);
        displayMenuField.setDisabledTextColor(Color.BLACK);
        orderDetailsField.setEnabled(false);
        orderDetailsField.setDisabledTextColor(Color.BLACK);

        // Make the scroll panes always have vertical scroll bars visible
        itemListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        orderDetailsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Action listeners for button presses
        submitOrderButton.addActionListener(this::actionPerformed);
        cancelOrderButton.addActionListener(this::actionPerformed);
        addItemButton.addActionListener(this::actionPerformed);
        removeLastItemButton.addActionListener(this::actionPerformed);
        removeItemButton.addActionListener(this::actionPerformed);
        exitButton.addActionListener(this::actionPerformed);



        totalCostField.setText("£0.00");
        discountedCostField.setText("£0.00");
        displayMenuField.append("Item ID, Name, Cost \n");
        for (String entry : menu.getMenuDetails()) {
            displayMenuField.append(entry + "\n");
        }

        orderDetailsField.setText("Empty");

    }

    // Functionality for when a button is pressed
    public void actionPerformed(ActionEvent e) {

        // Submit Order button functionality
        if (e.getSource() == submitOrderButton) {
            submitOrder();
        }

        else if (e.getSource() == cancelOrderButton) {
            cancelOrder();
        }

        // Add Item button functionality
        else if (e.getSource() == addItemButton) {
            addItem();
        }

        else if (e.getSource() == removeLastItemButton) {
            removeLastItem();
        }

        else if (e.getSource() == removeItemButton) {
            removeItem();
        }

        // Exit
        else if (e.getSource() == exitButton) {
            JOptionPane.showMessageDialog(GUI.this, "Good Bye!");
            closeGUI();
        }

    }

    private void addItem() {
        String itemID = itemIDField.getText();
        try {
            curOrder.addItem(itemID.toUpperCase());
        } catch (InvalidItemIDException e) {
            JOptionPane.showMessageDialog(GUI.this, itemID.toUpperCase() + " is not a valid item ID");
        }
        itemIDField.setText("");
        updateUI();
    }

    private void removeItem(){
        String itemID = itemIDField.getText();
        if (!curOrder.removeItem(itemID.toUpperCase())) JOptionPane.showMessageDialog(GUI.this, itemID.toUpperCase() + " is not a valid item ID");

        itemIDField.setText("");
        updateUI();
    }

    private void removeLastItem(){
        if (!curOrder.removeLastItem()) JOptionPane.showMessageDialog(GUI.this,  "No Items in this Order");
        updateUI();
    }

    public void submitOrder(){
        try {
            orders.add(curOrder);
            Demo.demoWriteOrders();
            JOptionPane.showMessageDialog(GUI.this, "Order has been submitted");
        } catch (InvalidOrderException e) {
            JOptionPane.showMessageDialog(GUI.this,"Can't submit an empty order");
        }
        try {
            curOrder = new Order(menu);
            updateUI();
        } catch (InvalidOrderException e) {
            throw new RuntimeException(e);
        }
    }

    public void cancelOrder() {
        JOptionPane.showMessageDialog(GUI.this, "Order Cancelled");
        try {
            curOrder = new Order(menu);
            updateUI();
        } catch (InvalidOrderException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void updateUI() {
        orderDetailsField.setText("Current Order: \n");
        for (String entry : curOrder.getDetails()) {
            orderDetailsField.append(entry + "\n");
        }
        totalCostField.setText("£" + String.format("%.2f", curOrder.getTotalCost()));
        discountedCostField.setText("£" + String.format("%.2f", curOrder.getDiscountedCost()));
    }

    public void closeGUI() {
        // close the window
        GUI.this.dispose();
    }
}