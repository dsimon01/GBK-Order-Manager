package com.gbk.simoni.gbk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final ListView ordersListView = findViewById(R.id.ordersListView);
        final ArrayList<String> orderDetails = new ArrayList<>();
        final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, orderDetails);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null){
                    if (objects.size() > 0){

                        for (ParseObject object : objects){

                            //orderDetails.add(object.getString("TableNumber"));
                            //orderDetails.add(object.getString("Status"));
                            orderDetails.add(object.get("OrderID").toString());
                            //orderDetails.add(object.get("Item").toString()); works but comes with braces

                            // what happens if the "orders" object is null? how can i make this not crash here? - How can I make this to look for orders every certain period and if null just retry
                            // Here I have to take the contents of the list and make them visible in the activity rather than adding them on the list.
                            // On the list just have to add the order number > when the user taps on the list item, display all other info on the side screen as per design
                            //https://stackoverflow.com/questions/32138061/how-to-run-a-parse-query-in-background-or-schedule-it-in-android

                        }

                        ordersListView.setAdapter(arrayAdapter);
                    }

                }else {
                    Log.i("ERRRRRRRRR", "ERROR");
                    e.printStackTrace();
                }

            }
        });

    }
}
