package com.example.ap_instagramclone;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class InstagramCloneMainPage extends AppCompatActivity implements View.OnClickListener {
    private EditText mPasswordInstag;
    private EditText mUserNameInstag;
    private EditText mEmailInstag;
    private Button mSignUpBtn, mLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram_clone_main_page);

        setTitle("Sign Up for Instagram"); // sets the title in the action bar for this main page

        // logout any user that is logged in
        if (ParseUser.getCurrentUser() != null) {
            ParseUser.getCurrentUser().logOut();
            Log.i("myTag", "current user was logged out");
        }
        // assign edit texts to variables
        mUserNameInstag = findViewById(R.id.txtUserInstagram);
        mEmailInstag = findViewById(R.id.txtEmailInstagram);
        mPasswordInstag = findViewById(R.id.txtPassInstagram);
        mSignUpBtn = findViewById(R.id.btnSignupInstagram);
        mLoginBtn = findViewById(R.id.btnLoginInstagram);

        // assigning the two buttons to the implemented onClickListener
        mSignUpBtn.setOnClickListener(this);
        mLoginBtn.setOnClickListener(this);

        // allowing the user to enter their data using the enter key by setting an
        // onKeyListener that looks for the enter key click and press down
        // set to the password field because it is the last one filled by the user (last in sequence on screen)
        mPasswordInstag.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN){

                    onClick(mSignUpBtn); // calls the onClick assigned to the signUP button as though it was clicked
                    Log.i("myTag","enter key pressed");
                }
                return false;
            }
        });

        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParseObject mInstagram = new ParseObject("InstagramClone");
        mInstagram.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Log.i("myTag", "the new class InstagramClone, was created");
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnLoginInstagram:
                Log.i("myTag", "login button was pushed");
                Intent intentLogin = new Intent(InstagramCloneMainPage.this,
                        InstagramCloneLoginPage.class);
                startActivity(intentLogin);
                break;

            case R.id.btnSignupInstagram:
                final ParseUser appUser = new ParseUser();
                appUser.setUsername(mUserNameInstag.getText().toString());
                appUser.setPassword(mPasswordInstag.getText().toString());
                appUser.setEmail(mEmailInstag.getText().toString());

                //create a progress dialog to notify the user that their signup is progressing
                final ProgressDialog signUpDialog = new ProgressDialog(InstagramCloneMainPage.this);
                signUpDialog.setTitle("Working");
                signUpDialog.setMessage(mUserNameInstag.getText().toString() + " Your signUp is in progress");
                signUpDialog.show();

                // check for an empty field in password, username, email address
                if (mEmailInstag.getText().toString().equals("") || mPasswordInstag.getText().toString().equals("")
                        || mPasswordInstag.getText().toString().equals("")) {
                    Toast.makeText(InstagramCloneMainPage.this, "email address, password, " +
                            "and username must be provided", Toast.LENGTH_LONG).show();
                    signUpDialog.dismiss();
                }
                else{
                        appUser.signUpInBackground(new SignUpCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Toast.makeText(InstagramCloneMainPage.this, "your password and login were " +
                                            "set successfully;", Toast.LENGTH_LONG).show();

                                } else {
                                    Toast.makeText(InstagramCloneMainPage.this, "your password and login were " +
                                            "not successful;" + e.getMessage(), Toast.LENGTH_LONG).show();


                                }
                                signUpDialog.dismiss();
                            }
                        });
                        break;

                    }
                }
        }
    }




