package item;

import interfaces.EntityList;
import java.util.HashMap;
import utils.ItemCategory;

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
    public void add(Item item) {
        if (item != null) {
            if (item.getItemID() != null) {
                if (!items.containsKey(item.getItemID())) {
                    items.put(item.getItemID(), item);
                }
                else throw new IllegalArgumentException("Item ID is not unique");
            }
            else throw new NullPointerException("Item ID is null");

            return;
        }
        throw new NullPointerException("Item is null");
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
    public void setCost(String itemID, float cost) {
        if (items.containsKey(itemID)) {
            Item i = items.get(itemID);
            i.setCost(cost);
            return;
        }
        throw new IllegalArgumentException(itemID + " is not a valid item ID");
    }

}