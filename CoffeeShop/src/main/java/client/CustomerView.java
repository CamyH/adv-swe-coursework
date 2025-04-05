package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * View Class (Refactored from GUI by Akash)
 * Represents the user interface for the Coffee Shop application.
 * @author Caelan Mackenzie
 * @author Akash Poonia
 */
public class CustomerView extends JFrame {

    // UI components
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
    private JCheckBox onlineOrderCheckBox;
    private JPanel dailySpecialPanel;
    private JTextArea dailySpecialTextArea;

    /**s
     * Sets up the View (GUI)
     */
    public CustomerView() {
        // UI parameters

        SwingUtilities.invokeLater(() -> {
            setContentPane(contentPanel);
            setTitle("Coffee Shop App");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(900, 400);
            setLocationRelativeTo(null); // Center the window
            setVisible(true);
            initDailySpecialPanel();

            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    Demo.cleanUp();
                }
            });

            // Disable editing for certain fields
            totalCostField.setEnabled(false);
            totalCostField.setDisabledTextColor(Color.BLACK);
            discountedCostField.setEnabled(false);
            discountedCostField.setDisabledTextColor(Color.BLACK);
            displayMenuField.setEnabled(false);
            displayMenuField.setDisabledTextColor(Color.BLACK);
            orderDetailsField.setEnabled(false);
            orderDetailsField.setDisabledTextColor(Color.BLACK);

            // Set scroll bars to always be visible
            itemListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            orderDetailsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            orderDetailsField.setText("Current Order: \n");

            // Initialize fields
            totalCostField.setText("£0.00");
            discountedCostField.setText("£0.00");
            displayMenuField.append("Item ID, Name, Cost \n");
        });

    }

    private void initDailySpecialPanel() {

        SwingUtilities.invokeLater(() -> {
            dailySpecialTextArea.setEditable(false);
            dailySpecialTextArea.setLineWrap(true);
            dailySpecialTextArea.setWrapStyleWord(true);
            dailySpecialTextArea.setMargin(new Insets(5, 5, 5, 5));

            // Set scroll policy if scroll pane exists
            if (dailySpecialTextArea.getParent() instanceof JScrollPane) {
                ((JScrollPane) dailySpecialTextArea.getParent())
                        .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            }
        });
    }

    /**
     * Updates the UI with the current order details
     *
     * @param orderDetails Details of the current order
     * @param totalCost Total cost of the order
     * @param discountedCost Discounted cost of the order
     */
    public void updateUI(String orderDetails, double totalCost, double discountedCost) {
        SwingUtilities.invokeLater(() -> {
            orderDetailsField.setText("Current Order: \n" + orderDetails);
            totalCostField.setText("£" + String.format("%.2f", totalCost));
            discountedCostField.setText("£" + String.format("%.2f", discountedCost));
        });
    }

    /**
     * Displays the menu in the UI
     *
     * @param menuDetails Details of the menu items
     */
    public void displayMenu(String[] menuDetails) {
        SwingUtilities.invokeLater(() -> {
            displayMenuField.setText("");
            for (String entry : menuDetails) {
                displayMenuField.append(entry + "\n");
            }
        });
    }

    public void displayDailySpecial(String specialText) {
        SwingUtilities.invokeLater(() -> {
            dailySpecialTextArea.setText(specialText);
        });
    }

    public void showPopup(String message) {
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(CustomerView.this, message));
    }

    /**
     * Closes the View (GUI)
     */
    public void closeGUI() {
        dispose();
    }

    // Getters for buttons (to be used by the Controller)
    public JButton getSubmitOrderButton() {
        return submitOrderButton;
    }

    public JButton getCancelOrderButton() {
        return cancelOrderButton;
    }

    public JCheckBox getOnlineOrderCheckBox() {
        return onlineOrderCheckBox;
    }

    public JButton getAddItemButton() {
        return addItemButton;
    }

    public JButton getRemoveLastItemButton() {
        return removeLastItemButton;
    }

    public JButton getRemoveItemButton() {
        return removeItemButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }

    public JTextField getItemIDField() {
        return itemIDField;
    }
}