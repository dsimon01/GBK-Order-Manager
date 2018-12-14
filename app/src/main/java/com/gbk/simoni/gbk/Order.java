package com.gbk.simoni.gbk;

public class Order {

    String tableNumber;
    String status;
    String items;
    int orderID;
    double price;


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

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getOrderID() {
        return String.valueOf(orderID);
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}