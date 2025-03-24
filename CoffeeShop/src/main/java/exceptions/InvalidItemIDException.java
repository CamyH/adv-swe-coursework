package exceptions; // Package declaration

/**
 * Exception thrown when an invalid item ID is encountered.
 *
 * @author Akash Poonia
 */
public class InvalidItemIDException extends Exception{
    private final String msg;

    /**
     * Constructs an InvalidItemIDException with a specified detail message.
     *
     * @param msg The detail message.
     */
    public InvalidItemIDException(String msg) {
        this.msg = msg;
    }

    /**
     * Returns the detail message of this exception.
     *
     * @return The detail message.
     */
    @Override
    public String getMessage() {
        return msg;
    }
}