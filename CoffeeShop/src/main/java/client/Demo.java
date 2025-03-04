package client;
import item.ItemList;
import order.OrderList;

/**
 * Demo initialises the CoffeeShop program
 * Contains the main method
 * @author Caelan Mackenzie
 */

public class Demo {

    private ItemList menu;
    private OrderList orders;

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
        GUI gui = new GUI();
    }


    public static void main(String[] args) {
        System.out.println("Hello, World!");

        // Create a new demo object
        Demo demo = new Demo();

        demo.showGUI();
        demo.showConsole();
    }
}