package Order;

import Interface.EntityList;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.UUID;

public class OrderList implements EntityList {
    private Queue<Object> queue = new ArrayDeque<>();

    public OrderList() {}

    @Override
    public void add(Object order) {
        queue.add(order);
    }

    @Override
    public void remove(Object ID) {
        if (ID instanceof UUID) {
            for (Object o : queue) {
                if (o.getOrderID().equals(ID)) {
                    queue.remove(o);
                    return;
                }
            }
            throw new IllegalArgumentException(ID + " is not a valid order ID");
        }
        throw new IllegalArgumentException(ID + " is not of type UUID");
    }

    public Object getOrder(String orderID) {
        return null;
    }

    public Queue<Object> getOrderList() {
        return queue;
    }

    public String getOrderDetails() {
        return "";
    }
}
