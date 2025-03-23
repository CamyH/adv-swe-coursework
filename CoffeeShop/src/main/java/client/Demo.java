package client;

import item.ItemFileReader;
import item.ItemList;
import order.OrderFileReadWrite;
import order.OrderList;
import utils.GenerateReportFileWriter;

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
    private static CustomerView view;
    private static CustomerController customerController;

    /**
     * Initialises and Empty ItemList and OrderList
     */
    public Demo() {}
    /**
     * Runs the Console Code
     */
    public void showConsole() {
        Console console = new Console();
        console.run();
    }

    /**
     * Starts the GUI
     */
    public void showGUI() {
        view = new CustomerView();
        OrderList orders = OrderList.getInstance();
        ItemList menu = ItemList.getInstance();
        customerController = new CustomerController(view);
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
        demo.showConsole();
    }

    /**
     * Closes the GUI
     */
    static void demoCloseGUI() {
        System.out.println("Goodbye.");
        view.closeGUI();

        GenerateReportFileWriter generateReportFileWriter = new GenerateReportFileWriter("report.txt");
        generateReportFileWriter.writeToFile();
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