package logs;

import java.io.File;
import java.io.IOException;
import java.util.logging.*;

/**
 * Logger class for whole coffee shop application.
 *
 * @author Mohd Faiz
 */
public class CoffeeShopLogger {

    private static final Logger logger = Logger.getLogger(CoffeeShopLogger.class.getName());
    private static CoffeeShopLogger instance = new CoffeeShopLogger();

    private CoffeeShopLogger() {
        try {
            // Ensure the logs directory exists
            File logsDir = new File("logs");
            if (!logsDir.exists()) {
                logsDir.mkdirs(); // Create the directory if it doesn't exist as not everyone will have this directory at the start of this application
            }

            FileHandler fileHandler = new FileHandler("logs/coffee_shop.log", true); // Append mode
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);

            /**
             * Keeping this for debugging purpose. Will remove this in the 2nd cycle when all loggers will be implemented
             // Added a ConsoleHandler for debugging
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(consoleHandler);
            */

            logger.setLevel(Level.ALL); // Set log level as needed
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static CoffeeShopLogger getInstance() {
        return instance;
    }

    // Info level logging
    public void logInfo(String message) {
        logger.info(message);
    }

    // Warning level logging
    public void logWarning(String message) {
        logger.warning(message);
    }

    // Severe level logging
    public void logSevere(String message) {
        logger.severe(message);
    }
}