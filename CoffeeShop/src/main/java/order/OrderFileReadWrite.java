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

                for (String itemId : itemIds) {
                    Item item = new Item(itemId,
                            itemList.getCategory(itemId),
                            itemList.getCost(itemId),
                            itemList.getDescription(itemId));

                    itemList.add(item);
                }

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
        } catch (InvalidItemIDException e) {
            System.err.println("Skipping " + e.getMessage());
        }

        return orderList.getOrderList();
    }
}
