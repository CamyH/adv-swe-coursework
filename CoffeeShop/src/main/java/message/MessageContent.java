package message;

/**
 * Enum representing various messages that can be sent relating to order processing
 * Each message body has a custom display name associated with it
 */
public enum MessageContent {
    /**
     * Indicates that the order has been received
     */
    RECEIVED("Received"),

    /**
     * Indicates that the order is being processed
     */
    PROCESSING("Processing"),

    /**
     * Indicates that the order has been completed
     */
    COMPLETE("Complete"),

    /**
     * Indicates that there has been an error with the order
     */
    ERROR("There has been an error processing order");

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

    /**
     * Returns the correct {@link MessageContent} based on the {@link MessageType}
     * @param type the {@link MessageType}
     * @return the {@link MessageContent} as a string
     */
    public String fromMessageType(MessageType type) {
        return switch (type) {
            case ORDER_RECEIVED -> RECEIVED.toString();
            case PROCESSING_ORDER -> PROCESSING.toString();
            case ORDER_COMPLETE -> COMPLETE.toString();
            case ERROR -> ERROR.toString();
        };
    }
}