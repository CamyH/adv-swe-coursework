package order;

import exceptions.InvalidItemIDException;
import exceptions.InvalidOrderException;
import item.Item;
import item.ItemCategory;
import item.ItemList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Discount;

import java.util.Set;
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

    void setUpItemList() {
        itemList = new ItemList();
        try {
            itemList.add(new Item("RL1", ItemCategory.ROLL, 3.0, "BACON ROLL"));
            itemList.add(new Item("RL2", ItemCategory.ROLL, 3.5, "SAUSAGE ROLL"));
            itemList.add(new Item("RL3", ItemCategory.ROLL, 2.8, "EGG ROLL"));
            itemList.add(new Item("RL4", ItemCategory.ROLL, 3.0, "CHEESE ROLL"));

            itemList.add(new Item("FD1", ItemCategory.FOOD, 4.0, "BAKED POTATO"));
            itemList.add(new Item("FD2", ItemCategory.FOOD, 4.5, "SANDWICH"));
            itemList.add(new Item("FD3", ItemCategory.FOOD, 5.0, "PANINI"));
            itemList.add(new Item("FD4", ItemCategory.FOOD, 4.5, "SALAD"));
            itemList.add(new Item("FD5", ItemCategory.FOOD, 3.0, "CHIPS"));

            itemList.add(new Item("HD1", ItemCategory.HOTDRINK, 2.0, "TEA"));
            itemList.add(new Item("HD2", ItemCategory.HOTDRINK, 2.2, "ESPRESSO"));
            itemList.add(new Item("HD3", ItemCategory.HOTDRINK, 2.5, "AMERICANO"));
            itemList.add(new Item("HD4", ItemCategory.HOTDRINK, 3.0, "LATTE"));
            itemList.add(new Item("HD5", ItemCategory.HOTDRINK, 3.2, "CAPPUCCINO"));
            itemList.add(new Item("HD6", ItemCategory.HOTDRINK, 3.5, "MOCHA"));
            itemList.add(new Item("HD7", ItemCategory.HOTDRINK, 3.5, "HOT CHOCOLATE"));

            itemList.add(new Item("SD1", ItemCategory.SOFTDRINK, 1.5, "STILL WATER"));
            itemList.add(new Item("SD2", ItemCategory.SOFTDRINK, 1.8, "SPARKLING WATER"));
            itemList.add(new Item("SD3", ItemCategory.SOFTDRINK, 2.5, "ORANGE JUICE"));
            itemList.add(new Item("SD4", ItemCategory.SOFTDRINK, 2.5, "APPLE JUICE"));
            itemList.add(new Item("SD5", ItemCategory.SOFTDRINK, 2.8, "LEMONADE"));
            itemList.add(new Item("SD6", ItemCategory.SOFTDRINK, 2.5, "COLA"));
            itemList.add(new Item("SD7", ItemCategory.SOFTDRINK, 2.5, "FANTA"));
            itemList.add(new Item("SD8", ItemCategory.SOFTDRINK, 3.0, "ROOT BEER"));
            itemList.add(new Item("SD9", ItemCategory.SOFTDRINK, 3.0, "GINGER BEER"));

            itemList.add(new Item("SCK1", ItemCategory.SNACK, 1.8, "CHOCOLATE BAR"));
            itemList.add(new Item("SCK2", ItemCategory.SNACK, 1.5, "CRISPS"));
            itemList.add(new Item("SCK3", ItemCategory.SNACK, 2.0, "POPCORN"));
            itemList.add(new Item("SCK4", ItemCategory.SNACK, 1.8, "PRETZELS"));
            itemList.add(new Item("SCK5", ItemCategory.SNACK, 2.0, "SHORTBREAD"));
            itemList.add(new Item("SCK6", ItemCategory.SNACK, 2.2, "GRANOLA BARS"));
            itemList.add(new Item("SCK7", ItemCategory.SNACK, 2.5, "CHEESE"));
            itemList.add(new Item("SCK8", ItemCategory.SNACK, 2.0, "CRACKERS"));

            itemList.add(new Item("PSY1", ItemCategory.PASTRY, 2.5, "CROISSANT"));
            itemList.add(new Item("PSY2", ItemCategory.PASTRY, 2.8, "DANISH PASTRY"));
            itemList.add(new Item("PSY3", ItemCategory.PASTRY, 3.0, "CINNAMON ROLL"));
            itemList.add(new Item("PSY4", ItemCategory.PASTRY, 3.5, "MACARONS"));
            itemList.add(new Item("PSY5", ItemCategory.PASTRY, 2.8, "PAIN AU CHOCOLAT"));
        } catch (InvalidItemIDException e) {
            System.out.println(e.getMessage());
        }
    }

    @BeforeEach
    public void setUp() {
        setUpItemList();

        try {
            order = new Order(itemList);
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
     * Tests that an InvalidOrderException is thrown when attempting to create an order with no menu.
     */
    @Test
    public void testOrderWithNoMenu() {
        // Create an order with a null menu and expect an exception
        assertThrows(InvalidOrderException.class, () -> {
            new Order(null);  // Should throw InvalidOrderException
        }, "Creating an order with a null menu should throw InvalidOrderException.");
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
