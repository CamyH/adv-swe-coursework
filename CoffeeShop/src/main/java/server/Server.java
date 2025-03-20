package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * The Server that runs the Simulation
 */
public class Server {
    private static ExecutorService threadPool = null;
    private static final CopyOnWriteArraySet<ObjectOutputStream> connectionsSingleton = new CopyOnWriteArraySet<>();
    private static final int port = 9876;
    /**
     * The host used by the server
     * Dev: localhost
     */
    private static String host = "localhost";
    private static int pool_size = 6;

    /**
     * Constructor
     */
    public Server() {}

    /**
     * Constructor that allows for passing in
     * a custom host and port
     * @param host the new host to use
     * @param port the new port to use
     */
    public Server(String host, int port) {
        Server.host = host;
        Server.pool_size = port;
    }

    /**
     * Start a new server instance
     */
    public void start() {
        try (ServerSocket serverSocket = setupServer()) {
            System.out.println("Server started on " + host + ":" + port);

            // A Thread pool is used to handle multiple
            // client connections concurrently
            threadPool = Executors.newFixedThreadPool(pool_size);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                threadPool.submit(clientHandler);
            }
        } catch (IOException e) {
            System.err.println("Could not start server: " + e.getMessage());
            // Shut down threadPool
            threadPool.shutdownNow();
        }
    }

    /**
     * Create a new Server on the specified port
     * @return the new instance of the server
     */
    protected static ServerSocket setupServer() {
        try {
            return new ServerSocket(port);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to setup server", e);
        }
    }

    /**
     * Initialise a new instance of connectionsSingleton if it
     * is null. A lazy initialised singleton is used here
     * as CopyOnWriteArrayList is expensive to create
     * so we only want to do it when a client connects
     * @return the current instance of connectionsSingleton
     */
    public static CopyOnWriteArraySet<ObjectOutputStream> getConnectionsSingletonInstance() {
        return connectionsSingleton;
    }
}
