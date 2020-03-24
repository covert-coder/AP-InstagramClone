package com.example.ap_instagramclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUp extends AppCompatActivity {

    private EditText SignUpPass, SignUpLogin, ExistingPass, ExistingLogin;
    private Button btnSignUp, btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        SignUpPass = findViewById(R.id.edittxtPasswordNew);
        SignUpLogin = findViewById(R.id.edttxtLoginNew);
        ExistingLogin= findViewById(R.id.edittxtLogin);
        ExistingPass = findViewById(R.id.edttxtPassword);

        btnSignUp=findViewById(R.id.btnSignUpUser);
        btnLogin=findViewById(R.id.btnLoginUser);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser appUser = new ParseUser();
                appUser.setUsername(SignUpLogin.getText().toString());
                appUser.setPassword(SignUpPass.getText().toString());

                appUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(SignUp.this, "your password and login were " +
                                    "set successfully;", Toast.LENGTH_LONG).show();

                            Intent welcomeIntent = new Intent(SignUp.this, WelcomeActivity.class);
                            startActivity(welcomeIntent);
                        }
                        else{
                            Toast.makeText(SignUp.this, "your password and login were " +
                                    "not successful;"+e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            ParseUser.logInInBackground(ExistingLogin.getText().toString(),
                    ExistingPass.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if(user!=null && e==null){
                                Toast.makeText(SignUp.this, "your login was successful "+user.get("username")
                                        , Toast.LENGTH_LONG).show();
                                Intent welcomeIntent = new Intent(SignUp.this, WelcomeActivity.class);
                                startActivity(welcomeIntent);
                            }else{
                                Toast.makeText(SignUp.this, "your login failed: "+ e.getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
            }
        });
    }
}

