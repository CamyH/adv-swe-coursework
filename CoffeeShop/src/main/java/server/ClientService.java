package server;

import client.Client;
import client.SimUIModel;
import exceptions.InvalidOrderException;
import interfaces.OrderObserver;
import item.ItemList;
import logs.CoffeeShopLogger;
import message.Message;
import message.MessageContent;
import message.MessageType;
import order.Order;
import order.OrderList;
import utils.RetryPolicy;

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
            sendItemListToClient();
            // Now wait for orders from the client
            while (true) {
                try {
                    logger.logDebug("Idling...");
                    Object object = inputStream.readObject();

                    // We only want to accept order objects
                    // from the client for now
                    if (!(object instanceof Order)) {
                        continue;
                    }

                    Order receivedOrder = receiveOrder(object);

                    System.out.println(receivedOrder.getCustomerName());

                    receivedOrder.setClientService(this);

                    logger.logInfo("Order " + receivedOrder.getOrderID());
                    RetryPolicy.retryAndLog(() ->
                        simUIModel.addOrder(receivedOrder),
                            3
                    );
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
        } catch (IOException | InvalidOrderException e) {
            logger.logSevere("Error in server: " + e.getCause() + e.getMessage());
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
    public synchronized Order receiveOrder(Object inputStream) throws IOException, ClassNotFoundException, InvalidOrderException {
        return Optional.ofNullable((Order) inputStream)
               .orElseThrow(() -> new InvalidOrderException("Order received is NULL"));
    }

    /**
     * Notifies the observer of a change to their order
     *
     * @param orderID the ID of the order that has been changed
     */
    @Override
    public void sendOrderNotification(UUID orderID, MessageType messageType) {
        Message message = new Message(UUID.randomUUID(), orderID, MessageContent.fromMessageType(messageType), messageType);
        try {
            sendMessage(message);
        } catch (IOException e) {
            logger.logSevere("Could not send " + messageType + " notification: " + e.getCause() + e.getMessage());
        }
    }

    /**
     * Sends both the {@link OrderList} and {@link ItemList} to the client.
     * This is typically used to initialise the client with both order and menu data.
     *
     * @param orderList the list of orders to send
     * @param itemList the list of items (menu) to send
     * @throws IOException if an I/O error occurs during transmission
     */
    private void sendOrderItemList(OrderList orderList, ItemList itemList) throws IOException {
        outputStream.writeObject(itemList);
        outputStream.writeObject(orderList);
        outputStream.flush();
    }

    /**
     * Sends the current {@link ItemList} (menu) to the client.
     * This method fetches the item list from the UI model and writes it to the output stream.
     * If an error occurs during transmission, it logs the error message to standard error.
     */
    public void sendItemListToClient() {
        try {
            System.out.println("sending ItemList to Client" + simUIModel.getMenu().getMenu());
            outputStream.writeObject(simUIModel.getMenu());  // Send item list to client
        } catch (IOException e) {
            System.err.println("Error sending item list to client: " + e.getMessage());
        }
    }
}
