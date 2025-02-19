package utils;

/**
 * Calculates the discount for the current price
 */
public enum Discount {
    DISCOUNT10(10),
    DISCOUNT25(25),
    DISCOUNT50(50),
    DISCOUNT75(75);

    private final int discount;

    Discount(int discount) { this.discount = discount; }

    public double calculateDiscount(double price) { return price - (price * (discount / 100.0)); }
}
