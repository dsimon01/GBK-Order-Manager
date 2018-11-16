package com.gbk.simoni.gbk;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class OrderManager extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Order> OrderSample = new ArrayList<>();


    ArrayList<String> tableNumber = new ArrayList<>();
    ArrayList<String> status = new ArrayList<>();
    ArrayList<String> items = new ArrayList<>();
    ArrayList<Integer> orderID = new ArrayList<>();
    ArrayList<Double> price = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        recyclerView = findViewById(R.id.recyclerHome);


        // ================================================================

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {

                        for (ParseObject object : objects) {

                            tableNumber.add(object.getString("TableNumber"));
                            status.add(object.getString("Status"));
                            items.add(object.get("Item").toString());
                            orderID.add(object.getInt("OrderID"));
                            price.add(object.getDouble("Price"));

                        }

                        createMockOrder();
                    }
                } else {
                    Log.i("ERRRRRRRRR", "ERROR");
                    e.printStackTrace();
                }
            }
        });
    }

    public void createMockOrder() {

        System.out.println("CREATING ORDER");
        for (int i = 0; i < tableNumber.size(); i++) {
            Order testOrder = new Order();
            testOrder.tableNumber = tableNumber.get(i);
            testOrder.items = items.get(i);
            testOrder.price = price.get(i);
            testOrder.status = status.get(i);
            testOrder.orderID = orderID.get(i);
            OrderSample.add(testOrder);
            System.out.println(OrderSample + " INSIDE ORDER CREATE");
            applyRecyclerView();
        }
    }

    public void applyRecyclerView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new RecyclerAdapter(OrderSample));

    }
}

/*


                           new CountDownTimer(50000, 1000) {

                                @Override
                                public void onTick(long millisUntilFinished) {
                                    System.out.println(tableNumber + " " + status +  " " + items + " " + orderID + " empty " + price);
                                }

                                @Override
                                public void onFinish() {

                                    System.out.println(tableNumber + " " + status +  " " + items + " " + orderID + " " + price);

                                }

                            }.start();




 */