package server;

import client.Client;
import exceptions.InvalidOrderException;
import message.Message;
import order.Order;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Optional;

/**
 * Handles the communication between the server and a single client.
 * Implements the {@link Runnable} interface to allow for multi-threaded processing of client requests.
 * Each {@link ClientHandler} instance is responsible for managing a single client's socket connection.
 */
public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;

    /**
     * Constructor to initialise the client handler
     *
     * @param clientSocket the {@link Socket} that is created by the client
     */
    public ClientHandler(Socket clientSocket) throws IOException {

        this.clientSocket = clientSocket;
        this.outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        outputStream.flush();
        this.inputStream = new ObjectInputStream(clientSocket.getInputStream());
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

    /**
     * Sends a {@link Message} object to the server.
     * The message is serialized and sent using {@link ObjectOutputStream}.
     *
     * @param message the {@link Message} object to be sent
     * @throws IOException if an I/O error occurs while sending the object
     */
    public synchronized void sendMessage(Message message) throws IOException {
        if (message == null) throw new NullPointerException("Message cannot be null");

        outputStream.writeObject(message);
        outputStream.flush();
    }

    /**
     * Receives an {@link Order} object from the client
     * The order is deserialized from the input stream and returned
     *
     * @return the {@link Order} object received from the client
     * @throws IOException if an I/O error occurs while receiving the object, aka if it is null
     * @throws ClassNotFoundException if the class of the received object cannot be found
     */
    public synchronized Order receiveOrder() throws IOException, ClassNotFoundException, InvalidOrderException {
        return Optional.ofNullable((Order) inputStream.readObject())
               .orElseThrow(() -> new InvalidOrderException("Order received is NULL"));
    }
}
