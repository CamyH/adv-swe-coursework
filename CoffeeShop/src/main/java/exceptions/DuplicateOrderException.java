package exceptions;

/**
 * Exception thrown when a duplicate Order is added to the OrderList
 *
 * @author Fraser Holman
 */
public class DuplicateOrderException extends Exception {
  private final String msg;

  /**
   * Constructs an DuplicateOrderException with a specified detail message.
   *
   * @param message The detail message.
   */
  public DuplicateOrderException(String message) {
        this.msg = message;
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
