package client;
import java.util.Scanner;
import java.util.UUID;

import exceptions.InvalidItemIDException;
import exceptions.InvalidOrderException;
import item.Item;
import item.ItemList;
import order.Order;
import order.OrderList;

/**
 * Console Class
 * @author Caelan Mackenzie
 */
public class Console {

    /** ItemList object holding the item information */
    private final ItemList menu;

    /** Order List holding all existing orders */
    private final OrderList orders;

    public Scanner scanner;

    /**
     * starts up the console
     *
     * @param itemList takes in itemList
     * @param orderList takes in orderList
     */
    public Console(ItemList itemList, OrderList orderList) {

        menu = itemList;
        orders = orderList;
        scanner = new Scanner(System.in);
    }

    /**
     * Runs the console
     */
    public void run(){
        System.out.println("Coffee Shop Console");
        System.out.println("Type 'help' for a list of commands.");

        while (true){
            System.out.println("Enter your command:");
            String command = scanner.nextLine();
            switch (command) {
                case "viewMenu": {
                    viewMenu();
                    break;
                }
                case "addToMenu": {
                    addToMenu();
                    break;
                }
                case "removeFromMenu": {
                    removeFromMenu();
                    break;
                }
                case "newOrder": {
                    newOrder();
                    break;
                }
                case "viewOrderList": {
                    viewOrderList();
                    break;
                }
                case "viewOrderDetails": {
                    viewOrderDetails();
                    break;
                }
                case "help": {
                    System.out.println("viewMenu, newOrder, viewOrderList, viewOrderDetails, addToMenu, removeFromMenu, help, quit");
                    break;
                }
                case "cmdDescriptions": {
                    printCmdDescriptions();
                    break;
                }
                case "quit": {
                    Demo.demoCloseGUI();
                    return;
                }
                default: {
                    System.out.println("Unknown command. Type 'help' for a list of commands.");
                }
            }
        }
    }

    /**
     * Prints the entire contents of the menu to the console
     */
    void viewMenu(){
        System.out.println("Item List:");
        System.out.println("------------------------");
        for (Item entry : menu.getMenu().values()) {
            System.out.println("ID: " + entry.getItemID());
            System.out.println("Description: " + entry.getDescription());
            System.out.println("Category: " + entry.getCategory());
            System.out.println("Cost: £" + entry.getCost());
            System.out.println("------------------------");
        }
    }

    /**
     * Not implemented for Stage 1
     */
    void addToMenu() {
        System.out.println("Not implemented for Stage 1");
    }

    /**
     * Not implemented for Stage 1
     */
    void removeFromMenu() {
        System.out.println("Not implemented for Stage 1");
    }

