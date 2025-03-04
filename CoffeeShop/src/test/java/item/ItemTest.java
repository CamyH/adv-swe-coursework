package item;

import exceptions.InvalidItemIDException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import utils.ItemCategory;

    /**
     * JUnit test class for the Item class.
     * This class contains test cases to validate the functionality of the Item class including item creation, validation checks, and getter methods.
     *
     * @author Mohd Faiz
     */
    public class ItemTest {

        private Item item;

        /**
         * Setup method to create a valid Item object before each test.
         * This method runs before each test and ensures that we have a valid Item to test.
         */
        @BeforeEach
        public void setUp() throws InvalidItemIDException {
            // Create a valid item before each test
            item = new Item("RL1", ItemCategory.ROLL, 3.0, "BACON ROLL");
        }

        /**
         * Test that an item can be created successfully with valid input.
         */
        @Test
        public void testItemCreationWithValidInput() {
            assertNotNull(item, "Item should not be null after creation.");
            assertEquals("RL1", item.getItemID(), "Item ID should match the given ID.");
            assertEquals(ItemCategory.ROLL, item.getCategory(), "Item category should match the given category.");
            assertEquals(3.0, item.getCost(), "Item cost should match the given cost.");
            assertEquals("BACON ROLL", item.getDescription(), "Item description should match the given description.");
        }

        /**
         * Test that an InvalidItemIDException is thrown if the item ID is null.
         */
        @Test
        public void testItemCreationWithNullItemID() {
            assertThrows(InvalidItemIDException.class, () -> new Item(null, ItemCategory.ROLL, 3.0, "BACON ROLL"), "Creating an item with a null item ID should throw InvalidItemIDException.");
        }

        /**
         * Test that an InvalidItemIDException is thrown if the item ID is empty.
         */
        @Test
        public void testItemCreationWithEmptyItemID() {
            assertThrows(InvalidItemIDException.class, () -> new Item("", ItemCategory.ROLL, 3.0, "BACON ROLL"), "Creating an item with an empty item ID should throw InvalidItemIDException.");
        }

        /**
         * Test that an InvalidItemIDException is thrown if the category is null.
         */
        @Test
        public void testItemCreationWithNullCategory() {
            assertThrows(InvalidItemIDException.class, () -> new Item("RL1", null, 3.0, "BACON ROLL"), "Creating an item with a null category should throw InvalidItemIDException.");
        }

        /**
         * Test that an InvalidItemIDException is thrown if the cost is negative.
         */
        @Test
        public void testItemCreationWithNegativeCost() {
            assertThrows(InvalidItemIDException.class, () -> new Item("RL1", ItemCategory.ROLL, -10.0, "BACON ROLL"), "Creating an item with a negative cost should throw InvalidItemIDException.");
        }

        /**
         * Test that an InvalidItemIDException is thrown if the description is null.
         */
        @Test
        public void testItemCreationWithNullDescription() {
            assertThrows(InvalidItemIDException.class, () -> new Item("RL1", ItemCategory.ROLL, 3.0, null), "Creating an item with a null description should throw InvalidItemIDException.");
        }

        /**
         * Test that an InvalidItemIDException is thrown if the description is empty.
         */
        @Test
        public void testItemCreationWithEmptyDescription() {
            assertThrows(InvalidItemIDException.class, () -> new Item("RL1", ItemCategory.ROLL, 3.0, ""), "Creating an item with an empty description should throw InvalidItemIDException.");
        }

        /**
         * Test that the cost can be updated correctly using the setCost method.
         */
        @Test
        public void testSetCost() {
            item.setCost(15.0);
            assertEquals(15.0, item.getCost(), "Item cost should be updated to the new value.");
        }

        /**
         * Test that the getItemID method returns the correct item ID.
         */
        @Test
        public void testGetItemID() {
            assertEquals("RL1", item.getItemID(), "Item ID should be returned correctly.");
        }

        /**
         * Test that the getCategory method returns the correct item category.
         */
        @Test
        public void testGetCategory() {
            assertEquals(ItemCategory.ROLL, item.getCategory(), "Item category should be returned correctly.");
        }

        /**
         * Test that the getDescription method returns the correct item description.
         */
        @Test
        public void testGetDescription() {
            assertEquals("BACON ROLL", item.getDescription(), "Item description should be returned correctly.");
        }

        /**
         * Test that the getCost method returns the correct item cost.
         */
        @Test
        public void testGetCost() {
            assertEquals(3.0, item.getCost(), "Item cost should be returned correctly.");
        }
    }

