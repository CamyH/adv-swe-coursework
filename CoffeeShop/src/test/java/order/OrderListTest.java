package order;

import exceptions.InvalidItemIDException;
import exceptions.InvalidOrderException;
import item.Item;
import item.ItemList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import item.ItemCategory;
import java.util.HashMap;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

public class OrderListTest {
    OrderList orderList;

    ItemList itemList;

    Order first;

    void setUpItemList() {
        itemList = new ItemList();
        try {
            itemList.add(new Item("RL1", ItemCategory.ROLL, 3.0, "BACON ROLL"));
            itemList.add(new Item("RL2", ItemCategory.ROLL, 3.5, "SAUSAGE ROLL"));
            itemList.add(new Item("RL3", ItemCategory.ROLL, 2.8, "EGG ROLL"));
            itemList.add(new Item("RL4", ItemCategory.ROLL, 3.0, "CHEESE ROLL"));

            itemList.add(new Item("FD1", ItemCategory.FOOD, 4.0, "BAKED POTATO"));
            itemList.add(new Item("FD2", ItemCategory.FOOD, 4.5, "SANDWICH"));
            itemList.add(new Item("FD3", ItemCategory.FOOD, 5.0, "PANINI"));
            itemList.add(new Item("FD4", ItemCategory.FOOD, 4.5, "SALAD"));
            itemList.add(new Item("FD5", ItemCategory.FOOD, 3.0, "CHIPS"));

            itemList.add(new Item("HD1", ItemCategory.HOTDRINK, 2.0, "TEA"));
            itemList.add(new Item("HD2", ItemCategory.HOTDRINK, 2.2, "ESPRESSO"));
            itemList.add(new Item("HD3", ItemCategory.HOTDRINK, 2.5, "AMERICANO"));
            itemList.add(new Item("HD4", ItemCategory.HOTDRINK, 3.0, "LATTE"));
            itemList.add(new Item("HD5", ItemCategory.HOTDRINK, 3.2, "CAPPUCCINO"));
            itemList.add(new Item("HD6", ItemCategory.HOTDRINK, 3.5, "MOCHA"));
            itemList.add(new Item("HD7", ItemCategory.HOTDRINK, 3.5, "HOT CHOCOLATE"));

            itemList.add(new Item("SD1", ItemCategory.SOFTDRINK, 1.5, "STILL WATER"));
            itemList.add(new Item("SD2", ItemCategory.SOFTDRINK, 1.8, "SPARKLING WATER"));
            itemList.add(new Item("SD3", ItemCategory.SOFTDRINK, 2.5, "ORANGE JUICE"));
            itemList.add(new Item("SD4", ItemCategory.SOFTDRINK, 2.5, "APPLE JUICE"));
            itemList.add(new Item("SD5", ItemCategory.SOFTDRINK, 2.8, "LEMONADE"));
            itemList.add(new Item("SD6", ItemCategory.SOFTDRINK, 2.5, "COLA"));
            itemList.add(new Item("SD7", ItemCategory.SOFTDRINK, 2.5, "FANTA"));
            itemList.add(new Item("SD8", ItemCategory.SOFTDRINK, 3.0, "ROOT BEER"));
            itemList.add(new Item("SD9", ItemCategory.SOFTDRINK, 3.0, "GINGER BEER"));

            itemList.add(new Item("SCK1", ItemCategory.SNACK, 1.8, "CHOCOLATE BAR"));
            itemList.add(new Item("SCK2", ItemCategory.SNACK, 1.5, "CRISPS"));
            itemList.add(new Item("SCK3", ItemCategory.SNACK, 2.0, "POPCORN"));
            itemList.add(new Item("SCK4", ItemCategory.SNACK, 1.8, "PRETZELS"));
            itemList.add(new Item("SCK5", ItemCategory.SNACK, 2.0, "SHORTBREAD"));
            itemList.add(new Item("SCK6", ItemCategory.SNACK, 2.2, "GRANOLA BARS"));
            itemList.add(new Item("SCK7", ItemCategory.SNACK, 2.5, "CHEESE"));
            itemList.add(new Item("SCK8", ItemCategory.SNACK, 2.0, "CRACKERS"));

            itemList.add(new Item("PSY1", ItemCategory.PASTRY, 2.5, "CROISSANT"));
            itemList.add(new Item("PSY2", ItemCategory.PASTRY, 2.8, "DANISH PASTRY"));
            itemList.add(new Item("PSY3", ItemCategory.PASTRY, 3.0, "CINNAMON ROLL"));
            itemList.add(new Item("PSY4", ItemCategory.PASTRY, 3.5, "MACARONS"));
            itemList.add(new Item("PSY5", ItemCategory.PASTRY, 2.8, "PAIN AU CHOCOLAT"));
        } catch (InvalidItemIDException e) {
            System.out.println(e.getMessage());
        }
    }

