package com.gbk.simoni.gbk;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

/* LATER FUNCTION
    public void accept(View view){
        // Parse query to edit value of status to confirmed
        // change the value of the button to Mark as ready
    }
*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final ListView ordersListView = findViewById(R.id.ordersListView);
        final ArrayList<String> orderDetails = new ArrayList<>();
        final ArrayList<String> forAdapter = new ArrayList<>();
        final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, forAdapter);



        ordersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                String orderSelected = forAdapter.get(position);
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
                                    Log.i("FOUND", object.getString("Status"));
                                    Log.i("FOUND", object.getString("TableNumber"));
                                    Log.i("FOUND", object.get("OrderID").toString());
                                    Log.i("FOUND", object.get("Item").toString());
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


// TODO: Make "view.VISIBLE" the order information in the home activity - they need to update as the user is pressing on another order ID
// TODO: MEMORY REFRESHER FOR CURRENT CODE: the code is able to retrieve info on order basis





// what happens if the "orders" object is null? how can i make this not crash here? - How can I make this to look for orders every certain period and if null just retry
// Here I have to take the contents of the list and make them visible in the activity rather than adding them on the list.
// On the list just have to add the order number > when the user taps on the list item, display all other info on the side screen as per design
//https://stackoverflow.com/questions/32138061/how-to-run-a-parse-query-in-background-or-schedule-it-in-android
//https://www.programcreek.com/java-api-examples/?class=com.parse.ParseQuery&method=findInBackground

/*
                String orderSelected = forAdapter.get(position);
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
                query.whereEqualTo("OrderID", orderSelected);
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {

                        if (e == null){
                            if (objects.size() > 0){

                                Log.i("QUERY", objects.toString());

                                for (ParseObject object : objects){

                                    orderDetails.add(object.get("OrderID").toString());
                                    orderDetails.add(object.getString("TableNumber"));
                                    orderDetails.add(object.getString("Status"));
                                    orderDetails.add(object.get("Item").toString());

                                    TextView orderN = findViewById(R.id.orderNumberTextView);
                                    orderN.setText(orderDetails.get(0));
                                    orderN.setVisibility(view.VISIBLE);

                                    TextView table = findViewById(R.id.tableNumberTextView);
                                    table.setText(orderDetails.get(1));
                                    table.setVisibility(view.VISIBLE);

                                    TextView items = findViewById(R.id.itemTextView1);
                                    items.setText(orderDetails.get(3));
                                    items.setVisibility(view.VISIBLE);

                                    Button accept = findViewById(R.id.accept);
                                    accept.setVisibility(view.VISIBLE);

                                }
                                Log.i("ORDER DETAILS", orderDetails.toString());
                            }
                        }else {
                            Log.i("ERRRRRRRRR", "ERROR");
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
 */