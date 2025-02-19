package utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Discount utility class
 */
public class DiscountTest {
    private double priceToDiscount;
    private Discount discount;

    /**
     * Setup the Discount to use and
     * the price to be discounted
     */
    @BeforeEach
    void setup(){
        priceToDiscount = 10;
        discount = Discount.DISCOUNT50;
    }

    /**
     * Calculate the new price after applying a 50%
     * discount
     */
    @Test
    void testCalculateDiscount() {
        assertEquals(priceToDiscount / 2, discount.calculateDiscount(priceToDiscount));
    }
}
