package com.example.ap_instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class InstagramCloneLoginPage extends AppCompatActivity {

    private EditText txtEmailOnfile;
    private EditText txtPassOnfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram_clone_login_page);

        txtEmailOnfile = findViewById(R.id.txtEmailExist);
        txtPassOnfile = findViewById(R.id.txtPassExist);


        findViewById(R.id.btnLoginExisting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logInInBackground(txtEmailOnfile.getText().toString(),
                        txtPassOnfile.getText().toString(), new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                if(user!=null && e==null){
                                    Toast.makeText(InstagramCloneLoginPage.this, "your login was successful "+user.get("username")
                                            , Toast.LENGTH_LONG).show();
                                    Intent welcomeIntent = new Intent(InstagramCloneLoginPage.this, WelcomeActivity.class);
                                    startActivity(welcomeIntent);
                                }else{
                                    Toast.makeText(InstagramCloneLoginPage.this, "your login failed: "+ e.getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

        findViewById(R.id.btnSignupReturn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSignUp = new Intent(InstagramCloneLoginPage.this,
                        InstagramCloneMainPage.class);
                startActivity(intentSignUp);
            }
        });
    }
}
