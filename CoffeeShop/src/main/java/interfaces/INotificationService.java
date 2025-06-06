package interfaces;

import server.ClientService;

import java.util.UUID;

/**
 * INotificationService provides methods for sending notifications to clients
 * @author Cameron Hunt
 */
public interface INotificationService {

    /**
     * Adds an observer
     * Observers will receive updates when relevant order events occur
     *
     * @param observer The observer to add
     */
    void addObserver(OrderObserver observer);

    /**
     * Removes an observer
     * The observer will no longer receive notifications
     *
     * @param observer The observer to remove.
     */
    void removeObserver(OrderObserver observer);

    /**
     * Sends a notification when an order is received and starts processing
     *
     * @param orderID The unique ID of the order
     */
    void sendOrderProcessingNotification(UUID orderID, ClientService clientService);

    /**
     * Sends a notification when an error occurs in order processing
     *
     * @param orderID The unique ID of the order
     */
    void sendOrderErrorNotification(UUID orderID, ClientService clientService);

    /**
     * Sends a notification when an order has been successfully completed
     *
     * @param orderID The unique ID of the order
     */
    void sendOrderCompleteNotification(UUID orderID, ClientService clientService);
}