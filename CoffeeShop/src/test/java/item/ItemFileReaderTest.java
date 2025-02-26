package item;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ItemFileReader class
 * @author Cameron Hunt
 */
public class ItemFileReaderTest {
    private static String tempFile;
    @BeforeAll
    static void setup(@TempDir Path tempDir) throws IOException {
        tempFile = String.valueOf(Files.createFile(tempDir.resolve("ItemsTest.txt")));

        Files.write(Path.of(tempFile), SetupItemFile.getItemListAsString());
    }

    @Test
    void testReadFile() throws IOException {
        // Arrange
        ItemFileReader fileReader = new ItemFileReader(tempFile);

        // Act
        ArrayList<Item> items = fileReader.readFile();

        // Assert
        assertNotNull(items);
        assertEquals(SetupItemFile.getItemList().size(), items.size());

        assertEquals("RL1", items.getFirst().getItemID());
        assertEquals(ItemCategory.ROLL, items.getFirst().getCategory());
        assertEquals(3.00, items.get(0).getCost(), 0.01);
        assertEquals("BACON ROLL", items.getFirst().getDescription());

        assertEquals("FD1", items.get(4).getItemID());
        assertEquals(ItemCategory.FOOD, items.get(4).getCategory());
        assertEquals(4.00, items.get(4).getCost(), 0.01);
        assertEquals("BAKED POTATO", items.get(4).getDescription());

        assertEquals("PSY5", items.getLast().getItemID());
        assertEquals(ItemCategory.PASTRY, items.getLast().getCategory());
        assertEquals(2.80, items.getLast().getCost(), 0.01);
        assertEquals("PAIN AU CHOCOLAT", items.getLast().getDescription());
    }

    @Test
    void testReadFileNotNull() {
        try (ItemFileReader itemFileReader = new ItemFileReader(tempFile.toString())) {
            ArrayList<Item> data = itemFileReader.readFile();

            Assertions.assertNotNull(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testReadFileDoesNotExist() {
        assertThrows(FileNotFoundException.class, () -> {
            try (ItemFileReader itemFileReader = new ItemFileReader("randomFile.txt")) {
            itemFileReader.readFile();
            }
        });
    }
}
