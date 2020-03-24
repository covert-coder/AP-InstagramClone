package com.example.ap_instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class InstagramCloneMainPage extends AppCompatActivity {
    private EditText mPasswordInstag;
    private EditText mUserNameInstag;
    private EditText mEmailInstag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram_clone_main_page);

        ParseUser.logOut();

        mUserNameInstag = findViewById(R.id.txtUserInstagram);
        mEmailInstag = findViewById(R.id.txtEmailInstagram);
        mPasswordInstag = findViewById(R.id.txtPassInstagram);

        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParseObject mInstagram = new ParseObject("InstagramClone");
        mInstagram.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Log.i("myTag","the new class InstagramClone, was created");
            }
        });

        findViewById(R.id.btnLoginInstagram).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogin = new Intent(InstagramCloneMainPage.this,
                        InstagramCloneLoginPage.class);
                startActivity(intentLogin);
            }
        });

        findViewById(R.id.btnSignupInstagram).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseUser appUser = new ParseUser();
                appUser.setUsername(mUserNameInstag.getText().toString());
                appUser.setPassword(mPasswordInstag.getText().toString());
                appUser.setEmail(mEmailInstag.getText().toString());

                appUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(InstagramCloneMainPage.this, "your password and login were " +
                                    "set successfully;", Toast.LENGTH_LONG).show();

                            Intent welcomeIntent = new Intent(InstagramCloneMainPage.this, WelcomeActivity.class);
                            startActivity(welcomeIntent);
                        }
                        else{
                            Toast.makeText(InstagramCloneMainPage.this, "your password and login were " +
                                    "not successful;"+e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}
