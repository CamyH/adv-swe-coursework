package utils;

import item.Item;
import item.ItemCategory;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Enum for storing the discounts available
 * Contains a method to calculate the discount for the current price
 * @author Cameron Hunt
 */
public enum Discount {
    DISCOUNT0(0),
    DISCOUNT10(10),
    DISCOUNT25(25),
    DISCOUNT50(50),
    DAILY_SPECIAL(generateDailySpecialDiscount());

    private final int discount;
    private static Item dailySpecialItem;

    Discount(int discount) { this.discount = discount; }

    /**
     * Generates a random daily special discount between 50-75 in intervals of 5
     * @return discount value
     */
    private static int generateDailySpecialDiscount() {
        Random rand = new Random();
        return 50 + rand.nextInt(6) * 5;
    }

    /**
     * Sets the daily special item
     * @param item the item to set as daily special
     */
    public static void setDailySpecialItem(Item item) {
        dailySpecialItem = item;
    }

    /**
     * Gets the daily special item
     * @return daily special item
     */
    public static Item getDailySpecialItem() {
        return dailySpecialItem;
    }

    /**
     * Calculates the price for the given discount
     * @param price the price to discount
     * @return the discounted price
     */
    public double calculateDiscount(double price) { return price - (price * (discount / 100.0)); }

    /**
     *
     * @return value related to discount
     */
    public int getValue() {
        return discount;
    }

    /**
     * Method creates a data structure holding all the possible discount combinations
     *
     * @return a Map with a set as a key and discount as the value
     */
    public static Map<Set<ItemCategory>, Discount> createDiscounts() {
        Map<Set<ItemCategory>, Discount> discounts = new HashMap<>();

        /** Any combination of item categories and discounts can be added here to be included as a discount */
        discounts.put(Set.of(ItemCategory.HOTDRINK, ItemCategory.ROLL), Discount.DISCOUNT25);
        discounts.put(Set.of(ItemCategory.HOTDRINK, ItemCategory.PASTRY), Discount.DISCOUNT50);
        discounts.put(Set.of(ItemCategory.SOFTDRINK, ItemCategory.SNACK), Discount.DISCOUNT10);
        discounts.put(Set.of(ItemCategory.SOFTDRINK, ItemCategory.FOOD), Discount.DISCOUNT25);
        discounts.put(Set.of(ItemCategory.SOFTDRINK, ItemCategory.PASTRY), Discount.DISCOUNT25);
        discounts.put(Set.of(ItemCategory.HOTDRINK, ItemCategory.SNACK), Discount.DISCOUNT25);

        return discounts;
    }
}
