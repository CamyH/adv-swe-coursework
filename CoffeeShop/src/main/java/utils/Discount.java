package utils;

/**
 * Author: Cameron Hunt
 * Enum for storing the discounts available
 * Contains a method to calculate the discount for the current price
 */
public enum Discount {
    DISCOUNT0(0),
    DISCOUNT10(10),
    DISCOUNT25(25),
    DISCOUNT50(50),
    DISCOUNT75(75);

    private final int discount;

    Discount(int discount) { this.discount = discount; }

    /**
     * Calculates the price for the given discount
     * @param price the price to discount
     * @return the discounted price
     */
    public double calculateDiscount(double price) { return price - (price * (discount / 100.0)); }
}
