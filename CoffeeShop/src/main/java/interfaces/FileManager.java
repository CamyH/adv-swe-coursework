package interfaces;

import java.io.FileNotFoundException;

/**
 * Defines contracts for reading to and writing from a file.
 * Use of Stream is recommended as the interface extends AutoCloseable
 * @author Cameron Hunt
 */
public interface FileManager<T> extends AutoCloseable {
    /**
     * Reads from a given file
     * @return an instance of type T representing the file content
     * @exception FileNotFoundException if the file does not exist
     */
    public T readFile() throws FileNotFoundException;

    /**
     * Write to a given file
     * @param report all order information to be used for reporting
     */
    public void writeToFile(T report);
}
