package com.example.ap_instagramclone;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Objects;

import javax.security.auth.login.LoginException;

/**
 * A simple {@link Fragment} subclass.
 */
public class SharePictureTab extends Fragment implements View.OnClickListener {
    private Button mBtnShareImg;
    private EditText mEditImgDescription;
    private ImageView mImgShare;
    private ProgressBar mProgressBar;
    private ParseObject parseObject;
    private Bitmap bitmap;
    private byte[] bytes;
    ByteArrayOutputStream byteArrayOutputStream;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.imgPlace:

                // first this if stmt checks to see if permission has been previously granted
                // if it has, this is stored in,
                if (android.os.Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    // body of if stmt
                    requestPermissions(new String[]{
                                    Manifest.permission.READ_EXTERNAL_STORAGE
                            }
                            , 1000); // we have created this code number, which will be stored int
                    // the manifest as a unique identifier of this READ_EXTERNAL_STORAGE request
                } else {
                    getChosenImage();
                }
                break;

            case R.id.btnShare:

                mProgressBar.setVisibility(View.VISIBLE);

                // confirming there is an image using; if the image has any size to it, (i.e., exists)
                if(bitmap != null){

                    // check to see if the description was filled out. An empty string will indicate it wasn't.
                    if(mEditImgDescription.getText().toString().equals("")){
                        Toast.makeText(getContext(), "You need to add a description before submission", Toast.LENGTH_LONG).show();
                    }else{
                        // call an off thread method to prevent slowing, lagging, or hanging of UI such as progress bar
                        // AsyncTask method contains, file compression, parse commands/puts and save
                        AsyncTask progressBar = new progressBar().execute();
                        mProgressBar.setVisibility(View.VISIBLE);
                    }
                }else {
                    Toast.makeText(getContext(), "You must select an image by clicking on the image " +
                            "above before posting your image", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
    public SharePictureTab() {
        // Required empty public constructor
    }
    public interface OnFragmentInteractionListener{
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_share_picture_tab, container, false);
            mBtnShareImg = view.findViewById(R.id.btnShare);
            mEditImgDescription = view.findViewById(R.id.edtImageDescrip);
            mImgShare = view.findViewById(R.id.imgPlace);
            mImgShare.setOnClickListener(SharePictureTab.this);
            mBtnShareImg.setOnClickListener(SharePictureTab.this);

            mProgressBar = view.findViewById(R.id.picPostProgress);
            mProgressBar.setVisibility(View.INVISIBLE);

    return view;
    }
    private void getChosenImage() {
        // Toast.makeText(getContext(), "access to images was granted", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2000);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(1000, permissions, grantResults);

        // we check for a length greater than zero to see if there are any results in that integer array of grant results
        // we check for grantResults zero-th record because it should be the one that contains our transaction
        // we check to see if that record equals permission granted (that's a 0 integer, a notgranted is a -1)
        if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            //call method to get pic
            getChosenImage(); // see method above
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        // if the request code for the image specified in the getChosenImage() method == 2000
        Log.i("myTag", "onActivityResult was entered");
        if(requestCode==2000) {
            Log.i("myTag", "request code == 2000");
            if (resultCode == Activity.RESULT_OK) {
                Log.i("myTag", "resultCode == Activity.RESULT_OK");

                // Do something with the captured image.
                try {
                    assert data != null;
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Log.i("myTag", "filePathColumn = "+filePathColumn.toString());

                    assert selectedImage != null;
                    bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),selectedImage);

                    //Cursor cursor = Objects.@NonNull(getActivity()).getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//                    assert cursor != null;
//                    Log.i("myTag", "cursor = "+cursor);
//                    cursor.moveToFirst();
//                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                    Log.i("myTag", "column index 0 = "+columnIndex);

//                    String picturePath = cursor.getString(columnIndex);
//                    Log.i("myTag", "picturePath = "+picturePath);
                    // set the placeholder to have the same image as the one chosen from the users data
                    mImgShare.setImageBitmap(bitmap);

//                    cursor.close();

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("myTag", "the exception is; " + e.getMessage());
                }
            }else{
                Log.i("myTag", "result code was not ok");
            }
        }else{
            Log.i("myTag", "request code did not = 2000");
        }
    }

    // AsyncTask to isolate resource intensive task from main thread, thereby allowing progress bar to run
    // immediately and to prevent crashes should the "post image" button be pressed again or should the
    // screen be rotated (to landscape)
    private class progressBar extends AsyncTask <String, Void, ParseObject> {

        @Override
        protected ParseObject doInBackground(String... strings) {

                // check to see if the description was filled out. An empty string will indicate it wasn't.
            byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
            bytes = byteArrayOutputStream.toByteArray();
            ParseFile newPicture = new ParseFile("pic.png", bytes);
            parseObject = new ParseObject("Photo");
            parseObject.put("picture", newPicture);

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ParseObject s) {
            super.onPostExecute(s);
            parseObject.put("image_des", mEditImgDescription.getText().toString());
            parseObject.put("username", ParseUser.getCurrentUser().getUsername());
            parseObject.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        //Toast.makeText(getContext(), "Done!!", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getContext(), "Your image has been posted to the server", Toast.LENGTH_LONG).show();

                        mProgressBar.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(getContext(), "Error :" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}

