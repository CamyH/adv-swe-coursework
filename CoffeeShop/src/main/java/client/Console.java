package client;
import java.util.Scanner;

/**
 * @author Caelan Mackenzie
 * Console Class
 */

public class Console {

    /*
    private ItemList menu;
    private OrderList orders
    */

    public Scanner scanner;

    public Console(/* ItemList itemList, OrderList orderList */) {

        /*
        menu = itemList;
        orders = orderList
        */

        scanner = new Scanner(System.in);
    }

    public void run(){
        System.out.println("Coffee Shop Console");
        System.out.println("Type 'help' for a list of commands.");

        String command;
        do{
            System.out.println("Enter your command:");
            command = scanner.nextLine();
            if(command.equals("add")){
                addToMenu();
            }
            else if(command.equals("help")){
                help();
            }
        } while(!(command.equals("quit")));
        System.out.println("Goodbye.");
    }

    private void addToMenu() {

    }

    private void help() {
        System.out.println("add, help, quit");
    }
}