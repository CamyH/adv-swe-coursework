package interfaces;

import client.GUI;
import item.ItemList;

import java.io.*;
import java.net.URISyntaxException;

/**
 * Abstract Implementation of File Manager
 * Contains the readFile code to reduce duplication
 * @param <T> readFile return type
 * @param <R> writeToFile param type
 */
public abstract class AbstractFileManager<T, R> implements FileManager<T, R> {
    protected final String fileName;

    protected ItemList menu;

    protected File filePath;

    /**
     * Constructor
     * @param fileName of the file to operate on
     */
    public AbstractFileManager(String fileName) {
        this.fileName = fileName;

        String jarDirPath = "";
        try {
            jarDirPath = new File(GUI.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
        } catch (URISyntaxException e) {
            System.err.println(e.getMessage());
        }

        File jarFilePath = new File(jarDirPath, "files/"+fileName);

        File ideFilePath = new File("src/main/java/files/", fileName);

        filePath = null;

        if (jarFilePath.exists()) {
            filePath = jarFilePath;
        }
        else if (ideFilePath.exists()) {
            filePath = ideFilePath;
        }
    }

    /**
     * Constructor
     * @param fileName of the file to operate on
     * @param menu the menu
     */
    public AbstractFileManager(String fileName, ItemList menu) {
        this(fileName);
        this.menu = menu;
    }

    /**
     * Reads from a given file
     *
     * @return an instance of type T representing the file content
     * @throws IOException for general IO exceptions
     */
    @Override
    public void readFile() throws FileNotFoundException {
        StringBuilder fileContents = new StringBuilder();

        if (filePath != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    fileContents.append(line);
                    fileContents.append(System.lineSeparator());
                }
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }
        }
        else {
            throw new FileNotFoundException("File Path is null");
        }

        ingestFileContents(fileContents);
    }

    /**
     * Write to a given file
     *
     */
    @Override
    public abstract void writeToFile() throws IOException;

    /**
     * Handle file content and assign to appropriate lists
     * @param fileContents the content of the file to ingest
     * @return the ingested file contents of the correct type
     */
    protected abstract void ingestFileContents(StringBuilder fileContents);

    /**
     * Closes this resource, relinquishing any underlying resources.
     * This method is invoked automatically on objects managed by the
     * {@code try}-with-resources statement.
     *
     * @throws Exception if this resource cannot be closed
     * @apiNote While this interface method is declared to throw {@code
     * Exception}, implementers are <em>strongly</em> encouraged to
     * declare concrete implementations of the {@code close} method to
     * throw more specific exceptions, or to throw no exception at all
     * if the close operation cannot fail.
     *
     * <p> Cases where the close operation may fail require careful
     * attention by implementers. It is strongly advised to relinquish
     * the underlying resources and to internally <em>mark</em> the
     * resource as closed, prior to throwing the exception. The {@code
     * close} method is unlikely to be invoked more than once and so
     * this ensures that the resources are released in a timely manner.
     * Furthermore it reduces problems that could arise when the resource
     * wraps, or is wrapped, by another resource.
     *
     * <p><em>Implementers of this interface are also strongly advised
     * to not have the {@code close} method throw {@link
     * InterruptedException}.</em>
     * <p>
     * This exception interacts with a thread's interrupted status,
     * and runtime misbehavior is likely to occur if an {@code
     * InterruptedException} is {@linkplain Throwable#addSuppressed
     * suppressed}.
     * <p>
     * More generally, if it would cause problems for an
     * exception to be suppressed, the {@code AutoCloseable.close}
     * method should not throw it.
     *
     * <p>Note that unlike the {@link Closeable#close close}
     * method of {@link Closeable}, this {@code close} method
     * is <em>not</em> required to be idempotent.  In other words,
     * calling this {@code close} method more than once may have some
     * visible side effect, unlike {@code Closeable.close} which is
     * required to have no effect if called more than once.
     * <p>
     * However, implementers of this interface are strongly encouraged
     * to make their {@code close} methods idempotent.
     */
    @Override
    public void close() throws Exception {
        // Not used here
    }
}
