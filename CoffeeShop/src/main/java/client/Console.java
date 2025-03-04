package client;
import java.util.Scanner;

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

    private final ItemList menu;
    private final OrderList orders;

    public Scanner scanner;

    public Console(ItemList itemList, OrderList orderList) {

        menu = itemList;
        orders = orderList;

        scanner = new Scanner(System.in);
    }

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
                case "help": {
                    System.out.println("viewMenu, newOrder, viewOrderList, addToMenu, removeFromMenu, quit");
                    break;
                }
                case "quit": {
                    System.out.println("Goodbye.");
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
                    case "previewOrder": {
                        previewOrder(curOrder);
                        break;
                    }
                    case "placeOrder": {
                        // UPDATE WITH TRY/CATCH WHEN EMPTY ORDER EXCEPTION IS ADDED TO OrderList.add(Order)
                        orders.add(curOrder);
                        return;
                    }
                    case "help": {
                        System.out.println("addItem, previewOrder, placeOrder, cancel");
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
        curOrder.addItem(curItemID);

    }

    /**
     * Prints the contents of the current order to console
     */
    void previewOrder(Order curOrder) {

        // Get order details
        String orderID = curOrder.getOrderID().toString();
        String customerID = curOrder.getCustomerID();
        String timestamp = curOrder.getTimestamp().toString();
        double totalCost = curOrder.getTotalCost();
        double discountedCost = curOrder.getDiscountedCost();

        // Print basic order information
        System.out.println("Order Preview:");
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
                System.out.println("Cost: £" + item.getCost());
                System.out.println("------------------------");
            }
        }

        // Print cost details
        System.out.println("Total Cost: £" + totalCost);
        System.out.println("Discounted Cost: £" + discountedCost);
        System.out.println("----------------------------");
    }

    /**
     * Prints a list of all the order IDs and customer IDs to console for all incomplete orders
     */
    void viewOrderList() {
        // IMPLEMENT WHEN required method in orderList is implemented
        // orders.orderIDsToString(false)
    }
}