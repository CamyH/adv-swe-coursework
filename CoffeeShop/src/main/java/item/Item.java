package item;

import exceptions.InvalidItemIDException;

import java.io.Serializable;

/**
 * Defines an item object
 * @author Akash Poonia
 */
public class Item implements Serializable {
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

    public Item(String itemID, ItemCategory category, double cost, String description) throws InvalidItemIDException {

        // Validate itemID: It should not be null or empty
        if (itemID == null || itemID.isEmpty()) {
            throw new InvalidItemIDException("Item ID cannot be null or empty.");
        }
        // Validate category: It should not be null
        if (category == null) {
            throw new InvalidItemIDException("Item category cannot be null.");
        }
        // Validate cost: It should not be negative
        if (cost < 0) {
            throw new InvalidItemIDException("Item cost cannot be negative.");
        }
        // Validate description: It should not be null or empty
        if (description == null || description.isEmpty()) {
            throw new InvalidItemIDException("Item description cannot be null or empty.");
        }
        // Assign values to instance variables after validation
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
    public void setCost(double cost) {
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
}
