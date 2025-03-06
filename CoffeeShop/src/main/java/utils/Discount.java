package utils;

import item.Item;
import item.ItemCategory;

import java.util.HashMap;
import java.util.Map;
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
    DISCOUNT50(50);

    private final int discount;

    Discount(int discount) { this.discount = discount; }

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
