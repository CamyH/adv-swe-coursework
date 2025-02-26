package exceptions;

/**
 * Throws an error if there is any invalid order.
 *
 * @author Mohd Faiz
 */
public class InvalidOrderException extends Exception {
    /**
     * Constructor accepting the exception message
     */
    public InvalidOrderException(String message) {
        super(message);
    }

}
