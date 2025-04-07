package message;

/**
 * Enum representing various types of messages related to order processing.
 * Each message type has a custom display name associated with it.
 */
public enum MessageType {
    /**
     * Indicates that the order has been received.
     */
    ORDER_RECEIVED("Order Received"),

    /**
     * Indicates that the order is being processed.
     */
    PROCESSING_ORDER("Processing Order"),

    /**
     * Indicates that the order has been completed.
     */
    ORDER_COMPLETE("Order Complete"),

    /**
     * Indicates an error occurred with the order.
     */
    ERROR("Something has gone wrong with the order");

    /**
     * The custom display name for the message type.
     */
    public final String displayName;

    /**
     * Constructor for creating a message type with a custom display name.
     * @param displayName The custom display name for the message type.
     */
    MessageType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the custom display name of the message type.
     * @return The display name associated with the message type.
     */
    @Override
    public String toString() {
        return displayName;
    }
}