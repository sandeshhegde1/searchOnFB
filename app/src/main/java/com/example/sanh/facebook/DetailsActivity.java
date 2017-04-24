package com.example.sanh.facebook;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.sanh.facebook.Adapters.DetailsAdapter;

/**
 * Created by San H on 4/24/2017.
 */
public class DetailsActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_main);

        id = getIntent().getStringExtra("EXTRA_ID");

        setupToolbar();
        initTabs();
        setupViewPager();



    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set image for opening navigation view
        final ActionBar ab = getSupportActionBar();
        //TODO
        // ab.setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace_black_24dp);
        //  ab.setDisplayHomeAsUpEnabled(true);
        //  ab.setTitle("Results");


    }

    private void initTabs(){

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.albums).setText("Albums"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.posts).setText("Posts"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

    }


    private void setupViewPager(){

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final DetailsAdapter adapter = new DetailsAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount(),id);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }


}
