package Order;

import Interface.EntityList;

import java.util.ArrayDeque;
import java.util.Queue;

public class OrderList implements EntityList {
    private Queue<Object> queue = new ArrayDeque<>();

    public OrderList() {}

    @Override
    public void add(Object item) {

    }

    @Override
    public void remove(Object ID) {

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
