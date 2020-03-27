package com.example.ap_instagramclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class tabAdapter extends FragmentPagerAdapter {

    int tabCount;

    public tabAdapter(@NonNull FragmentManager fm, int numberOfTabs) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.tabCount = numberOfTabs; // numberOfTabs is a variable we have created that holds the
                                    // value of the number of tabs open as determined by the getCount method
                                    // which returns its count to the tabCount variable
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0: // ranks the tab as first in line
                return new ProfileTab(); // returns that value to the method as an integer called position for case 0

            case 1:
                return new UsersTab();

            case 2:
                return new SharePictureTab();
            default:
                return null;
        }
    }
    public int getCount() {
        return tabCount;
    }

    @Nullable
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Profile";
            case 1:
                return "Users";
            case 2:
                return "Share Pictures";
            default:
                return null;
        }
    }
}




