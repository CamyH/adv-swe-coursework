package item;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.ItemCategory;

public class ItemListTest {
    private static ItemList itemList;

    @BeforeAll
    static void setUp() {
        itemList = new ItemList();

        itemList.add(new Item("RL1", ItemCategory.ROLL, 3.0F, "BACON ROLL"));
        itemList.add(new Item("RL2", ItemCategory.ROLL, 3.5F, "SAUSAGE ROLL"));
        itemList.add(new Item("RL3", ItemCategory.ROLL, 2.8F, "EGG ROLL"));
        itemList.add(new Item("RL4", ItemCategory.ROLL, 3.0F, "CHEESE ROLL"));

        itemList.add(new Item("FD1", ItemCategory.FOOD, 4.0F, "BAKED POTATO"));
        itemList.add(new Item("FD2", ItemCategory.FOOD, 4.5F, "SANDWICH"));
        itemList.add(new Item("FD3", ItemCategory.FOOD, 5.0F, "PANINI"));
        itemList.add(new Item("FD4", ItemCategory.FOOD, 4.5F, "SALAD"));
        itemList.add(new Item("FD5", ItemCategory.FOOD, 3.0F, "CHIPS"));

        itemList.add(new Item("HD1", ItemCategory.HOTDRINK, 2.0F, "TEA"));
        itemList.add(new Item("HD2", ItemCategory.HOTDRINK, 2.2F, "ESPRESSO"));
        itemList.add(new Item("HD3", ItemCategory.HOTDRINK, 2.5F, "AMERICANO"));
        itemList.add(new Item("HD4", ItemCategory.HOTDRINK, 3.0F, "LATTE"));
        itemList.add(new Item("HD5", ItemCategory.HOTDRINK, 3.2F, "CAPPUCCINO"));
        itemList.add(new Item("HD6", ItemCategory.HOTDRINK, 3.5F, "MOCHA"));
        itemList.add(new Item("HD7", ItemCategory.HOTDRINK, 3.5F, "HOT CHOCOLATE"));

        itemList.add(new Item("SD1", ItemCategory.SOFTDRINK, 1.5F, "STILL WATER"));
        itemList.add(new Item("SD2", ItemCategory.SOFTDRINK, 1.8F, "SPARKLING WATER"));
        itemList.add(new Item("SD3", ItemCategory.SOFTDRINK, 2.5F, "ORANGE JUICE"));
        itemList.add(new Item("SD4", ItemCategory.SOFTDRINK, 2.5F, "APPLE JUICE"));
        itemList.add(new Item("SD5", ItemCategory.SOFTDRINK, 2.8F, "LEMONADE"));
        itemList.add(new Item("SD6", ItemCategory.SOFTDRINK, 2.5F, "COLA"));
        itemList.add(new Item("SD7", ItemCategory.SOFTDRINK, 2.5F, "FANTA"));
        itemList.add(new Item("SD8", ItemCategory.SOFTDRINK, 3.0F, "ROOT BEER"));
        itemList.add(new Item("SD9", ItemCategory.SOFTDRINK, 3.0F, "GINGER BEER"));

        itemList.add(new Item("SCK1", ItemCategory.SNACK, 1.8F, "CHOCOLATE BAR"));
        itemList.add(new Item("SCK2", ItemCategory.SNACK, 1.5F, "CRISPS"));
        itemList.add(new Item("SCK3", ItemCategory.SNACK, 2.0F, "POPCORN"));
        itemList.add(new Item("SCK4", ItemCategory.SNACK, 1.8F, "PRETZELS"));
        itemList.add(new Item("SCK5", ItemCategory.SNACK, 2.0F, "SHORTBREAD"));
        itemList.add(new Item("SCK6", ItemCategory.SNACK, 2.2F, "GRANOLA BARS"));
        itemList.add(new Item("SCK7", ItemCategory.SNACK, 2.5F, "CHEESE"));
        itemList.add(new Item("SCK8", ItemCategory.SNACK, 2.0F, "CRACKERS"));

        itemList.add(new Item("PSY1", ItemCategory.PASTRY, 2.5F, "CROISSANT"));
        itemList.add(new Item("PSY2", ItemCategory.PASTRY, 2.8F, "DANISH PASTRY"));
        itemList.add(new Item("PSY3", ItemCategory.PASTRY, 3.0F, "CINNAMON ROLL"));
        itemList.add(new Item("PSY4", ItemCategory.PASTRY, 3.5F, "MACARONS"));
        itemList.add(new Item("PSY5", ItemCategory.PASTRY, 2.8F, "PAIN AU CHOCOLAT"));

    }

    // get description
    // get cost
    // get category

    @Test
    public void testGetCost() {
        assertEquals(3.0F, itemList.getCost("RL1"), 0.01);
        assertEquals(3.5F, itemList.getCost("RL2"), 0.01);
        assertEquals(2.8F, itemList.getCost("RL3"), 0.01);
        assertEquals(3.0F, itemList.getCost("RL4"), 0.01);

        assertEquals(4.0F, itemList.getCost("FD1"), 0.01);
        assertEquals(4.5F, itemList.getCost("FD2"), 0.01);
        assertEquals(5.0F, itemList.getCost("FD3"), 0.01);
        assertEquals(4.5F, itemList.getCost("FD4"), 0.01);
        assertEquals(3.0F, itemList.getCost("FD5"), 0.01);

        assertEquals(2.0F, itemList.getCost("HD1"), 0.01);
        assertEquals(3.5F, itemList.getCost("HD6"), 0.01);

        assertEquals(2.5F, itemList.getCost("SD3"), 0.01);
        assertEquals(3.0F, itemList.getCost("SD8"), 0.01);

        assertEquals(1.8F, itemList.getCost("SCK1"), 0.01);
        assertEquals(2.5F, itemList.getCost("SCK7"), 0.01);

        assertEquals(2.5F, itemList.getCost("PSY1"), 0.01);
        assertEquals(3.5F, itemList.getCost("PSY4"), 0.01);
    }
}
