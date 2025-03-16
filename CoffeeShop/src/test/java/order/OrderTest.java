package order;

import exceptions.InvalidItemIDException;
import exceptions.InvalidOrderException;
import item.ItemList;
import item.SetupItemFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for the Order class.
 * This class contains test cases that validate the functionality of the Order class including item addition, cost calculation, and exception handling
 *
 * @author Mohd Faiz
 */

public class OrderTest {

    private Order order;

    ItemList itemList;

    /**
     * Sets up an ItemList object
     */
    @BeforeEach
    public void setUp() {
        itemList = SetupItemFile.generateItemList();

        try {
            order = new Order();
        } catch (InvalidOrderException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Tests the creation of an order.
     */
    @Test
    public void testOrderCreation() {
        assertNotNull(order.getOrderID(), "Order ID should not be null.");
        assertNotNull(order.getCustomerID(), "Customer ID should not be null.");
        assertNotNull(order.getTimestamp(), "Timestamp should not be null.");
        assertTrue(order.getDetails().isEmpty(), "Order details should be empty at creation.");
        assertEquals(0.0, order.getTotalCost(), "Total cost should be 0 initially.");
    }

    /**
     * Tests adding an item to the order.
     * Adds a valid item ("RL1") and checks that it is added to the order details and the total cost is updated accordingly.
     */
    @Test
    public void testAddItemToOrder() {
        // Add an item and check if it is added properly
        try {
            order.addItem("RL1");
            assertEquals(1, order.getDetails().size(), "Order should have one item.");
            assertTrue(order.getDetails().contains("RL1"), "Order details should contain 'RL1'.");

            // Check the total cost after adding item
            assertEquals(3.0, order.getTotalCost(), "Total cost should be 3.0 after adding 'RL1'.");
        }
        catch (InvalidItemIDException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Tests adding multiple items to the order.
     * Adds multiple items and checks that they are added to the order details list and the total cost is updated correctly.
     */
    @Test
    public void testAddMultipleItemsToOrder() {
        // Add multiple items and check the total cost
        try {
            order.addItem("RL1");
            order.addItem("HD1");
            order.addItem("SD1");
            order.addItem("SD1");

            assertEquals(4, order.getDetails().size(), "Order should have three items.");
            assertTrue(order.getDetails().contains("RL1"), "Order details should contain 'RL1'.");
            assertTrue(order.getDetails().contains("HD1"), "Order details should contain 'HD1'.");
            assertTrue(order.getDetails().contains("SD1"), "Order details should contain 'SD1'.");

            // Check the total cost after adding multiple items
            double expectedCost = 3.0 + 2.0 + 1.5 + 1.5;  // RL1 + HD1 + SD1 + SD1
            assertEquals(expectedCost, order.getTotalCost(), "Total cost should match the sum of added items.");
        }
        catch (InvalidItemIDException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Tests that an InvalidItemIDException is thrown when attempting to add an invalid item.
     */
    @Test
    public void testInvalidItemIDThrowsException() {
        // Try adding an invalid item and expect an exception
        assertThrows(InvalidItemIDException.class, () -> {order.addItem("invalidItemID");}, "Adding an invalid item ID should throw InvalidItemIDException.");
        assertThrows(InvalidItemIDException.class, () -> {order.addItem("");}, "Adding an invalid item ID should throw InvalidItemIDException.");
        assertThrows(InvalidItemIDException.class, () -> {order.addItem(null);}, "Adding an invalid item ID should throw InvalidItemIDException.");
    }

    /**
     * Tests that remove method works as intended
     */
    @Test
    public void testRemoveMethod() {
        try {
            order.addItem("RL1");
            order.addItem("HD1");
            order.addItem("SD1");
            order.addItem("SD1");

            assertTrue(order.removeItem("RL1"));

            assertEquals(3, order.getDetails().size(), "Order should have three items.");
            assertFalse(order.getDetails().contains("RL1"), "Order details should not contain 'RL1'.");
            assertTrue(order.getDetails().contains("HD1"), "Order details should contain 'HD1'.");
            assertTrue(order.getDetails().contains("SD1"), "Order details should contain 'SD1'.");

            // Check the total cost after adding multiple items
            double expectedCost = 2.0 + 1.5 + 1.5;  // HD1 + SD1 + SD1
            assertEquals(expectedCost, order.getTotalCost(), "Total cost should match the sum of added items.");

            assertFalse(order.removeItem("RL1"), "Removing an invalid item ID should return false");

            assertTrue(order.removeItem("SD1"), "Removing one of the duplicate items");

            assertEquals(2, order.getDetails().size(), "Order should have three items.");
            assertFalse(order.getDetails().contains("RL1"), "Order details should not contain 'RL1'.");
            assertTrue(order.getDetails().contains("HD1"), "Order details should contain 'HD1'.");
            assertTrue(order.getDetails().contains("SD1"), "Order details should contain 'SD1'.");

            // Check the total cost after adding multiple items
            expectedCost = 2.0 + 1.5;  // HD1 + SD1 + SD1
            assertEquals(expectedCost, order.getTotalCost(), "Total cost should match the sum of added items.");
        }
        catch (InvalidItemIDException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Tests that removeLast method works as intended
     */
    @Test
    public void testRemoveLastMethod() {
        try {
            order.addItem("RL1");
            order.addItem("HD1");
            order.addItem("SCK1");
            order.addItem("SD1");

            assertTrue(order.removeLastItem());

            assertEquals(3, order.getDetails().size(), "Order should have three items.");
            assertFalse(order.getDetails().contains("SD1"), "Order details should not contain 'SD1'.");
            assertTrue(order.getDetails().contains("SCK1"), "Order details should contain 'SCK1'.");
            assertTrue(order.getDetails().contains("RL1"), "Order details should contain 'RL1'.");
            assertTrue(order.getDetails().contains("HD1"), "Order details should contain 'HD1'.");

            // Check the total cost after adding multiple items
            double expectedCost = 3.0 + 2.0 + 1.8;  // RL1 + HD1 + SCK1
            assertEquals(expectedCost, order.getTotalCost(), "Total cost should match the sum of added items.");

            assertTrue(order.removeLastItem());

            assertEquals(2, order.getDetails().size(), "Order should have three items.");
            assertFalse(order.getDetails().contains("SD1"), "Order details should not contain 'SD1'.");
            assertFalse(order.getDetails().contains("SCK1"), "Order details should not contain 'SCK1'.");
            assertTrue(order.getDetails().contains("RL1"), "Order details should contain 'RL1'.");
            assertTrue(order.getDetails().contains("HD1"), "Order details should contain 'HD1'.");

            // Check the total cost after adding multiple items
            expectedCost = 3.0 + 2.0;  // RL1 + HD1
            assertEquals(expectedCost, order.getTotalCost(), "Total cost should match the sum of added items.");
        }
        catch (InvalidItemIDException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Tests that Calculating Discounts works as intended
     */
    @Test
    public void testDiscountedCost() {
        try {
            order.addItem("SD1"); // 1.5
            order.addItem("HD1"); // 2
            order.addItem("SCK1"); // 1.8
            order.addItem("PSY1"); // 2.5

            double totalCost = 1.5 + 1.8 + 2 + 2.5;  // RL1 + HD1 + SD1 + SD1
            assertEquals(totalCost, order.getTotalCost(), "Total cost should match the sum of added items.");
            assertNotEquals(totalCost, order.getDiscountedCost(), "Discounted cost should not match the sum of added items.");

            double discountCost = 1.5 * 0.9 + 1.8 * 0.9 + 2 * 0.5 + 2.5 * 0.5;
            assertEquals(discountCost, order.getDiscountedCost(), "Discount cost should match the sum of added items.");

            order.addItem("RL1"); // 3
            order.addItem("SCK2"); // 1.5
            order.addItem("FD4"); // 4.5
            order.addItem("SD3"); // 2.5

            totalCost = 1.5 + 2 + 1.8 + 2.5 + 3 + 1.5 + 4.5+ 2.5;
            assertEquals(totalCost, order.getTotalCost(), "Total cost should match the sum of added items.");
            assertNotEquals(totalCost, order.getDiscountedCost(), "Discounted cost should not match the sum of added items.");

            discountCost = 1.5 * 0.75 + 2 * 0.5 + 1.8 * 0.9 + 2.5 * 0.5 + 3 + 1.5 + 4.5 * 0.75 + 2.5 * 0.9;
            assertEquals(discountCost, order.getDiscountedCost(), "Discount cost should match the sum of added items.");

        } catch (InvalidItemIDException e) {
            System.out.println(e.getMessage());
        }
    }
}
