package com.example.ap_instagramclone;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.service.autofill.FillEventHistory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */


public class CustomAlert extends Fragment {

    // this fragment is used to produce a custom progress alert with a spinning progress wheel
    // for use during image download from the UsersTab page

    private TextView mTextView1;
    private TextView mTextView2;
    private ProgressBar mProgressBar;
    private View myLayout;
    private View mFrame;

    public CustomAlert() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_alert_custom, container, false);

        Intent intent = new Intent(getContext(), UsersPosts.class);
        mProgressBar = view.findViewById(R.id.progressBar2);
        mProgressBar = new ProgressBar(getContext());
        mProgressBar.setVisibility(View.VISIBLE);

        return view;
    }

}


