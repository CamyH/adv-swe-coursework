package utils;

import interfaces.ThrowingRunnable;

/**
 * RetryPolicy provides a utility for executing tasks with retry logic.
 * It attempts to run a given {@link Runnable} multiple times if it fails.
 * Example:
 * <pre>{@code
 *     RetryPolicy.retryOnFailure(() -> someMethodThatMightFail(), 3);
 * }</pre>
 *
 * @author Cameron Hunt
 */
public class RetryPolicy {
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

        throw new RuntimeException("Could not retry after " + maxRetries + " attempts failed", exception);
    }
}
