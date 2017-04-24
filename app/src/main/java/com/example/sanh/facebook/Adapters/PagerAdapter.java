package com.example.sanh.facebook.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.sanh.facebook.Fragments.EventFragment;
import com.example.sanh.facebook.Fragments.GroupFragment;
import com.example.sanh.facebook.Fragments.PageFragment;
import com.example.sanh.facebook.Fragments.PlaceFragment;
import com.example.sanh.facebook.Fragments.UserFragment;

/**
 * Created by San H on 4/23/2017.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    private int mNumOfTabs;

    private Bundle bundle;

    public PagerAdapter(FragmentManager fm, int NumOfTabs,String key) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        bundle = new Bundle();
        bundle.putString("key",key);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                UserFragment userTab = new UserFragment();
                userTab.setArguments(bundle);

                return userTab;
            case 1:
                PageFragment pageTab = new PageFragment();
                pageTab.setArguments(bundle);
                return pageTab;
            case 2:
                EventFragment eventTab = new  EventFragment();
                eventTab.setArguments(bundle);
                return eventTab;
            case 3:
                PlaceFragment placeTab = new PlaceFragment();
                placeTab.setArguments(bundle);
                return placeTab;
            case 4:
                GroupFragment groupTab = new GroupFragment();
                groupTab.setArguments(bundle);
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
