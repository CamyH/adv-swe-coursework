package item;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides a pre-defined list of item data used for testing
 * @author Cameron Hunt
 */
public class SetupItemFile {
    private static final ArrayList<Item> itemList = new ArrayList<>(List.of(
            new Item("RL1", ItemCategory.ROLL, 3.00, ItemDescription.BACON_ROLL),
            new Item("RL2", ItemCategory.ROLL, 3.50, ItemDescription.SAUSAGE_ROLL),
            new Item("RL3", ItemCategory.ROLL, 2.80, ItemDescription.EGG_ROLL),
            new Item("RL4", ItemCategory.ROLL, 3.00, ItemDescription.CHEESE_ROLL),
            new Item("FD1", ItemCategory.FOOD, 4.00, ItemDescription.BAKED_POTATO),
            new Item("FD2", ItemCategory.FOOD, 4.50, ItemDescription.SANDWICH),
            new Item("FD3", ItemCategory.FOOD, 5.00, ItemDescription.PANINI),
            new Item("FD4", ItemCategory.FOOD, 4.50, ItemDescription.SALAD),
            new Item("FD5", ItemCategory.FOOD, 3.00, ItemDescription.CHIPS),
            new Item("HD1", ItemCategory.HOTDRINK, 2.00, ItemDescription.TEA),
            new Item("HD2", ItemCategory.HOTDRINK, 2.20, ItemDescription.ESPRESSO),
            new Item("HD3", ItemCategory.HOTDRINK, 2.50, ItemDescription.AMERICANO),
            new Item("HD4", ItemCategory.HOTDRINK, 3.00, ItemDescription.LATTE),
            new Item("HD5", ItemCategory.HOTDRINK, 3.20, ItemDescription.CAPPUCCINO),
            new Item("HD6", ItemCategory.HOTDRINK, 3.50, ItemDescription.MOCHA),
            new Item("HD7", ItemCategory.HOTDRINK, 3.50, ItemDescription.HOT_CHOCOLATE),
            new Item("SD1", ItemCategory.SOFTDRINK, 1.50, ItemDescription.STILL_WATER),
            new Item("SD2", ItemCategory.SOFTDRINK, 1.80, ItemDescription.SPARKLING_WATER),
            new Item("SD3", ItemCategory.SOFTDRINK, 2.50, ItemDescription.ORANGE_JUICE),
            new Item("SD4", ItemCategory.SOFTDRINK, 2.50, ItemDescription.APPLE_JUICE),
            new Item("SD5", ItemCategory.SOFTDRINK, 2.80, ItemDescription.LEMONADE),
            new Item("SD6", ItemCategory.SOFTDRINK, 2.50, ItemDescription.COLA),
            new Item("SD7", ItemCategory.SOFTDRINK, 2.50, ItemDescription.FANTA),
            new Item("SD8", ItemCategory.SOFTDRINK, 3.00, ItemDescription.ROOT_BEER),
            new Item("SD9", ItemCategory.SOFTDRINK, 3.00, ItemDescription.GINGER_BEER),
            new Item("SCK1", ItemCategory.SNACK, 1.80, ItemDescription.CHOCOLATE_BAR),
            new Item("SCK2", ItemCategory.SNACK, 1.50, ItemDescription.CRISPS),
            new Item("SCK3", ItemCategory.SNACK, 2.00, ItemDescription.POPCORN),
            new Item("SCK4", ItemCategory.SNACK, 1.80, ItemDescription.PRETZELS),
            new Item("SCK5", ItemCategory.SNACK, 2.00, ItemDescription.SHORTBREAD),
            new Item("SCK6", ItemCategory.SNACK, 2.20, ItemDescription.GRANOLA_BARS),
            new Item("SCK7", ItemCategory.SNACK, 2.50, ItemDescription.CHEESE),
            new Item("SCK8", ItemCategory.SNACK, 2.00, ItemDescription.CRACKERS),
            new Item("PSY1", ItemCategory.PASTRY, 2.50, ItemDescription.CROISSANT),
            new Item("PSY2", ItemCategory.PASTRY, 2.80, ItemDescription.DANISH_PASTRY),
            new Item("PSY3", ItemCategory.PASTRY, 3.00, ItemDescription.CINNAMON_ROLL),
            new Item("PSY4", ItemCategory.PASTRY, 3.50, ItemDescription.MACARONS),
            new Item("PSY5", ItemCategory.PASTRY, 2.80, ItemDescription.PAIN_AU_CHOCOLAT)
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
