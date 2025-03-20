package client;
import item.ItemFileReader;
import order.OrderFileReadWrite;
import server.Server;
import utils.GenerateReportFileWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Demo initialises the CoffeeShop program
 * Contains the main method
 * @author Caelan Mackenzie
 */

public class Demo {

    private static OrderFileReadWrite orderReader;
    private static ItemFileReader itemReader;
    private static GUI gui;
    private static Console console;

    /**
     * Initialises and Empty ItemList and OrderList
     */
    public Demo() {}

    /**
     * Runs the Console Code
     */
    public void showConsole() {
        console =  new Console();
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
    static void demoCloseGUI(){
        System.out.println("Goodbye.");
        gui.closeGUI();
        GenerateReportFileWriter generateReportFileWriter = new GenerateReportFileWriter("report.txt");
        generateReportFileWriter.writeToFile();

        System.exit(0);
    }

    /**
     * Writes to order txt file
     */
    static void demoWriteOrders(){
        try {
            orderReader.writeToFile();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}