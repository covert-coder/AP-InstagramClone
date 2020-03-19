package com.example.ap_instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mName, mStrength,mSpeed, mStamina;
    private Button mBtnSave;

//public void hellowWorldTap(View view){
//ParseObject boxer = new ParseObject("Boxer");
//boxer.put("punch_speed", 200);
//boxer.saveInBackground(new SaveCallback() {
//    @Override
//    public void done(ParseException e) {
//        if(e == null){
//            Toast.makeText(MainActivity.this,"Your boxer punch speed was successfully saved",
//                    Toast.LENGTH_LONG).show();
//        }
//        else{
//            Toast.makeText(MainActivity.this,"Your save is incomplete, Try again "+ e,
//                    Toast.LENGTH_LONG).show();
//        }
//    }
//});
//    ParseObject kickBoxer = new ParseObject("kickBoxer");
//    kickBoxer.put("punch_speed", 300);
//    kickBoxer.put("name", "Fred");
//    kickBoxer.put("kick_power", 150);
//    kickBoxer.put("speed", 325);
//    kickBoxer.saveInBackground(new SaveCallback() {
//        @Override
//        public void done(ParseException e) {
//            if(e == null){
//                Toast.makeText(MainActivity.this,"Your kickboxer punch speed was successfully saved",
//                        Toast.LENGTH_LONG).show();
//            }
//            else{
//                Toast.makeText(MainActivity.this,"Your save is incomplete, Try again "+ e,
//                        Toast.LENGTH_LONG).show();
//            }
//        }
//    });
//}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // following code tracks installs to the Back4app parse server
        ParseInstallation.getCurrentInstallation().saveInBackground();
        // assign the variables to the respective editText views
        mSpeed = findViewById(R.id.txtSpeed);
        mStamina = findViewById(R.id.txtStamina);
        mStrength = findViewById(R.id.txtStrength);
        mName = findViewById(R.id.txtName);
        mBtnSave=findViewById(R.id.button);
        mBtnSave.setOnClickListener(MainActivity.this);
    }
    @Override
    public void onClick(View v) {
        ParseObject kickBoxer = new ParseObject("kickBoxer");
        Log.i("myTag; ",""+mSpeed.getText().toString() +" "+mStamina.getText().toString()
                +" "+mStrength.getText().toString()+" "+mName.getText().toString());
        kickBoxer.put("stamina", Integer.parseInt(mStamina.getText().toString()));
        kickBoxer.put("name", mName.getText().toString());
        kickBoxer.put("strength", Integer.parseInt(mStrength.getText().toString()));
        kickBoxer.put("speed",Integer.parseInt(mSpeed.getText().toString()));

        kickBoxer.saveInBackground(new SaveCallback() {
            @Override
        public void done(ParseException e) {
            if(e == null){
                Toast.makeText(MainActivity.this,"Your kickboxer stats were successfully saved",
                        Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(MainActivity.this,"Your save is incomplete, Try again "+ e,
                        Toast.LENGTH_LONG).show();
            }
        }
    });
    }
}
