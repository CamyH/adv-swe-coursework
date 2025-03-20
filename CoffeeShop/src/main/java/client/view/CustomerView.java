package client.view;

import javax.swing.*;
import java.awt.*;

/**
 * CustomerView Class - Handles the graphical user interface components.
 *private JPanel totalCostPane;
 * private JPanel costsPane;
 *     private JPanel discountedCostPane;
 */
public class CustomerView extends JFrame {


    private JButton submitOrderButton;
    private JButton cancelOrderButton;
    private JTextField totalCostField;
    private JTextField discountedCostField;

    public CustomerView() {
        setTitle("Coffee Shop - Customer Order");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        // GUI components
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        // Order details panel
        JPanel detailsPane = new JPanel();
        detailsPane.setLayout(new GridLayout(2, 2));

        JLabel totalCostLabel = new JLabel("Total Cost:");
        totalCostField = new JTextField();
        JLabel discountedCostLabel = new JLabel("Discounted Cost:");
        discountedCostField = new JTextField();

        detailsPane.add(totalCostLabel);
        detailsPane.add(totalCostField);
        detailsPane.add(discountedCostLabel);
        detailsPane.add(discountedCostField);

        // Buttons
        submitOrderButton = new JButton("Submit Order");
        cancelOrderButton = new JButton("Cancel Order");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitOrderButton);
        buttonPanel.add(cancelOrderButton);

        contentPanel.add(detailsPane, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(contentPanel);
    }

    // Getters for buttons
    public JButton getSubmitOrderButton() {
        return submitOrderButton;
    }

    public JButton getCancelOrderButton() {
        return cancelOrderButton;
    }

    public JTextField getTotalCostField() {
        return totalCostField;
    }

    public JTextField getDiscountedCostField() {
        return discountedCostField;
    }
}
