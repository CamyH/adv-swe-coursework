package utils;

/**
 * Author: Cameron Hunt
 * Enum for storing the different item categories
 * Contains a method to return the category
 */
public enum ItemCategory {
    ROLL("Roll"),
    FOOD("Food"),
    HOTDRINK("HotDrink"),
    SOFTDRINK("SoftDrink"),
    SNACK("Snack"),
    PASTRY("Pastry");

    private final String name;

    ItemCategory(String name) { this.name = name; }

    /**
     * Return the category name
     * @return the category value
     */
    public String getName() {
        return name;
    }
}
