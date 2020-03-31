package com.example.ap_instagramclone;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import java.io.ByteArrayOutputStream;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class SharePictureTab extends Fragment implements View.OnClickListener {
    private Button mBtnShareImg;
    private EditText mEditImgDescription;
    private ImageView mImgShare;
    private ProgressBar mProgressBar;
    private ParseObject parseObject;
    private ParseFile mParseFile;
    private Bitmap receivedImageBitmap;
    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.imgPlace:
                // first this if stmt checks to see if permission has been previously granted
                // if it has, this is stored in,
            if(android.os.Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()),
                    Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

                // body of if stmt
                requestPermissions(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE
                        }
                        , 1000); // we have created this code number, which will be stored int
                                            // the manifest as a unique identifier of this READ_EXTERNAL_STORAGE request
            } else{
                getChosenImage();
            }
                break;

            case R.id.btnShare:

                Log.i("myTag","post image was pressed");

                if(receivedImageBitmap != null){

                    // check to see if the description was filled out. An empty string will indicate it wasn't.
                    if(mEditImgDescription.getText().toString().equals("")){
                        Toast.makeText(getContext(), "You need to add a description before submission", Toast.LENGTH_LONG).show();
                    }else{
                        mProgressBar.setVisibility(View.VISIBLE);

                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        receivedImageBitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                        byte[] bytes = byteArrayOutputStream.toByteArray();
                        ParseFile parseFile = new ParseFile("pic.png", bytes);
                        parseObject = new ParseObject("Photo");
                        parseObject.put("picture", parseFile);
                        parseObject.put("image_des", mEditImgDescription.getText().toString());
                        parseObject.put("username", ParseUser.getCurrentUser().getUsername());

                        parseObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e==null){
                                    //Toast.makeText(getContext(), "Done!!", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getContext(), "Your image has been posted to the server", Toast.LENGTH_LONG).show();

                                    mProgressBar.setVisibility(View.GONE);
                                }else{
                                    Toast.makeText(getContext(), "Error :"+e.getMessage(), Toast.LENGTH_LONG).show();
                                    Log.i("myTag", "the error is; "+e.getMessage());
                                }
                            }
                        });
                    }
                }else
                    Toast.makeText(getContext(), "You must select an image by clicking on the image " +
                            "above before posting your image", Toast.LENGTH_LONG).show();
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
        Toast.makeText(getContext(), "access to images was granted", Toast.LENGTH_LONG).show();
        Intent upLoadIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(upLoadIntent, 2000);
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
        if(requestCode==2000) {
            if (resultCode == Activity.RESULT_OK) {
                // Do something with the captured image.
                try {
                    assert data != null;
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    assert selectedImage != null;
                    Cursor cursor = Objects.requireNonNull(getActivity()).getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    receivedImageBitmap= BitmapFactory.decodeFile(picturePath);
                    mImgShare.setImageBitmap(receivedImageBitmap);
                    cursor.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("myTag", "the exception is; " + e.getMessage());
                }
            }
        }
    }
}

