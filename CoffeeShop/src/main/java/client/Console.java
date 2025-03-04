package client;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

import item.Item;
import item.ItemList;
import order.Order;
import order.OrderList;

/**
 * Console Class
 * @author Caelan Mackenzie
 */
public class Console {

    private ItemList menu;
    private OrderList orders;

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

                /*

                case "addItem": {
                    addItem();
                    break;
                }
                case "placeOrder": {
                    placeOrder();
                    break;
                }

                */

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
            System.out.println("Cost: $" + entry.getCost());
            System.out.println("------------------------");
        }
    }

    /**
     * Not implemented for Stage 1
     */
    void addToMenu() {

    }

    /**
     * Not implemented for Stage 1
     */
    void removeFromMenu() {

    }

    /**
     * Initialises a new order so that the user can add items and then add to order list
     */
    void newOrder() {

        ;

        System.out.println("Enter the command 'addItem' to add an item to the order, then enter 'placeOrder' to place an order.");
        System.out.println("Enter 'cancel' to cancel the order or 'help' for a list of commands.");
        while (true) {
            /* needs menu to be initialised
            Order order = new Order(menu);
            */
            System.out.println("Enter your command:");
            String command = scanner.nextLine();
            switch (command) {
                case "addItem": {
                    addItem();
                    break;
                }
                case "previewOrder": {
                    previewOrder();
                    break;
                }
                case "placeOrder": {
                    placeOrder();
                    break;
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
    }

    /**
     * Adds an item to the current order
     */
    void addItem() {

    }

    /**
     * Prints the contents of the current order to console
     */
    void previewOrder() {

    }

    /**
     * Description
     */
    void placeOrder() {

    }

    /**
     * Description
     */
    void viewOrderList() {

    }
}