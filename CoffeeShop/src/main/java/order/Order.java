package order;
import utils.Discount;
// import ItemList class here once defined
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Author: Mohd Faiz
 */

public class Order {
    private final UUID orderID;
    private final String customerID;
    private final LocalDateTime timestamp;
    private final ArrayList<String> orderDetails;
    private final ItemList menu;    // ItemList class not defined yet
    private float totalCost;
    private final Discount discount;

    // Constructor for creating an Order with order id, customerID, timestamp, and menu
    public Order(String customerID, UUID orderID, LocalDateTime timestamp, ArrayList<String> orderDetails, ItemList menu, float totalCost, Discount discount) {
        this.customerID = customerID;
        this.orderID = UUID.randomUUID();  // Generate a unique orderID \
        this.timestamp = timestamp;
        this.orderDetails = orderDetails;
        this.menu = menu;
        this.totalCost = totalCost;
        this.discount = discount;
    }

    // Method to add an item to the order
    public void addItem(String itemID) {
        orderDetails.add(itemID);
        totalCost = calculateTotalCost();  // Recalculate the total cost after adding an item
    }

    // Method to calculate the total cost (simple example)
    private float calculateTotalCost() {
        float cost = 0.0f;      //assuming default cost here
        for (String itemID : orderDetails) {
            // Assuming each item has a fixed cost, and the cost is added up (we can modify according to need)
            cost += menu.getCost(itemID);
        }
        return cost;
    }

    // Getter for orderID
    public UUID getOrderID() {
        return orderID;
    }

    // Getter for customerID
    public String getCustomerID() {
        return customerID;
    }

    // Getter for timestamp
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    // Getter for order details (list of item IDs)
    public ArrayList<String> getDetails() {
        return orderDetails;
    }

    // Getter for total cost
    public float getTotalCost() {
        return totalCost;
    }

    // Getter for the cost after applying any discount
    public double getDiscountedCost() {
        return totalCost - discount.calculateDiscount(totalCost);   //can modify later according to need
    }


}
