package item;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ItemFileReader class
 * @author Cameron Hunt
 */
public class ItemFileReaderTest {
    private static String itemFile;

    /**
     * Sets up a temporary file directory to be used for tests
     *
     * @throws IOException if file cannot be opened
     */
    @BeforeAll
    static void setup() throws IOException {
        itemFile = "menu.txt";
    }

    /**
     * Tests if the file can be read
     *
     * @throws IOException if file cannot be opened
     */
    @Test
    void testReadFile() throws IOException {
        // Arrange
        ItemFileReader fileReader = new ItemFileReader(itemFile);

        // Act
        ItemList items = fileReader.readFile();

        // Assert
        assertNotNull(items);

        assertEquals("RL1", items.getMenu().get("RL1").getItemID());
        assertEquals(ItemCategory.ROLL, items.getMenu().get("RL1").getCategory());
        assertEquals(3.00, items.getMenu().get("RL1").getCost(), 0.01);
        assertEquals("BACON ROLL", items.getMenu().get("RL1").getDescription());
    }

    /**
     * Tests if the read file is not null
     */
    @Test
    void testReadFileNotNull() {
        try (ItemFileReader itemFileReader = new ItemFileReader(itemFile.toString())) {
            ItemList data = itemFileReader.readFile();

            Assertions.assertNotNull(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Tests if the file being read does not exist
     */
    @Test
    void testReadFileDoesNotExist() {
        assertThrows(FileNotFoundException.class, () -> {
            try (ItemFileReader itemFileReader = new ItemFileReader("randomFile.txt")) {
            itemFileReader.readFile();
            }
        });
    }
}
