package message;

/**
 * Enum representing various messages that can be sent relating to order processing
 * Each message body has a custom display name associated with it
 */
public enum MessageContent {
    /**
     * Indicates that the order has been received.
     */
    Received("Received"),

    /**
     * Indicates that the order is being processed.
     */
    Processing("Processing"),

    /**
     * Indicates that the order has been completed.
     */
    COMPLETE("Complete");

    /**
     * The custom display name for the message type.
     */
    public final String displayName;

    /**
     * Constructor for creating a message body with a custom display name
     * @param displayName The custom display name for the message type
     */
    MessageContent(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the custom display name of the message body.
     * @return The display name associated with the message type.
     */
    @Override
    public String toString() {
        return displayName;
    }
}