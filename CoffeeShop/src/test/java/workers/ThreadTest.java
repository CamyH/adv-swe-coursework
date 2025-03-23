package workers;

import exceptions.DuplicateOrderException;
import exceptions.InvalidItemIDException;
import exceptions.InvalidOrderException;
import order.Order;
import order.OrderList;
import order.SetupOrderFile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Fraser Holman
 */

public class ThreadTest {
    Staff a;
    Staff b;
    Staff c;

    OrderList orderList;

    /**
     * Sets up an ItemList object
     */
    @BeforeEach
    public void setUp() {
        orderList = SetupOrderFile.generateOrderList();

        try {
            Order o = new Order();
            o.addItem("RL1");
            o.addItem("HD4");
            o.addItem("SD7");
            o.addItem("PSY1");
            o.setOnlineStatus();
            orderList.add(o);

            o = new Order();
            o.addItem("FD3");
            o.addItem("HD5");
            o.addItem("RL1");
            o.addItem("SD1");
            o.setOnlineStatus();
            orderList.add(o);

            o = new Order();
            o.addItem("RL3");
            o.addItem("HD1");
            o.addItem("PSY2");
            o.setOnlineStatus();
            orderList.add(o);

            o = new Order();
            o.addItem("FD5");
            o.addItem("RL1");
            o.addItem("SCK4");
            o.addItem("HD5");
            o.addItem("SCK7");
            o.setOnlineStatus();
            orderList.add(o);

            o = new Order();
            o.addItem("RL1");
            o.addItem("FD2");
            o.addItem("HD6");
            o.addItem("SD8");
            o.addItem("PSY4");
            o.addItem("SCK6");
            o.setOnlineStatus();
            orderList.add(o);
        }
        catch (InvalidItemIDException | DuplicateOrderException | InvalidOrderException e) {
            System.out.println(e.getMessage());
        }

        a = StaffFactory.getStaff("barista", "Bob", 1);
        b = StaffFactory.getStaff("barista", "Bill", 0.2);
        c = StaffFactory.getStaff("barista", "Fraser", 0.6);
    }

    @AfterEach
    void tearDown() {
        a.removeStaff();
        b.removeStaff();
        c.removeStaff();
    }

    /**
     * Tests the creation of an order.
     */
    @Test
    public void testThreads() throws InterruptedException {
//        a.start();
//        b.start();
//        c.start();
//
//
//
//        //b.removeStaff();
//
//        orderList = SetupOrderFile.generateOrderList();
//
//        Thread.sleep(5000);
//
//        orderList = SetupOrderFile.generateOrderList();
//
//        Thread.sleep(20000);
//
//        a.removeStaff();
//        b.removeStaff();
//        c.removeStaff();
    }
}
