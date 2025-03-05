package order;

import exceptions.InvalidItemIDException;
import exceptions.InvalidOrderException;
import item.ItemList;
import item.SetupItemFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

public class OrderListTest {
    OrderList orderList;

    ItemList itemList;

    Order first;

    @BeforeEach
    public void setUp() {
        itemList = SetupItemFile.generateItemList();
        orderList = SetupOrderFile.generateOrderList();
        first = orderList.getOrder();
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
        } catch (InvalidOrderException | InvalidItemIDException e) {
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
        } catch (InvalidOrderException | InvalidItemIDException e) {
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

            String s1 = String.format("%s,%s,%s,%s",
                    first.getOrderID().toString(),
                    first.getCustomerID(),
                    first.getTimestamp().toString(),
                    "RL2;SD4;PSY5"
            );

            String s2 = String.format("%s,%s,%s,%s",
                    second.getOrderID().toString(),
                    second.getCustomerID(),
                    second.getTimestamp().toString(),
                    "RL1;HD4;SD7;PSY1"
            );

            arr[0] = s1;
            arr[1] = s2;
        }
        catch (InvalidOrderException | InvalidItemIDException e) {
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

            String s1 = String.format("%s,%s,%s,%s",
                    first.getOrderID().toString(),
                    first.getCustomerID(),
                    first.getTimestamp().toString(),
                    "RL2;SD4;PSY5"
            );

            String s2 = String.format("%s,%s,%s,%s",
                    second.getOrderID().toString(),
                    second.getCustomerID(),
                    second.getTimestamp().toString(),
                    "RL1;HD4;SD7;PSY1"
            );

            arr[0] = s1;
            arr[1] = s2;
        }
        catch (InvalidOrderException | InvalidItemIDException e) {
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
        catch (InvalidOrderException | InvalidItemIDException e) {
            System.out.println(e.getMessage());
        }

        myMap.put("RL1", 3.0);
        myMap.put("HD4", 1.0);
        myMap.put("SD7", 1.0);
        myMap.put("PSY1", 1.0);
        myMap.put("PSY5", 1.0);

        myMap.put("total-cost", 19.8);

        HashMap<String, Double> itemCount = newOrderList.completedOrderItemCount();

        assertEquals(itemCount, myMap);
    }

}
