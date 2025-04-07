package client;

import logs.CoffeeShopLogger;
import utils.RetryPolicy;

import java.io.IOException;
import java.net.Socket;

/**
 * Test Class to show how to run a new client
 * instance and connect to the server
 */
public class ClientApp {
    private static final CoffeeShopLogger logger = CoffeeShopLogger.getInstance();
    public static void main(String[] args) {
        RetryPolicy.retryAndCustomLog(ClientApp::startClient,
                5,
                "Server is not running, client is unable to connect"
        );
    }

    /**
     * Attempts to start the client
     * and connect to the server
     */
    private static void startClient() throws IOException {
        Socket clientSocket = new Socket("localhost", 9876);
        CustomerModel customerModel = new CustomerModel();
        CustomerView view = new CustomerView();
        Client client = new Client(clientSocket, view, customerModel);
        logger.logInfo("Connected to server");
        Demo demo = new Demo();
        client.startListening();
        demo.showCustomerGUI(client, view);
    }
}