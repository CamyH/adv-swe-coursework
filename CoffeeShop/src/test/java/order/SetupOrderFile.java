package order;

import exceptions.InvalidOrderException;
import item.ItemList;
import item.SetupItemFile;

public class SetupOrderFile {
    private static final OrderList orderList = new OrderList();
    private static final ItemList itemList = SetupItemFile.generateItemList();

    /**
     * Generate an order list to be used for tests
     * @return the generated OrderList
     */
    public static OrderList generateOrderList() {
        try {
            Order first = new Order(itemList);
            first.addItem("RL2");
            first.addItem("SD4");
            first.addItem("PSY5");
            orderList.add(first);

            Order o = new Order(itemList);
            o.addItem("RL1");
            o.addItem("HD4");
            o.addItem("SD7");
            o.addItem("PSY1");
            orderList.add(o);

            o = new Order(itemList);
            o.addItem("FD3");
            o.addItem("HD5");
            o.addItem("RL1");
            o.addItem("SD1");
            orderList.add(o);

            o = new Order(itemList);
            o.addItem("RL3");
            o.addItem("HD1");
            o.addItem("PSY2");
            orderList.add(o);

            o = new Order(itemList);
            o.addItem("FD5");
            o.addItem("RL1");
            o.addItem("SCK4");
            o.addItem("HD5");
            o.addItem("SCK7");
            orderList.add(o);

            o = new Order(itemList);
            o.addItem("RL1");
            o.addItem("FD2");
            o.addItem("HD6");
            o.addItem("SD8");
            o.addItem("PSY4");
            o.addItem("SCK6");
            orderList.add(o);
        } catch (InvalidOrderException e) {
            System.out.println(e.getMessage());
        }

        return orderList;
    }

    /**
 * Generate the OrderList as string
 * @return a string of the order list
 */
public static String generateOrderListAsString() {
    StringBuilder sb = new StringBuilder();

    for (Order order : orderList.getOrderList()) {
        sb.append("Order ID: ").append(order.getOrderID()).append("\n");

        // Adding the semicolon between itemIds
        for (String itemId : order.getDetails()) {
            sb.append(itemId).append(";");
        }

        // Remove trailing comma and space if they exist
        if (!sb.isEmpty() && sb.charAt(sb.length() - 2) == ',') {
            sb.delete(sb.length() - 2, sb.length());
        }

        sb.append("\n\n");
    }

    return sb.toString();
}
}
