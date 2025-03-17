package client;

import order.OrderList;

import java.util.ArrayList;

public class SimUIModel {

    private OrderList orders;

    private int simSpeed;

    private String curStaff; // not implemented yet
    private String staffName;
    private String staffRole;
    private String staffExperience;


    public SimUIModel(OrderList orderList, int simSpd) {
        this.orders = orderList;
        this.simSpeed = simSpd;
        this.curStaff = null;
        this.staffName = null;
        this.staffRole = null;
        this.staffExperience = null;
    }
}
