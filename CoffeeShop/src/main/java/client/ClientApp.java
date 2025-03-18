package client;

import exceptions.InvalidOrderException;
import item.ItemFileReader;
import message.Message;
import order.Order;

import java.io.IOException;
import java.net.Socket;

/**
 * Test Class to show how to run a new client
 * instance and connect to the server
 */
public class ClientApp {
    public static void main(String[] args) {
        try (Socket clientSocket = new Socket("localhost", 9876)) {
            System.out.println("Connected to server");

            Client client = new Client(clientSocket);

            // Sending the test message to the server
            //client.sendMessage(new Message(UUID.randomUUID(), "Test Message", MessageType.ORDER_COMPLETED));

            // Sending test object
            //Order testOrder = OrderList.getInstance().getOrderList().peek();
            initialiseItemsFromFile();
            Order testOrder = new Order();
            client.sendOrder(testOrder);

            // Receiving the response message
            Message response = client.receiveMessage();
            System.out.println("Server response: " + response);

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error in client: " + e.getMessage());
        } catch (InvalidOrderException e) {
            throw new RuntimeException(e);
        }
    }

    private static void initialiseItemsFromFile() {
        // Read in items file and
        // populate itemList instance
        try (ItemFileReader reader = new ItemFileReader("menu.txt")) {
            reader.readFile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}