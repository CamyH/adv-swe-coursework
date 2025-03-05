package item;

import exceptions.InvalidItemIDException;
import java.util.Map;

/**
 * Provides a pre-defined list of item data used for testing
 * @author Cameron Hunt
 */
public class SetupItemFile {
    private static ItemList itemList;

    /**
     * Generate an item list for use in tests
     * @return the ItemList
     */
    public static ItemList generateItemList() {
        try {
            itemList = new ItemList();
            itemList.add(new Item("RL1", ItemCategory.ROLL, 3.00, "BACON ROLL"));
            itemList.add(new Item("RL2", ItemCategory.ROLL, 3.50, "SAUSAGE ROLL"));
            itemList.add(new Item("RL3", ItemCategory.ROLL, 2.80, "EGG ROLL"));
            itemList.add(new Item("RL4", ItemCategory.ROLL, 3.00, "CHEESE ROLL"));
            itemList.add(new Item("FD1", ItemCategory.FOOD, 4.00, "BAKED POTATO"));
            itemList.add(new Item("FD2", ItemCategory.FOOD, 4.50, "SANDWICH"));
            itemList.add(new Item("FD3", ItemCategory.FOOD, 5.00, "PANINI"));
            itemList.add(new Item("FD4", ItemCategory.FOOD, 4.50, "SALAD"));
            itemList.add(new Item("FD5", ItemCategory.FOOD, 3.00, "CHIPS"));
            itemList.add(new Item("HD1", ItemCategory.HOTDRINK, 2.00, "TEA"));
            itemList.add(new Item("HD2", ItemCategory.HOTDRINK, 2.20, "ESPRESSO"));
            itemList.add(new Item("HD3", ItemCategory.HOTDRINK, 2.50, "AMERICANO"));
            itemList.add(new Item("HD4", ItemCategory.HOTDRINK, 3.00, "LATTE"));
            itemList.add(new Item("HD5", ItemCategory.HOTDRINK, 3.20, "CAPPUCCINO"));
            itemList.add(new Item("HD6", ItemCategory.HOTDRINK, 3.50, "MOCHA"));
            itemList.add(new Item("HD7", ItemCategory.HOTDRINK, 3.50, "HOT CHOCOLATE"));
            itemList.add(new Item("SD1", ItemCategory.SOFTDRINK, 1.50, "STILL WATER"));
            itemList.add(new Item("SD2", ItemCategory.SOFTDRINK, 1.80, "SPARKLING WATER"));
            itemList.add(new Item("SD3", ItemCategory.SOFTDRINK, 2.50, "ORANGE JUICE"));
            itemList.add(new Item("SD4", ItemCategory.SOFTDRINK, 2.50, "APPLE JUICE"));
            itemList.add(new Item("SD5", ItemCategory.SOFTDRINK, 2.80, "LEMONADE"));
            itemList.add(new Item("SD6", ItemCategory.SOFTDRINK, 2.50, "COLA"));
            itemList.add(new Item("SD7", ItemCategory.SOFTDRINK, 2.50, "FANTA"));
            itemList.add(new Item("SD8", ItemCategory.SOFTDRINK, 3.00, "ROOT BEER"));
            itemList.add(new Item("SD9", ItemCategory.SOFTDRINK, 3.00, "GINGER BEER"));
            itemList.add(new Item("SCK1", ItemCategory.SNACK, 1.80, "CHOCOLATE BAR"));
            itemList.add(new Item("SCK2", ItemCategory.SNACK, 1.50, "CRISPS"));
            itemList.add(new Item("SCK3", ItemCategory.SNACK, 2.00, "POPCORN"));
            itemList.add(new Item("SCK4", ItemCategory.SNACK, 1.80, "PRETZELS"));
            itemList.add(new Item("SCK5", ItemCategory.SNACK, 2.00, "SHORTBREAD"));
            itemList.add(new Item("SCK6", ItemCategory.SNACK, 2.20, "GRANOLA BARS"));
            itemList.add(new Item("SCK7", ItemCategory.SNACK, 2.50, "CHEESE"));
            itemList.add(new Item("SCK8", ItemCategory.SNACK, 2.00, "CRACKERS"));
            itemList.add(new Item("PSY1", ItemCategory.PASTRY, 2.50, "CROISSANT"));
            itemList.add(new Item("PSY2", ItemCategory.PASTRY, 2.80, "DANISH PASTRY"));
            itemList.add(new Item("PSY3", ItemCategory.PASTRY, 3.00, "CINNAMON ROLL"));
            itemList.add(new Item("PSY4", ItemCategory.PASTRY, 3.50, "MACARONS"));
            itemList.add(new Item("PSY5", ItemCategory.PASTRY, 2.80, "PAIN AU CHOCOLAT"));
        } catch (InvalidItemIDException e) {
            throw new RuntimeException(e);
        }

        return itemList;
    }

    /**
     * Convert itemList to string to be used in test file
     * @return the itemList as a string
     */
    public static String convertItemListToString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Item> entry : itemList.getMenu().entrySet()) {
            Item item = entry.getValue();  // Get the Item from the map
            sb.append(item.getItemID()).append(",")
              .append(item.getCategory()).append(",")
              .append(String.format("%.2f", item.getCost())).append(",")
              .append(item.getDescription()).append("\n");
        }
        return sb.toString();
    }
}
