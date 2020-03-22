package com.example.ap_instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mName, mStrength, mSpeed, mStamina;
    private Button mBtnSave;
    private TextView mTxtGetData;
    private ParseObject mKickBoxer;
    private Button mRetrieveAll;
    private String mAllKickBoxers;

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
        mBtnSave = findViewById(R.id.button);
        mBtnSave.setOnClickListener(MainActivity.this);
        mTxtGetData= findViewById(R.id.txtRetrieveData);
        mRetrieveAll = findViewById(R.id.btnRetrieveAllData);
        mAllKickBoxers = "";

        mKickBoxer = new ParseObject("kickBoxer");

        mTxtGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("kickBoxer");
                parseQuery.getInBackground("nBTd5NAPg3", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (object!=null && e==null){
                            mTxtGetData.setText(object.get("name").toString()+":" + " strength: "+
                                object.get("strength")+", speed: " + object.get("speed")
                                +", stamina:"+ object.get("stamina"));
                    }
                    }
                });
            }
        });mRetrieveAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("kickBoxer");
                queryAll.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (objects.size()>0 && e==null){

                            for(ParseObject KickBoxerParsed: objects){
                                mAllKickBoxers = mAllKickBoxers + KickBoxerParsed.get("name").toString()+"\n";
                            }
                            // when the query process above is completed then, inside this done message
                            // we can use the for loop code to extract the info and store it in mAllKickBoxers
                            // rather than a single object, this gets all of the data objects from kickBoxer class
                            Toast.makeText(MainActivity.this, "the contents of objects is;  "+"\n" +mAllKickBoxers,
                                    Toast.LENGTH_LONG).show();
                            // our output to the user is in the form of a toast message listing the
                            // names of our parse server data entries.
                        }else{
                            Log.i("myTag", "the value of objects is; "+objects);
                            Toast.makeText(MainActivity.this, "data retrieval error: " +e,
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
    @Override
    public void onClick(View v) {

        try {
            // create a parse object called kickBoxer of class kickBoxer
            Log.i("myTag; ", "" + mSpeed.getText().toString() + " " + mStamina.getText().toString()
                    + " " + mStrength.getText().toString() + " " + mName.getText().toString());
            // assigning values to data categories on the parse server from input text/integers
            mKickBoxer.put("stamina", Integer.parseInt(mStamina.getText().toString()));
            mKickBoxer.put("name", mName.getText().toString());
            mKickBoxer.put("strength", Integer.parseInt(mStrength.getText().toString()));
            mKickBoxer.put("speed", Integer.parseInt(mSpeed.getText().toString()));
            mKickBoxer.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Toast.makeText(MainActivity.this, "Your kickboxer stats were successfully saved",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Your save is incomplete, Try again " + e,
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Your input format is incorrect. " +
                            "If an integer is expected, then enter an integer. Try again " + e,
                    Toast.LENGTH_LONG).show();
        }
    }
}





