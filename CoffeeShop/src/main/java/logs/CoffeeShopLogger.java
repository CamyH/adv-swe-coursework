package logs;

import java.io.IOException;
import java.util.logging.*;

/**
 * Logger class for the whole coffee shop application.
 *
 * @author Mohd Faiz
 */
public class CoffeeShopLogger {

    private static final Logger logger = Logger.getLogger(CoffeeShopLogger.class.getName());
    private static final CoffeeShopLogger instance = new CoffeeShopLogger();

    private CoffeeShopLogger() {
        try {
            // Create and configure the FileHandler
            createFileHandler();

            // Set the log level
            logger.setLevel(Level.ALL); // Log all levels (INFO, WARNING, SEVERE, etc.)
        } catch (IOException e) {
            System.err.println(e + e.getMessage());
        }
    }

    /**
     * Creates and configures the FileHandler for logging to a file.
     */
    private void createFileHandler() throws IOException {
        // Create the FileHandler
        FileHandler fileHandler = new FileHandler("src/main/java/files/coffee_shop.log", true); // Append mode
        fileHandler.setFormatter(new SimpleFormatter()); // Use a simple text format for logs
        logger.addHandler(fileHandler); // Add the FileHandler to the logger
    }

    public static CoffeeShopLogger getInstance() {
        return instance;
    }

    /**
     * Logs a message at the INFO level
     * Used for general messages
     *
     * @param message the message to log
     */
    public void logInfo(String message) {
        logger.info(message);
    }

    /**
     * Logs a message at the WARNING level
     * Use for potential problems
     *
     * @param message the message to log
     */
    public void logWarning(String message) {
        logger.warning(message);
    }

    /**
     * Logs a message at the SEVERE level
     * Used for critical errors
     *
     * @param message the message to log
     */
    public void logSevere(String message) {
        logger.severe(message);
    }

    /**
     * Logs a message at the SEVERE level
     * Used for critical errors
     * Allows for passing in the exception itself
     *
     * @param message the message to log
     * @param exceptionDetails the extra exception details
     */
    public void logSevere(String message, Exception exceptionDetails) {
        logger.severe(message + exceptionDetails);
    }

    /**
     * Logs a message at the FINE level
     * Used for debugging
     *
     * @param message the message to log
     */
    public void logDebug(String message) {
        logger.fine(message);
    }
}