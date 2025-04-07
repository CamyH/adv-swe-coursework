package workers;

import interfaces.INotificationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Collection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for the Staff List class.
 *
 * @author Fraser Holman
 */
public class StaffListTest {
    private StaffList staffList;

    @Mock
    private INotificationService notificationService;

    @BeforeEach
    public void setUp() {
        StaffList.resetInstance();
        staffList = StaffList.getInstance();

        StaffFactory.getStaff("barista", "Bob", 1, notificationService);
        StaffFactory.getStaff("barista", "Bill", 2, notificationService);
        StaffFactory.getStaff("barista", "Fraser", 3, notificationService);
    }

    @AfterEach
    void tearDown() {
        Collection<Staff> all = staffList.getStaffList().values();

        for (Staff staff : all) {
            staff.removeStaff();
        }
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
        assertTrue(staffList.remove(staffList.getStaffIDs()[0]));
        assertTrue(staffList.remove(staffList.getStaffIDs()[0]));
        assertTrue(staffList.remove(staffList.getStaffIDs()[0]));
    }

    /**
     * Tests to get the staff object from UUID
     */
    @Test
    public void getStaffObject() throws InterruptedException {
        assertInstanceOf(Staff.class, staffList.getStaff(staffList.getStaffIDs()[0]));
    }
}
