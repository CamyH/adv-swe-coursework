package client;
import java.util.Scanner;

/**
 * @author Caelan Mackenzie
 * Console Class
 */

public class Console {

    /*
    private ItemList menu;
    private OrderList orders
    */

    public Scanner scanner;

    public Console(/* ItemList itemList, OrderList orderList */) {

        /*
        menu = itemList;
        orders = orderList
        */

        scanner = new Scanner(System.in);
    }

    public void run(){
        System.out.println("Coffee Shop Console");
        System.out.println("Type 'help' for a list of commands.");

        while (true){
            System.out.println("Enter your command:");
            String command = scanner.nextLine();
            switch (command) {
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
                case "addItem": {
                    addItem();
                    break;
                }
                case "placeOrder": {
                    placeOrder();
                    break;
                }
                case "viewOrderList": {
                    viewOrderList();
                    break;
                }
                case "help": {
                    System.out.println("newOrder, viewOrderList, addToMenu, removeFromMenu, quit");
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

    private void addToMenu() {

    }

    private void removeFromMenu() {

    }

    private void newOrder() {
        System.out.println("Enter the command 'addItem' to add an item to the order, then enter 'placeOrder' to place an order.");
    }

    private void addItem() {

    }

    private void placeOrder() {

    }

    private void viewOrderList() {

    }
}