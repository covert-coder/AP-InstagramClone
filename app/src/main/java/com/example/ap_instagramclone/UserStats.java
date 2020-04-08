package com.example.ap_instagramclone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Objects;

public class UserStats extends AppCompatActivity {

    private TextView userStats;
    private Button btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_stats);

        // extras and intents from other sources are bundled. This retrieves the intents and extras
        // directed at this class.  In this case, from UsersTab.java, long-click method.
        Bundle extras = getIntent().getExtras();
        // pull the string from the extra put with the key name "username". This is the value of the
        // object in the ListArray from the UsersTab.  It will also be the username of that user in the database.
        String receivedUsersName2 = extras.getString("username");

        userStats = findViewById(R.id.txtUserStats);
        btnClose=findViewById(R.id.btnCloseStats);
        // a close button is coded for the frame layout of this userStats class
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // end this activity
                finish();
                // assign and start the socialMediaActivity (was unable to start UsersTab)(cannot cast to Activity class)
                Intent intent3 = new Intent(UserStats.this, SocialMediaActivity.class);
                startActivity(intent3);
            }
        });

        // ParseQuery to gain access to ParseUser information (columns). Name of query is parseQuery
        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();

        // where the "username" on the parse server equals the content in the arrayList at that position
        parseQuery.whereEqualTo("username", receivedUsersName2);

        // get the data off the parse server
        parseQuery.getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser ourTargetedUser, ParseException e) {
                if (ourTargetedUser != null && e == null) {
                    // set the output to the TextView to show the user the stats for this user selected
                    userStats.setText("Username: "+ ourTargetedUser.get("username").toString() + "\n" +"\n" + "Hobbies: "
+                            ourTargetedUser.get("Hobbies").toString() +
                            "\n" + "\n" +"Favourite Sports: "+ ourTargetedUser.get("FavouriteSports").toString() +
                            "\n" +"\n" + "Profession: " + ourTargetedUser.get("Profession").toString());
                }
            }
        });
    }
}


