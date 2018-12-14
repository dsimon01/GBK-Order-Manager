package com.gbk.simoni.gbk;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

    public class ParseServerConfig extends Application {

        @Override
        public void onCreate() {
            super.onCreate();

            // Enable Local Datastore.
            Parse.enableLocalDatastore(this);

            // Config keys with Parse-Bitnami.
            Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                    .applicationId("a150e5d0e42666ddf6864edf392bed450d9bb305")
                    .clientKey("ec3b36f8e6eea59e2a7d52b4358efaf7594aaca8")
                    .server("http://3.8.131.250:80/parse/")
                    .build()
            );

            /*
            ParseObject object = new ParseObject("ExampleObject");
            object.put("myNumber", "123");
            object.put("myString", "gourmet burger user");

            object.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException ex) {
                    if (ex == null) {
                        Log.i("Parse Result", "Successful!");
                    } else {
                        Log.i("Parse Result", "Failed" + ex.toString());
                    }
                }
            });


            ParseUser.enableAutomaticUser();

            */

            ParseACL defaultACL = new ParseACL();
            defaultACL.setPublicReadAccess(true);
            defaultACL.setPublicWriteAccess(true);
            ParseACL.setDefaultACL(defaultACL, true);

        }
    }
