package order;

import interfaces.EntityList;
import interfaces.Observer;
import interfaces.Subject;
import logs.CoffeeShopLogger;
import workers.Waiter;

import java.util.*;

public class FoodList extends Subject {
    private static FoodList instance;

    private Queue<Map.Entry<Waiter, String>> foodList;

    private FoodList() {
        foodList = new ArrayDeque<Map.Entry<Waiter, String>>();
    }

    public boolean add(Map.Entry<Waiter, String> food) {
        notifyObservers();
        return foodList.offer(food);
    }

    public Map.Entry<Waiter, String> remove() {
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
