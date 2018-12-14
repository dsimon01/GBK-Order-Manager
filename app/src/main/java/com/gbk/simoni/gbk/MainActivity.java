package com.gbk.simoni.gbk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.parse.ParseAnalytics;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity{

    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.main_activity_text);

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        if (ParseUser.getCurrentUser() != null){
            Intent alreadyLoggedIn = new Intent(getApplicationContext(), OrderManager.class);
            startActivity(alreadyLoggedIn);
        }
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }
}