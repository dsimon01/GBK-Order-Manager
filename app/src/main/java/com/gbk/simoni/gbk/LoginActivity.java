package com.gbk.simoni.gbk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

// test commit -

public class LoginActivity extends AppCompatActivity {

    public void onLoginClicked(View view){

        EditText username = findViewById(R.id.usernameEditText);
        EditText password = findViewById(R.id.passwordEditText);

        if (username.getText().toString().matches("") || password.getText().toString().matches("")){

            Toast.makeText(this, "Username/Password required.", Toast.LENGTH_SHORT).show();

        }else {

            ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {

                    if (e == null){

                        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(intent);
                    }

                }
            });

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
