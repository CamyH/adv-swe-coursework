package order;

import exceptions.DuplicateOrderException;
import exceptions.InvalidItemIDException;
import exceptions.InvalidOrderException;
import item.ItemList;
import item.SetupItemFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import java.util.HashMap;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for the Order List class.
 *
 * @author Fraser Holman
 */

public class OrderListTest {
    OrderList orderList;

    OrderList newOrderList;

    ItemList itemList;

    Order first;

    /**
     * Sets up the menu in an ItemList object and generates a basic OrderList class
     */
    @BeforeEach
    public void setUp() {
        itemList = SetupItemFile.generateItemList();
        orderList = SetupOrderFile.generateOrderList();
        first = orderList.getOrder(false);
    }

    /**
     * Tests adding orders to the OrderList
     */
    @Test
    void testAddOrder() {
        orderList = OrderList.getInstance();
        try {
            Order o = new Order();
            assertThrows(InvalidOrderException.class, () -> {orderList.add(o);});
        }
        catch (InvalidOrderException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Tests removing orders from an order List object
     */
    @Test
    void testRemove() {
        orderList = OrderList.getInstance();
        try {
            Order o = new Order();
            o.addItem("RL4");
            o.addItem("SCK8");
            o.addItem("FD3");
            orderList.add(new Order());

            Order o1 = orderList.getOrder(o.getOrderID());

            assertEquals(o1.getOrderID(), o.getOrderID()); // check that the object has been added to OrderList

            assertTrue(orderList.remove(o1.getOrderID()));

            assertFalse(orderList.remove(o1.getOrderID()));
        } catch (InvalidOrderException | InvalidItemIDException | DuplicateOrderException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Tests getting the first order in the queue
     */
    @Test
    void testGetOrder() {
        orderList = OrderList.getInstance();
        Order o1 = orderList.getOrder(false);

        System.out.println(o1.getOrderID());

        assertNotNull(o1);

        assertEquals(o1.getOrderID(), first.getOrderID());
        assertEquals(o1.getTotalCost(), first.getTotalCost());
        assertEquals(o1.getCustomerID(), first.getCustomerID());
        assertEquals(o1.getDetails(), first.getDetails());
    }

    /**
     * Tests getting an order with its Order ID
     */
    @Test
    void testGetOrderWithID() {
        orderList = OrderList.getInstance();
        try {
            Order o = new Order();
            o.addItem("RL4");
            o.addItem("SCK8");
            o.addItem("FD3");
            orderList.add(new Order());

            Order o1 = orderList.getOrder(o.getOrderID());

            assertEquals(o1.getOrderID(), o.getOrderID());
            assertEquals(o1.getTotalCost(), o.getTotalCost());
            assertEquals(o1.getCustomerID(), o.getCustomerID());
            assertEquals(o1.getDetails(), o.getDetails());
        } catch (InvalidOrderException | InvalidItemIDException | DuplicateOrderException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Tests getting the Order List data structure
     */
    @Test
    void testGetOrderList() {
        orderList = OrderList.getInstance();
        Queue<Order> myList = orderList.getOrderList();

        assertNotNull(myList);

        assertEquals(orderList.getOrder(false), myList.peek());

        assertNotNull(myList.poll());

        assertNotEquals(orderList.getOrder(false), myList.peek());
    }

    /**
     * Tests getting a string array of completed order details
     */
    @Test
    void testGetCompletedOrdersToString() {
        String[] arr = new String[2];

        OrderList newOrderList = OrderList.getInstance();

        Order first, second;

        try {
            first = new Order();
            first.addItem("RL2");
            first.addItem("SD4");
            first.addItem("PSY5");
            newOrderList.add(first);

            second = new Order();
            second.addItem("RL1");
            second.addItem("HD4");
            second.addItem("SD7");
            second.addItem("PSY1");
            newOrderList.add(second);

            newOrderList.remove(first.getOrderID());
            newOrderList.remove(second.getOrderID());

            String s1 = String.format("%s,%s,%s,%s,%s",
                    first.getOrderID().toString(),
                    first.getCustomerID(),
                    first.getTimestamp().toString(),
                    "RL2;SD4;PSY5",
                    "false"
            );

            String s2 = String.format("%s,%s,%s,%s,%s",
                    second.getOrderID().toString(),
                    second.getCustomerID(),
                    second.getTimestamp().toString(),
                    "RL1;HD4;SD7;PSY1",
                    "false"
            );

            arr[0] = s1;
            arr[1] = s2;
        }
        catch (InvalidOrderException | InvalidItemIDException | DuplicateOrderException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(arr[0], newOrderList.getOrdersToString(true)[0]);
        assertEquals(arr[1], newOrderList.getOrdersToString(true)[1]);
        assertEquals(arr.length, newOrderList.getOrdersToString(true).length);
    }

    /**
     * Tests getting a string array of in complete order details
     */
    @Test
    void testGetUnCompletedOrdersToString() {
        OrderList.resetInstance();
        orderList = OrderList.getInstance();
        String[] arr = new String[2];

        //OrderList newOrderList = OrderList.getInstance();

        Order first, second;

        System.out.println(orderList.getOrdersToString(false).length);

        try {
            first = new Order();
            first.addItem("RL2");
            first.addItem("SD4");
            first.addItem("PSY5");
            orderList.add(first);

            second = new Order();
            second.addItem("RL1");
            second.addItem("HD4");
            second.addItem("SD7");
            second.addItem("PSY1");
            orderList.add(second);

            String s1 = String.format("%s,%s,%s,%s,%s",
                    first.getOrderID().toString(),
                    first.getCustomerID(),
                    first.getTimestamp().toString(),
                    "RL2;SD4;PSY5",
                    "false"
            );

            String s2 = String.format("%s,%s,%s,%s,%s",
                    second.getOrderID().toString(),
                    second.getCustomerID(),
                    second.getTimestamp().toString(),
                    "RL1;HD4;SD7;PSY1",
                    "false"
            );

            arr[0] = s1;
            arr[1] = s2;
        }
        catch (InvalidOrderException | InvalidItemIDException | DuplicateOrderException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(arr[0]);

        assertEquals(arr[0], orderList.getOrdersToString(false)[0]);
        assertEquals(arr[1], orderList.getOrdersToString(false)[1]);
        assertEquals(arr.length, orderList.getOrdersToString(false).length);

    }

    /**
     * Tests getting a summary of the number of items purchased in a session
     */
    @Test
    void testCompletedOrderItemCount() {
        OrderList.resetInstance();
        orderList = OrderList.getInstance();
        HashMap<String, Double> myMap = new HashMap<>();

        OrderList newOrderList = OrderList.getInstance();

        Order first, second;

        try {
            first = new Order();
            first.addItem("RL1");
            first.addItem("RL1");
            first.addItem("PSY5");
            newOrderList.add(first);

            second = new Order();
            second.addItem("RL1");
            second.addItem("HD4");
            second.addItem("SD7");
            second.addItem("PSY1");
            newOrderList.add(second);

//            newOrderList.remove(first.getOrderID());
//            newOrderList.remove(second.getOrderID());
        }
        catch (InvalidOrderException | InvalidItemIDException | DuplicateOrderException e) {
            System.out.println(e.getMessage());
        }

        myMap.put("RL1", 3.0);
        myMap.put("HD4", 1.0);
        myMap.put("SD7", 1.0);
        myMap.put("PSY1", 1.0);
        myMap.put("PSY5", 1.0);

        myMap.put("total-cost", 19.8);
        myMap.put("discount-cost", 19.8 - 1.5 - 1.25);
        myMap.put("num-orders", 2.0);

        HashMap<String, Double> itemCount = newOrderList.completedOrderItemCount();

        assertEquals(itemCount, myMap);
    }

    /**
     * Tests what happens when the queue size is exceeded
     */
    @Test
    void testMaxQueueSize() {
        OrderList.resetInstance();
        orderList = OrderList.getInstance();
        orderList.setMaxQueueSize(2);

        try {
            Order o = new Order();
            o.addItem("RL1");
            o.addItem("RL1");
            o.addItem("PSY5");

            assertTrue(orderList.add(o));

            o = new Order();
            o.addItem("RL1");
            o.addItem("RL1");
            o.addItem("PSY5");

            assertTrue(orderList.add(o));

            o = new Order();
            o.addItem("RL1");
            o.addItem("RL1");
            o.addItem("PSY5");

            assertFalse(orderList.add(o));
        } catch (InvalidOrderException | InvalidItemIDException | DuplicateOrderException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Tests adding a duplicate order
     */
    @Test
    void testDuplicateOrders() {
        OrderList.resetInstance();
        orderList = OrderList.getInstance();

        try {
            Order o = new Order();
            o.addItem("RL1");
            o.addItem("RL1");
            o.addItem("PSY5");

            assertTrue(orderList.add(o));
            assertThrows(DuplicateOrderException.class, () -> {orderList.add(o);});

        } catch (InvalidOrderException | InvalidItemIDException | DuplicateOrderException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Tests adding online orders
     */
    @Test
    void testOnlineOrders() {
        OrderList.resetInstance();
        orderList = OrderList.getInstance();

        try {
            Order o = new Order();
            o.addItem("RL1");
            o.addItem("RL1");
            o.addItem("PSY5");
            o.setOnlineStatus();
            orderList.add(o);

            o = new Order();
            o.addItem("SD1");
            o.addItem("HD2");
            o.addItem("PSY5");
            o.setOnlineStatus();
            orderList.add(o);

            o = new Order();
            o.addItem("SD1");
            o.addItem("SD2");
            o.addItem("PSY5");
            orderList.add(o);

            System.out.println("ONLINE ORDERS");
            System.out.println(orderList.getOrdersForDisplay(1));
            System.out.println("IN PERSON ORDERS");
            System.out.println(orderList.getOrdersForDisplay(0));

        } catch (InvalidOrderException | InvalidItemIDException | DuplicateOrderException e) {
            System.out.println(e.getMessage());
        }
    }

}
