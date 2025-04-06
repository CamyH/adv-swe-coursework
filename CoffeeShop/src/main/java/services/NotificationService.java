package services;

import interfaces.INotificationService;
import interfaces.OrderObserver;
import logs.CoffeeShopLogger;
import message.MessageType;
import server.ClientService;

import java.util.*;

/**
 * Notification service for sending messages from the server to clients
 * <p>
 * Handles the registration of observers and the dispatching of order-related
 * notifications to the correct client observer
 * </p>
 *
 * @author Cameron Hunt
 */
public class NotificationService implements INotificationService {
    private final List<OrderObserver> observers = Collections.synchronizedList(new ArrayList<>());
    private final CoffeeShopLogger logger = CoffeeShopLogger.getInstance();

    /**
     * Adds an observer to the list of observers.
     * Observers will receive notifications about order events.
     *
     * @param observer The observer to be added.
     */
    @Override
    public void addObserver(OrderObserver observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer from the list of observers.
     * The observer will no longer receive order notifications.
     *
     * @param observer The observer to be removed.
     */
    @Override
    public void removeObserver(OrderObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all registered observers about an order event
     *
     * @param orderID The unique identifier of the order.
     * @param type The type of message to be sent.
     */
    private void notifySpecificObserver(UUID orderID, OrderObserver observer, MessageType type) {
        findObserver(observer)
                .ifPresentOrElse(presentObserver ->
                        presentObserver
                        .sendOrderNotification(orderID, type), () ->
                        logger.logWarning("No observer found for order " + orderID)
                );
    }

    /**
     * Searches for a specific observer in the list of registered observers
     *
     * @param observerToFind The observer to find
     * @return A {@code Optional} containing the matching {@code OrderObserver}
     * if found, or an empty {@code Optional} if no match is present
     */
    private Optional<OrderObserver> findObserver(OrderObserver observerToFind) {
        return observers.stream().filter(observer -> observer.equals(observerToFind)).findFirst();
    }

    /**
     * Sends a notification to all observers when an order is received
     * and starts processing.
     *
     * @param orderID The unique identifier of the order
     */
    @Override
    public void sendOrderProcessingNotification(UUID orderID, ClientService clientService) {
        notifySpecificObserver(orderID, clientService, MessageType.ORDER_RECEIVED);
    }

    /**
     * Sends a notification to all observers when an order is completed.
     *
     * @param orderID The unique identifier of the order.
     */
    @Override
    public void sendOrderCompleteNotification(UUID orderID, ClientService clientService) {
        notifySpecificObserver(orderID, clientService, MessageType.ORDER_COMPLETE);
    }

    /**
     * Sends a notification to all observers when an error occurs
     * during order processing.
     *
     * @param orderID The unique identifier of the order.
     */
    @Override
    public void sendOrderErrorNotification(UUID orderID, ClientService clientService) {
        notifySpecificObserver(orderID, clientService, MessageType.ERROR);
    }
}
