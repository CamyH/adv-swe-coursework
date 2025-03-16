package Customer;

import java.util.UUID;

/**
 * Customer class
 * Stores details about a customer
 */
public class Customer {
    private final UUID customerId;
    private final String firstName;
    private final String lastName;

    /**
     * Constructs a new Customer with a generated UUID
     * @param firstName the customer's first name
     * @param lastName the customer's last name
     */
    public Customer(String firstName, String lastName) {
        this.customerId = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Gets the customer's unique identifier.
     * @return the customer's {@link UUID}
     */
    public UUID getCustomerId() {
        return customerId;
    }

    /**
     * Gets the customer's first name.
     * @return the customer's first name {@link String}
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets the customer's last name.
     * @return the customer's last name {@link String}
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets the full name of the customer, combining the first and last name.
     * @return the customer's full name as a {@link String}
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
