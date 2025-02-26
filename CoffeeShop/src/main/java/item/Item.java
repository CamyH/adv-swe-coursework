package item;

/**
 * Defines an item object
 * @author Akash Poonia
 */

/**
 * Represents an item with an ID, category, cost, and description.
 */
public class Item {
    // Unique identifier for the item
    private final String itemID;

    // Category of the item (defined in the ItemCategory enum)
    private final ItemCategory category;

    // Cost of the item
    private double cost;

    // Description of the item
    private final String description;

    /**
     * Constructor to initialize an Item object with given values.
     *
     * @param itemID      The unique identifier for the item.
     * @param category    The category of the item.
     * @param cost        The cost of the item.
     * @param description A brief description of the item.
     */
    public Item(String itemID, ItemCategory category, double cost, String description) {
        this.itemID = itemID;
        this.category = category;
        this.cost = cost;
        this.description = description;
    }

    /**
     * Sets the cost of the item.
     *
     * @param cost The new cost of the item.
     */
    public void setCost(float cost) {
        this.cost = cost;
    }

    /**
     * Gets the item ID.
     *
     * @return The unique identifier of the item.
     */
    public String getItemID() {
        return itemID;
    }

    /**
     * Gets the category of the item.
     *
     * @return The category of the item.
     */
    public ItemCategory getCategory() {
        return category;
    }

    /**
     * Gets the description of the item.
     *
     * @return A brief description of the item.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the cost of the item.
     *
     * @return The cost of the item.
     */
    public double getCost() {
        return cost;
    }

    /**
     * Custom toString to return correct format of item object
     * @return custom formatted item object to match menu.txt
     */
    @Override
    public String toString() {
        return itemID + "," + category + "," + String.format("%.2f", cost) + "," + description;
    }
}
