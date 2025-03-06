package utils;

import interfaces.AbstractFileManager;
import item.ItemList;
import order.OrderList;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Generates a report of all information from that day
 * Run on program exit
 * @author Cameron Hunt
 */
public class GenerateReportFileWriter extends AbstractFileManager<Object, ArrayList<String>> {
    public GenerateReportFileWriter(String fileName) {
        super(fileName);
    }

    /**
     * Write to a given file
     *
     * @param report all information to be written to the file
     */
    @Override
    public void writeToFile(ArrayList<String> report) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write the orders to the order file
            for (String order : report) {
                writer.write(order);
                writer.newLine();
            }

            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
    }

    public static ArrayList<String> generateReport(OrderList orders, ItemList items) {
        ArrayList<String> reportDetails = new ArrayList<>();

        String[] IDs = items.getItemIDs();
        String[] orderStrings = orders.getOrdersToString(false);
        HashMap<String, Double> orderedItems = orders.completedOrderItemCount();

        reportDetails.add("=======================");

        reportDetails.add("Current Order Details");
        reportDetails.add("-----------------------");

        for (String o : orderStrings) {
            reportDetails.add(o);
        }

        reportDetails.add("-----------------------");
        reportDetails.add("Number of Items Ordered");
        reportDetails.add("-----------------------");

        for (String ID : IDs) {
            reportDetails.add(ID + " = " + (int) orderedItems.getOrDefault(ID, 0.0).doubleValue());
        }

        reportDetails.add("-----------------------");
        reportDetails.add("Total Cost Breakdown");
        reportDetails.add("-----------------------");

        reportDetails.add("Total Income (Excluding Discounts) : £" + String.format("%.2f", orderedItems.get("total-cost")));
        reportDetails.add("Total Income (Including Discounts) : £" + String.format("%.2f", orderedItems.get("discount-cost")));
        reportDetails.add("Total Orders : " + ((int) orderedItems.get("num-orders").doubleValue()));
        reportDetails.add("Average Spend Per Order (Excluding Discounts) : £" + String.format("%.2f",
                orderedItems.get("num-orders") != 0
                        ? orderedItems.get("total-cost") / orderedItems.get("num-orders")
                        : 0.0));
        reportDetails.add("Average Spend Per Order (Including Discounts) : £" + String.format("%.2f",
                orderedItems.get("num-orders") != 0
                        ? orderedItems.get("discount-cost") / orderedItems.get("num-orders")
                        : 0.0));
        reportDetails.add("=======================");

        return reportDetails;
    }

    /**
     * Handle file content and assign to appropriate lists
     * @param fileContents the content of the file to ingest
     * @return the ingested file contents of the correct type
     */
    @Override
    protected Object ingestFileContents(StringBuilder fileContents) {
        throw new UnsupportedOperationException("Not Implemented.");
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