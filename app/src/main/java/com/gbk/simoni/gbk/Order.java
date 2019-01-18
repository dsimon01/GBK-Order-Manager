package com.gbk.simoni.gbk;

// Order Object with getters.
public class Order {

    private String tableNumber;
    private String status;
    private String items;
    private int orderID;
    private double price;


    public Order(String tableNumber, String status, String items, int orderID, double price) {
        this.tableNumber = tableNumber;
        this.status = status;
        this.items = items;
        this.orderID = orderID;
        this.price = price;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public String getStatus() {
        return status;
    }

    public String getItems() {
        return items;
    }

    public String getOrderID() {
        return String.valueOf(orderID);
    }
}