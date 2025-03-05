package client;

import exceptions.InvalidItemIDException;
import item.Item;
import item.ItemList;
import order.OrderList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import item.ItemCategory;

/**
 * author: Caelan Mackenzie
 * Unit tests for the Console Class
 */

public class ConsoleTest {
    private static ItemList itemList;
    private static OrderList orderList;
    private static Console console;
    @BeforeEach
    void setup(){
        itemList = new ItemList();
        orderList = new OrderList();
        console = new Console(itemList, orderList);
    }

    @Test
    public void testViewMenu(){
        try {
            itemList.add(new Item("RL1", ItemCategory.ROLL, 3.0, "BACON ROLL"));

            itemList.add(new Item("FD1", ItemCategory.FOOD, 4.0, "BAKED POTATO"));

            itemList.add(new Item("HD1", ItemCategory.HOTDRINK, 2.0, "TEA"));

            console.viewMenu();
        } catch (InvalidItemIDException e) {
            System.out.println(e.getMessage());
        }
    }
}

