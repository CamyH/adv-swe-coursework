package workers;

import static org.junit.jupiter.api.Assertions.*;

import interfaces.INotificationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Collection;

/**
 * JUnit tests for Staff class and Staff Factory
 *
 * @author Fraser Holman
 */
class StaffTest {
    private Staff staff;
    @Mock
    private INotificationService notificationService;

    @BeforeEach
    void setUp() {
        staff = StaffFactory.getStaff("barista", "Bob", 1, notificationService);
    }

    @AfterEach
    void tearDown() {
        staff.removeStaff();
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
        staff.setExperience(2);
        assertEquals(2, staff.getExperience(), "Experience level was not updated correctly.");
    }
}
