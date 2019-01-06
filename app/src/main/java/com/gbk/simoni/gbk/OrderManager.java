package com.gbk.simoni.gbk;


import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
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
import java.util.List;

public class OrderManager extends AppCompatActivity implements OrderListAdapter.ItemClicked {

    public static ArrayList<String> orderItems;

    TextView orderNumber, tableNumber, no_active_orders, orderItemsTextView;
    Button acknowledged, markedReady;
    String orderSelected, orderStatus;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    static OrderListFragment orderListFragment;
    FragmentManager fragmentManager;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        orderNumber = findViewById(R.id.order_number_information_fragment);
        tableNumber = findViewById(R.id.table_number_information_fragment);
        acknowledged = findViewById(R.id.acknowledgeOrderButton);
        markedReady = findViewById(R.id.markOrderReadyButton);
        no_active_orders = findViewById(R.id.no_active_orders);
        orderItemsTextView = findViewById(R.id.orderItems);
        fragmentManager = this.getSupportFragmentManager();
        orderListFragment = (OrderListFragment) fragmentManager.findFragmentById(R.id.fragment);

        orderDetails();
        fetchOrderStatusUpdates();
    }

    @Override
    public void onItemClicked(int which) {

        orderStatus = ParseServer.orders.get(which).getStatus();
        buttonDisplay(orderStatus);
        orderSelected = ParseServer.orders.get(which).getOrderID();
        orderNumber.setText("#" + orderSelected);
        tableNumber.setText(ParseServer.orders.get(which).getTableNumber().toUpperCase());
        String items = ParseServer.orders.get(which).getItems();
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
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ItemListAdapter(this, items);
        recyclerView.setAdapter(adapter);
    }

    public void onAcknowledgedOrder(View view) {

        dialog = new ProgressDialog(OrderManager.this);
        dialog.setTitle("Accepting Order");
        dialog.setMessage("Please wait...");
        dialog.show();

        ParseServer.orders.clear();
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
                    acknowledged.setVisibility(View.INVISIBLE);
                    markedReady.setVisibility(View.VISIBLE);
                } else {
                    Log.i("ERROR", "ERROR");
                    e.printStackTrace();
                }
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                dialog.cancel();
            }
        }, 3000);
    }
    public void onMarkedOrderReady(View view){

        dialog = new ProgressDialog(OrderManager.this);
        dialog.setTitle("Marking Order as Ready");
        dialog.setMessage("Please wait...");
        dialog.show();

        ParseServer.orders.clear();
        System.out.println(ParseServer.orders.size() + " <-- CLEAR");
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
                        acknowledged.setVisibility(View.INVISIBLE);
                        markedReady.setVisibility(View.INVISIBLE);
                    }
                }else {
                    Log.i("ERROR", "ERROR");
                    e.printStackTrace();
                }
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                dialog.cancel();
            }
        }, 3000);
    }

    public void orderDetails(){

        if (ParseServer.orders.size() > 0) {
            System.out.println(ParseServer.orders.size() + " <-- ORDER DETAIL");
            onItemClicked(0);
            orderNumber.setVisibility(View.VISIBLE);
            tableNumber.setVisibility(View.VISIBLE);
            orderItemsTextView.setVisibility(View.VISIBLE);
            no_active_orders.setVisibility(View.INVISIBLE);
        }
    }

    public void buttonDisplay(String status){

        if (status.equals("new")){
            acknowledged.setVisibility(View.VISIBLE);
            markedReady.setVisibility(View.INVISIBLE);
        }

        if (status.equals("accepted")){
            acknowledged.setVisibility(View.INVISIBLE);
            markedReady.setVisibility(View.VISIBLE);
        }

        if (status.equals("ready")){
            acknowledged.setVisibility(View.INVISIBLE);
            markedReady.setVisibility(View.INVISIBLE);
        }
    }

    public void fetchOrderStatusUpdates(){

        final Handler handler = new Handler();
        final int delay = 2000;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ParseServer.orders.clear();
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null) {
                            if (objects.size() > 0) {
                                for (ParseObject object : objects) {
                                    ParseServer.orders.add(new Order(
                                            object.getString("TableNumber"),
                                            object.getString("Status"),
                                            object.get("Item").toString(),
                                            object.getInt("OrderID"),
                                            object.getDouble("Price")
                                    ));
                                    orderDetails();
                                    orderListFragment.updateOrderList();
                                }
                            }else {
                                ParseServer.orders.clear();
                                orderListFragment.updateOrderList();
                                no_active_orders.setVisibility(View.VISIBLE);
                                orderNumber.setVisibility(View.INVISIBLE);
                                tableNumber.setVisibility(View.INVISIBLE);
                                orderItemsTextView.setVisibility(View.INVISIBLE);
                                recyclerView = findViewById(R.id.recycler_view_order_info);
                                recyclerView.setVisibility(View.INVISIBLE);
                                markedReady.setVisibility(View.INVISIBLE);
                            }

                        } else {
                            Log.i("ERRRRRRRRR", "ERROR");
                            e.printStackTrace();
                        }

                    }
                });

                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed(); commented this line in order to disable back press
        Toast.makeText(getApplicationContext(), "Back press disabled!", Toast.LENGTH_SHORT).show();
    }
}