    @BeforeEach
    public void setUp() {
        setUpItemList();
        orderList = new OrderList();

        try {
            first = new Order(itemList);
            first.addItem("RL2");
            first.addItem("SD4");
            first.addItem("PSY5");
            orderList.add(first);

            Order o = new Order(itemList);
            o.addItem("RL1");
            o.addItem("HD4");
            o.addItem("SD7");
            o.addItem("PSY1");
            orderList.add(o);

            o = new Order(itemList);
            o.addItem("FD3");
            o.addItem("HD5");
            o.addItem("RL1");
            o.addItem("SD1");
            orderList.add(o);

            o = new Order(itemList);
            o.addItem("RL3");
            o.addItem("HD1");
            o.addItem("PSY2");
            orderList.add(o);

            o = new Order(itemList);
            o.addItem("FD5");
            o.addItem("RL1");
            o.addItem("SCK4");
            o.addItem("HD5");
            o.addItem("SCK7");
            orderList.add(o);

            o = new Order(itemList);
            o.addItem("RL1");
            o.addItem("FD2");
            o.addItem("HD6");
            o.addItem("SD8");
            o.addItem("PSY4");
            o.addItem("SCK6");
            orderList.add(o);
        } catch (InvalidOrderException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void testAddOrder() {
        try {
            Order o = new Order(itemList);
            assertThrows(InvalidOrderException.class, () -> {orderList.add(o);});
        }
        catch (InvalidOrderException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void testRemove() {
        try {
            Order o = new Order(itemList);
            o.addItem("RL4");
            o.addItem("SCK8");
            o.addItem("FD3");
            orderList.add(new Order(itemList));

            Order o1 = orderList.getOrder(o.getOrderID());

            assertEquals(o1.getOrderID(), o.getOrderID()); // check that the object has been added to OrderList

            assertTrue(orderList.remove(o1.getOrderID()));

            assertFalse(orderList.remove(o1.getOrderID()));
        } catch (InvalidOrderException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void testGetOrder() {
        Order o1 = orderList.getOrder();

        assertNotNull(o1);

        assertEquals(o1.getOrderID(), first.getOrderID());
        assertEquals(o1.getTotalCost(), first.getTotalCost());
        assertEquals(o1.getCustomerID(), first.getCustomerID());
        assertEquals(o1.getDetails(), first.getDetails());
    }

    @Test
    void testGetOrderWithID() {
        try {
            Order o = new Order(itemList);
            o.addItem("RL4");
            o.addItem("SCK8");
            o.addItem("FD3");
            orderList.add(new Order(itemList));

            Order o1 = orderList.getOrder(o.getOrderID());

            assertEquals(o1.getOrderID(), o.getOrderID());
            assertEquals(o1.getTotalCost(), o.getTotalCost());
            assertEquals(o1.getCustomerID(), o.getCustomerID());
            assertEquals(o1.getDetails(), o.getDetails());
        } catch (InvalidOrderException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void testGetOrderList() {
        Queue<Order> myList = orderList.getOrderList();

        assertNotNull(myList);

        assertEquals(orderList.getOrder(), myList.peek());

        assertNotNull(myList.poll());

        assertNotEquals(orderList.getOrder(), myList.peek());
    }

    @Test
    void testGetCompletedOrdersToString() {
        String[] arr = new String[2];

        OrderList newOrderList = new OrderList();

        Order first, second;

        try {
            first = new Order(itemList);
            first.addItem("RL2");
            first.addItem("SD4");
            first.addItem("PSY5");
            newOrderList.add(first);

            second = new Order(itemList);
            second.addItem("RL1");
            second.addItem("HD4");
            second.addItem("SD7");
            second.addItem("PSY1");
            newOrderList.add(second);

            newOrderList.remove(first.getOrderID());
            newOrderList.remove(second.getOrderID());

            String s1 = String.format("%s,%s,%s,%s,%.2f,%.2f",
                    first.getOrderID().toString(),
                    first.getCustomerID(),
                    first.getTimestamp().toString(),
                    "RL2;SD4;PSY5",
                    first.getTotalCost(),
                    first.getDiscountedCost()
            );

            String s2 = String.format("%s,%s,%s,%s,%.2f,%.2f",
                    second.getOrderID().toString(),
                    second.getCustomerID(),
                    second.getTimestamp().toString(),
                    "RL1;HD4;SD7;PSY1",
                    second.getTotalCost(),
                    second.getDiscountedCost()
            );

            arr[0] = s1;
            arr[1] = s2;
        }
        catch (InvalidOrderException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(arr[0], newOrderList.getOrdersToString(true)[0]);
        assertEquals(arr[1], newOrderList.getOrdersToString(true)[1]);
        assertEquals(arr.length, newOrderList.getOrdersToString(true).length);
    }

    @Test
    void testGetUnCompletedOrdersToString() {
        String[] arr = new String[2];

        OrderList newOrderList = new OrderList();

        Order first, second;

        try {
            first = new Order(itemList);
            first.addItem("RL2");
            first.addItem("SD4");
            first.addItem("PSY5");
            newOrderList.add(first);

            second = new Order(itemList);
            second.addItem("RL1");
            second.addItem("HD4");
            second.addItem("SD7");
            second.addItem("PSY1");
            newOrderList.add(second);

            String s1 = String.format("%s,%s,%s,%s,%.2f,%.2f",
                    first.getOrderID().toString(),
                    first.getCustomerID(),
                    first.getTimestamp().toString(),
                    "RL2;SD4;PSY5",
                    first.getTotalCost(),
                    first.getDiscountedCost()
            );

            String s2 = String.format("%s,%s,%s,%s,%.2f,%.2f",
                    second.getOrderID().toString(),
                    second.getCustomerID(),
                    second.getTimestamp().toString(),
                    "RL1;HD4;SD7;PSY1",
                    second.getTotalCost(),
                    second.getDiscountedCost()
            );

            arr[0] = s1;
            arr[1] = s2;
        }
        catch (InvalidOrderException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(arr[0], newOrderList.getOrdersToString(false)[0]);
        assertEquals(arr[1], newOrderList.getOrdersToString(false)[1]);
        assertEquals(arr.length, newOrderList.getOrdersToString(false).length);

    }

    @Test
    void testCompletedOrderItemCount() {
        HashMap<String, Double> myMap = new HashMap<>();

        OrderList newOrderList = new OrderList();

        Order first, second;

        try {
            first = new Order(itemList);
            first.addItem("RL1");
            first.addItem("RL1");
            first.addItem("PSY5");
            newOrderList.add(first);

            second = new Order(itemList);
            second.addItem("RL1");
            second.addItem("HD4");
            second.addItem("SD7");
            second.addItem("PSY1");
            newOrderList.add(second);

            newOrderList.remove(first.getOrderID());
            newOrderList.remove(second.getOrderID());
        }
        catch (InvalidOrderException e) {
            System.out.println(e.getMessage());
        }

        myMap.put("RL1", 3.0);
        myMap.put("HD4", 1.0);
        myMap.put("SD7", 1.0);
        myMap.put("PSY1", 1.0);
        myMap.put("PSY5", 1.0);

        myMap.put("Total Cost", 19.8);

        HashMap<String, Double> itemCount = newOrderList.completedOrderItemCount();

        assertEquals(itemCount, myMap);
    }

}