    /**
     * Initialises a new order so that the user can add items and then add to order list
     */
    void newOrder() {
        Order curOrder;
        try {
            curOrder = new Order(menu);
            System.out.println("Enter the command 'addItem' to add an item to the order, then enter 'placeOrder' to place an order.");
            System.out.println("Enter 'cancel' to cancel the order or 'help' for a list of commands.");
            while (true) {

                System.out.println("Enter your command:");
                String command = scanner.nextLine();
                switch (command) {
                    case "addItem": {
                        addItem(curOrder);
                        break;
                    }
                    case "removeLastItem": {
                        removeLastItem(curOrder);
                        break;
                    }
                    case "removeItem": {
                        removeItem(curOrder);
                        break;
                    }
                    case "previewOrder": {
                        previewOrder(curOrder);
                        break;
                    }
                    case "placeOrder": {
                        placeOrder(curOrder);
                        return;
                    }
                    case "help": {
                        System.out.println("addItem, removeItem, removeLastItem, previewOrder, placeOrder, cancel");
                        break;
                    }
                    case "cancel": {
                        System.out.println("Order cancelled");
                        return;
                    }
                    default: {
                        System.out.println("Unknown command. Type 'help' for a list of commands.");
                    }
                }
            }
        } catch (InvalidOrderException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adds an item to the current order
     */
    void addItem(Order curOrder) {

        System.out.println("Enter the item ID:");
        String curItemID = scanner.nextLine();
        try {
            curOrder.addItem(curItemID.toUpperCase());
        } catch (InvalidItemIDException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Removes a specific item from the current order
     */
    void removeItem(Order curOrder) {

        System.out.println("Enter the item ID:");
        String curItemID = scanner.nextLine();
        if (!curOrder.removeItem(curItemID.toUpperCase())) System.out.println(curItemID.toUpperCase() + " not a valid item ID");

    }

    /**
     * Removes the last item from the current order object
     *
     * @param curOrder The order to remove the last item from
     */
    void removeLastItem(Order curOrder) {
        curOrder.removeLastItem();
        System.out.println("Removed the last item from the current order");
    }

    /**
     * Prints the contents of the current order to console
     * @param curOrder The current order requested by the user
     */
    void previewOrder(Order curOrder) {

        // Get order details
        String orderID = curOrder.getOrderID().toString();
        String customerID = curOrder.getCustomerID().toString();
        String timestamp = curOrder.getTimestamp().toString();
        double totalCost = curOrder.getTotalCost();
        double discountedCost = curOrder.getDiscountedCost();

        // Print basic order information
        System.out.println("Order Preview");
        System.out.println("--------------------------");
        System.out.println("Order ID: " + orderID);
        System.out.println("Customer ID: " + customerID);
        System.out.println("Timestamp: " + timestamp);
        System.out.println("----------------------------");

        // Print item details
        System.out.println("Items in the Order:");
        for (String itemID : curOrder.getDetails()) {
            Item item = menu.getMenu().get(itemID);
            if (item != null) {
                System.out.println("Item ID: " + item.getItemID());
                System.out.println("Description: " + item.getDescription());
                System.out.println("Category: " + item.getCategory());
                System.out.println("Cost: £" + String.format("%.2f", item.getCost()));
                System.out.println("------------------------");
            }
        }

        // Print cost details
        System.out.println("Total Cost: £" + String.format("%.2f", totalCost));
        System.out.println("Discounted Cost: £" + String.format("%.2f", discountedCost));
        System.out.println("----------------------------");
    }

    /**
     * Method to place and order from the console
     *
     * @param curOrder Current Order that should be placed
     */
    private void placeOrder(Order curOrder) {
        try {
            orders.add(curOrder);
            Demo.demoWriteOrders();
            System.out.println("Order placed successfully");
        } catch (InvalidOrderException e) {
            //System.out.println(e.getMessage());
            System.out.println("Order cannot be empty, add an item or cancel the order");
        }
    }

    /**
     * Method to print console command descriptions
     */
    void printCmdDescriptions() {
        System.out.println("viewMenu prints a list of all available items and their details.");
        System.out.println("newOrder starts a new order.");
        System.out.println("viewOrderList prints a list of all of the order IDs and their related customer IDs in the order list.");
        System.out.println("viewOrderDetails prints the details from the desired order ID and the contained item IDs");
        System.out.println("addToMenu is unimplemented in Stage 1");
        System.out.println("removeFromMenu is unimplemented in Stage 1");
        System.out.println("help shows all available commands");
        System.out.println("quit terminates the console and closes the GUI, if it is open");
    }

    /**
     * Prints a list of all the order IDs and customer IDs to console for all incomplete orders
     */
    void viewOrderList() {
        String[] orderIDArr = orders.orderIDsToString(false);
        System.out.println("Order List:");
        System.out.println("(Order ID, Customer ID)");
        for (String orderID : orderIDArr) {
            System.out.println(orderID);
        }
    }

    /**
     * Method to view order details
     */
    void viewOrderDetails() {
        System.out.println("Enter the order ID:");
        System.out.println("Enter 'cancel' to cancel");
        String orderID = scanner.nextLine();
        if (orderID.equals("cancel")) {
            System.out.println("View order cancelled");
            return;
        }
        try {
            Order curOrder = orders.getOrder(UUID.fromString(orderID));
            System.out.println("--------------------------");
            System.out.println("Order details:");
            System.out.println("Order ID: " + curOrder.getOrderID());
            System.out.println("Customer ID: " + curOrder.getCustomerID());
            System.out.println("Timestamp: " + curOrder.getTimestamp());
            System.out.println("Total Cost: £" + String.format("%.2f", curOrder.getTotalCost()));
            System.out.println("Discounted Cost: £" + String.format("%.2f", curOrder.getDiscountedCost()));
            System.out.println("--------------------------");
            System.out.println("Items in the Order:");
            for (String itemID : curOrder.getDetails()) {
                System.out.println(itemID);
            }
            System.out.println("--------------------------");
        } catch (InvalidOrderException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.println("Invalid Order ID");
        }
    }
}
