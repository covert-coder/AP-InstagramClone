package com.example.ap_instagramclone;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomAlert extends Fragment {

    private TextView mTextView1;
    private TextView mTextView2;
    private ProgressBar mProgressBar;
    private View myLayout;
    private View mFrame;

    public CustomAlert() {
        public class FragmentKiller extends Fragment {

            public void doSomething(String killFrag) {
                if (killFrag.equals("true")){
                    mProgressBar.setVisibility(View.GONE);
                    mFrame.setVisibility(View.GONE);
                    mTextView1.setVisibility(View.GONE);
                    mTextView2.setVisibility(View.GONE);
                }
            }
        }


    }

    public static void doSomething(String aTrue) {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_alert_custom, container, false);

        Intent intent = new Intent(getContext(), UsersPosts.class);

        mProgressBar=view.findViewById(R.id.progressBar2);
        mProgressBar= new ProgressBar(getContext());
        mFrame=view.findViewById(R.id.frameLayout);

        return view;
    }



}
