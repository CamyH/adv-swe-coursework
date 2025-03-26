package order;

import interfaces.EntityList;
import interfaces.Observer;
import interfaces.Subject;
import item.Item;
import logs.CoffeeShopLogger;
import workers.Waiter;

import java.util.*;

public class FoodList extends Subject {
    private static FoodList instance;

    private Queue<Map.Entry<Waiter, Item>> foodList;

    private FoodList() {
        foodList = new ArrayDeque<Map.Entry<Waiter, Item>>();
    }

    public boolean add(Map.Entry<Waiter, Item> food) {
        notifyObservers();
        return foodList.offer(food);
    }

    public Map.Entry<Waiter, Item> remove() {
        return foodList.poll();
    }

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
