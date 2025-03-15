package interfaces;

import java.io.IOException;

/**
 * Defines contracts for reading to and writing from a file.
 * Use of Stream is recommended as the interface extends AutoCloseable
 * So far AutoCloseable is only used in the tests for cleanup
 * @author Cameron Hunt
 */
public interface FileManager<T, R> extends AutoCloseable {
    /**
     * Reads from a given file
     * @return an instance of type T representing the file content
     * @exception IOException for general IO exceptions
     */
    void readFile() throws IOException;

    /**
     * Write to a given file
     */
    void writeToFile() throws IOException;
}
