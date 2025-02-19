package utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ItemCategory enum
 */
public class ItemCategoryTest {
    /**
     * Ensure the enum values have not changed unexpectedly
     */
    @Test
    void testEnumValues() {
        assertEquals("Roll", ItemCategory.ROLL.getName());
        assertEquals("Food", ItemCategory.FOOD.getName());
        assertEquals("HotDrink", ItemCategory.HOTDRINK.getName());
        assertEquals("SoftDrink", ItemCategory.SOFTDRINK.getName());
        assertEquals("Snack", ItemCategory.SNACK.getName());
        assertEquals("Pastry", ItemCategory.PASTRY.getName());
    }
}
