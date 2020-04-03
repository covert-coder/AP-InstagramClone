package com.example.ap_instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;
import java.util.Objects;

public class UsersPosts extends AppCompatActivity {

private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_posts);

        mLinearLayout=findViewById(R.id.linearLayoutScroll);

        // use the data sent from the UsersTab class
        Intent receivedIntentObject = getIntent(); //automatically gets any intent sent to this java class
        // store the data associated with the position clicked in the ListArray as "username" regardless of what it may be
        String receivedUsersName = receivedIntentObject.getStringExtra("username");

        //Toast.makeText(UsersPosts.this, receivedUsersName.toString(), Toast.LENGTH_SHORT).show();

        // set the title of the UsersPosts page to match the user photos accessed
        setTitle(receivedUsersName+ "'s pictures");

        // get the parsed data

        ParseQuery<ParseObject> parseQuery = new ParseQuery<>("Photo");
        // parse query where the username key we created, username, which intentionally matches the column name on
        // the parse server, is equal to whatever was retrieved from the arraylist field
        // i.e. "where username equals receivedUsersName" is the command being given to the parse server software
        parseQuery.whereEqualTo("username", receivedUsersName);
        // then sort the results by date/time submitted to the server
        parseQuery.orderByDescending("createdAt");

        // TODO create a progressBar to run while the photos download
        // TODO move the resource intensive code into an Async method

        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                // make sure text objects are valid from server (photo descriptions as posted by user)
                if(objects.size()>0 && e==null){
                    // get parseobjects and call each of them postedDescription, iteratively
                    for(final ParseObject postedDescription: objects){

                        // create a textview in code instead of in the xml
                        final TextView imageDescription = new TextView(UsersPosts.this);

                        // we have a column called image_des in the parse server. imageDescription is assigned the text content, iteratively
                        imageDescription.setText(Objects.requireNonNull(postedDescription.get("image_des")).toString());

                        // for, below, picture is the column on the parse server that contains photos
                        // postedDescription is the object created in the for arguments of tpe ParseObject
                        // iteratively, the photo will be downloaded and stored in a variable called postPicture
                        // It manages to cast to a parse file using: (ParseFile)
                        // each picture is thus associated with each description
                        ParseFile postPicture = (ParseFile) postedDescription.get("picture");

                        // now we iteratively retrieve the photos as byte arrays (still in the for loop)
                        postPicture.getDataInBackground(new GetDataCallback() {
                            @Override
                            // done has parameters of byte array data, and the error, if any. If not then == null.
                            // get the data as an array of bytes called data
                            public void done(byte[] data, ParseException e) {

                                // is the retrieved data from the server sound?
                                if(data != null && e==null){
                                    // byte array data (uncompressed) is decoded then stored in bitmap, length variable, as a bitmap
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

                                    // create an image view in code, called postImageView (instance of ImageView)
                                    ImageView postImageView = new ImageView(UsersPosts.this);

                                    // below, we set all of the parameters for our ImageView that would ordinarily be in the layout file
                                    // here we create a ViewGroup with parameters that match the parent and wrap content (width and height);
                                    // that parent being the linear layout in the activity_users_posts layout)
                                    LinearLayout.LayoutParams imageView_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT); // the ViewGroup is called imageView_params

                                    //  now margins are set on the viewgroup
                                    imageView_params.setMargins(5,0,5,0);
                                    // now the instantiated imageView postImageView is given the same parameters as the view group
                                    postImageView.setLayoutParams(imageView_params);
                                    // the image view is centered
                                    postImageView.setScaleType(ImageView.ScaleType.FIT_START);
                                    // and the contents of the imageview are set to the bitmap retrieved from the parse server
                                    postImageView.setImageBitmap(bitmap);
                                    postImageView.setAdjustViewBounds(true);


                                    // parameters are still needed for the text view imageDescription
                                    LinearLayout.LayoutParams des_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT);
                                    des_params.setMargins(5,5,5,5);
                                    imageDescription.setLayoutParams(des_params);
                                    imageDescription.setGravity(Gravity.CENTER);
                                    imageDescription.setBackgroundColor(Color.BLUE);
                                    imageDescription.setTextColor(Color.WHITE);
                                    imageDescription.setTextSize(24f);

                                    // now add the two UI components to the layout

                                    mLinearLayout.addView(imageDescription);
                                    mLinearLayout.addView(postImageView);
                                }
                            }
                        });
                    }
                }else{
                    Toast.makeText(UsersPosts.this, "This user does " +
                            "not have any photo posts", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

    }
}
