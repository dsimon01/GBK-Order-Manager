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

            // Config with Parse-Bitnami
            Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                    .applicationId("0f8520e454f6ad2e9d7b288af8fb6ab2e2e15f65")
                    .clientKey("ebe48418c0faaea3bee4ede298d571d663ddcde0")
                    .server("http://35.178.79.238:80/parse/")
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
