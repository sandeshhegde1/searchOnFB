package com.example.sanh.facebook.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.sanh.facebook.Fragments.FavEventFragment;
import com.example.sanh.facebook.Fragments.FavGroupFragment;
import com.example.sanh.facebook.Fragments.FavPageFragment;
import com.example.sanh.facebook.Fragments.FavPlaceFragment;
import com.example.sanh.facebook.Fragments.FavUserFragment;

/**
 * Created by San H on 4/26/2017.
 */
public class FavPagerAdapter  extends FragmentStatePagerAdapter {

    private int mNumOfTabs;



    public FavPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;

    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                FavUserFragment userTab = new FavUserFragment();
                return userTab;
            case 1:
                FavPageFragment pageTab = new FavPageFragment();
                return pageTab;
            case 2:
                FavEventFragment eventTab = new  FavEventFragment();
                return eventTab;
            case 3:
                FavPlaceFragment placeTab = new FavPlaceFragment();
                return placeTab;
            case 4:
                FavGroupFragment groupTab = new FavGroupFragment();
                return groupTab;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }


}
