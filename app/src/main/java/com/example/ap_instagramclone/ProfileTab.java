package com.example.ap_instagramclone;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.net.Uri;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * A simple {@link Fragment} subclass.
 */

public class ProfileTab extends Fragment {
    private EditText mEdtProfileName;
    private EditText mEdtProfession;
    private EditText mEdtHobbies;
    private EditText mEdtSports;
    private EditText mEdtBio;
    private Button mBtnSbmtInfo;

    public ProfileTab() {
        // Required empty public constructor
    }

    public interface OnFragmentInteractionListener {
        // TODO:  Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // we changed this from return inflater.inflate... to View view = inflater.inflate....
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);

        mEdtProfileName = view.findViewById(R.id.edtProfile2);// each findView must be preceded by view.
        mEdtHobbies = view.findViewById(R.id.edtHobbies2);// the normal format will not work in these fragment files
        mEdtProfession = view.findViewById(R.id.edtProfession);
        mEdtSports = view.findViewById(R.id.edtSports2);
        mEdtBio = view.findViewById(R.id.edtBio2);
        mBtnSbmtInfo = view.findViewById(R.id.btnInfo);

        final ParseUser parseUser = ParseUser.getCurrentUser(); // getting the current user from Back4.com

        // retrieve data from server to populate the edtText fields prior to the user submitting new info
        // will be null if not submitted previously

        if (parseUser.get("profileName") == null || parseUser.get("Bio")==null || parseUser.get("Profession") == null || parseUser.get("FavouriteSports") == null || parseUser.get("Hobbies") == null ) {
            mEdtProfileName.setText("");
            mEdtProfileName.setText("");
            mEdtBio.setText("");
            mEdtProfession.setText("");
            mEdtSports.setText("");
            mEdtHobbies.setText("");

        } else{// populate the edit texts with data from the server
            mEdtProfileName.setText(parseUser.get("profileName").toString());
            mEdtBio.setText(parseUser.get("Bio").toString());
            mEdtProfession.setText(parseUser.get("Profession").toString());
            mEdtSports.setText(parseUser.get("FavouriteSports").toString());
            mEdtHobbies.setText(parseUser.get("Hobbies").toString());

        }


            mBtnSbmtInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String ProfileName = mEdtProfileName.getText().toString();
                    String Hobbies = mEdtHobbies.getText().toString();
                    String Sports = mEdtSports.getText().toString();
                    String Profession = mEdtProfession.getText().toString();
                    String Bio = mEdtBio.getText().toString();
                    Log.i("myTag", "Bio = "+Bio);

                    // if any one field was not filled in the form, reset the field values to empty text
                    if (Bio.length()<1 || Hobbies.length()<1 || Sports.length()<1 || ProfileName.length()<1 || Profession.length()<1) {

                        mEdtProfileName.setText(null);
                        mEdtBio.setText(null);
                        mEdtProfession.setText(null);
                        mEdtSports.setText(null);
                        mEdtHobbies.setText(null);
                        Toast.makeText(getContext(), "You must fill all fields", Toast.LENGTH_SHORT).show();

                        // otherwise go ahead and put the input values to the parse server and give a toast message of success (or error)
                    }else{
                        parseUser.put("profileName", mEdtProfileName.getText().toString());
                        parseUser.put("Hobbies", mEdtHobbies.getText().toString());
                        parseUser.put("FavouriteSports", mEdtSports.getText().toString());
                        parseUser.put("Profession", mEdtProfession.getText().toString());
                        parseUser.put("Bio", mEdtBio.getText().toString());

                        parseUser.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Toast.makeText(getContext(), "Your data was saved successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Your data was NOT saved successfully" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            });
        // we also need to add a return statement that returns a view
        return view;
        }
    }

