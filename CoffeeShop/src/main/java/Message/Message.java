package Message;

import java.util.UUID;

/**
 * Represents a message with a specific type and sender
 */
public record Message(String message, MessageType messageType, UUID customerId) {

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
                ", messageType=" + messageType +
                ", customerId=" + customerId + '}';
    }
}