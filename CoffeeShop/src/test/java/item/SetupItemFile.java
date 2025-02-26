package item;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides a pre-defined list of item data used for testing
 * @author Cameron Hunt
 */
public class SetupItemFile {
    private static final ArrayList<Item> itemList = new ArrayList<>(List.of(
            new Item("RL1", ItemCategory.ROLL, 3.00, "BACON ROLL"),
            new Item("RL2", ItemCategory.ROLL, 3.50, "SAUSAGE ROLL"),
            new Item("RL3", ItemCategory.ROLL, 2.80, "EGG ROLL"),
            new Item("RL4", ItemCategory.ROLL, 3.00, "CHEESE ROLL"),
            new Item("FD1", ItemCategory.FOOD, 4.00, "BAKED POTATO"),
            new Item("FD2", ItemCategory.FOOD, 4.50, "SANDWICH"),
            new Item("FD3", ItemCategory.FOOD, 5.00, "PANINI"),
            new Item("FD4", ItemCategory.FOOD, 4.50, "SALAD"),
            new Item("FD5", ItemCategory.FOOD, 3.00, "CHIPS"),
            new Item("HD1", ItemCategory.HOTDRINK, 2.00, "TEA"),
            new Item("HD2", ItemCategory.HOTDRINK, 2.20, "ESPRESSO"),
            new Item("HD3", ItemCategory.HOTDRINK, 2.50, "AMERICANO"),
            new Item("HD4", ItemCategory.HOTDRINK, 3.00, "LATTE"),
            new Item("HD5", ItemCategory.HOTDRINK, 3.20, "CAPPUCCINO"),
            new Item("HD6", ItemCategory.HOTDRINK, 3.50, "MOCHA"),
            new Item("HD7", ItemCategory.HOTDRINK, 3.50, "HOT CHOCOLATE"),
            new Item("SD1", ItemCategory.SOFTDRINK, 1.50, "STILL WATER"),
            new Item("SD2", ItemCategory.SOFTDRINK, 1.80, "SPARKLING WATER"),
            new Item("SD3", ItemCategory.SOFTDRINK, 2.50, "ORANGE JUICE"),
            new Item("SD4", ItemCategory.SOFTDRINK, 2.50, "APPLE JUICE"),
            new Item("SD5", ItemCategory.SOFTDRINK, 2.80, "LEMONADE"),
            new Item("SD6", ItemCategory.SOFTDRINK, 2.50, "COLA"),
            new Item("SD7", ItemCategory.SOFTDRINK, 2.50, "FANTA"),
            new Item("SD8", ItemCategory.SOFTDRINK, 3.00, "ROOT BEER"),
            new Item("SD9", ItemCategory.SOFTDRINK, 3.00, "GINGER BEER"),
            new Item("SCK1", ItemCategory.SNACK, 1.80, "CHOCOLATE BAR"),
            new Item("SCK2", ItemCategory.SNACK, 1.50, "CRISPS"),
            new Item("SCK3", ItemCategory.SNACK, 2.00, "POPCORN"),
            new Item("SCK4", ItemCategory.SNACK, 1.80, "PRETZELS"),
            new Item("SCK5", ItemCategory.SNACK, 2.00, "SHORTBREAD"),
            new Item("SCK6", ItemCategory.SNACK, 2.20, "GRANOLA BARS"),
            new Item("SCK7", ItemCategory.SNACK, 2.50, "CHEESE"),
            new Item("SCK8", ItemCategory.SNACK, 2.00, "CRACKERS"),
            new Item("PSY1", ItemCategory.PASTRY, 2.50, "CROISSANT"),
            new Item("PSY2", ItemCategory.PASTRY, 2.80, "DANISH PASTRY"),
            new Item("PSY3", ItemCategory.PASTRY, 3.00, "CINNAMON ROLL"),
            new Item("PSY4", ItemCategory.PASTRY, 3.50, "MACARONS"),
            new Item("PSY5", ItemCategory.PASTRY, 2.80, "PAIN AU CHOCOLAT")
    ));

    /**
     * Convert and return the list of Item to a list of strings
     * @return list of item data as strings
     */
    public static ArrayList<String> getItemListAsString() {
        ArrayList<String> convertedItemList = new ArrayList<>();
        for (Item item : itemList) {
            convertedItemList.add(item.toString());
        }
        return convertedItemList;
    }

    /**
     * Return the list of Items
     * @return list of item data as original type
     */
    public static ArrayList<Item> getItemList() {
        return itemList;
    }
}
