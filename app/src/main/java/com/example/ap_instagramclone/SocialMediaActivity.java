package com.example.ap_instagramclone;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.net.Uri;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

public class SocialMediaActivity extends AppCompatActivity implements ProfileTab.OnFragmentInteractionListener, UsersTab.OnFragmentInteractionListener, SharePictureTab.OnFragmentInteractionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);

        setTitle("Instagram Clone Demonstration App!"); // sets the title of the action bar

        FloatingActionButton floatingActionButton = findViewById(R.id.fab); // assign and instantiate a circular floating action button (message icon)

        // when the button is pushed, the snackbar appears at the screen bottom
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
        // it is a child of the AppBarLayout in the same file and appears as a view in the app bar
        // add tabs to tabLayout
        tabLayout.addTab(tabLayout.newTab().setText("Profile"));
        tabLayout.addTab(tabLayout.newTab().setText("Users"));
        tabLayout.addTab(tabLayout.newTab().setText("Picture Share"));

        // the ViewPager handles scrolling/sliding of pages, is a member of the View superclass
        // a pager adapter is assigned to this View class that is responsible for handling the fragments (tabs)
        // pager is the name assigned to the ViewPager in the activity_social_media xml layout
        final ViewPager viewPager = findViewById(R.id.pager); // instantiated and assigned the "viewPager"

        // the statement below, creates a new instance of the class tabAdapter (tabAdapter.java)
        // tabAdaptor.java extends the class FragmentPagerAdapter
        // accessing the tabAdapter class and the methods therein.  This tab adapter is thus a PagerAdapter
        final PagerAdapter adapter = new tabAdapter(getSupportFragmentManager(), tabLayout.getTabCount()); //

        // the pager adapter, of tabAdapter class, is set to our viewPager object in the xml file
        viewPager.setAdapter(adapter);

        // tabLayout, an object of class TabLayout contains our three tabs in a horizontal array
        // adding an OnPageChangeListener to the viewPager with the contents of tabLayout
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // the onTabSelectedListener is now added to that "three-tab" tabLayout for tab specificity
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // position is determined by where the user taps on the tab array on the action bar
                // this is built-in with TabLayout and is not the same "position" as the variable in tabAdapter.java
                // that value is passed to tabAdapter.java, regardless, for use as "position" in that class by
                // "setCurrentItem" and by virtue of the fact that viewPager is of tabAdapter class
                viewPager.setCurrentItem(tab.getPosition());

                // change the title of the page in the app bar as the tabs are selected, to match content
                // getPageTitle is a method in tabAdapter and "adapter" is of tabAdapter class so the method can be accessed via
                // adapter.  The tab position, intentionally, has the same value as position in tabAdapter so it can
                // be substituted in that method to arrive at the desired fragment (tab) heading
                String title = (adapter.getPageTitle(tab.getPosition())).toString();
                setTitle(title);
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




