package com.example.ap_instagramclone;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.Objects;

public class InstagramCloneMainPage extends AppCompatActivity implements View.OnClickListener {
    private EditText mPasswordInstag;
    private EditText mUserNameInstag;
    private EditText mEmailInstag;
    private Button mSignUpBtn, mLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram_clone_main_page);

        ParseUser.getCurrentUser();
        ParseUser.logOut();
        setTitle("Sign Up for Instagram"); // sets the title in the action bar for this main page

//        Toast.makeText(this, "current user name is "
//                + ParseUser.getCurrentUser().getUsername().toString(), Toast.LENGTH_LONG).show();

        // logout any user that is logged in
        if (ParseUser.getCurrentUser() != null) {

            transitionToSocialMediaActivity();
            Log.i("myTag", "current user was transitioned to the media page");
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
    }
    // onClick implemented by main class
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // the login button is pressed sending user to the login screen
            case R.id.btnLoginInstagram:
                Log.i("myTag", "login button was pushed sending user to login screen");
                Intent intentLogin = new Intent(InstagramCloneMainPage.this,
                        InstagramCloneLoginPage.class);
                startActivity(intentLogin);
                break;
            // signup button is pressed, sending data to the parse server
            // and generating a progress dialog
            // plus error checking
            case R.id.btnSignupInstagram:

                Log.i("myTag", "signup new button was pushed for submission of registration");



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
                    signUpDialog.dismiss(); // signup is not occurring so..
                    Log.i("myTag", "a field was left unfilled and the dialog was dismissed");
                }
                // but.., if all fields have been filled, then
                else{
                        appUser.signUpInBackground(new SignUpCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Toast.makeText(InstagramCloneMainPage.this, "your password and login were " +
                                            "set successfully;", Toast.LENGTH_LONG).show();
                                    signUpDialog.dismiss();
                                    // possibility of server error since e is not null
                                    Log.i("myTag", "sign up included all fields and dialog was dismissed");
                                    transitionToSocialMediaActivity();
                                } else {
                                    Toast.makeText(InstagramCloneMainPage.this, "your password and login were " +
                                            "not successful;" + e.getMessage(), Toast.LENGTH_LONG).show();
                                    signUpDialog.dismiss();
                                    Log.i("myTag", "there was a problem with parse server sign up and the dialog was dismissed");

                                }
                            }
                        });
                        break; // break from case R.id.btnSignupInstag
                    }
                } // end of switch stmt
        } // end of implemented onClick containing switch stmts

    // the method below is an onClickListener for the constraint layout of the main activity page
    // clicking anywhere on the page (other than established UI's) triggers the method
    public void rootLayoutTapped(View layoutView) {
        // encapsulate in a try / catch to catch the error generated when the user taps the screen and the
        // keyboard is not displayed.  This causes a crash without this code.
        try {
            // access the inputMethodManager
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            // use the manager to hide the current focus (the keyboard) by getting its token
            assert inputMethodManager != null;
            inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace(); // sends some info to the stack regarding the error generated instead of causing a crash
        }
    }
    // this method, takes us to the social media page when signup is complete
    private void transitionToSocialMediaActivity(){
            Intent intentSocialActivity = new Intent(InstagramCloneMainPage.this, SocialMediaActivity.class);
            startActivity(intentSocialActivity);
    }
} // end of main class




