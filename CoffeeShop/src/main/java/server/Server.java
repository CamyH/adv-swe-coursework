package server;

import client.ClientHandler;
import order.OrderFileReadWrite;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * The Server that runs the Simulation
 */
public class Server {
    private static final int port = 9876;
    /**
     * The host used by the server
     * Dev: localhost
     */
    private static final String host = "localhost";
    private static final int pool_size = 6;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = setupServer()) {
            System.out.println("Server started on " + host + ":" + port);

            initialiseOrdersFromFile();

            // A Thread pool is used to handle multiple
            // client connections concurrently
            ExecutorService threadPool = Executors.newFixedThreadPool(pool_size);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                threadPool.submit(clientHandler);
            }
        } catch (IOException e) {
            System.err.println("Could not start server: " + e.getMessage());
        }
    }

    /**
     * Create a new Server on the specified port
     * @return the new instance of the server
     */
    private static ServerSocket setupServer() {
        try {
            return new ServerSocket(port);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to setup server", e);
        }
    }

    /**
     * Read in the orders from the orders file
     */
    private static void initialiseOrdersFromFile() {
        // Read in orders file and
        // populate orderList instance
        try (OrderFileReadWrite reader = new OrderFileReadWrite("orders.txt")) {
            reader.readFile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
