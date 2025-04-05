package client;

import item.Item;
import item.ItemFileReader;
import item.ItemList;
import order.OrderFileReadWrite;
import order.OrderList;
import utils.Discount;
import utils.GenerateReportFileWriter;
import workers.StaffList;
import workers.Waiter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private static SimUIView simView;
    private static SimUIModel simModel;
    private static CustomerController customerController;
    private static Console console;
    private static SimUIController simController;

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
        customerController = new CustomerController(view);
    }

    /**
     * Starts the Simulation GUI
     */
    public void showSimUI(){
        simModel = new SimUIModel();
        simView = new SimUIView(simModel);
        simController =  new SimUIController(simView, simModel);
    }

    /**
     * Starts the whole system
     */
    public static void main(String[] args) {
        Demo demo = new Demo();

        itemReader = new ItemFileReader("menu.txt");
        try {
            itemReader.readFile();
            setDailySpecial();
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
        demo.showSimUI();
        demo.showConsole();
    }

    /**
     * Randomly selects an item from the item list to be today's special offer.
     * Sets the selected item in the Discount class with a random discount between 50-75 percentage.
     */
    private static void setDailySpecial() {
        ItemList itemList = ItemList.getInstance();
        List<Item> items = new ArrayList<>(itemList.getMenu().values());

        if (!items.isEmpty()) {
            Random random = new Random();
            Item dailySpecial = items.get(random.nextInt(items.size()));
            Discount.setDailySpecialItem(dailySpecial);
        }
    }

    /**
     * Closes the GUI
     */
    static void demoCloseGUI() {
        System.out.println("Goodbye.");
        view.closeGUI();
        simController.close();

        GenerateReportFileWriter generateReportFileWriter = new GenerateReportFileWriter("report.txt");
        generateReportFileWriter.writeToFile();

        System.exit(0);
    }

    /**
     * Writes to order txt file
     */
    static void demoWriteOrders() {
        // is this a coursework requirement to write orders as they are added?
        // think it will just be worth just writing to the file at the end with all the orders or adjust it to just append rather than overwrite
//        try {
//            orderReader.writeToFile();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }

    /**
     * Shuts down the system as necessary
     */
    static void cleanUp() {
        System.out.println("Goodbye.");

        Waiter.addBackAllCurrentOrders();

        try {
            orderReader.writeToFile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        GenerateReportFileWriter generateReportFileWriter = new GenerateReportFileWriter("report.txt");
        generateReportFileWriter.writeToFile();
    }
}
