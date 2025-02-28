package exceptions;

/**
 * Throws an error if there is any invalid order.
 *
 * @author Mohd Faiz
 */
public class InvalidOrderException extends Exception {
    private final String message;
    /**
     * Constructor accepting the exception message
     */
    public InvalidOrderException(String message) {
        this.message = message;
    }
    /**
     * Returns the detail message of this exception.
     *
     * @return The detail message.
     */
    @Override
    public String getMessage(){
        return message;
    }
}
