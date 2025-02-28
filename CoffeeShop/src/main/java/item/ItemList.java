package item;

import interfaces.EntityList;
import java.util.HashMap;
import utils.ItemCategory;
import exceptions.DuplicateItemIDException;

/**
 * @author Fraser Holman
 *
 * Class represents a list of all avaible items that can be ordered
 *
 * Contains a hashmap of all the items
 */

public class ItemList implements EntityList<Item, String> {
    private HashMap<String, Item> items;

    /**
     * Initialises the hashmap to contain the items
     */
    public ItemList() {
        items = new HashMap<String, Item>();
    };

    /**
     * Adds an item to the hashmap
     *
     * @param item The item to be added to the hashmap
     */
    @Override
    public void add(Item item) throws DuplicateItemIDException {
        if (items.containsKey(item.getItemID())) {
            throw new DuplicateItemIDException("Item ID is not unique");
        }
        else items.put(item.getItemID(), item);
    }

    /**
     *
     * @param ID
     */
    @Override
    public void remove(String ID) {
        if (items.containsKey(ID)) {
            items.remove(ID);
            return;
        }
        throw new IllegalArgumentException(ID + " is not a valid item ID");
    }

    /**
     *
     * @return
     */
    public HashMap<String, Item> getMenu() {
        return items;
    }

    /**
     *
     * @param itemID
     * @return
     */
    public ItemCategory getCategory(String itemID) {
        if (items.containsKey(itemID)) {
            Item i = items.get(itemID);
            return i.getCategory();
        }
        throw new IllegalArgumentException(itemID + " is not a valid item ID");
    }

    /**
     *
     * @param itemID
     * @return
     */
    public double getCost(String itemID) {
        if (items.containsKey(itemID)) {
            Item i = items.get(itemID);
            return i.getCost();
        }
        throw new IllegalArgumentException(itemID + " is not a valid item ID");
    }

    /**
     *
     * @param itemID
     * @return
     */
    public String getDescription(String itemID) {
        if (items.containsKey(itemID)) {
            Item i = items.get(itemID);
            return i.getDescription();
        }
        throw new IllegalArgumentException(itemID + " is not a valid item ID");
    }

    /**
     *
     * @param itemID
     * @param cost
     */
    public void setCost(String itemID, double cost) {
        if (items.containsKey(itemID)) {
            Item i = items.get(itemID);
            i.setCost(cost);
            return;
        }
        throw new IllegalArgumentException(itemID + " is not a valid item ID");
    }

}