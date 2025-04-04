package server;

import client.Client;
import client.SimUIModel;
import exceptions.InvalidOrderException;
import interfaces.OrderObserver;
import logs.CoffeeShopLogger;
import message.Message;
import order.Order;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Optional;
import java.util.UUID;

/**
 * Handles the communication between the server and a single client.
 * Implements the {@link Runnable} interface to allow for multi-threaded processing of client requests.
 * Each {@link ClientService} instance is responsible for managing a single client's socket connection.
 */
public class ClientService implements Runnable, OrderObserver {
    private final Socket clientSocket;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private final CoffeeShopLogger logger = CoffeeShopLogger.getInstance();
    private final SimUIModel simUIModel;

    /**
     * Constructor to initialise the client handler
     *
     * @param clientSocket the {@link Socket} that is created by the client
     */
    public ClientService(Socket clientSocket, SimUIModel simUIModel) throws IOException {
        this.clientSocket = clientSocket;
        this.simUIModel = simUIModel;
        this.inputStream = new ObjectInputStream(clientSocket.getInputStream());
        this.outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        outputStream.flush();
        logger.logInfo("Initialising client handler for: " + clientSocket);
    }

    /**
     * Executed within a separate thread
     * Initialises a {@link Client} instance for the client, handles the sending and receiving
     * of messages, and processes client orders.
     */
    @Override
    public void run() {
        try {
            // Now wait for orders from the client
            while (true) {
                try {
                    logger.logDebug("Idling...");
                    Order receivedOrder = (Order) inputStream.readObject();

                    logger.logInfo("Order " + receivedOrder.getOrderID());
                    simUIModel.addOrder(receivedOrder);
                } catch (EOFException e) {
                    logger.logInfo("Client disconnected: " + clientSocket.getInetAddress());
                    clientSocket.close();
                    break;
                } catch (IOException | ClassNotFoundException e) {
                    logger.logSevere("Error while handling client: " + e.getMessage());
                    clientSocket.close();
                    break;
                }
            }
        } catch (Exception e) {
            logger.logSevere("Error in server: " + e.getMessage());
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

    /**
     * Notifies the observer that an order has moved to the processing stage
     *
     * @param orderID the ID of the order that has been updated
     */
    @Override
    public void sendOrderProcessingNotification(UUID orderID) {
        
    }

    public void sendItemListToClient() {
        try {
            System.out.println("sending ItemList to Client" + simUIModel.getMenu().getMenu());
            outputStream.writeObject(simUIModel.getMenu());  // Send item list to client
        } catch (IOException e) {
            System.err.println("Error sending item list to client: " + e.getMessage());
        }
    }

    }
}
