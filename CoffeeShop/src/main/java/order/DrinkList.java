package order;

import interfaces.Observer;
import interfaces.Subject;
import item.Item;
import workers.Waiter;

import java.util.*;

public class DrinkList extends Subject {
    private static DrinkList instance;

    private Queue<Map.Entry<Waiter, Item>> drinkList;

    private DrinkList() {
        drinkList = new ArrayDeque<Map.Entry<Waiter, Item>>();
    }

    public boolean add(Map.Entry<Waiter, Item> food) {
        notifyObservers();
        return drinkList.offer(food);
    }

    public Map.Entry<Waiter, Item> remove() {
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
