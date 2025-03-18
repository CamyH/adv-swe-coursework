package client;

import exceptions.InvalidOrderException;
import item.ItemList;
import order.Order;
import message.Message;
import order.OrderList;
import server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * The Client Class
 * Used to connect to the server
 */
public class Client {
    private final CopyOnWriteArraySet<ObjectOutputStream> connectionsSingletonInstance;
    private final Socket socket;
    private final ObjectInputStream inputStream;

    private final ObjectOutputStream outputStream;

    /**
     * Constructor for initialising a client
     * Initialises the output/input streams for sending/receiving
     * objects
     * @param socket the server's socket
     */
    public Client(Socket socket) throws IOException {
        this.socket = socket;
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.flush();
        this.inputStream = new ObjectInputStream(socket.getInputStream());
        // Keep a record of the client's ObjectOutputStream
        this.connectionsSingletonInstance = Server.getConnectionsSingletonInstance();
        connectionsSingletonInstance.add(outputStream);
    }
    /**
     * Sends an {@link Order} object to the server.
     * The order is serialized and sent using {@link ObjectOutputStream}.
     *
     * @param order the {@link Order} object to be sent
     * @throws IOException if an I/O error occurs while sending the object
     * @throws NullPointerException if the order is null
     */
    public synchronized void sendOrder(Order order) throws IOException {
        if (order == null) throw new NullPointerException("Order cannot be null");

        System.out.println("Sending order: " + order.getOrderID());

        outputStream.writeObject(order);
        outputStream.flush();
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
     * Receives a {@link Message} object from the server.
     * The message is deserialized and printed to the console.
     *
     * @throws IOException if an I/O error occurs while receiving the object
     * @throws ClassNotFoundException if the class of the received object cannot be found
     */
    public synchronized Message receiveMessage() throws IOException, ClassNotFoundException {
        return Optional.ofNullable((Message) inputStream.readObject())
               .orElseThrow(() -> new IOException("Message received is NULL"));
    }

    /**
     * Receives an {@link Order} object from the server.
     * The order is deserialized from the input stream and returned.
     *
     * @return the {@link Order} object received from the server
     * @throws IOException if an I/O error occurs while receiving the object, aka if it is null
     * @throws ClassNotFoundException if the class of the received object cannot be found
     */
    public synchronized Order receiveOrder() throws IOException, ClassNotFoundException, InvalidOrderException {
        return Optional.ofNullable((Order) inputStream.readObject())
               .orElseThrow(() -> new InvalidOrderException("Order received is NULL"));
    }

    /**
     * Broadcast OrderList to all connected clients
     * @throws IOException if OrderList is unable to be sent
     * to a client
     */
    public synchronized void updateClientOrderList() throws IOException {
        broadcast(OrderList.getInstance());
    }

    /**
     * Broadcast ItemList to all connected clients
     * @throws IOException if ItemList is unable to be sent
     * to a client
     */
    public synchronized void updateClientItemList() throws IOException {
        broadcast(ItemList.getInstance());
    }

    /**
     * Broadcast OrderList or ItemList to a
     * collection of clients
     * @param listToSend the updated {@link OrderList} or {@link ItemList} to send
     * @param <T> Generic type that must be {@link Serializable} to allow for polymorphism
     * @throws IOException if an object is unable to be sent to a client
     * @throws IllegalArgumentException if the list object is null
     */
    private synchronized <T extends Serializable> void broadcast(T listToSend) throws IOException {
        if (listToSend == null) throw new IllegalArgumentException("List to send cannot be null");

        for (ObjectOutputStream outputStream : connectionsSingletonInstance) {
            outputStream.writeObject(listToSend);
        }
    }

    /**
     * Closes the client's I/O streams and attempts to close the socket.
     * This method closes both the {@link ObjectOutputStream} and
     * {@link ObjectInputStream} to release resources. It also attempts
     * to close the {@link Socket}, even if stream closures fail
     *
     * <p><b>Note:</b> If multiple errors occur during cleanup, the
     * first encountered {@link IOException} is thrown.</p>
     *
     * @throws IOException if an I/O error occurs while closing the output or input stream
     */
    public synchronized void close() throws IOException {
        // p.s I hate these multiple try catches
        // but since these can independently fail to
        // close it is needed in case one fails to close
        // so we can still try close the others
        IOException exception = null;

        // Remove the connection from the list
        connectionsSingletonInstance.remove(outputStream);

        try {
            outputStream.close();
        } catch (IOException e) {
            exception = e;
        }

        try {
            inputStream.close();
        } catch (IOException e) {
            if (exception == null) exception = e;
        }

        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Failed to close socket: " + e.getMessage());
        }

        if (exception != null) throw exception;
    }
}
