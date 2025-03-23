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
    private final Socket socket;
    private static String host = "localhost";
    private static int port = 9876;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;

    /**
     * Constructor for initialising a client
     * Initialises the output/input streams for sending/receiving
     * objects
     * @param socket the client's socket
     * @throws IOException if creation of a stream fails
     */
    public Client(Socket socket) throws IOException {
        this.socket = socket;
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.flush();
        this.inputStream = new ObjectInputStream(socket.getInputStream());
        Server.addClient(outputStream);
    }

    /**
     * Constructor for initialising a client with a custom host and port
     * @param socket the client's socket
     * @param host the custom host to use
     * @param port the custom port to use
     * @throws IOException if creation of a stream fails
     */
    public Client(Socket socket, String host, int port) throws IOException {
        this.socket = socket;
        Client.host = host;
        Client.port = port;
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.flush();
        this.inputStream = new ObjectInputStream(socket.getInputStream());
        Server.addClient(outputStream);
    }

    public Client start() {
        try {
            Socket clientSocket = new Socket(host, port);
            System.out.println("Connected to server");

            Client client = new Client(clientSocket);

            // Test for now
            Order testOrder = new Order();
            client.sendOrder(testOrder);

            Message response = client.receiveMessage();
            System.out.println("Server response: " + response);

            return client;
        } catch (IOException | ClassNotFoundException | InvalidOrderException e) {
            System.err.println("Error in client: " + e.getMessage());
        }
        return null;
    }

    /**
     * Sends an {@link Order} object to the server
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
     * Receives a {@link Message} object from the server
     * The message is deserialized from the input stream and returned
     *
     * @throws IOException if an I/O error occurs while receiving the object
     * @throws ClassNotFoundException if the class of the received object cannot be found
     */
    public synchronized Message receiveMessage() throws IOException, ClassNotFoundException {
        return Optional.ofNullable((Message) inputStream.readObject())
               .orElseThrow(() -> new IOException("Message received is NULL"));
    }

    /**
     * Closes the client's I/O streams and attempts to close the socket
     * This method closes both the {@link ObjectOutputStream} and
     * {@link ObjectInputStream} to release resources. It also attempts
     * to close the {@link Socket}, even if stream closures fail
     *
     * <p><b>Note:</b> If multiple errors occur during cleanup, the
     * first encountered {@link IOException} is thrown</p>
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
        Server.removeClient(outputStream);

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
