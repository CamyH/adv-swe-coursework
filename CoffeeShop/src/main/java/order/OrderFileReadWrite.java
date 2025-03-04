package order;

import exceptions.InvalidItemIDException;
import exceptions.InvalidOrderException;
import interfaces.FileManager;
import item.Item;
import item.ItemList;
import utils.Discount;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Reads and Writes Order data using Java Stream
 * @author Cameron Hunt
 */
public class OrderFileReadWrite implements FileManager<Order, OrderList> {
    private final String fileName;

    public OrderFileReadWrite(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Reads from a given file
     * @return an instance of type T representing the file content
     * @throws IOException for general IO exceptions
     */
    @Override
    public Queue<Order> readFile() throws IOException {
        File orderFile = new File(fileName);

        // Throw exception early if file does not exist
        if (!orderFile.exists()) throw new FileNotFoundException();
        StringBuilder stringBuilder = new StringBuilder();

        try (FileInputStream fis = new FileInputStream(orderFile)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Skipping " + e.getMessage());
        }

        System.out.println(stringBuilder.toString());

        return ingestFileContents(stringBuilder);
    }

    /**
     * Write to a given file
     * @param orders all order information to be added to the end of the file
     */
    @Override
    public void writeToFile(OrderList orders) {
        String[] ordersToWrite = orders.getOrdersToString(false);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            // Write the orders to the order file
            // appends onto the end
            for (String order : ordersToWrite) {
                writer.write(order);
                writer.newLine();
            }

            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
    }

    /**
     * Convert StringBuilder file content to a queue of Orders
     * @param fileContents the contents of the read from file
     * @return an Array Deque of type order
     */
    private Queue<Order> ingestFileContents(StringBuilder fileContents) {
        OrderList orderList = new OrderList();
        ItemList itemList = new ItemList();

        try {
            // We only really care about the InvalidOrderException
            for (String line : fileContents.toString().split("\n")) {
                // Skip empty lines
                if (line.trim().isEmpty()) continue;

                String[] lineData = line.split(",");
                String[] itemIds = lineData[3].split(";");

                orderList.add(new Order(lineData[0],
                        lineData[1],
                        LocalDateTime.parse(lineData[2]),
                        new ArrayList<>(List.of(itemIds)),
                        itemList,
                        Double.parseDouble(lineData[3]),
                        Discount.valueOf(lineData[4])));
            }
        } catch (InvalidOrderException e) {
            System.err.println("Skipping " + e.getMessage());
        }

        return orderList.getOrderList();
    }

    /**
     * Closes this resource, relinquishing any underlying resources.
     * This method is invoked automatically on objects managed by the
     * {@code try}-with-resources statement.
     *
     * @throws Exception if this resource cannot be closed
     * @apiNote While this interface method is declared to throw {@code
     * Exception}, implementers are <em>strongly</em> encouraged to
     * declare concrete implementations of the {@code close} method to
     * throw more specific exceptions, or to throw no exception at all
     * if the close operation cannot fail.
     *
     * <p> Cases where the close operation may fail require careful
     * attention by implementers. It is strongly advised to relinquish
     * the underlying resources and to internally <em>mark</em> the
     * resource as closed, prior to throwing the exception. The {@code
     * close} method is unlikely to be invoked more than once and so
     * this ensures that the resources are released in a timely manner.
     * Furthermore it reduces problems that could arise when the resource
     * wraps, or is wrapped, by another resource.
     *
     * <p><em>Implementers of this interface are also strongly advised
     * to not have the {@code close} method throw {@link
     * InterruptedException}.</em>
     * <p>
     * This exception interacts with a thread's interrupted status,
     * and runtime misbehavior is likely to occur if an {@code
     * InterruptedException} is {@linkplain Throwable#addSuppressed
     * suppressed}.
     * <p>
     * More generally, if it would cause problems for an
     * exception to be suppressed, the {@code AutoCloseable.close}
     * method should not throw it.
     *
     * <p>Note that unlike the {@link Closeable#close close}
     * method of {@link Closeable}, this {@code close} method
     * is <em>not</em> required to be idempotent.  In other words,
     * calling this {@code close} method more than once may have some
     * visible side effect, unlike {@code Closeable.close} which is
     * required to have no effect if called more than once.
     * <p>
     * However, implementers of this interface are strongly encouraged
     * to make their {@code close} methods idempotent.
     */
    @Override
    public void close() throws Exception {
        // Not used
    }
}
