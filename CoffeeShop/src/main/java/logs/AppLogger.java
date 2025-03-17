package logs;

import java.io.IOException;
import java.util.logging.*;

public class AppLogger {

    private static final Logger logger = Logger.getLogger(AppLogger.class.getName());

    static {
        try {
            FileHandler fileHandler = new FileHandler("coffee_shop.log", true); // Append mode
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL); // Set log level as needed
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Info level logging
    public static void logInfo(String message) {
        logger.info(message);
    }

    // Warning level logging
    public static void logWarning(String message) {
        logger.warning(message);
    }

    // Severe level logging
    public static void logSevere(String message) {
        logger.severe(message);
    }
}
