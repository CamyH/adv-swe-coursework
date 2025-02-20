package Item;

import Interface.EntityList;

import java.util.HashMap;

public class ItemList implements EntityList {
    private HashMap<String, Integer> items = new HashMap<>();

    public ItemList() {};

    @Override
    public void add(Object item) {}

    @Override
    public void remove(Object ID) {}

    public HashMap getMenu() {
        return items;
    }

    public String getCategory(String itemID) {
        return "";
    }

    public float getCost(String itemID) {
        return 0;
    }

    public String getDescription(String itemID) {
        return "";
    }

    public void setCost(String itemID, float cost) {

    }

}