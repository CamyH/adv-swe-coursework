package order;

import item.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the OrderFileReadWrite class
 * @author Cameron Hunt
 */
public class OrderFileReadWriteTest {
    private static String tempFile;

    private static ItemList menu;

    /**
     * Sets up the temporary file
     *
     * @throws IOException if file cannot be opened
     */
    @BeforeAll
    static void setup() throws IOException {
        tempFile = "OrdersTest.txt";

        menu = SetupItemFile.generateItemList();
    }

    /**
     * Tests if file can be read
     *
     * @throws IOException if file cannot be opened
     */
    @Test
    void testReadFile() throws IOException {
        // Arrange
        OrderFileReadWrite fileReader = new OrderFileReadWrite(tempFile);

        // Act
        fileReader.readFile();

        OrderList orders = OrderList.getInstance();

        // Assert
        assertNotNull(orders);
    }

    /**
     * Tests if a file can be written to
     *
     * @throws IOException if file cannot be opened
     */
    @Test
    void testWriteFile() throws IOException {
        // Arrange
        OrderFileReadWrite fileWriter = new OrderFileReadWrite(tempFile);
        OrderList orders = SetupOrderFile.generateOrderList();

        // Act
        fileWriter.writeToFile();
        fileWriter.readFile();

        OrderList readOrders = OrderList.getInstance();

        // Assert
        assertFalse(tempFile.isEmpty(), "The file should exist");
        assertEquals(readOrders.getOrderList().element().getOrderID(),
                orders.getOrderList().element().getOrderID(),
                "Check the first OrderID of each element is identical");
    }
}
