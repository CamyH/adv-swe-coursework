package order;

import interfaces.Observer;
import interfaces.Subject;
import item.Item;
import workers.Waiter;

import java.util.*;

/**
 * Singleton class and uses Observer Design Pattern (this class is the subject)
 *
 * Class represents a list of drinks to be processed
 *
 * Contains a queue of different item IDs and the waiter the item has come from
 *
 * @author Fraser Holman
 */
public class DrinkList extends Subject {
    private static DrinkList instance;

    private final Queue<Map.Entry<Waiter, DrinkItem>> drinkList;

    /**
     * Constructor to set up the drink list class
     */
    private DrinkList() {
        drinkList = new ArrayDeque<>();
    }

    /**
     * Method to add items to DrinkList
     *
     * @param food A map entry of ItemID and the waiter it came from
     * @return a boolean if adding was a success
     */
    public boolean add(Map.Entry<Waiter, DrinkItem> food) {
        notifyObservers();
        return drinkList.offer(food);
    }

    /**
     * Method to return an entry from the DrinkList
     *
     * @return A map entry of the next drink item to be processed
     */
    public Map.Entry<Waiter, DrinkItem> remove() {
        return drinkList.poll();
    }

    /**
     * Method to return singleton instance of class
     *
     * @return the class instance
     */
    public static DrinkList getInstance() {
        if (instance == null) {
            instance = new DrinkList();
        }

        return instance;
    }

    /**
     * Reset the OrderList singleton instance
     * Used by tests
     */
    public static void resetInstance() {
        instance = new DrinkList();
    }
}
