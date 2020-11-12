package com.example.ap_instagramclone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsersTab extends Fragment implements AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener {

    private ArrayList<String> arrayList;
    private ListView mListView;
    private ArrayAdapter<String> mAdapter;

    // below is an empty constructor that is autogenerated in the fragment
    public UsersTab() {
        // Required empty public constructor
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // a new Intent to broadcast to UsersPosts from this class (the getContext())
        Intent intent = new Intent(getContext(), Users_Posts.class);
        // send the value in the username field of the arrayList, as per the above intent, to
        // UsersPosts with keyword username
        intent.putExtra("username", arrayList.get(position));// putExtra is used when you want
        // to send data to another class or fragment
        // "username" is the key and arrayList.get(position) is the value of
        // that key
        // arrayList holds all of the user data such as names,hobbies, etc,
        // as strings, in successive array fields
        // the value, "arrayList.get(position) is passed to UsersPosts.  In UsersPosts, we must now
        // get that value.
        // move to that class to see the code

        // the Intent, called intent, must be started, or nothing will happen
        startActivity(intent);
    }
    // this method is called when the user holds down on the username in the usersTab list of users
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        // TODO: working on a code block to create a custom notice (alert) containing the
        // TODO: listed stats for the user (modelling on custom alert in profile tab
        // this intent is directed to UserStats
        Intent intent2 = new Intent(getContext(), UserStats.class);
        // as before, the value from the username field in the array list is sent
        intent2.putExtra("username", arrayList.get(position));
        // start this intent.  Notice, it has a different name than the earlier intent with a
        // different target
        startActivity(intent2);
        // this method requires a boolean return value
        return true;
    }

    // this interface is also autogenerated as is, the onCreateView, up to the end of the inflater
    // statement
    public interface OnFragmentInteractionListener{
        // TODO:  Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users_tab, container, false);

        // assign the ListView
        mListView = view.findViewById(R.id.listView);

        // assign the array for storing the usernames in the for loop below
        arrayList = new ArrayList<>();

        // set the ListView as the onItemClickListener (context is the parameter)
        // now an instance of the users tab, List View mListView is going to be the
        // itemOnClickListener
        // specifically, for items inside the ListView such that, tapping on an item will trigger
        // the listener
        mListView.setOnItemClickListener(UsersTab.this);
        mListView.setOnItemLongClickListener(UsersTab.this);

        final ParseUser parseUser = ParseUser.getCurrentUser();
        // set the type of parse query to <ParseUser>
        final ParseQuery<ParseUser> userQuery = ParseUser.getQuery();

        // filter out the currentUser using "where not equal to" followed by what we want to filter out
        // that exception is the user name and we get it using ParseUser.get.....
        userQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());

        // now we create the "findInBackground method with a callback to find all objects that
        // match the query of type ParseUser
        // less the excluded current user
        userQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> ourUserList, ParseException e) {

                if(e==null){
                    if(ourUserList.size()>0){

                        for(ParseUser user: ourUserList){
                            // this is where we will create our array list, but we first needed to
                            // initialize and define
                            // that array in our class userTab and in the onCreateView, above
                            // the parameters of ArrayAdapter are; the context, the line item
                            // designated to populate the array, and the name of our array
                            mAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                                    android.R.layout.simple_list_item_1, arrayList);
                            // get the next username in the database and add it to the list
                            arrayList.add(user.getUsername());
                            // add a straight line separator on the array list to separate the users
                            // visually on the page
                            arrayList.add("_______________________________________");

                            // let the adapter know where we are in the list as iteration progresses
                            mAdapter.notifyDataSetChanged();
                        }
                        // set the adapter to our ListView, mListView so we can see the list on the
                        // screen(ListView)
                        mListView.setAdapter(mAdapter);
                    }else{
                        Toast.makeText(getContext(), "No records to show, or, data retrieval error. " +
                                "Try again later", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        return view;
    }
    public void stopTheAlert() {
        // get access to the supportFragmentManager
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        // if there is anything in the stack
        if (fragmentManager.getBackStackEntryCount() > 0) {
            //get the last transaction
            fragmentManager.popBackStack();
        }
    }
}
