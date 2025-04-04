package client;

import item.ItemList;
import logs.CoffeeShopLogger;
import order.Order;
import message.Message;
import server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Optional;

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
    private static final CoffeeShopLogger logger = CoffeeShopLogger.getInstance();
    private final CustomerModel customerModel;

    /**
     * Constructor for initialising a client
     * Initialises the output/input streams for sending/receiving
     * objects
     * @param socket the client's socket
     * @throws IOException if creation of a stream fails
     */
    public Client(Socket socket, CustomerModel customerModel) throws IOException {
        this.socket = socket;
        this.customerModel = customerModel;
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
    public Client(Socket socket, String host, int port, CustomerModel customerModel) throws IOException {
        this.socket = socket;
        Client.host = host;
        Client.port = port;
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.customerModel = customerModel;
        outputStream.flush();
        this.inputStream = new ObjectInputStream(socket.getInputStream());
        Server.addClient(outputStream);
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
        if (Order.isInvalidOrder(order)) throw new NullPointerException("Order cannot be null");

        logger.logInfo("Sending order: " + order.getOrderID());

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
    public synchronized Message receiveMessage(Object inputStream) throws IOException, ClassNotFoundException {
        return Optional.ofNullable((Message) inputStream)
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
            logger.logSevere("Failed to close socket: " + e.getMessage());
        }

        if (exception != null) throw exception;
    }

    public void startListening(Demo demo) {
        Thread listenerThread = new Thread(() -> {
            try {
                while (true) {
                    Object object = inputStream.readObject();
                    if (object instanceof ItemList itemList) {
                        customerModel.updateItemList(itemList);
                    }

                    if (object instanceof Message) {
                        Message message = receiveMessage(object);
                        customerModel.displayMessage(message);
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        listenerThread.start();
    }
}
