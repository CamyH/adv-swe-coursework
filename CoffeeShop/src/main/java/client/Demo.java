package client;

import interfaces.INotificationService;
import item.Item;
import item.ItemFileReader;
import item.ItemList;
import order.OrderFileReadWrite;
import services.NotificationService;
import utils.GenerateReportFileWriter;
import workers.Waiter;

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
    private static SimUIView simView;
    private static SimUIModel simModel;
    private static CustomerController customerController;
    private static Console console;
    private static SimUIController simController;
    private CustomerModel customerModel;

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
    public void showCustomerGUI(Client client) {
        view = new CustomerView();
        customerModel = new CustomerModel();
        customerController = new CustomerController(view, client, customerModel);
    }

    public void showSimUI(INotificationService notificationService) {
        loadMenuAndOrdersFromFile();
        simModel = new SimUIModel(notificationService);
        simView = new SimUIView(simModel);
        simController =  new SimUIController(simView, simModel);
    }

    /**
     * Starts the whole system
     */
    public static void main(String[] args) {
        // Start Services Here
        INotificationService notificationService = new NotificationService();

        Demo demo = new Demo();

        demo.showSimUI(notificationService);
        demo.showConsole();
    }

    /**
     * Closes the GUI
     */
    static void demoCloseGUI() {
        System.out.println("Goodbye.");
        view.closeGUI();
        Waiter.addBackAllCurrentOrders();
        GenerateReportFileWriter generateReportFileWriter = new GenerateReportFileWriter("report.txt");
        generateReportFileWriter.writeToFile();

        try {
            orderReader.writeToFile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

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

    static void cleanUp() {
        System.out.println("Goodbye.");

        Waiter.addBackAllCurrentOrders();

        GenerateReportFileWriter generateReportFileWriter = new GenerateReportFileWriter("report.txt");
        generateReportFileWriter.writeToFile();

        try {
            orderReader.writeToFile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads menu items and customer orders from order and menu files
     * <p>
     * This method uses {@code ItemFileReader} to read the menu from {@code menu.txt}
     * and {@code OrderFileReadWrite} to load existing orders from {@code orders.txt}
     * </p>
     *
     * <p>
     * Throws a {@code RuntimeException} if either file is not found
     * </p>
     */
    private void loadMenuAndOrdersFromFile() {
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
    }
}