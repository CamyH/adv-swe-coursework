package client;
import item.ItemList;
import order.OrderList;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI Class
 * @author Caelan Mackenzie
 */

public class GUI extends JFrame {

    // The item list (menu) to be displayed
    // private ItemList menu;

    // The order list to add submitted orders to
    // private OrderList orders

    // GUI components
    private JPanel contentPanel;
        private JPanel detailsPane;
            private JPanel costsPane;
                private JButton submitOrderButton;
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
                private JTextField displayListField;
            private JScrollPane orderDetailsScrollPane;
                private JTextField orderDetailsField;
            private JButton exitButton;


    // Constructor
    public GUI(ItemList menu, OrderList orders) {

        // UI parameters
        setContentPane(contentPanel);
        setTitle("Coffee Shop App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,300);
        // Ensure the UI window is shown in the center of the screen
        setLocationRelativeTo(null);
        setVisible(true);

        // Make the scroll panes always have vertical scroll bars visible
        itemListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        orderDetailsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Action listeners for button presses
        submitOrderButton.addActionListener(this::actionPerformed);
        addItemButton.addActionListener(this::actionPerformed);
        exitButton.addActionListener(this::actionPerformed);
    }

    // Functionality for when a button is pressed
    public void actionPerformed(ActionEvent e) {

        // Submit Order button functionality
        if (e.getSource() == submitOrderButton) {
            JOptionPane.showMessageDialog(GUI.this, "Order has been submitted");
        }

        // Add Item button functionality
        else if (e.getSource() == addItemButton) {
            String itemID = itemIDField.getText();
            JOptionPane.showMessageDialog(GUI.this, itemID);
        }

        // Exit
        else if (e.getSource() == exitButton) {
            JOptionPane.showMessageDialog(GUI.this, "Good Bye!");
            closeGUI();
        }

    }

    public void closeGUI() {
        // close the window
        GUI.this.dispose();
    }
}