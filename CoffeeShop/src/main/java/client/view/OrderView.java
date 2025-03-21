package client.view;

import client.controller.OrderController;
import exceptions.InvalidOrderException;
import interfaces.Observer;
import order.Order;
import javax.swing.*;
import java.awt.*;
import java.util.Queue;
import java.util.UUID;

public class OrderView extends JFrame implements Observer {
    private OrderController controller;
    private JTextArea orderDisplay;
    private JButton placeOrderButton;
    private JButton removeOrderButton;

    public OrderView(OrderController controller) {
        this.controller = controller;
        setupUI();
    }

    private void setupUI() {
        this.setTitle("Coffee Shop Orders");
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        orderDisplay = new JTextArea();
        orderDisplay.setEditable(false);
        this.add(new JScrollPane(orderDisplay), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        placeOrderButton = new JButton("Place Order");
        removeOrderButton = new JButton("Remove Order");
        buttonPanel.add(placeOrderButton);
        buttonPanel.add(removeOrderButton);
        this.add(buttonPanel, BorderLayout.SOUTH);

        // Add button listeners
        placeOrderButton.addActionListener(e -> {
            Order newOrder = null;  // You need to get details from the user
            try {
                newOrder = new Order();
            } catch (InvalidOrderException ex) {
                throw new RuntimeException(ex);
            }
            controller.placeOrder(newOrder);
        });

        removeOrderButton.addActionListener(e -> {
            String orderIDStr = JOptionPane.showInputDialog("Enter Order ID to remove:");
            if (orderIDStr != null) {
                try {
                    UUID orderID = UUID.fromString(orderIDStr);
                    controller.removeOrder(orderID);
                } catch (IllegalArgumentException ex) {
                    showErrorMessage("Invalid Order ID format!");
                }
            }
        });

        this.setVisible(true);
    }

    public void updateOrderList(Queue<Order> orders) {
        orderDisplay.setText(""); // Clear existing text
        for (Order order : orders) {
            orderDisplay.append(order.getOrderID() + " - " + order.getCustomerID() + "\n");
        }
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void update() {
        controller.placeOrder(null); // Refresh orders when notified
    }

    public void setController(OrderController controller) {
        this.controller = controller;
    }
}
