package order;

import exceptions.InvalidItemIDException;
import exceptions.InvalidOrderException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for the Order class.
 * This class contains test cases that validate the functionality of the Order class including item addition, cost calculation, and exception handling
 *
 * @author Mohd Faiz
 */

public class OrderTest {

    private final Order order;
    /**
     * Constructor for the OrderTest class.
     * Initializes the order object for testing.
     *
     * @param order The Order object to be tested.
     */
    public OrderTest(Order order) {
        this.order = order;
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
     *
     * @throws InvalidItemIDException if the item ID is invalid
     */
    @Test
    public void testAddItemToOrder() throws InvalidItemIDException {
        // Add an item and check if it is added properly
        order.addItem("RL1");
        assertEquals(1, order.getDetails().size(), "Order should have one item.");
        assertTrue(order.getDetails().contains("RL1"), "Order details should contain 'RL1'.");

        // Check the total cost after adding item
        assertEquals(3.0, order.getTotalCost(), "Total cost should be 3.0 after adding 'RL1'.");
    }

    /**
     * Tests adding multiple items to the order.
     * Adds multiple items and checks that they are added to the order details list and the total cost is updated correctly.
     *
     * @throws InvalidItemIDException if any item ID is invalid
     */
    @Test
    public void testAddMultipleItemsToOrder() throws InvalidItemIDException {
        // Add multiple items and check the total cost
        order.addItem("RL1");
        order.addItem("HD1");
        order.addItem("SD1");

        assertEquals(3, order.getDetails().size(), "Order should have three items.");
        assertTrue(order.getDetails().contains("RL1"), "Order details should contain 'RL1'.");
        assertTrue(order.getDetails().contains("HD1"), "Order details should contain 'HD1'.");
        assertTrue(order.getDetails().contains("SD1"), "Order details should contain 'SD1'.");

        // Check the total cost after adding multiple items
        double expectedCost = 3.0 + 2.0 + 1.5;  // RL1 + HD1 + SD1
        assertEquals(expectedCost, order.getTotalCost(), "Total cost should match the sum of added items.");
    }

    /**
     * Tests that an InvalidItemIDException is thrown when attempting to add an invalid item.
     */
    @Test
    public void testInvalidItemIDThrowsException() {
        // Try adding an invalid item and expect an exception
        assertThrows(InvalidItemIDException.class, () -> order.addItem("invalidItemID"), "Adding an invalid item ID should throw InvalidItemIDException.");
    }

    /**
     * Tests the calculation of the discounted cost.
     */
    @Test
    public void testGetDiscountedCost() throws InvalidItemIDException {
        order.addItem("RL1");
        order.addItem("HD1");
        // Since `Discount.DISCOUNT0` is assumed to be 0, the discounted cost should match the total cost
        double expectedCost = 3.0 + 2.0;
        assertEquals(expectedCost, order.getDiscountedCost(), "Discounted cost should be same as total cost if no discount is applied.");
    }

    /**
     * Tests that an InvalidOrderException is thrown when attempting to create an order with no menu.
     */
    @Test
    public void testOrderWithNoMenu() {
        // Create an order with a null menu and expect an exception
        assertThrows(InvalidOrderException.class, () -> {
            new Order(null);  // Should throw InvalidOrderException
        }, "Creating an order with a null menu should throw InvalidOrderException.");
    }
}
