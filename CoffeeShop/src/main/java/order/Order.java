package order;
import customer.Customer;
import exceptions.InvalidItemIDException;
import exceptions.InvalidOrderException;
import item.Item;
import item.ItemCategory;
import item.ItemFileReader;
import item.ItemList;
import server.ClientService;
import utils.Discount;
import utils.DiscountDataStructure;
import utils.SoundPlayer;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Represents an order placed by a customer.
 * Contains details about the items in the order, the customer, the timestamp, and the total cost.
 * The order may also have an associated discount.
 *
 * @author Mohd Faiz
 */

public class Order implements Serializable {
    /** Unique identifier for the order */
    private final UUID orderID;

    /** Unique identifier for the customer who placed the order */
    private final UUID customerID;

    /** Timestamp representing when the order was placed */
    private final LocalDateTime timestamp;

    /** List of item IDs associated with the order */
    private final ArrayList<String> orderDetails;

    /** Menu containing available items and their associated costs */
    private ItemList menu;

    /** The total cost of the order before any discount is applied */
    private double totalCost;

    /** The total discounted cost of the order */
    private double discountedCost;

    private boolean onlineStatus;
    private ClientService clientService;
    private final Customer customer;

    private Map<Set<ItemCategory>, Discount> discountsMap = new HashMap<>();

