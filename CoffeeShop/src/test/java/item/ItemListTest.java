package item;

import static org.junit.jupiter.api.Assertions.*;

import exceptions.InvalidItemIDException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ItemListTest {
    private static ItemList itemList;

    @BeforeEach
    void setUp() {
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

    /**
     * Test Method to test ItemList getDescription() method
     * getDescription() method also throws an InvalidItemIDException if the ItemID is incorrect
     */
    @Test
    public void testGetDescription() {
        try {
            /** JUnit Tests for Equal Cases */
            assertEquals("BACON ROLL", itemList.getDescription("RL1"));
            assertEquals("SANDWICH", itemList.getDescription("FD2"));
            assertEquals("LATTE", itemList.getDescription("HD4"));
            assertEquals("COLA", itemList.getDescription("SD6"));
            assertEquals("CROISSANT", itemList.getDescription("PSY1"));

            /** JUnit Tests for Non Equal Cases */
            assertNotEquals("BACONROLL", itemList.getDescription("RL1"));
            assertNotEquals("EGG ROLL", itemList.getDescription("RL2"));
            assertNotEquals("LATTE", itemList.getDescription("HD1"));
            assertNotEquals("CROISSANT", itemList.getDescription("PSY3"));
            assertNotEquals("APPLE JUICE", itemList.getDescription("SD6"));
            assertNotEquals("PANINII", itemList.getDescription("FD3"));
        }
        catch (InvalidItemIDException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Test Method to test ItemList getCost() method
     * getCost() method also throws an InvalidItemIDException if the ItemID is incorrect
     */
    @Test
    public void testGetCost() {
        try {
            /** JUnit Tests for Equal Cases */
            assertEquals(3.0, itemList.getCost("RL1"));
            assertEquals(3.5, itemList.getCost("RL2"));
            assertEquals(2.8, itemList.getCost("RL3"));
            assertEquals(3.0, itemList.getCost("RL4"));
            assertEquals(4.0, itemList.getCost("FD1"));
            assertEquals(4.5, itemList.getCost("FD2"));
            assertEquals(5.0, itemList.getCost("FD3"));
            assertEquals(4.5, itemList.getCost("FD4"));
            assertEquals(3.0, itemList.getCost("FD5"));

            /** JUnit Tests for Non Equal Cases */
            assertNotEquals(1.99, itemList.getCost("HD1"));
            assertNotEquals(3.51, itemList.getCost("HD6"));
            assertNotEquals(2.4, itemList.getCost("SD3"));
            assertNotEquals(3.1, itemList.getCost("SD8"));
            assertNotEquals(1.4, itemList.getCost("SCK1"));
            assertNotEquals(3.0, itemList.getCost("SCK7"));
            assertNotEquals(2.0, itemList.getCost("PSY1"));
            assertNotEquals(3.0, itemList.getCost("PSY4"));
        }
        catch (InvalidItemIDException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Test Method to test ItemList getCategory() method
     * getCategory() method also throws an InvalidItemIDException if the ItemID is incorrect
     */
    @Test
    public void testGetCategory() {
        try {
            /** JUnit Tests for Equal Cases */
            assertEquals(ItemCategory.ROLL, itemList.getCategory("RL1"));
            assertEquals(ItemCategory.FOOD, itemList.getCategory("FD3"));
            assertEquals(ItemCategory.HOTDRINK, itemList.getCategory("HD5"));
            assertEquals(ItemCategory.SOFTDRINK, itemList.getCategory("SD6"));
            assertEquals(ItemCategory.SNACK, itemList.getCategory("SCK2"));
            assertEquals(ItemCategory.PASTRY, itemList.getCategory("PSY1"));

            /** JUnit Tests for Non Equal Cases */
            assertNotEquals(ItemCategory.ROLL, itemList.getCategory("FD1"));
            assertNotEquals(ItemCategory.FOOD, itemList.getCategory("SD3"));
            assertNotEquals(ItemCategory.HOTDRINK, itemList.getCategory("SCK4"));
            assertNotEquals(ItemCategory.SOFTDRINK, itemList.getCategory("HD2"));
            assertNotEquals(ItemCategory.SNACK, itemList.getCategory("PSY3"));
        }
        catch (InvalidItemIDException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Test Method to test ItemList setCost() method
     */
    @Test
    public void testSetCost() {
        try {
            /** Setting Item Costs */
            itemList.setCost("RL1", 3.5);
            itemList.setCost("FD3", 5.5);
            itemList.setCost("HD4", 2.99);
            itemList.setCost("SD6", 2.8);
            itemList.setCost("SCK2", 1.51);

            /** JUnit Tests for Equal Cases */
            assertEquals(3.5, itemList.getCost("RL1"));
            assertEquals(5.5, itemList.getCost("FD3"));
            assertEquals(2.99, itemList.getCost("HD4"));
            assertEquals(2.8, itemList.getCost("SD6"));
            assertEquals(1.51, itemList.getCost("SCK2"));

            /** JUnit Tests for Non Equal Cases */
            assertNotEquals(3.0, itemList.getCost("RL1"));
            assertNotEquals(5.0, itemList.getCost("FD3"));
            assertNotEquals(3.0, itemList.getCost("HD4"));
            assertNotEquals(2.5, itemList.getCost("SD6"));
            assertNotEquals(1.5, itemList.getCost("SCK2"));
        }
        catch (InvalidItemIDException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Test Method to test ItemList add() method with unique items
     */
    @Test
    void testAddUniqueItems() {
        try {
            /** JUnit Test to check adding a unique item returns true */
            assertTrue(itemList.add(new Item("X1", ItemCategory.ROLL, 3.2, "HAGGIS ROLL")));
            assertTrue(itemList.add(new Item("X2", ItemCategory.FOOD, 4.8, "PASTA")));
            assertTrue(itemList.add(new Item("X3", ItemCategory.HOTDRINK, 2.7, "GREEN TEA")));
            assertTrue(itemList.add(new Item("X4", ItemCategory.SOFTDRINK, 3.5, "GRAPE SODA")));
            assertTrue(itemList.add(new Item("X5", ItemCategory.SNACK, 2.3, "COOKIES")));

            /** JUnit Test to check contents of new Item is correct */
            assertEquals("HAGGIS ROLL", itemList.getDescription("X1"));
            assertEquals(4.8, itemList.getCost("X2"));
            assertEquals(ItemCategory.HOTDRINK, itemList.getCategory("X3"));
            assertEquals("GRAPE SODA", itemList.getDescription("X4"));
            assertEquals(2.3, itemList.getCost("X5"));
        }
        catch (InvalidItemIDException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Test Method to test ItemList add() method with duplicate item IDS
     */
    @Test
    void testAddDuplicateItems() {
        try {
            /** JUnit Test to check adding a duplicate item returns false */
            assertFalse(itemList.add(new Item("RL1", ItemCategory.SOFTDRINK, 5.00, "FAKE BACON ROLL")));
            assertFalse(itemList.add(new Item("FD2", ItemCategory.SNACK, 1.50, "FAKE SANDWICH")));
            assertFalse(itemList.add(new Item("HD4", ItemCategory.FOOD, 3.50, "FAKE LATTE")));
            assertFalse(itemList.add(new Item("SD6", ItemCategory.HOTDRINK, 4.20, "FAKE COLA")));
            assertFalse(itemList.add(new Item("SCK3", ItemCategory.ROLL, 6.00, "FAKE POPCORN")));

            /** JUnit Test to check contents of original item has not been affected */
            assertEquals("BACON ROLL", itemList.getDescription("RL1"));
            assertEquals(4.50, itemList.getCost("FD2"));
            assertEquals(ItemCategory.HOTDRINK, itemList.getCategory("HD4"));
            assertEquals("COLA", itemList.getDescription("SD6"));
            assertEquals(2.00, itemList.getCost("SCK3"));

            /** JUnit Test to check contents of original item has not been affected */
            assertNotEquals("FAKE BACON ROLL", itemList.getDescription("RL1"));
            assertNotEquals(1.50, itemList.getCost("FD2"));
            assertNotEquals(ItemCategory.FOOD, itemList.getCategory("HD4"));
            assertNotEquals("FAKE COLA", itemList.getDescription("SD6"));
            assertNotEquals(6.00, itemList.getCost("SCK3"));
        }
        catch (InvalidItemIDException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Test Method to test ItemList remove() method with existing items
     */
    @Test
    void testRemoveExistingItems() {
        /** JUnit Test to check removing an existing Item from the ItemList returns true */
        assertTrue(itemList.remove("RL1"));
        assertTrue(itemList.remove("FD2"));
        assertTrue(itemList.remove("HD4"));
        assertTrue(itemList.remove("SD6"));
        assertTrue(itemList.remove("SCK3"));
    }

    /**
     * Test Method to test ItemList remove() method with non-existing items
     */
    @Test
    void testRemoveNonExistingItems() {
        /** JUnit Test to check removing a non-existing Item from the ItemList returns false */
        assertFalse(itemList.remove("XX99"));
        assertFalse(itemList.remove("YY88"));
        assertFalse(itemList.remove("ZZ77"));
        assertFalse(itemList.remove("RL0"));
    }

    /**
     * Test Method to test ItemList getMenu() method
     */
    @Test
    void testGetMenu() {
        /** Retrieve the menu */
        HashMap<String, Item> menu = itemList.getMenu();

        /** JUnit test to check that the menu is not null */
        assertNotNull(menu);

        /** JUnit tests to check that the menu contains expected items */
        assertTrue(menu.containsKey("RL1"));
        assertEquals("BACON ROLL", menu.get("RL1").getDescription());
        assertEquals(3.00F, menu.get("RL1").getCost());
        assertEquals(ItemCategory.ROLL, menu.get("RL1").getCategory());
        assertTrue(menu.containsKey("FD3"));
        assertEquals("PANINI", menu.get("FD3").getDescription());
        assertEquals(5.00F, menu.get("FD3").getCost());
        assertEquals(ItemCategory.FOOD, menu.get("FD3").getCategory());

        /** JUnit test to ensure the returned menu is a reference to the original or a copy */
        menu.remove("RL1");
        assertTrue(itemList.getMenu().containsKey("RL1"));
    }

    /**
     * Test Method to test ItemList itemExists() method
     */
    @Test
    void testItemExists() {
        /** JUnit Test to check existing items return true */
        assertTrue(itemList.itemExists("RL1"));
        assertTrue(itemList.itemExists("FD2"));
        assertTrue(itemList.itemExists("HD3"));
        assertTrue(itemList.itemExists("SD4"));
        assertTrue(itemList.itemExists("SCK5"));

        /** JUnit Test to check non-existing items return false */
        assertFalse(itemList.itemExists("RL0"));
        assertFalse(itemList.itemExists("FD21"));
        assertFalse(itemList.itemExists("HD01"));
        assertFalse(itemList.itemExists("SD10"));
        assertFalse(itemList.itemExists("SCK"));
    }

    /**
     * Test Method to test getSummaryMenu() method works as intended
     */
    @Test
    void testGetSummaryMenu() {
        /** Maually generating the expected list */
        String[] itemIDs = {
            "RL1", "RL2", "RL3", "RL4",
            "FD1", "FD2", "FD3", "FD4", "FD5",
            "HD1", "HD2", "HD3", "HD4", "HD5", "HD6", "HD7",
            "SD1", "SD2", "SD3", "SD4", "SD5", "SD6", "SD7", "SD8", "SD9",
            "SCK1", "SCK2", "SCK3", "SCK4", "SCK5", "SCK6", "SCK7", "SCK8",
            "PSY1", "PSY2", "PSY3", "PSY4", "PSY5"
        };

        /** Convert to set as order of data structure does not matter */
        Set<String> expectedSet = new HashSet<>(Arrays.asList(itemIDs));
        Set<String> actualSet = new HashSet<>(Arrays.asList(itemList.getSummaryMenu()));

        assertEquals(expectedSet, actualSet);

    }


}
