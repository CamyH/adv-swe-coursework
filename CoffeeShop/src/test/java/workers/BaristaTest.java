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
class BaristaTest {

    Staff staff;

    @BeforeEach
    void setUp() {
        staff = StaffFactory.getStaff("barista", "Bob", 1);
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
        assertEquals(0.8, ((Barista) staff).getBaristaPriority());

        Staff a = StaffFactory.getStaff("barista", "Bob", 1);

        assertEquals(Math.pow(0.8, 2), ((Barista) staff).getBaristaPriority());
        assertEquals((Math.pow(0.8, 2) + ( ( 0.8 - Math.pow(0.8, 2) ) * 2 ) / ( 2 - 1 )), ((Barista) a).getBaristaPriority());

        Staff b = StaffFactory.getStaff("barista", "Bill", 0.2);

        assertEquals(Math.pow(0.8, 3), ((Barista) staff).getBaristaPriority());
        assertEquals((Math.pow(0.8, 3) + ( ( 0.8 - Math.pow(0.8, 3) ) * 2 ) / ( 3 - 1 )), ((Barista) a).getBaristaPriority());
        assertEquals((((Barista) a).getBaristaPriority() + ( ( 0.8 - Math.pow(0.8, 3) ) * 2 ) / ( 3 - 1 )), ((Barista) b).getBaristaPriority());

        Staff c = StaffFactory.getStaff("barista", "Fraser", 0.6);

        assertEquals(Math.pow(0.8, 4), ((Barista) staff).getBaristaPriority());
        assertEquals((Math.pow(0.8, 4) + ( ( 0.8 - Math.pow(0.8, 4) ) * 2 ) / ( 4 - 1 )), ((Barista) a).getBaristaPriority());
        assertEquals((((Barista) a).getBaristaPriority() + ( ( 0.8 - Math.pow(0.8, 4) ) * 2 ) / ( 4 - 1 )), ((Barista) b).getBaristaPriority());
        assertEquals((((Barista) b).getBaristaPriority() + ( ( 0.8 - Math.pow(0.8, 4) ) * 2 ) / ( 4 - 1 )), ((Barista) c).getBaristaPriority());

        b.removeStaff();

        assertEquals(Math.pow(0.8, 3), ((Barista) staff).getBaristaPriority());
        assertEquals((Math.pow(0.8, 3) + ( ( 0.8 - Math.pow(0.8, 3) ) * 2 ) / ( 3 - 1 )), ((Barista) a).getBaristaPriority());
        assertEquals((((Barista) a).getBaristaPriority() + ( ( 0.8 - Math.pow(0.8, 3) ) * 2 ) / ( 3 - 1 )), ((Barista) c).getBaristaPriority());

        a.removeStaff();
        c.removeStaff();
    }

    /**
     * Test Staff Factory
     */
    @Test
    void testStaffFactory() {
        assertInstanceOf(Barista.class, StaffFactory.getStaff("barista", "John", 0.2));
    }
}
