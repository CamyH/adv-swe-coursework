package workers;

import exceptions.InvalidItemIDException;
import exceptions.InvalidOrderException;
import order.Order;
import order.OrderList;
import order.SetupOrderFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for the Order class.
 * This class contains test cases that validate the functionality of the Order class including item addition, cost calculation, and exception handling
 *
 * @author Mohd Faiz
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

        a = StaffFactory.getStaff("barista", "Bob", 1);
        b = StaffFactory.getStaff("barista", "Bill", 0.2);
        c = StaffFactory.getStaff("barista", "Fraser", 0.6);
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
//        Thread.sleep(1000);
//
//        b.removeStaff();
//
//        orderList = SetupOrderFile.generateOrderList();
//
//        Thread.sleep(5000);
//
//        orderList = SetupOrderFile.generateOrderList();
//
//        Thread.sleep(10000);
//
//        a.removeStaff();
//        c.removeStaff();
    }
}
