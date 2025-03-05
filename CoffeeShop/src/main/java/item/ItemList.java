package item;

import exceptions.InvalidItemIDException;
import interfaces.EntityList;

import java.util.*;

import order.Order;
import utils.ItemCategory;

/**
 * Class represents a list of all avaible items that can be ordered
 *
 * Contains a hashmap of all the items
 *
 * @author Fraser Holman
 */

public class ItemList implements EntityList<Item, String> {
    /** Hashmap data structure to hold item information */
    private Map<String, Item> items;

    /**
     * Initialises the hashmap to contain the items
     */
    public ItemList() {
        items = new LinkedHashMap<String, Item>();
    };

    /**
     * Adds an item to the hashmap
     *
     * @param item The item ID to be added to the hashmap
     */
    @Override
    public Boolean add(Item item) {
        return items.putIfAbsent(item.getItemID(), item) == null;
    }

    /**
     * Removes an item from the hashmap
     *
     * @param ID The item ID to be removed from the hashmap
     */
    @Override
    public Boolean remove(String ID) {
        return items.remove(ID) != null;
    }

    /**
     * Get method to return the items hashmap
     *
     * @return items hashmap
     */
    public LinkedHashMap<String, Item> getMenu() {
        return new LinkedHashMap<>(items); // Creates a copy
    }


    /**
     * Get method to return a specific items category
     *
     * @param itemID The item ID used to find the desired item
     * @return an ItemCategory enum containing category information
     */
    public ItemCategory getCategory(String itemID) throws InvalidItemIDException {
        return items.get(itemID).getCategory();
    }

    /**
     * Get method to return the cost of an item
     *
     * @param itemID The item ID used to find the correct item
     * @return a double which holds cost information
     */
    public double getCost(String itemID) {
        return items.get(itemID).getCost();
    }

    /**
     * Get method to return the description of an item
     *
     * @param itemID The item ID used to find the correct item
     * @return a String which holds a description of the item
     */
    public String getDescription(String itemID) throws InvalidItemIDException {
        return items.get(itemID).getDescription();
    }

    /**
     * Set method to set the cost of an item
     *
     * @param itemID The item ID used to find the correct item
     * @param cost The cost to set the item to
     */
    public void setCost(String itemID, double cost) throws InvalidItemIDException {
        items.get(itemID).setCost(cost);
    }

    /**
     * Check if itemID exists
     *
     * @param itemID The itemID to check if the item exists
     */
    public Boolean itemExists(String itemID) {
        return items.containsKey(itemID);
    }

    /**
     * Method to return array of ItemIDs used at end of program for the log
     *
     * @return String array containing all ItemIDs
     */
    public String[] getItemIDs() {
        return items.keySet().toArray(new String[0]);
    }

    /**
     * Method to return array of strings representing itemID, description, and cost
     *
     * Method is used by the console and GUI to display item information
     *
     * @return String array containing menu details
     */
    public String[] getMenuDetails() {
        String[] menuDetailsString = new String[items.size()];

        int count = 0;

        for (Item item : items.values()) {
            String s = String.format("%s,%s,%.2f",
                    item.getItemID(),
                    item.getDescription(),
                    item.getCost()
            );

            menuDetailsString[count] = s;

            count++;
        }

        return menuDetailsString;
    }

}