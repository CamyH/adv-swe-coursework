package server;

import client.Client;

import java.net.Socket;

/**
 * Handles the communication between the server and a single client.
 * Implements the {@link Runnable} interface to allow for multi-threaded processing of client requests.
 * Each {@link ClientHandler} instance is responsible for managing a single client's socket connection.
 */
public class ClientHandler implements Runnable {
    private final Socket clientSocket;

    /**
     * Constructor to initialise the client handler
     *
     * @param clientSocket the {@link Socket} that is created by the client
     */
    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    /**
     * Executed within a separate thread
     * Initialises a {@link Client} instance for the client, handles the sending and receiving
     * of messages, and processes client orders.
     */
    @Override
    public void run() {
        try {
            // Create a new client to handle communication with the connected client
            Client client = new Client(clientSocket);

            /* Example:
            Order order = client.receiveOrder();
            System.out.println("Received order: " + order.getOrderID());
            OrderList orderList = OrderList.getInstance();
            orderList.add(order);
            System.out.println(orderList.getOrderList()); */

        } catch (Exception e) {
            System.err.println("Error while handling client: " + e.getMessage());
        }
    }
}
