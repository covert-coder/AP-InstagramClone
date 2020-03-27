package com.example.ap_instagramclone;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.net.Uri;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.widget.ViewPager2;

public class SocialMediaActivity extends AppCompatActivity implements ProfileTab.OnFragmentInteractionListener, UsersTab.OnFragmentInteractionListener, SharePictureTab.OnFragmentInteractionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);
        Toolbar toolbar = findViewById(R.id.mytoolbar);
        //setSupportActionBar(toolbar);



        setTitle("Social Media App !!");
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Press tabs", Snackbar.LENGTH_LONG).setAction("Action", null).show();

            }
        });

        configureTabLayout();  // calls the function below
    }
    private void configureTabLayout() {
        TabLayout tabLayout = findViewById(R.id.tab_layout); // the id for this layout was added to the
        //xml file activity_social_media.xml
        // it is a child of the AppBarLayout in the same file
        // add tabs to tabLayout
        tabLayout.addTab(tabLayout.newTab().setText("Profile"));
        tabLayout.addTab(tabLayout.newTab().setText("Users"));
        tabLayout.addTab(tabLayout.newTab().setText("Picture Share"));

        final ViewPager viewPager = findViewById(R.id.pager);
        // the statement below, creates a new instance of the class tabAdapter thereby accessing the tabAdapter class
        // and the methods therein
        final PagerAdapter adapter = new tabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
        @Override
        public void onFragmentInteraction(Uri uri){

        }

    }

//    private androidx.appcompat.widget.Toolbar toolbar;
//    private com.google.android.material.tabs.TabLayoutMediator mTabLayoutMediator;
//    private ViewPager2 mViewPager2;
//    private TabLayoutMediator mLayoutMediator;
//    private TabLayout mTabLayout;
//    private androidx.viewpager.widget.ViewPager mViewPager;
//    private FragmentStatePagerAdapter mTabAdapter;


