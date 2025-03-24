package workers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for the Staff List class.
 *
 * @author Fraser Holman
 */
public class StaffListTest {
    Staff a;
    Staff b;
    Staff c;

    StaffList staffList;

    @BeforeEach
    public void setUp() {
        StaffList.resetInstance();
        staffList = StaffList.getInstance();

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
     * Tests adding staff to the staff list
     */
    @Test
    public void testAddStaff() throws InterruptedException {
        assertTrue(staffList.add(a));
        assertTrue(staffList.add(b));
        assertTrue(staffList.add(c));
    }

    /**
     * Tests adding null staff to the staff list
     */
    @Test
    public void testAddNullStaff() throws InterruptedException {
        assertFalse(staffList.add(null));
    }

    /**
     * Tests removing null staff from the staff list
     */
    @Test
    public void testRemoveNullStaff() throws InterruptedException {
        assertFalse(staffList.remove(null));
    }

    /**
     * Tests removing non-existent staff from the staff list
     */
    @Test
    public void testRemoveNonExistentStaff() throws InterruptedException {
        assertFalse(staffList.remove(UUID.randomUUID()));
    }

    /**
     * Tests removing staff from staff list
     */
    @Test
    public void testRemoveStaff() throws InterruptedException {
        assertTrue(staffList.add(a));
        assertTrue(staffList.add(b));
        assertTrue(staffList.add(c));

        assertTrue(staffList.remove(staffList.getStaffIDs()[0]));
        assertTrue(staffList.remove(staffList.getStaffIDs()[0]));
        assertTrue(staffList.remove(staffList.getStaffIDs()[0]));
    }

    /**
     * Tests to get the staff object from UUID
     */
    @Test
    public void getStaffObject() throws InterruptedException {
        assertTrue(staffList.add(a));
        assertTrue(staffList.add(b));
        assertTrue(staffList.add(c));

        assertInstanceOf(Staff.class, staffList.getStaff(staffList.getStaffIDs()[0]));
    }
}
