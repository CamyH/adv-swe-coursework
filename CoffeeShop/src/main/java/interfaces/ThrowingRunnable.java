package interfaces;

/**
 * ThrowingRunnable Interface
 * Allows for throwing a checked exception within
 * the RetryPolicy lambda expression
 * <a href="https://www.javadoc.io/doc/pl.touk/throwing-function/1.2/pl/touk/throwing/ThrowingRunnable.html">JavaDoc.io</a>
 */
@FunctionalInterface
public interface ThrowingRunnable {
    void run() throws Exception;
}
