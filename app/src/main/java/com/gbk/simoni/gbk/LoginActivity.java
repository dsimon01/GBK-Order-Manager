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


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /*
    Method that handles user authentication when the user chooses to login.
    If the input is empty or does not match any stored data in the database
    the login will fail, otherwise the user can login successfully.
    Upon successful login, the user will be directed to the OrderManager Activity.
     */

    public void onLoginClicked(View view){
        EditText username = findViewById(R.id.usernameEditText);
        EditText password = findViewById(R.id.passwordEditText);
        if (username.getText().toString().matches("") || password.getText().toString()
                .matches("")){
            Toast.makeText(this, "Username/Password required.",
                    Toast.LENGTH_SHORT).show();
        }else {
            ParseUser.logInInBackground(username.getText().toString(), password.getText()
                    .toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (e == null){
                        Intent intent = new Intent(getApplicationContext(),OrderManager.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(LoginActivity.this, e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    // Disables back button in this activity.
    @Override
    public void onBackPressed() {
        // super.onBackPressed(); commented this line in order to disable back press
    }
}
