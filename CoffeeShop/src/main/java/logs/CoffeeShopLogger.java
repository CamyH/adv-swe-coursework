package logs;

import item.ItemList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.*;

/**
 * Logger class for the whole coffee shop application.
 *
 * @author Mohd Faiz
 */
public class CoffeeShopLogger {

    private static final Logger logger = Logger.getLogger(CoffeeShopLogger.class.getName());
    private static CoffeeShopLogger instance;

    private CoffeeShopLogger() {
        // Create and configure the FileHandler
        createFileHandler();

        // Set the log level
        logger.setLevel(Level.ALL); // Log all levels (INFO, WARNING, SEVERE, etc.)
    }

/**
     * Creates and configures the FileHandler for logging to a file.
     */
    private void createFileHandler() {
        // Check if the FileHandler is already added
        for (Handler handler : logger.getHandlers()) {
            if (handler instanceof FileHandler) {
                return;
            }
        }

        // Create the FileHandler if not added already
        ConsoleHandler consoleHandler = new ConsoleHandler();

        FileHandler fileHandler;

        try {
            fileHandler = new FileHandler("src/main/java/files/coffee_shop.log", true);
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            //logger.addHandler(consoleHandler);
        }
        catch (IOException e) {
            try {
                fileHandler = new FileHandler("files/coffee_shop.log", true);
                fileHandler.setLevel(Level.ALL);
                fileHandler.setFormatter(new SimpleFormatter());
                logger.addHandler(fileHandler);
            }
            catch (IOException e1) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static CoffeeShopLogger getInstance() {
        if (instance == null) instance = new CoffeeShopLogger();
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