package message;

import java.io.Serializable;
import java.util.UUID;

/**
 * Represents a message with a specific type and sender
 */
public record Message(UUID customerId, String message, MessageType messageType) implements Serializable {

    /**
     * Returns a string representation of the message, including message content,
     * type, and customer Id.
     *
     * @return A string representation of the message.
     */
    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", customerId=" + customerId +
                ", messageType=" + messageType + '}';
    }
}