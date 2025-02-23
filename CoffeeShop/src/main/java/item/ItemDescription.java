package item;

/**
 * Enum for storing the different item descriptions
 * Contains a method to return the description
 * @author Cameron Hunt
 */
public enum ItemDescription {
    BACON_ROLL("Bacon Roll"),
    SAUSAGE_ROLL("Sausage Roll"),
    EGG_ROLL("Egg Roll"),
    CHEESE_ROLL("Cheese Roll"),
    BAKED_POTATO("Baked Potato"),
    SANDWICH("Sandwich"),
    PANINI("Panini"),
    SALAD("Salad"),
    CHIPS("Chips"),
    TEA("Tea"),
    ESPRESSO("Espresso"),
    AMERICANO("Americano"),
    LATTE("Latte"),
    CAPPUCCINO("Cappuccino"),
    MOCHA("Mocha"),
    HOT_CHOCOLATE("Hot Chocolate"),
    STILL_WATER("Still Water"),
    SPARKLING_WATER("Sparkling Water"),
    ORANGE_JUICE("Orange Juice"),
    APPLE_JUICE("Apple Juice"),
    LEMONADE("Lemonade"),
    COLA("Cola"),
    FANTA("Fanta"),
    ROOT_BEER("Root Beer"),
    GINGER_BEER("Ginger Beer"),
    CHOCOLATE_BAR("Chocolate Bar"),
    CRISPS("Crisps"),
    POPCORN("Popcorn"),
    PRETZELS("Pretzels"),
    SHORTBREAD("Shortbread"),
    GRANOLA_BARS("Granola Bars"),
    CHEESE("Cheese"),
    CRACKERS("Crackers"),
    CROISSANT("Croissant"),
    DANISH_PASTRY("Danish Pastry"),
    CINNAMON_ROLL("Cinnamon Roll"),
    MACARONS("Macarons"),
    PAIN_AU_CHOCOLAT("Pain au Chocolat");

    private final String displayName;

    ItemDescription(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Return the description name
     * @return the description value
     */
    public String getDisplayName() {
        return displayName;
    }
}