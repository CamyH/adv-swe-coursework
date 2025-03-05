package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Class represents a custom data structure to deal with adding and removing available discounts
 *
 * Contains a 2-dimensional array list to hold discount information
 *
 * @author Fraser Holman
 */

public class DiscountDataStructure {

    ArrayList<ArrayList<Object>> structure;

    /**
     * Constructor to initialise the array list
     */
    public DiscountDataStructure() {
        structure = new ArrayList<>();
    }

    /**
     * Method adds a new discount entry to the structure data structure
     * The add method will insert the new entry by size of discount
     *
     * @param discount Discount enum related to the pair
     * @param item1 Index that item1 appears in the list of items in the calling method
     * @param item2 Index that item2 appears in the list of items in the calling method
     */
    public void addEntry(Discount discount, int item1, int item2) {
        for (int i=0; i<structure.size(); i++) {
            if (discount.getValue() > ((Discount) structure.get(i).getFirst()).getValue()) {
                structure.add(i, new ArrayList<>(Arrays.asList(discount, item1, item2)));
                return;
            }
        }
        structure.add(structure.size(), new ArrayList<>(Arrays.asList(discount, item1, item2)));
    }

    public ArrayList<Object> removeEntry() {
        if (structure.isEmpty()) return null;

        ArrayList<Object> n = structure.removeFirst();

        for (int i=0; i<structure.size(); i++) {
            if (Objects.equals(structure.get(i).get(1), n.get(1)) || Objects.equals(structure.get(i).get(1), n.get(2))) {
                structure.remove(i);
                i-=1;
            }
            else if (Objects.equals(structure.get(i).get(2), n.get(1)) || Objects.equals(structure.get(i).get(2), n.get(2))) {
                structure.remove(i);
                i-=1;
            }
            else {
                if ((Integer) structure.get(i).get(1) > (Integer) n.get(2)) {
                    structure.get(i).set(1, (Integer) structure.get(i).get(1) - 1);
                }
                if ((Integer) structure.get(i).get(1) > (Integer) n.get(1)) {
                    structure.get(i).set(1, (Integer) structure.get(i).get(1) - 1);
                }
                if ((Integer) structure.get(i).get(2) > (Integer) n.get(2)) {
                    structure.get(i).set(2, (Integer) structure.get(i).get(2) - 1);
                }
                if ((Integer) structure.get(i).get(2) > (Integer) n.get(1)) {
                    structure.get(i).set(2, (Integer) structure.get(i).get(2) - 1);
                }
            }
        }

        return n;
    }
}
