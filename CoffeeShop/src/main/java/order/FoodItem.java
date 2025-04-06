package order;

import server.ClientService;

import java.util.Objects;
import java.util.UUID;

public record FoodItem(UUID orderID, String foodItem, ClientService clientService) {
    /**
     * Constructor
     * @param orderID the orderID cannot be null
     * @param foodItem the foodItem cannot be null
     */
    public FoodItem {
        Objects.requireNonNull(orderID);
        Objects.requireNonNull(foodItem);
    }
}
