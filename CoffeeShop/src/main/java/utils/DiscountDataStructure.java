package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Class represents a custom data structure to deal with adding and removing available discounts
 *
 * Contains a 2-dimensional array list to hold discount information
 *
 * The data structure only contains discounts and the index of the items to apply the discount to
 * This works by adding possible discounts from the highest discount to lowest
 * And removing a discount will allow the calling method to apply it to its list of items
 * The removal method will then need to check for any discounts that can no longer be applied (ie cannot apply two discounts to the same item) and removes these from the data structure
 * The calling method needs to remove the item from its array list so the removal method must update the index positions accordingly
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
                /** Adds an entry in the format <Discount, Integer, Integer> */
                structure.add(i, new ArrayList<>(Arrays.asList(discount, item1, item2)));
                return;
            }
        }
        structure.add(structure.size(), new ArrayList<>(Arrays.asList(discount, item1, item2)));
    }

    /**
     *
     * @return Array List of the current discount to apply and what items to apply it to
     * The array list is in the format (Discount, Item1 Index, Item2 Index)
     * The calling method can use this array list to apply the discount to the intended items
     */
    public ArrayList<Object> removeEntry() {
        if (structure.isEmpty()) return null;

        /** Removes the first entry or the highest discount from the datastructure */
        ArrayList<Object> lastEntry = structure.removeFirst();

        /** For loop to check for recurring item indexs that occur for different discounts
         * This is important as we only want to apply one discount to one item
         * So any item that has a discount applied to can be removed from the data structure */
        for (int i=0; i<structure.size(); i++) {
            /** Checks if the data structures second column contains an item which will have the discount applied and then removes this entry */
            if (Objects.equals(structure.get(i).get(1), lastEntry.get(1)) || Objects.equals(structure.get(i).get(1), lastEntry.get(2))) {
                structure.remove(i);
                i-=1;
            }
            /** Checks if the data structures third column contains an item which will have the discount applied and then removes this entry */
            else if (Objects.equals(structure.get(i).get(2), lastEntry.get(1)) || Objects.equals(structure.get(i).get(2), lastEntry.get(2))) {
                structure.remove(i);
                i-=1;
            }
            /** Then needs to shift the index value for any other discount entries in the data structure accordingly */
            else {
                if ((Integer) structure.get(i).get(1) > (Integer) lastEntry.get(2)) {
                    structure.get(i).set(1, (Integer) structure.get(i).get(1) - 1);
                }
                if ((Integer) structure.get(i).get(1) > (Integer) lastEntry.get(1)) {
                    structure.get(i).set(1, (Integer) structure.get(i).get(1) - 1);
                }
                if ((Integer) structure.get(i).get(2) > (Integer) lastEntry.get(2)) {
                    structure.get(i).set(2, (Integer) structure.get(i).get(2) - 1);
                }
                if ((Integer) structure.get(i).get(2) > (Integer) lastEntry.get(1)) {
                    structure.get(i).set(2, (Integer) structure.get(i).get(2) - 1);
                }
            }
        }

        return lastEntry;
    }
}
