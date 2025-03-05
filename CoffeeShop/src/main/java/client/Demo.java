package client;
import exceptions.InvalidItemIDException;
import exceptions.InvalidOrderException;
import item.Item;
import item.ItemCategory;
import item.ItemFileReader;
import item.ItemList;
import order.Order;
import order.OrderFileReadWrite;
import order.OrderList;

import java.io.IOException;

/**
 * Demo initialises the CoffeeShop program
 * Contains the main method
 * @author Caelan Mackenzie
 */

public class Demo {

    private static ItemList menu;
    private static OrderList orders;
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

        OrderFileReadWrite orderReader = new OrderFileReadWrite("src/main/java/files/orders.txt");
        try {
            orders = orderReader.readFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ItemFileReader itemReader = new ItemFileReader("src/main/java/files/menu.txt");
        try {
            menu = itemReader.readFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        demo.showGUI();
        demo.showConsole();
    }

    public static void main2(String[] args) {
        // Create a new demo object
        Demo demo = new Demo();

        // Populate menu
        try {
            menu.add(new Item("RL1", ItemCategory.ROLL, 3.0, "BACON ROLL"));
            menu.add(new Item("FD1", ItemCategory.FOOD, 4.0, "BAKED POTATO"));
            menu.add(new Item("HD1", ItemCategory.HOTDRINK, 2.0, "TEA"));

        } catch (InvalidItemIDException e) {
            throw new RuntimeException(e);
        }

        // Populate orders
        try {
            try {
                Order order1 = new Order(menu);
                order1.addItem("RL1");
                order1.addItem("HD1");
                Order order2 = new Order(menu);
                order2.addItem("RL1");
                order2.addItem("FD1");
                order2.addItem("HD1");
                order2.addItem("HD1");
                orders.add(order1);
                orders.add(order2);
            } catch (InvalidItemIDException e) {
                throw new RuntimeException(e);
            }
        } catch (InvalidOrderException e) {
            throw new RuntimeException(e);
        }
        
        demo.showGUI();
        demo.showConsole();
    }

    static void demoCloseGUI(){
        gui.closeGUI();
    }
}