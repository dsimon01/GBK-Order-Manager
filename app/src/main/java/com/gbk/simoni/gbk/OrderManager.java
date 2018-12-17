package com.gbk.simoni.gbk;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class OrderManager extends AppCompatActivity implements OrderListAdapter.ItemClicked {

    TextView orderNumber, tableNumber, itemInfo, orderStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        orderNumber = findViewById(R.id.order_number_information_fragment);
        tableNumber = findViewById(R.id.table_number_information_fragment);
        itemInfo = findViewById(R.id.item_information);
        orderStatus = findViewById(R.id.order_status);


        if (ParseServerConfig.orders.size() > 0) {
            onItemClicked(0);
        }
    }

    @Override
    public void onItemClicked(int which) {

        orderNumber.setText("#" + ParseServerConfig.orders.get(which).getOrderID());
        tableNumber.setText(ParseServerConfig.orders.get(which).getTableNumber().toUpperCase());
        itemInfo.setText(ParseServerConfig.orders.get(which).getItems().toUpperCase());
        orderStatus.setText(ParseServerConfig.orders.get(which).getStatus().toUpperCase());

    }
}