package com.gbk.simoni.gbk;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class OrderManager extends AppCompatActivity implements OrderListAdapter.ItemClicked {

    TextView orderNumber, tableNumber,orderStatus;
    ArrayList<String> orderItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        orderNumber = findViewById(R.id.order_number_information_fragment);
        tableNumber = findViewById(R.id.table_number_information_fragment);
        orderStatus = findViewById(R.id.order_status);


        if (ParseServerConfig.orders.size() > 0) {
            onItemClicked(0);
        }
    }

    @Override
    public void onItemClicked(int which) {

        orderNumber.setText("#" + ParseServerConfig.orders.get(which).getOrderID());
        tableNumber.setText(ParseServerConfig.orders.get(which).getTableNumber().toUpperCase());

        String items = ParseServerConfig.orders.get(which).getItems();
        orderItems = converter(items);
        System.out.println(" --> " + orderItems);
    }

    public ArrayList<String> converter(String string){

        String convert = string;
        convert = convert.substring(1, convert.length() - 1);
        ArrayList<String> order_items = new ArrayList(Arrays.asList(convert.split(",")));
        return order_items;
    }
}