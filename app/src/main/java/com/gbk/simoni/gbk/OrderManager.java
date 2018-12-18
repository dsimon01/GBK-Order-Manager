package com.gbk.simoni.gbk;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrderManager extends AppCompatActivity implements OrderListAdapter.ItemClicked {

    public static ArrayList<String> orderItems;
    TextView orderNumber, tableNumber, no_active_orders, orderItemsTextView;
    Button acknowledged, markedReady;
    String orderSelected, orderStatus;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        orderNumber = findViewById(R.id.order_number_information_fragment);
        tableNumber = findViewById(R.id.table_number_information_fragment);
        no_active_orders = findViewById(R.id.no_active_orders);
        orderItemsTextView = findViewById(R.id.orderItems);
        acknowledged = findViewById(R.id.acknowledgeOrderButton);
        markedReady = findViewById(R.id.markOrderReadyButton);

        if (ParseServerConfig.orders.size() > 0) {
            onItemClicked(0);
            orderNumber.setVisibility(View.VISIBLE);
            tableNumber.setVisibility(View.VISIBLE);
            orderItemsTextView.setVisibility(View.VISIBLE);
            no_active_orders.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onItemClicked(int which) {

        orderStatus = ParseServerConfig.orders.get(which).getStatus();

        if (orderStatus.equals("new") || orderStatus.equals("NEW")) {
            acknowledged.setVisibility(View.VISIBLE);
            markedReady.setVisibility(View.INVISIBLE);
        }else {
            acknowledged.setVisibility(View.INVISIBLE);
            markedReady.setVisibility(View.VISIBLE);
        }

        orderSelected = ParseServerConfig.orders.get(which).getOrderID();
        orderNumber.setText("#" + orderSelected);
        tableNumber.setText(ParseServerConfig.orders.get(which).getTableNumber().toUpperCase());
        String items = ParseServerConfig.orders.get(which).getItems();
        orderItems = converter(items);
        updateRecycler(orderItems);
    }

    public ArrayList<String> converter(String string) {
        String convert = string;
        convert = convert.substring(1, convert.length() - 1);
        ArrayList<String> order_items = new ArrayList(Arrays.asList(convert.split(",")));
        return order_items;
    }

    public void updateRecycler(ArrayList<String> items) {
        recyclerView = OrderInformationFragment.view.findViewById(R.id.recycler_view_order_info);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ItemListAdapter(this, items);
        recyclerView.setAdapter(adapter);
    }

    public void onAcknowledgedOrder(View view) {

        Toast.makeText(OrderManager.this, "ACK'ED THE ORDER", Toast.LENGTH_LONG).show();
        ParseQuery<ParseObject> updateOrderStatus = ParseQuery.getQuery("Order");
        updateOrderStatus.whereEqualTo("OrderID",  Integer.parseInt(orderSelected));
        updateOrderStatus.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects != null) {
                    for (ParseObject object : objects) {
                        object.put("Status", "accepted");
                        object.saveInBackground();
                    }
                } else {
                    Log.i("ERROR", "ERROR");
                    e.printStackTrace();
                }
            }
        });
    }


    public void onMarkedOrderReady(View view){

        ParseQuery<ParseObject> updateOrderStatus = ParseQuery.getQuery("Order");
        updateOrderStatus.whereEqualTo("OrderID", Integer.parseInt(orderSelected));
        updateOrderStatus.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects != null){
                    for (ParseObject object : objects){
                        object.put("Status", "ready");
                        object.saveInBackground();

                        Toast.makeText(OrderManager.this, "Order Marked as ready", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Log.i("ERROR", "ERROR");
                    e.printStackTrace();
                }
            }
        });
    }

}
