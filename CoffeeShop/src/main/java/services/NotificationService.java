package services;

import interfaces.INotificationService;
import interfaces.OrderObserver;
import message.MessageType;
import server.ClientService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NotificationService implements INotificationService {
    private final List<ClientService> observers = new ArrayList<>();

    /**
     * Adds an observer to the list of observers.
     * Observers will receive notifications about order events.
     *
     * @param observer The observer to be added.
     */
    public void addObserver(ClientService observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer from the list of observers.
     * The observer will no longer receive order notifications.
     *
     * @param observer The observer to be removed.
     */
    public void removeObserver(ClientService observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all registered observers about an order event.
     *
     * @param orderID The unique identifier of the order.
     * @param type The type of message to be sent.
     */
    private void notifyObservers(UUID orderID, MessageType type) {
        for (OrderObserver observer : observers) {
            observer.sendOrderNotification(orderID, type);
        }
    }

    /**
     * Sends a notification to all observers when an order is received
     * and starts processing.
     *
     * @param orderID The unique identifier of the order
     */
    public void sendOrderProcessingNotification(UUID orderID) {
        notifyObservers(orderID, MessageType.ORDER_RECEIVED);
    }

    /**
     * Sends a notification to all observers when an order is completed.
     *
     * @param orderID The unique identifier of the order.
     */
    public void sendOrderCompleteNotification(UUID orderID) {
        notifyObservers(orderID, MessageType.ORDER_COMPLETED);
    }

    /**
     * Sends a notification to all observers when an error occurs
     * during order processing.
     *
     * @param orderID The unique identifier of the order.
     */
    public void sendOrderErrorNotification(UUID orderID) {
        notifyObservers(orderID, MessageType.ERROR);
    }
}
