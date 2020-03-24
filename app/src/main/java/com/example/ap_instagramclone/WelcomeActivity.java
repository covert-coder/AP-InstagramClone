package com.example.ap_instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class WelcomeActivity extends AppCompatActivity {

    private TextView textViewWelcome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        textViewWelcome= findViewById(R.id.txtWelcome);

        textViewWelcome.setText("Welcome " + ParseUser.getCurrentUser().getUsername());

        // this onClickListener for the logout button was created without first creating
        // either a variable or doing its assignment
        findViewById(R.id.btnLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut(); // logs out of the parse server
                finish(); // finishes the current activity and returns the user to the activity linking
                            // to this one through the intent statement
            }
        });

            }

    }

