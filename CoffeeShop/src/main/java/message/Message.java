package message;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a message with a specific type and sender
 */
public record Message(UUID messageId, UUID customerId, String message, MessageType messageType) implements Serializable {
    /**
     * Constructor to generate a random messageId
     * @param customerId Id of the customer sending the message
     * @param message the message body
     * @param messageType the message type
     */
    public Message(UUID customerId, String message, MessageType messageType) {
        this(UUID.randomUUID(), customerId, message, messageType);
    }
    /**
     * Constructor
     * @param customerId the customerId cannot be null
     * @param message the message cannot be null
     * @param messageType the message cannot be null
     */
    public Message {
        Objects.requireNonNull(customerId);
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
        return "{" +
                "messageId=" + messageId +
                ", message='" + message + '\'' +
                ", customerId=" + customerId +
                ", messageType=" + messageType + '}';
    }
}