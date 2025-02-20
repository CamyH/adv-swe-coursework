package Item;

import Interface.EntityList;

import java.util.HashMap;

public class ItemList implements EntityList {
    private HashMap<String, Object> items = new HashMap<>();

    public ItemList() {};

    @Override
    public void add(Object item) {
        if (item.getItemID() != null) items.put(item.getItemID(), item);
        else throw new NullPointerException("Item ID is null");
    }

    @Override
    public void remove(Object ID) {
        if (ID instanceof String) {
            if (items.containsKey(ID)) {
                items.remove(ID);
                return;
            }
            throw new IllegalArgumentException(ID + " is not a valid item ID");
        }
        throw new IllegalArgumentException(ID + " is not of type String");
    }

    public HashMap<String, Object> getMenu() {
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