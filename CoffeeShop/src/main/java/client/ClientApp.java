package client;

import logs.CoffeeShopLogger;

import java.io.IOException;
import java.net.Socket;

/**
 * Test Class to show how to run a new client
 * instance and connect to the server
 */
public class ClientApp {
    private static final CoffeeShopLogger logger = CoffeeShopLogger.getInstance();
    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket("localhost", 9876);
            CustomerModel customerModel = new CustomerModel();
            Client client = new Client(clientSocket, customerModel);
            logger.logInfo("Connected to server");
            Demo demo = new Demo();
            demo.showCustomerGUI(client);
            client.startListening();

        } catch (IOException e) {
            logger.logSevere("Error in client: " + e.getClass() + " " + e.getCause() + " " + e.getMessage());
        }
    }
}