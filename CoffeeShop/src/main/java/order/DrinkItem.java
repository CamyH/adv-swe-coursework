package order;

import server.ClientService;

import java.util.Objects;
import java.util.UUID;

public record DrinkItem(UUID orderID, String drinkItem, ClientService clientService) {
    /**
     * Constructor
     * @param orderID the orderID cannot be null
     * @param drinkItem the foodItem cannot be null
     */
    public DrinkItem {
        Objects.requireNonNull(orderID);
        Objects.requireNonNull(drinkItem);
    }
}
