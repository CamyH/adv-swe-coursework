package item;

import static org.junit.jupiter.api.Assertions.*;

import exceptions.InvalidItemIDException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class ItemListTest {
    private static ItemList itemList;

    @BeforeEach
    void setUp() {
        itemList = SetupItemFile.generateItemList();
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
        LinkedHashMap<String, Item> menu = itemList.getMenu();

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
        Set<String> actualSet = new HashSet<>(Arrays.asList(itemList.getItemIDs()));

        assertEquals(expectedSet, actualSet);

    }

    /**
     * Test Method to test getMenuDetails() method works as intended
     */
    @Test
    void testGetMenuDetails() {
        String[] itemDetails = {
                "RL1,BACON ROLL,3.00",
                "RL2,SAUSAGE ROLL,3.50",
                "RL3,EGG ROLL,2.80",
                "RL4,CHEESE ROLL,3.00",

                "FD1,BAKED POTATO,4.00",
                "FD2,SANDWICH,4.50",
                "FD3,PANINI,5.00",
                "FD4,SALAD,4.50",
                "FD5,CHIPS,3.00",

                "HD1,TEA,2.00",
                "HD2,ESPRESSO,2.20",
                "HD3,AMERICANO,2.50",
                "HD4,LATTE,3.00",
                "HD5,CAPPUCCINO,3.20",
                "HD6,MOCHA,3.50",
                "HD7,HOT CHOCOLATE,3.50",

                "SD1,STILL WATER,1.50",
                "SD2,SPARKLING WATER,1.80",
                "SD3,ORANGE JUICE,2.50",
                "SD4,APPLE JUICE,2.50",
                "SD5,LEMONADE,2.80",
                "SD6,COLA,2.50",
                "SD7,FANTA,2.50",
                "SD8,ROOT BEER,3.00",
                "SD9,GINGER BEER,3.00",

                "SCK1,CHOCOLATE BAR,1.80",
                "SCK2,CRISPS,1.50",
                "SCK3,POPCORN,2.00",
                "SCK4,PRETZELS,1.80",
                "SCK5,SHORTBREAD,2.00",
                "SCK6,GRANOLA BARS,2.20",
                "SCK7,CHEESE,2.50",
                "SCK8,CRACKERS,2.00",

                "PSY1,CROISSANT,2.50",
                "PSY2,DANISH PASTRY,2.80",
                "PSY3,CINNAMON ROLL,3.00",
                "PSY4,MACARONS,3.50",
                "PSY5,PAIN AU CHOCOLAT,2.80"
        };

        assertArrayEquals(itemDetails, itemList.getMenuDetails());

    }

}
