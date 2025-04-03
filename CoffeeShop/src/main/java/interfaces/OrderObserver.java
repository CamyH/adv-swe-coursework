package interfaces;

import message.MessageType;

import java.util.UUID;

/**
 * Order-specific observer interface
 * Extends Observer to provide order-related notifications
 * @author Cameron Hunt
 */
public interface OrderObserver {
    /**
     * Notifies the observer that an order has been updated
     * @param orderID the ID of the order that has been updated
     */
    void sendOrderNotification(UUID orderID, MessageType message);
}