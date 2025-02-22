package item;

import utils.ItemCategory;

public class Item {
    private String itemID;
    private ItemCategory category;
    private float cost;
    private String description;

    public Item(String itemID, ItemCategory category, float cost, String description) {
        this.itemID = itemID;
        this.category = category;
        this.cost = cost;
        this.description = description;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getItemID() {
        return itemID;
    }

    public ItemCategory getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public float getCost() {
        return cost;
    }
}
