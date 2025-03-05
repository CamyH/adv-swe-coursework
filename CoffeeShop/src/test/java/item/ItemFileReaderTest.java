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

        String itemListString = SetupItemFile.convertItemListToString();
        try {
            Files.write(Path.of(tempFile), itemListString.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testReadFile() throws IOException {
        // Arrange
        ItemFileReader fileReader = new ItemFileReader(tempFile);

        // Act
        ItemList items = fileReader.readFile();

        // Assert
        assertNotNull(items);

        assertEquals("RL1", items.getMenu().get("RL1").getItemID());
        assertEquals(ItemCategory.ROLL, items.getMenu().get("RL1").getCategory());
        assertEquals(3.00, items.getMenu().get("RL1").getCost(), 0.01);
        assertEquals("BACON ROLL", items.getMenu().get("RL1").getDescription());
    }

    @Test
    void testReadFileNotNull() {
        try (ItemFileReader itemFileReader = new ItemFileReader(tempFile.toString())) {
            ItemList data = itemFileReader.readFile();

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
