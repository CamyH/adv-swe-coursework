package client;
import item.ItemFileReader;
import item.ItemList;
import order.OrderFileReadWrite;
import order.OrderList;
import utils.GenerateReportFileWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Demo initializes the CoffeeShop program
 * Contains the main method
 * @author Caelan Mackenzie
 */

public class Demo {

    private static OrderFileReadWrite orderReader;
    private static ItemFileReader itemReader;
    private static GUI gui;
    private static Console console;

    /**
     * Initializes an Empty ItemList and OrderList
     */
    public Demo() {}

    /**
     * Runs the Console Code
     */
    public void showConsole() {
        console = new Console();
        console.run();
    }

    /**
     * Starts the GUI
     */
    public void showGUI() {
        gui = new GUI();
    }

    /**
     * Starts the whole system
     */
    public static void main(String[] args) {
        // Create a new demo object
        Demo demo = new Demo();

        try {
            itemReader = new ItemFileReader("menu.txt");
            itemReader.readFile();
        } catch (FileNotFoundException e) {
            System.err.println("Error: menu.txt file not found.");
            return;
        }

        try {
            orderReader = new OrderFileReadWrite("orders.txt");
            orderReader.readFile();
        } catch (FileNotFoundException e) {
            System.err.println("Error: orders.txt file not found.");
            return;
        }

        demo.showGUI();
        demo.showConsole();
    }

    /**
     * Closes the GUI
     */
    static void demoCloseGUI() {
        System.out.println("Goodbye.");
        if (gui != null) {
            gui.closeGUI();
        }
        GenerateReportFileWriter generateReportFileWriter = new GenerateReportFileWriter("report.txt");
        generateReportFileWriter.writeToFile();

        System.exit(0);
    }

    /**
     * Writes to order txt file
     */
    static void demoWriteOrders() {
        try {
            if (orderReader != null) {
                orderReader.writeToFile();
            }
        } catch (IOException e) {
            System.err.println("Error writing to orders.txt: " + e.getMessage());
        }
    }
}
