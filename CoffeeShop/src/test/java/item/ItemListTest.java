package item;

import static org.junit.jupiter.api.Assertions.*;

import exceptions.DuplicateItemIDException;
import exceptions.InvalidItemIDException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.ItemCategory;

public class ItemListTest {
    private static ItemList itemList;

    @BeforeAll
    static void setUp() {
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
        } catch (DuplicateItemIDException e) {
            System.out.println(e.getMessage());
        }
    }

    // get description
    // get cost
    // get category

    @Test
    public void testGetDescription() {
        assertEquals("BACON ROLL", itemList.getDescription("RL1"));
    }

    @Test
    public void testGetCost() {
        assertEquals(3.0, itemList.getCost("RL1"));
        assertEquals(3.5, itemList.getCost("RL2"));
        assertEquals(2.8, itemList.getCost("RL3"));
        assertEquals(3.0, itemList.getCost("RL4"));

        assertEquals(4.0, itemList.getCost("FD1"));
        assertEquals(4.5, itemList.getCost("FD2"));
        assertEquals(5.0, itemList.getCost("FD3"));
        assertEquals(4.5, itemList.getCost("FD4"));
        assertEquals(3.0, itemList.getCost("FD5"));

        assertNotEquals(1.99, itemList.getCost("HD1"));
        assertNotEquals(3.51, itemList.getCost("HD6"));

        assertNotEquals(2.4, itemList.getCost("SD3"));
        assertNotEquals(3.1, itemList.getCost("SD8"));

        assertNotEquals(1.4, itemList.getCost("SCK1"));
        assertNotEquals(3.0, itemList.getCost("SCK7"));

        assertNotEquals(2.0, itemList.getCost("PSY1"));
        assertNotEquals(3.0, itemList.getCost("PSY4"));
    }
}
