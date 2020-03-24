package com.example.ap_instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InstagramCloneMainPage extends AppCompatActivity {
    private EditText mLoginInstag;
    private EditText mSignupInstag;
    private EditText mEmailInstag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram_clone_main_page);

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

            }
        });


    }
}
