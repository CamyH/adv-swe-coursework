package exceptions; // Package declaration

/**
 * Exception thrown when an order is retrieved from a staff that has no order.
 *
 * @author Caelan Mackenzie
 */
public class InvalidStaffException extends Exception{
    private final String msg;
    /**
     * Constructs an InvalidStaffException with a specified detail message.
     *
     * @param msg The detail message.
     */
    public InvalidStaffException(String msg) {
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