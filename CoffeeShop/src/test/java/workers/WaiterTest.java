package workers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit tests for Staff class and Staff Factory
 *
 * @author Fraser Holman
 */
class WaiterTest {

    Staff staff;

    @BeforeEach
    void setUp() {
        staff = StaffFactory.getStaff("waiter", "Bob", 1);
    }

    @AfterEach
    void tearDown() {
        staff.removeStaff();
    }

    /**
     * Test priority setting
     */
    @Test
    void testPriority() {
        assertEquals(0.8, ((Waiter) staff).getWaiterPriority());

        Staff a = StaffFactory.getStaff("waiter", "Bob", 1);

        assertEquals(Math.pow(0.8, 2), ((Waiter) staff).getWaiterPriority());
        assertEquals((Math.pow(0.8, 2) + ( ( 0.8 - Math.pow(0.8, 2) ) * 2 ) / ( 2 - 1 )), ((Waiter) a).getWaiterPriority());

        Staff b = StaffFactory.getStaff("waiter", "Bill", 2);

        assertEquals(Math.pow(0.8, 3), ((Waiter) staff).getWaiterPriority());
        assertEquals((Math.pow(0.8, 3) + ( ( 0.8 - Math.pow(0.8, 3) ) * 2 ) / ( 3 - 1 )), ((Waiter) a).getWaiterPriority());
        assertEquals((((Waiter) a).getWaiterPriority() + ( ( 0.8 - Math.pow(0.8, 3) ) * 2 ) / ( 3 - 1 )), ((Waiter) b).getWaiterPriority());

        Staff c = StaffFactory.getStaff("waiter", "Fraser", 3);

        assertEquals(Math.pow(0.8, 4), ((Waiter) staff).getWaiterPriority());
        assertEquals((Math.pow(0.8, 4) + ( ( 0.8 - Math.pow(0.8, 4) ) * 2 ) / ( 4 - 1 )), ((Waiter) a).getWaiterPriority());
        assertEquals((((Waiter) a).getWaiterPriority() + ( ( 0.8 - Math.pow(0.8, 4) ) * 2 ) / ( 4 - 1 )), ((Waiter) b).getWaiterPriority());
        assertEquals((((Waiter) b).getWaiterPriority() + ( ( 0.8 - Math.pow(0.8, 4) ) * 2 ) / ( 4 - 1 )), ((Waiter) c).getWaiterPriority());

        b.removeStaff();

        assertEquals(Math.pow(0.8, 3), ((Waiter) staff).getWaiterPriority());
        assertEquals((Math.pow(0.8, 3) + ( ( 0.8 - Math.pow(0.8, 3) ) * 2 ) / ( 3 - 1 )), ((Waiter) a).getWaiterPriority());
        assertEquals((((Waiter) a).getWaiterPriority() + ( ( 0.8 - Math.pow(0.8, 3) ) * 2 ) / ( 3 - 1 )), ((Waiter) c).getWaiterPriority());

        a.removeStaff();
        c.removeStaff();
    }

    /**
     * Test Staff Factory
     */
    @Test
    void testStaffFactory() {
        assertInstanceOf(Waiter.class, StaffFactory.getStaff("waiter", "John", 2));
    }
}
