package order;

import interfaces.Observer;
import interfaces.Subject;
import item.Item;
import workers.Waiter;

import java.util.*;

public class DrinkList extends Subject {
    private static DrinkList instance;

    private Queue<Map.Entry<Waiter, String>> drinkList;

    private DrinkList() {
        drinkList = new ArrayDeque<Map.Entry<Waiter, String>>();
    }

    public boolean add(Map.Entry<Waiter, String> food) {
        notifyObservers();
        return drinkList.offer(food);
    }

    public Map.Entry<Waiter, String> remove() {
        return drinkList.poll();
    }

    public static DrinkList getInstance() {
        if (instance == null) instance = new DrinkList();
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
