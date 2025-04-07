package customer;

import java.util.UUID;

/**
 * Customer class
 * Stores details about a customer
 */
public class Customer {
    private final UUID customerId;
    private String name;

    /**
     * Constructs a new Customer with a generated UUID
     */
    public Customer() {
        this.customerId = UUID.randomUUID();
    }

    /**
     * Gets the customer's unique identifier.
     * @return the customer's {@link UUID}
     */
    public UUID getCustomerId() {
        return customerId;
    }

    /**
     * Gets the customer's name.
     * @return the customer's name {@link String}
     */
    public String getName() {
        return name;
    }

    /**
     * Method to set a name for the customer
     *
     * @param name The customer's name
     */
    public void setName(String name) {
        this.name = name;
    }
}
