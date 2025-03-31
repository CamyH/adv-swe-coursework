package client;

import item.ItemFileReader;
import message.Message;
import message.MessageType;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

/**
 * Test Class to show how to run a new client
 * instance and connect to the server
 */
public class ClientApp {
    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket("localhost", 9876);
            Client client = new Client(clientSocket);
            System.out.println("Connected to server");
            Demo demo = new Demo();
            demo.showGUI();
            // Test messages
            client.sendMessage(new Message(UUID.randomUUID(), "hi", MessageType.ORDER_RECEIVED));
            client.sendMessage(new Message(UUID.randomUUID(), "Test Message", MessageType.ORDER_COMPLETED));

        } catch (IOException e) {
            System.err.println("Error in client: " + e.getMessage());
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