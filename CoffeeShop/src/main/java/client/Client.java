package client;

import order.Order;
import Message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * The Client Class
 * Used to connect to the server
 */
public class Client {
    private final Socket socket;
    private final ObjectInputStream inputStream;

    private final ObjectOutputStream outputStream;

    /**
     * Constructor for initialising a client
     * Initialises the output/input streams for sending/receiving
     * objects
     * @param socket the server's socket
     */
    public Client(Socket socket) {
        this.socket = socket;
        try {
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.flush();

            this.inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
/**
     * Sends an {@link Order} object to the server.
     * The order is serialized and sent using {@link ObjectOutputStream}.
     *
     * @param order the {@link Order} object to be sent
     * @throws IOException if an I/O error occurs while sending the object
     */
    public synchronized void sendOrder(Order order) throws IOException {
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
    public synchronized void receiveMessage() throws IOException, ClassNotFoundException {
        // Currently just prints out the message
        // can modify in future to handle the message properly
        // if needed, depending on messageType
        Message message = (Message) inputStream.readObject();
        System.out.println(message);
    }
}
