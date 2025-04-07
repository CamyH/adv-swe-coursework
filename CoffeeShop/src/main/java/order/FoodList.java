package order;

import interfaces.Subject;
import workers.Waiter;

import java.util.*;

/**
 * Singleton class and uses Observer Design Pattern (this class is the subject)
 * Class represents a list of food items to be processed
 * Contains a queue of different item IDs and the waiter the item has come from
 *
 * @author Fraser Holman
 */
public class FoodList extends Subject {
    private static FoodList instance;

    private final Queue<Map.Entry<Waiter, FoodItem>> foodList;

    /**
     * Constructor to set up the food list class
     */
    private FoodList() {
        foodList = new ArrayDeque<>();
    }

    /**
     * Method to add items to FoodList
     *
     * @param food A map entry of ItemID and the waiter it came from
     * @return a boolean if adding was a success
     */
    public boolean add(Map.Entry<Waiter, FoodItem> food) {
        notifyObservers();
        return foodList.offer(food);
    }

    /**
     * Method to return an entry from the FoodList
     *
     * @return A map entry of the next food item to be processed
     */
    public Map.Entry<Waiter, FoodItem> remove() {
        return foodList.poll();
    }

    /**
     * Method to return singleton instance of class
     *
     * @return the class instance
     */
    public static FoodList getInstance() {
        if (instance == null) instance = new FoodList();
        return instance;
    }

    /**
     * Reset the OrderList singleton instance
     * Used by tests
     */
    public static void resetInstance() {
        instance = new FoodList();
    }
}
