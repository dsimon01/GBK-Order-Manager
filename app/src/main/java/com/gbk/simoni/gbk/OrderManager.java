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

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderManager extends AppCompatActivity implements OrderListAdapter.ItemClicked {

    private TextView orderNumber, tableNumber, no_active_orders, orderItemsTextView;
    private Button acknowledged, markedReady;
    private String orderSelected;
    private RecyclerView recyclerView;
    private OrderListFragment orderListFragment;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        // Declares variables with Views from Resource file.
        findViews();

        // Declarations to access method updateOrderList in OrderListFragment from this class.
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        orderListFragment = (OrderListFragment) fragmentManager.findFragmentById(R.id.fragment);

        /*
        When the Activity is created, this method call checks if there are any active orders.
        If there are it selects the order and shows the order details in the UI.
        */
        orderDetails();

        // Scheduled GET request runs every 30" looking for new orders.
        fetchOrderStatusUpdates();
    }

    private void findViews() {
        orderNumber = findViewById(R.id.order_number_information_fragment);
        tableNumber = findViewById(R.id.table_number_information_fragment);
        acknowledged = findViewById(R.id.acknowledgeOrderButton);
        markedReady = findViewById(R.id.markOrderReadyButton);
        no_active_orders = findViewById(R.id.no_active_orders);
        orderItemsTextView = findViewById(R.id.orderItems);
    }

    private void orderDetails() {
        if (ParseServer.orders.size() > 0) {
            onItemClicked(0);
            orderNumber.setVisibility(View.VISIBLE);
            tableNumber.setVisibility(View.VISIBLE);
            orderItemsTextView.setVisibility(View.VISIBLE);
            no_active_orders.setVisibility(View.INVISIBLE);
        }
    }

    /*
    This Class implements OrderListAdapter.ItemClicked so it can define the item clicked method.
    Arranges view visibility as appropriate and based on the item clicked.
     */

    @Override
    public void onItemClicked(int which) {
        orderNumber.setVisibility(View.VISIBLE);
        tableNumber.setVisibility(View.VISIBLE);
        orderItemsTextView.setVisibility(View.VISIBLE);
        String orderStatus = ParseServer.orders.get(which).getStatus();
        buttonDisplay(orderStatus);
        orderSelected = ParseServer.orders.get(which).getOrderID();
        orderNumber.setText("#" + orderSelected);
        tableNumber.setText(ParseServer.orders.get(which).getTableNumber().toUpperCase());
        String items = ParseServer.orders.get(which).getItems();
        ArrayList<String> orderItems = convertStringToArrayList(items);
        updateRecycler(orderItems);
    }

    // Arranges button display based on order status.
    private void buttonDisplay(String status) {

        if (status.equals("new")) {
            acknowledged.setVisibility(View.VISIBLE);
            markedReady.setVisibility(View.INVISIBLE);
        }

        if (status.equals("accepted")) {
            acknowledged.setVisibility(View.INVISIBLE);
            markedReady.setVisibility(View.VISIBLE);
        }

        if (status.equals("ready")) {
            acknowledged.setVisibility(View.INVISIBLE);
            markedReady.setVisibility(View.INVISIBLE);
        }
    }

    /*
    Trims first and last character of a string, then creates an arrayList from the items within
    this string that are separated by a comma.
    */
    private ArrayList<String> convertStringToArrayList(String string) {
        String trim = string;
        trim = trim.substring(1, trim.length() - 1);
        ArrayList<String> order_items = new ArrayList(Arrays.asList(trim.split(",")));
        return order_items;
    }

    // Populates the recycler view with an array list of items.
    private void updateRecycler(ArrayList<String> items) {
        recyclerView = OrderInformationFragment.view.findViewById(R.id.recycler_view_order_info);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter = new ItemListAdapter(items);
        recyclerView.setAdapter(adapter);
    }

    /*
    When the user taps to accept the order this method is executed.
    Creates a progress dialog for 2"
    Calls method to update status in database.
    Changes button visibility.
     */
    public void onAcknowledgedOrder(View view) {

        setDialog("Accepting Order", "Updating Order...");
        dialog.show();

        updateOrderStatusInDatabase("accepted");
        acknowledged.setVisibility(View.INVISIBLE);
        markedReady.setVisibility(View.VISIBLE);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                dialog.cancel();
            }
        }, 2000);
    }

    /*
    When the user taps to mark the order as ready this method is executed.
    Creates a progress dialog for 2"
    Calls method to update status in database.
    Changes button visibility.
     */

    public void onMarkedOrderReady(View view) {

        setDialog("Marking Order as Ready", "Please wait...");
        dialog.show();

        updateOrderStatusInDatabase("ready");
        acknowledged.setVisibility(View.INVISIBLE);
        markedReady.setVisibility(View.INVISIBLE);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                dialog.cancel();
            }
        }, 2000);
    }

    // Expects String parameters that will be used to populate the title and message of the dialog.
    private void setDialog(String title, String msg) {

        dialog = new ProgressDialog(OrderManager.this);
        dialog.setTitle(title);
        dialog.setMessage(msg);
    }

    /*
    Method that is called when the user taps on accept or ready an order.
    Runs a query by looking for the Order object where the order number matches the current one.
    updates the status based on the String parameter passed in the method call with a PUT request.
    Calls updateStatus method to update the recycler view with the updated status.
     */
    private void updateOrderStatusInDatabase(final String status) {

        ParseQuery<ParseObject> updateOrderStatus = ParseQuery.getQuery("Order");
        updateOrderStatus.whereEqualTo("OrderID", Integer.parseInt(orderSelected));
        updateOrderStatus.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects != null) {
                    for (ParseObject object : objects) {
                        object.put("Status", status);
                        object.saveInBackground();
                        updateStatus();
                    }
                } else {
                    Log.i("ERROR", "ERROR");
                    e.printStackTrace();
                }
            }
        });
    }

    // GET request to the database. Updates the Recycler view.
    private void updateStatus() {
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
                        }
                        orderListFragment.updateOrderList();
                    }
                } else {
                    Log.i("ERROR", "ERROR");
                    e.printStackTrace();
                }
            }
        });
    }

    /*
    Scheduled GET request to the database every 30" using a Handler.
    Clears all existing items within the Orders List
    Populates it again with the updates, if any. otherwise sets the UI in no active orders mode.
     */
    private void fetchOrderStatusUpdates() {

        final Handler handler = new Handler();
        final int delay = 30000;
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
                                    no_active_orders.setVisibility(View.INVISIBLE);
                                    orderListFragment.updateOrderList();
                                }
                            } else {
                                setNoActiveOrdersDisplay();
                            }

                        } else {
                            Log.i("ERROR", "ERROR");
                            e.printStackTrace();
                        }

                    }
                });

                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    // Updates the UI to show the no active orders design.
    private void setNoActiveOrdersDisplay() {
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

    // Disables back button.
    @Override
    public void onBackPressed() {
        // super.onBackPressed(); commented this line in order to disable back press
    }
}
