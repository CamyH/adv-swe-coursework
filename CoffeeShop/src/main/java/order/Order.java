package order;
import exceptions.InvalidItemIDException;
import exceptions.InvalidOrderException;
import item.ItemList;
import utils.Discount;
// import ItemList class here once defined
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Represents an order placed by a customer.
 * Contains details about the items in the order, the customer, the timestamp, and the total cost.
 * The order may also have an associated discount.
 *
 * @author Mohd Faiz
 */

public class Order {
    /** Unique identifier for the order */
    private final UUID orderID;

    /** Unique identifier for the customer who placed the order */
    private final String customerID;

    /** Timestamp representing when the order was placed */
    private final LocalDateTime timestamp;

    /** List of item IDs associated with the order */
    private final ArrayList<String> orderDetails;

    /** Menu containing available items and their associated costs */
    private final ItemList menu;

    /** The total cost of the order before any discount is applied */
    private double totalCost;

    /** Discount object representing the discount applied to the order */
    private final Discount discount;

    /** Constructor for creating an Order with only the menu */
    public Order(ItemList menu) throws InvalidOrderException {

        // Validate menu: Menu must not be null
        if (menu == null) {
            throw new InvalidOrderException("Menu cannot be null.");
        }

        // Initialize fields
        this.customerID = UUID.randomUUID().toString(); // Generate a random UUID for customer ID
        this.orderID = UUID.randomUUID();  // Generate a unique orderID
        this.timestamp = LocalDateTime.now(); // Set the current timestamp
        this.orderDetails = new ArrayList<>(); // Initialize order details as an empty list
        this.menu = menu;
        this.totalCost = 0.0;   // Initialize the total cost to 0
        this.discount = Discount.DISCOUNT0; // Set a default discount for the order
    }

    /**
     * Adds an item to the order.
     * The total cost is recalculated after the item is added.
     *
     * @param itemID The ID of the item to add to the order
     */
    public void addItem(String itemID) {
        orderDetails.add(itemID);
        totalCost = calculateTotalCost();  // Recalculate the total cost after adding an item
    }

    /**
     * Calculates the total cost of the order based on the items in the order.
     * The cost of each item is fetched from the menu.
     * @return The total cost of the order
     */
    private double calculateTotalCost() {
        double cost = 0.0;      //assuming default cost here
        for (String itemID : orderDetails) {
            // Assuming each item has a fixed cost, and the cost is added up (we can modify according to need)
            try {
                cost += menu.getCost(itemID); // Can change this method according to need
            }
            catch (InvalidItemIDException e) {
                System.out.println(e.getMessage());
            }
        }
        return cost;
    }

    /**
     * Returns the unique order ID.
     *
     * @return The UUID of the order
     */
    public UUID getOrderID() {
        return orderID;
    }

   /**
    * Returns the customer ID associated with the order.
    *
    * @return The customer ID as a string
    */
    public String getCustomerID() {
        return customerID;
    }

   /**
    * Returns the timestamp when the order was placed.
    *
    * @return The timestamp as a LocalDateTime object
    */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
    * Returns the order details.
    *
    * @return the order details
    */
    public ArrayList<String> getDetails() {
        return orderDetails;
    }

    /**
     * Returns the total cost of the order before applying any discounts.
     *
     * @return The total cost of the order
     */
    public double getTotalCost() {
        return totalCost;
    }

   /**
    * Returns the total cost of the order after applying any applicable discounts.
    * The discount is calculated using the Discount object associated with the order.
    * @return The discounted cost of the order
    */
    public double getDiscountedCost() {
        return totalCost - discount.calculateDiscount(totalCost);   //can modify later according to need
    }
}
