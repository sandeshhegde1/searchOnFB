package com.example.sanh.facebook.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sanh.facebook.Adapters.FavPagerAdapter;
import com.example.sanh.facebook.R;

/**
 * Created by San H on 4/26/2017.
 */
public class FavFragment extends Fragment {

    private TabLayout tabLayout;
    private View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fav_fragment,container,false);
        initTabs();
        setupViewPager();
        return v;
    }

    private void initTabs(){

        tabLayout = (TabLayout) v.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.users).setText("Users"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.pages).setText("Pages"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.events).setText("Events"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.places).setText("Places"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.groups).setText("Groups"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

    }

    private void setupViewPager(){

        final ViewPager viewPager = (ViewPager) v.findViewById(R.id.pager);
        final FavPagerAdapter adapter = new FavPagerAdapter
                (getChildFragmentManager(), tabLayout.getTabCount());
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
}
