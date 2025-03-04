package order;

import item.Item;
import item.ItemCategory;
import item.ItemFileReader;
import item.SetupItemFile;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for the OrderFileReadWrite class
 * @author Cameron Hunt
 */
public class OrderFileReadWriteTest {
    private static String tempFile;

    @BeforeAll
    static void setup(@TempDir Path tempDir) throws IOException {
        tempFile = String.valueOf(Files.createFile(tempDir.resolve("OrdersTest.txt")));

        Files.write(Path.of(tempFile), SetupItemFile.getItemListAsString());
    }

    @Test
    void testReadFile() throws IOException {
        // Arrange
        OrderFileReadWrite fileReader = new OrderFileReadWrite(tempFile);

        // Act
        Queue<Order> orders = fileReader.readFile();

        // Assert
        assertNotNull(orders);
    }

    @Test
    void testWriteFile() throws IOException {
        // Arrange
        OrderFileReadWrite fileWriter = new OrderFileReadWrite(tempFile);

        // Act

        // Assert
    }
}
