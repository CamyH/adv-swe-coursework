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

import java.sql.SQLOutput;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Fraser Holman
 */

public class ThreadTest {
    Staff a;
    Staff b;
    Staff c;
    Staff d;
    Staff e;
    Staff f;

    OrderList orderList;

    /**
     * Sets up an ItemList object
     */
    @BeforeEach
    public void setUp() {
//        orderList = SetupOrderFile.generateOrderList();
        orderList = OrderList.getInstance();

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
//
//            o = new Order();
//            o.addItem("FD5");
//            o.addItem("RL1");
//            o.addItem("SCK4");
//            o.addItem("HD5");
//            o.addItem("SCK7");
//            o.setOnlineStatus();
//            orderList.add(o);
//
//            o = new Order();
//            o.addItem("RL1");
//            o.addItem("FD2");
//            o.addItem("HD6");
//            o.addItem("SD8");
//            o.addItem("PSY4");
//            o.addItem("SCK6");
//            o.setOnlineStatus();
//            orderList.add(o);
        }
        catch (InvalidItemIDException | DuplicateOrderException | InvalidOrderException e) {
            System.out.println(e.getMessage());
        }

        a = StaffFactory.getStaff("waiter", "Waiter1", 1);
        b = StaffFactory.getStaff("chef", "Chef1", 2);
        c = StaffFactory.getStaff("barista", "Barista1", 3);
        d = StaffFactory.getStaff("waiter", "Waiter2", 1);
        e = StaffFactory.getStaff("chef", "Chef2", 2);
        f = StaffFactory.getStaff("barista", "Barista2", 3);
    }

    @AfterEach
    void tearDown() {
        StaffList staffList = StaffList.getInstance();
        Collection<Staff> all = staffList.getStaffList().values();

        for (Staff staff : all) {
            staff.removeStaff();
        }
    }

    /**
     * Tests the creation of an order.
     */
    @Test
    public void testThreads() throws InterruptedException {
//        a.start();
//        b.start();
//        c.start();
//        d.start();
//        e.start();
//        f.start();
//
//        StaffList staffList = StaffList.getInstance();
//        staffList.setDefaultDelay(10000);
//
////        b.removeStaff();
//
////        orderList = SetupOrderFile.generateOrderList();
////
//        Thread.sleep(10000);
//
//        staffList.setDefaultDelay(1000);
//
//        Thread.sleep(20000);
//
//        orderList = SetupOrderFile.generateOrderList();

//        Thread.sleep(20000);

//        a.removeStaff();
//        b.removeStaff();
//        c.removeStaff();

//        StaffList staffList = StaffList.getInstance();
//        Collection<Staff> all = staffList.getStaffList().values();
//
//        for (Staff staff : all) {
//            System.out.println(staff.getWorkerName());
//        }

    }
}
