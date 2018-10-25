package com.gbk.simoni.gbk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.parse.ParseAnalytics;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);

                /*

        if (ParseUser.getCurrentUser() != null){
            Intent alreadyLoggedIn = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(alreadyLoggedIn);
        }

        */

        ParseAnalytics.trackAppOpenedInBackground(getIntent());


    }
}
