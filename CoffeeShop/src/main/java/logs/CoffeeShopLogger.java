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
        // Check if the FileHandler is already added
        for (Handler handler : logger.getHandlers()) {
            if (handler instanceof FileHandler) {
                return;
            }
        }

        // Create the FileHandler if not added already
        ConsoleHandler consoleHandler = new ConsoleHandler();
        FileHandler fileHandler = new FileHandler("src/main/java/files/coffee_shop.log", true);
        fileHandler.setLevel(Level.ALL);
        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);
        //logger.addHandler(consoleHandler);
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