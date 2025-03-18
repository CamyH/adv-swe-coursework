package workers;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit tests for Staff class and Staff Factory
 *
 * @author Fraser Holman
 */
class StaffTest {

    Staff staff;

    @BeforeEach
    void setUp() {
        staff = StaffFactory.getStaff("barista", "Bob", 1);
    }

    /**
     * Test getWorkerName method
     */
    @Test
    void testGetWorkerName() {
        assertEquals("Bob", staff.getWorkerName());
    }

    /**
     * Test getExperience method
     */
    @Test
    void testGetExperience() {
        assertEquals(1, staff.getExperience());
    }

    /**
     * Test setExperience method
     */
    @Test
    void testSetExperience() {
        staff.setExperience(0.2);
        assertEquals(0.2, staff.getExperience(), "Experience level was not updated correctly.");
    }

    /**
     * Test Staff Factory
     */
    @Test
    void testStaffFactory() {
        assertInstanceOf(Barista.class, StaffFactory.getStaff("barista", "John", 0.2));
    }
}
