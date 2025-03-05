package client;
import item.ItemFileReader;
import item.ItemList;
import order.OrderFileReadWrite;
import order.OrderList;
import utils.GenerateReportFileWriter;

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
    static GUI gui;

    public Demo() {
        // Initialise empty Item List and Order List
        menu = new ItemList();
        orders = new OrderList();
    }

    public void showConsole() {
        Console console =  new Console(menu,orders);
        console.run();
    }

    public void showGUI() {
        gui = new GUI(menu,orders);
    }


    public static void main(String[] args) {
        // Create a new demo object
        Demo demo = new Demo();

        orderReader = new OrderFileReadWrite("src/main/java/files/orders.txt");
        try {
            orders = orderReader.readFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        itemReader = new ItemFileReader("src/main/java/files/menu.txt");
        try {
            menu = itemReader.readFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        demo.showGUI();
        demo.showConsole();
    }

    static void demoCloseGUI(){
        gui.closeGUI();
        GenerateReportFileWriter generateReportFileWriter = new GenerateReportFileWriter("src/main/java/files/test.txt");
        ArrayList<String> test2 = generateReportFileWriter.generateReport(orders, menu);
        generateReportFileWriter.writeToFile(test2);
    }

    static void demoWriteOrders(){
        orderReader.writeToFile(orders);
    }
}