    /** Constructor for creating an Order with only the menu */
    public Order() throws InvalidOrderException {
        // Initialize fields
        this.customerID = UUID.randomUUID();
        this.orderID = UUID.randomUUID();
        this.customer = new Customer();
        this.timestamp = LocalDateTime.now();
        this.orderDetails = new ArrayList<>();
        this.menu = ItemList.getInstance();
        this.onlineStatus = false;
        this.clientService = null;

        if (menu.getItemCount() == 0) {
            ItemList.resetInstance();

            try (ItemFileReader itemReader = new ItemFileReader("menu.txt")) {
                itemReader.readFile();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            this.menu = ItemList.getInstance();

            if (menu.getItemCount() == 0) {
                throw new InvalidOrderException("Menu cannot be null.");
            }
        }

        discountsMap = Discount.createDiscounts();

        calculateTotalCost();
        calculateDiscountedCost();
    }

    /**
     * Constructor for reading in Order File
     * @param orderID order ID string
     * @param customerID customer ID string
     * @param timestamp timestamp string
     * @param menu items within the order
     * @throws InvalidOrderException if any params are incorrect
     */
    public Order(String orderID,
                 String customerID,
                 String customerName,
                 LocalDateTime timestamp,
                 ArrayList<String> orderDetails,
                 ItemList menu,
                 boolean onlineStatus) throws InvalidOrderException {

        this.orderID = UUID.fromString(orderID);
        this.customerID = UUID.fromString(customerID);
        this.customer = new Customer();
        this.timestamp = timestamp;
        this.orderDetails = orderDetails;
        this.onlineStatus = onlineStatus;
        this.clientService = null;

        if (menu == null) {
            throw new InvalidOrderException("Menu cannot be null.");
        }
        if (menu.getMenu().isEmpty()) {
            throw new InvalidOrderException("Menu cannot be null.");
        }
        this.menu = menu;

        this.customer.setName(customerName);

        discountsMap = Discount.createDiscounts();

        calculateTotalCost();
        calculateDiscountedCost();
    }

    /**
     * Adds an item to the order.
     * The total cost is recalculated after the item is added.
     *
     * @param itemID The ID of the item to add to the order
     * @throws InvalidItemIDException if the item ID is invalid
     */
    public void addItem(String itemID) throws InvalidItemIDException{
        if (!menu.itemExists(itemID)) {
            throw new InvalidItemIDException("Invalid Item ID: " + itemID);
        }

        if (orderDetails.add(itemID)) {
            SoundPlayer.playSound(SoundPlayer.SoundType.NEW_ITEM_ADD);
            calculateTotalCost();  // Recalculate the total cost after adding an item
            calculateDiscountedCost();
        }
    }

    /**
     * Removes an item to the order.
     * The total cost is recalculated after the item is removed.
     *
     * @param itemID The ID of the item to remove from the order
     */
    public boolean removeItem(String itemID) {
        if (orderDetails.remove(itemID)) {
            calculateTotalCost();
            calculateDiscountedCost();
            return true;
        }
        return false;
    }

    /**
     * Removes the last item added to the order.
     * The total cost is recalculated after the item is removed.
     *
     */
    public boolean removeLastItem() {
        if (orderDetails.isEmpty()) return false;

        orderDetails.removeLast();

        calculateTotalCost();
        calculateDiscountedCost();

        return true;
    }

    /**
     * Calculates the total cost of the order based on the items in the order.
     * The cost of each item is fetched from the menu.
     */
    private void calculateTotalCost() {
        totalCost = 0.0;      //assuming default cost here
        for (String itemID : orderDetails) {
            // Assuming each item has a fixed cost, and the cost is added up (we can modify according to need)
            totalCost += menu.getCost(itemID); // Can change this method according to need
        }
    }

    /**
     * Calculates the discounted cost
     */
    private void calculateDiscountedCost() {
        discountedCost = totalCost;

        ArrayList<String> myOrderDetails = new ArrayList<>(orderDetails);

        DiscountDataStructure structure = new DiscountDataStructure();

        // First checking discounts for daily special items
        Item dailySpecial = Discount.getDailySpecialItem();
        if (dailySpecial != null) {
            for (int i = 0; i < myOrderDetails.size(); i++) {
                if (myOrderDetails.get(i).equals(dailySpecial.getItemID())) {
                    structure.addEntry(Discount.DAILY_SPECIAL, i, i);
                }
            }
        }

        // After checking for Daily Specials, checking for other discounts

        /** Nested for loop to compare each item to another to check for available discounts */
        for (int i = 0; i < myOrderDetails.size(); i++) {
            for (int j = i + 1; j < myOrderDetails.size(); j++) {
                if (menu.getCategory(myOrderDetails.get(i)) != menu.getCategory(myOrderDetails.get(j))) {
                    /** If a discount is found this will then be added to the custom DiscountDataStructure */
                    Discount d = discountsMap.get(Set.of(menu.getCategory(myOrderDetails.get(i)), menu.getCategory(myOrderDetails.get(j))));
                    if (d != null) {
                        structure.addEntry(d, i, j);
                    }
                }
            }
        }

        /** Removes and looks at the first entry in the discount data structure (ie the first discount to apply) */
        ArrayList<Object> s = structure.removeEntry();

        while (s != null) {
            Discount discount = (Discount) s.get(0);

            int index1 = (Integer) s.get(1);
            int index2 = (Integer) s.get(2);

            if (discount == Discount.DAILY_SPECIAL) {
                if (index1 < myOrderDetails.size()) {
                    discountedCost -= (menu.getCost(myOrderDetails.get(index1)) - ((Discount) s.get(0)).calculateDiscount(menu.getCost(myOrderDetails.get(index1))));
                    myOrderDetails.remove(index1);
                }
            }
            else {
                // Process the higher index first to avoid index shifting issues
                int higherIndex = Math.max(index1, index2);
                int lowerIndex = Math.min(index1, index2);

                if (higherIndex < myOrderDetails.size() && lowerIndex < myOrderDetails.size()) {
                    // Apply discount to both items
                    discountedCost -= (menu.getCost(myOrderDetails.get(higherIndex)) -
                            discount.calculateDiscount(menu.getCost(myOrderDetails.get(higherIndex))));
                    discountedCost -= (menu.getCost(myOrderDetails.get(lowerIndex)) -
                            discount.calculateDiscount(menu.getCost(myOrderDetails.get(lowerIndex))));

                    // Remove items starting from higher index first
                    myOrderDetails.remove(higherIndex);
                    myOrderDetails.remove(lowerIndex);
                }
            }
            /** This is then repeated for anymore available discounts */
            s = structure.removeEntry();
        }
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
    public UUID getCustomerID() {
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
        return discountedCost;   //can modify later according to need
    }

    /**
     * Method to change order to online status
     *
     * This implementation will probably be changed later but useful for me for initial testing
     */
    public void setOnlineStatus() {
        this.onlineStatus = true;
    }

    /**
     * Method to return online status of order
     *
     * @return boolean representing if the order was made online
     */
    public boolean getOnlineStatus() {
        return onlineStatus;
    }

    public ClientService getClientService() {
        return clientService;
    }

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * Checks if the given order is invalid
     * An order is considered invalid if it is {@code null} or if its order ID is {@code null}
     *
     * @param order the order to check
     * @return {@code true} if the order is null or has a null order ID, {@code false} otherwise
     */
    public static boolean isInvalidOrder(Order order) {
        return order == null || order.getOrderID() == null;
    }

    /**
     * Checks if the order details are null or empty
     *
     * @param order the order to check
     * @return true if the order details are null or empty, false otherwise
     */
    public static boolean isOrderDetailsNullOrEmpty(Order order) {
        return order.getDetails() == null || order.getDetails().isEmpty();
    }

    /**
     * Get the customer's name
     * @return the name of the customer as a string
     */
    public String getCustomerName() {
        return customer.getName();
    }
}