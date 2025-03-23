package server;

import item.ItemList;
import order.OrderList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PipedOutputStream;
import java.io.PipedInputStream;
import java.util.concurrent.CopyOnWriteArraySet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for logic within the Server Class
 */
public class ServerTest {
    private static Server server;

    @BeforeAll
    public static void setup() {
        server = new Server();
    }

    @Test
    public void testGetConnectionSingletonInstanceIsNotNull() throws IOException {
        CopyOnWriteArraySet<ObjectOutputStream> connectionsSingleton = Server.getConnectionsSingletonInstance();

        assertNotNull(connectionsSingleton, "ConnectionsSingleton instance should not be null");

        PipedOutputStream mockSocketOutput = new PipedOutputStream();
        // Not used but the output and input popes must be connected for output to work
        PipedInputStream mockSocketInput = new PipedInputStream(mockSocketOutput);
        ObjectOutputStream mockOutputStream = new ObjectOutputStream(mockSocketOutput);

        connectionsSingleton.add(mockOutputStream);

        assertEquals(connectionsSingleton, Server.getConnectionsSingletonInstance());
    }
}