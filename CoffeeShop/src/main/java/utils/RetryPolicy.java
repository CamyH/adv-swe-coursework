package utils;

import interfaces.ThrowingRunnable;
import logs.CoffeeShopLogger;

import java.util.function.Consumer;

/**
 * RetryPolicy provides a utility for executing tasks with retry logic
 * It attempts to run a given {@link ThrowingRunnable} multiple times if it fails
 *
 * @author Cameron Hunt
 */
public class RetryPolicy {
    private static final CoffeeShopLogger logger = CoffeeShopLogger.getInstance();
    /**
     * Constructor
     */
    private RetryPolicy() {}

    /**
     * Run a task with retry enabled
     * @param task the task to run
     * @param maxRetries the number of retries to do before
     * throwing a runtime exception
     * @throws RuntimeException if retry fails
     */
    public static void retryOnFailure(ThrowingRunnable task, int maxRetries) {
        retry(task, maxRetries, (e) -> {
            // This tells the Consumer what to do if the rery
            // logic fails, in this case we want to throw an exception
            // below we want to log
            throw new RuntimeException("Could not run after " + maxRetries + " failed attempts", e);
        });
    }

    /**
     * Run a task with retry logic and log the failure instead of throwing
     * @param task the task to run
     * @param maxRetries max number of retries
     */
    public static void retryAndLog(ThrowingRunnable task, int maxRetries) {
        retry(task, maxRetries, (e) ->
                logger.logWarning("Could not run after "
                        + maxRetries
                        + " failed attempts. Exception: "
                        + e.getClass() + " \n"
                        + e.getCause() + " \n"
                        + e.getMessage()));
    }

    /**
     * Run a task with retry logic and log the failure instead of throwing
     * @param task the task to run
     * @param maxRetries max number of retries
     */
    public static void retryAndCustomLog(ThrowingRunnable task, int maxRetries, String exceptionMessage) {
        retry(task, maxRetries, (e) ->
                logger.logSevere("Could not retry after "
                        + maxRetries
                        + " failed attempts. Exception: "
                        + e.getMessage()
                        + ". \n"
                        + exceptionMessage));
    }

    /**
     * Retry Loop
     * Consumer<Throwable> here allows us to pass in a custom action
     * to perform if the retry logic fails
     * @param task the task to run
     * @param maxRetries the max number of retries allowed
     * @param onFailure the custom action to perform on failure
     */
    private static void retry(ThrowingRunnable task, int maxRetries, Consumer<Throwable> onFailure) {
        int attempts = 0;
        Exception exception = null;
        while (attempts < maxRetries) {
            try {
                task.run();
                return;
            } catch (Exception e) {
                exception = e;
                attempts++;
            }
        }

        onFailure.accept(exception);
    }
}
