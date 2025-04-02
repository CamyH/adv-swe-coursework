package server;

import item.ItemList;
import logs.CoffeeShopLogger;
import order.OrderList;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * The Server that runs the Simulation
 */
public class Server {
    private static final CopyOnWriteArraySet<ObjectOutputStream> activeConnectionsInstance = new CopyOnWriteArraySet<>();
    private static ExecutorService threadPool = null;
    private static final int port = 9876;
    private static final CoffeeShopLogger logger = CoffeeShopLogger.getInstance();

    /**
     * The host used by the server
     * Dev: localhost
     */
    private String host = "localhost";
    private int pool_size = 6;

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
        this.host = host;
        this.pool_size = port;
    }

    /**
     * Start a new server instance
     */
    public void start() {
        try (ServerSocket serverSocket = setupServer()) {
            logger.logInfo("Server started on " + host + ":" + port);
            // A Thread pool is used to handle multiple
            // client connections concurrently
            threadPool = Executors.newFixedThreadPool(pool_size);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                logger.logInfo("Client connected: " + clientSocket.getInetAddress());

                ClientService clientHandler = new ClientService(clientSocket, simUIModel);
                threadPool.submit(clientHandler);
            }
        } catch (IOException e) {
            logger.logSevere("Error in server: " + e);
            // Shut down threadPool to clean up resources
            shutdownThreadPool();
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
    public static CopyOnWriteArraySet<ObjectOutputStream> getActiveConnectionsInstance() {
        return activeConnectionsInstance;
    }

    /**
     * Adds a client connection to the activeConnections instance
     *
     * @param clientOutputStream the ObjectOutputStream of the client to add
     * @throws IllegalArgumentException if the clientOutputStream is null
     */
    public static synchronized void addClient(ObjectOutputStream clientOutputStream) {
        if (clientOutputStream == null) {
            throw new IllegalArgumentException("Client output stream cannot be null");
        }

        if (!activeConnectionsInstance.add(clientOutputStream))
            logger.logSevere("Client cannot be added");

        logger.logInfo("Client added. Total clients: " + activeConnectionsInstance.size());
    }

    /**
     * Removes a client connection from the activeConnections instance
     *
     * @param clientOutputStream the ObjectOutputStream of the client to remove
     * @throws IllegalArgumentException if the clientOutputStream is null
     */
    public static synchronized void removeClient(ObjectOutputStream clientOutputStream) {
        if (clientOutputStream == null) {
            throw new IllegalArgumentException("Client output stream cannot be null");
        }

        if (!activeConnectionsInstance.remove(clientOutputStream)) {
            logger.logSevere("Client not found in active connections.");
        }

        logger.logInfo("Total clients: " + activeConnectionsInstance.size());
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

        CopyOnWriteArraySet<ObjectOutputStream> activeConnections = Server.getActiveConnectionsInstance();

        for (ObjectOutputStream outputStream : activeConnections) {
            logger.logInfo("Broadcasting " + listToSend.getClass().getName());
            outputStream.writeObject(listToSend);
        }
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
     * Shuts down the thread pool gracefully, if the shutdown does not complete within
     * 10 seconds, the thread pool is forcibly terminated
     *
     */
    private void shutdownThreadPool() {
        try {
            threadPool.shutdown();
            // If it has not fully shut down after 10 seconds
            // then we want to force it
            if (!threadPool.awaitTermination(10, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (Exception e) {
            logger.logSevere("Error shutting down thread pool: " + e.getMessage(), e);
        }
    }
}
