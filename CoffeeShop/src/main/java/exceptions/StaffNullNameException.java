package exceptions; // Package declaration

/**
 * Exception thrown when a Staff is created with no name.
 *
 * @author Caelan Mackenzie
 */
public class StaffNullNameException extends Exception {
    private final String msg;
    /**
     * Constructs an StaffNullorderException with a specified detail message.
     *
     * @param msg The detail message.
     */
    public StaffNullNameException(String msg) {
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