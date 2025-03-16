package Message;

import java.util.UUID;

/**
 * Represents a message with a specific type and sender.
 */
public class Message {
    private final String message;
    private final MessageType messageType;
    private final UUID customerId;

    /**
     * Constructs a Message with the specified details.
     *
     * @param message The content of the message.
     * @param messageType The type of the message.
     * @param customerId The ID of the customer sending the message.
     */
    public Message(String message, MessageType messageType, UUID customerId) {
        this.message = message;
        this.messageType = messageType;
        this.customerId = customerId;
    }

    /**
     * Returns the content of the message.
     * @return The message content.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns the type of the message.
     * @return The message type.
     */
    public MessageType getMessageType() {
        return messageType;
    }

    /**
     * Returns the customer ID associated with the message.
     * @return The customer ID.
     */
    public UUID getCustomerId() {
        return customerId;
    }

    /**
     * Provides a string representation of the message, including message content,
     * type, and customer ID.
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