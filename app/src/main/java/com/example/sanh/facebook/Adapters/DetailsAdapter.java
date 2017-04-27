package com.example.sanh.facebook.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.sanh.facebook.Fragments.AlbumFragment;
import com.example.sanh.facebook.Fragments.PostFragment;



/**
 * Created by San H on 4/24/2017.
 */
public class DetailsAdapter extends FragmentStatePagerAdapter {

    private int mNumOfTabs;

    private Bundle bundle;


    public DetailsAdapter(FragmentManager fm, int NumOfTabs, String key) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        bundle = new Bundle();
        bundle.putString("id",key);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                AlbumFragment albumTab = new AlbumFragment();
                albumTab.setArguments(bundle);
                return albumTab;
            case 1:
                PostFragment postTab = new PostFragment();
                postTab.setArguments(bundle);
                return postTab;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }


}

