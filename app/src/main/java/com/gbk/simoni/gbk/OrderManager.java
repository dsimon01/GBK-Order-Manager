package com.gbk.simoni.gbk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class OrderManager extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Order> OrderSample = new ArrayList<>();

    String[] tableNumber = {

            "1234",
    };

    String[] status = {

            "NEW",

    };

    String[] items = {

            "Burger",

    };

    int[] orderID = {

            1234,
    };

    double[] price = {

            12.34,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        recyclerView = findViewById(R.id.recyclerHome);

        for (int i = 0; i < tableNumber.length; i++) {
            Order testOrder = new Order();
            testOrder.tableNumber = tableNumber[i];
            testOrder.items = items[i];
            testOrder.price = price[i];
            testOrder.status = status[i];
            testOrder.orderID = orderID[i];
            OrderSample.add(testOrder);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new RecyclerAdapter(OrderSample));

    }

    }

