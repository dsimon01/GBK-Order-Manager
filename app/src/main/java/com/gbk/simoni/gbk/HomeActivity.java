package com.gbk.simoni.gbk;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    String orderSelected;



    // Parse query to edit value of status to accepted
    // change the value of the button to Mark as ready - for now done inside the on item click listener
    public void onAccept(final View view){
        Log.i("TO ACCEPT", orderSelected);
        ParseQuery<ParseObject> updateOrderStatus = ParseQuery.getQuery("Order");
        updateOrderStatus.whereEqualTo("OrderID", Integer.parseInt(orderSelected));
        updateOrderStatus.setLimit(1);
        updateOrderStatus.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects != null){
                    for (ParseObject object : objects){
                        Log.i("STATUS RETRIEVED", object.getString("Status"));
                        object.put("Status", "accepted");
                        object.saveInBackground();
                        Button accept = findViewById(R.id.accept);
                        accept.setVisibility(view.GONE);
                        Toast.makeText(HomeActivity.this, "Order Accepted", Toast.LENGTH_LONG).show();
                        Button markAsCompleted = findViewById(R.id.markAsReady);
                        markAsCompleted.setVisibility(view.VISIBLE);
                    }
                }else {
                    Log.i("ERRRRRRRRR", "ERROR");
                    e.printStackTrace();
                }
            }
        });
    }





    // Parse query to edit value of status to ready
    // change the visibility of the button to invisible - for now done inside the on item click listener
    public void onMarkAsReady(final View view){
        Log.i("TO COMPLETE", orderSelected);
        ParseQuery<ParseObject> updateOrderStatus = ParseQuery.getQuery("Order");
        updateOrderStatus.whereEqualTo("OrderID", Integer.parseInt(orderSelected));
        // updateOrderStatus.setLimit(1);
        updateOrderStatus.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects != null){
                    for (ParseObject object : objects){
                        Log.i("STATUS RETRIEVED", object.getString("Status"));
                        object.put("Status", "completed");
                        object.saveInBackground();
                        Button markAsCompleted = findViewById(R.id.markAsReady);
                        markAsCompleted.setVisibility(view.GONE);
                        Toast.makeText(HomeActivity.this, "Order Marked as completed", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Log.i("ERRRRRRRRR", "ERROR");
                    e.printStackTrace();
                }

            }
        });
    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final ListView ordersListView = findViewById(R.id.ordersListView);
        //final ArrayList<String> orderDetails = new ArrayList<>();
        final ArrayList<String> forAdapter = new ArrayList<>();
        final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, forAdapter);



        ordersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                orderSelected = forAdapter.get(position);
                Log.i("CLICKED", orderSelected);

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");

                query.whereEqualTo("OrderID", Integer.parseInt(orderSelected));

                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null) {

                            Log.i("FINDINBACKGROUND", "Retrieved " + objects.size() + " objects");

                            if (objects.size() > 0) {

                                for (ParseObject object : objects) {
                                    String status = object.getString("Status");
                                    String tableNumber = object.getString("TableNumber");
                                    String OrderID = object.get("OrderID").toString();
                                    String item = object.get("Item").toString();

                                    TextView orderN = findViewById(R.id.orderNumberTextView);
                                    orderN.setText(OrderID);
                                    orderN.setVisibility(view.VISIBLE);

                                    TextView table = findViewById(R.id.tableNumberTextView);
                                    table.setText(tableNumber);
                                    table.setVisibility(view.VISIBLE);

                                    TextView items = findViewById(R.id.itemTextView1);
                                    items.setText(item);
                                    items.setVisibility(view.VISIBLE);

                                    if (status.matches("new")) {

                                        Button accept = findViewById(R.id.accept);
                                        accept.setVisibility(view.VISIBLE);
                                        Button markAsCompleted = findViewById(R.id.markAsReady);
                                        markAsCompleted.setVisibility(view.GONE);

                                    }else if(status.matches("accepted")){

                                        Button markAsCompleted = findViewById(R.id.markAsReady);
                                        markAsCompleted.setVisibility(view.VISIBLE);
                                        Button accept = findViewById(R.id.accept);
                                        accept.setVisibility(view.GONE);
                                    }else{

                                        Button markAsCompleted = findViewById(R.id.markAsReady);
                                        markAsCompleted.setVisibility(view.GONE);
                                        Button accept = findViewById(R.id.accept);
                                        accept.setVisibility(view.GONE);

                                    }
                                }
                            }
                        } else {
                            Log.i("ERRRRRRRRR", "ERROR");
                            e.printStackTrace();
                        }
                    }
                });
            }
        });


        ParseQuery<ParseObject> orderID = ParseQuery.getQuery("Order");
        orderID.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null){
                    if (objects.size() > 0){
                        for (ParseObject object : objects){
                            forAdapter.add(object.get("OrderID").toString());
                        }
                        ordersListView.setAdapter(arrayAdapter);
                        // Log.i("ORDER DETAILS", orderDetails.toString());
                    }
                }else {
                    Log.i("ERRRRRRRRR", "ERROR");
                    e.printStackTrace();
                }
            }
        });

    }
}


// TODO: Updated the last version of code with this one where order updates are now possible :)
// TODO: MEMORY REFRESHER FOR CURRENT CODE: the code is able to retrieve info and update on order basis





// what happens if the "orders" object is null? how can i make this not crash here? - How can I make this to look for orders every certain period and if null just retry
// Here I have to take the contents of the list and make them visible in the activity rather than adding them on the list.
// On the list just have to add the order number > when the user taps on the list item, display all other info on the side screen as per design
//https://stackoverflow.com/questions/32138061/how-to-run-a-parse-query-in-background-or-schedule-it-in-android
//https://www.programcreek.com/java-api-examples/?class=com.parse.ParseQuery&method=findInBackground
// ec2-35-178-79-238.eu-west-2.compute.amazonaws.com > Server link

