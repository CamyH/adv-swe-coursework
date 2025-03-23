package client;

import item.ItemFileReader;
import item.ItemList;
import order.OrderFileReadWrite;
import order.OrderList;

import java.io.FileNotFoundException;

/**
 * Refactored to support MVC by Akash
 * Demo initializes the Coffee Shop program.
 * Contains the main method.
 * @author Caelan Mackenzie
 */
public class Demo {

    private static OrderFileReadWrite orderReader;
    private static ItemFileReader itemReader;
    private static View view;
    private static Controller controller;

    /**
     * Initializes and Empty ItemList and OrderList
     */
    public Demo() {}

    /**
     * Starts the GUI
     */
    public void showGUI() {
        view = new View();
        OrderList orders = OrderList.getInstance();
        ItemList menu = ItemList.getInstance();
        controller = new Controller(view, orders, menu);
    }

    /**
     * Starts the whole system
     */
    public static void main(String[] args) {
        Demo demo = new Demo();

        itemReader = new ItemFileReader("menu.txt");
        try {
            itemReader.readFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        orderReader = new OrderFileReadWrite("orders.txt");
        try {
            orderReader.readFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        demo.showGUI();
    }

    /**
     * Closes the GUI
     */
    static void demoCloseGUI() {
        System.out.println("Goodbye.");
        view.close();
        System.exit(0);
    }

    /**
     * Writes to order txt file
     */
    static void demoWriteOrders() {
        try {
            orderReader.writeToFile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}