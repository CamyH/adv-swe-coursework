package message;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a message with a specific type and sender
 */
public record Message(UUID messageID, UUID customerID, UUID orderID, String message, MessageType messageType) implements Serializable {
    /**
     * Constructor to generate a random messageId
     * @param customerId ID of the customer receiving the message
     * @param orderID the ID of the order
     * @param message the body of the message
     * @param messageType the message type
     */
    public Message(UUID customerId, UUID orderID, String message, MessageType messageType) {
        this(UUID.randomUUID(), customerId, orderID, message, messageType);
    }
    /**
     * Constructor
     * @param customerID the customerID cannot be null
     * @param orderID the orderID cannot be null
     * @param message the message cannot be null
     * @param messageType the message cannot be null
     */
    public Message {
        Objects.requireNonNull(customerID);
        Objects.requireNonNull(orderID);
        Objects.requireNonNull(message);
        Objects.requireNonNull(messageType);
    }
    /**
     * Returns a string representation of the message, including message content,
     * type, and customer Id.
     *
     * @return A string representation of the message.
     */
    @Override
    public String toString() {
        return "Order: " + orderID + " " + message;
    }
}