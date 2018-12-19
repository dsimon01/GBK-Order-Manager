package com.gbk.simoni.gbk;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.parse.FindCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ParseServer extends Application {

    public static ArrayList<Order> orders;

    @Override
    public void onCreate() {
        super.onCreate();
        ParseServerConfig();
        getRequest_OrderObject();
    }

    public interface getParseRequest{
        void getOrderRequest();
    }

    public void ParseServerConfig() {

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Config keys with Parse-Bitnami.
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("a150e5d0e42666ddf6864edf392bed450d9bb305")
                .clientKey("ec3b36f8e6eea59e2a7d52b4358efaf7594aaca8")
                .server("http://3.8.131.250:80/parse/")
                .build()
        );

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }

    // RUN THIS EVERY 30s --------------------------------------------------------------------
    public void getRequest_OrderObject() {
        orders = new ArrayList<>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        for (ParseObject object : objects) {
                            orders.add(new Order(
                                    object.getString("TableNumber"),
                                    object.getString("Status"),
                                    object.get("Item").toString(),
                                    object.getInt("OrderID"),
                                    object.getDouble("Price")
                            ));
                        }
                    }
                } else {
                    Log.i("ERRRRRRRRR", "ERROR");
                    e.printStackTrace();
                }
            }
        });

    }
}
