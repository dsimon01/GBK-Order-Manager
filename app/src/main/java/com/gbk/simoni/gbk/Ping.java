package com.gbk.simoni.gbk;

import java.util.TimerTask;

public class Ping extends TimerTask {

    OrderManager orderManager = new OrderManager();

    @Override
    public void run() {
        System.out.println(" --> scheduled request to parse server Order object ");
        ParseServer.orders.clear();
        orderManager.getRequestOrderObject();
    }
}