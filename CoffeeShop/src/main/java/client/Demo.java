package client;
import item.ItemFileReader;
import item.ItemList;
import order.OrderFileReadWrite;
import order.OrderList;
import utils.GenerateReportFileWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Demo initialises the CoffeeShop program
 * Contains the main method
 * @author Caelan Mackenzie
 */

public class Demo {

    private static ItemList menu;
    private static OrderList orders;
    private static OrderFileReadWrite orderReader;
    private static ItemFileReader itemReader;
    private static GUI gui;
    private static Console console;

    /**
     * Initialises and Empty ItemList and OrderList
     */
    public Demo() {
        // Initialise empty Item List and Order List
        menu = new ItemList();
        orders = new OrderList();
    }

    /**
     * Runs the Console Code
     */
    public void showConsole() {
        console =  new Console(menu,orders);
        console.run();
    }

    /**
     * Starts the GUI
     */
    public void showGUI() {
        gui = new GUI(menu,orders);
    }

    /**
     * Starts the whole system
     */
    public static void main(String[] args) {
        // Create a new demo object
        Demo demo = new Demo();

        //itemReader = new ItemFileReader("src/main/java/files/menu.txt");
        //itemReader = new ItemFileReader("/files/menu.txt");
        itemReader = new ItemFileReader("menu.txt");
        try {
            menu = itemReader.readFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //orderReader = new OrderFileReadWrite("/files/orders.txt", menu);
        orderReader = new OrderFileReadWrite("orders.txt", menu);
        try {
            orders = orderReader.readFile();
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
        //GenerateReportFileWriter generateReportFileWriter = new GenerateReportFileWriter("/files/report.txt");
        GenerateReportFileWriter generateReportFileWriter = new GenerateReportFileWriter("report.txt");
        ArrayList<String> report = GenerateReportFileWriter.generateReport(orders, menu);
        generateReportFileWriter.writeToFile(report);

        System.exit(0);
    }

    /**
     * Writes to order txt file
     */
    static void demoWriteOrders(){
        try {
            orderReader.writeToFile(orders);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}