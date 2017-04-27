package com.example.sanh.facebook;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.sanh.facebook.Adapters.PagerAdapter;

/**
 * Created by San H on 4/23/2017.
 */
public class ResultActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_main);

        keyword = getIntent().getStringExtra("EXTRA_INPUT");
        setupToolbar();
        initTabs();
        setupViewPager();


    }


    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace_black_24dp);
            getSupportActionBar().setTitle("Results");

        }

    }

    private void initTabs(){

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.users).setText("Users"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.pages).setText("Pages"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.events).setText("Events"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.places).setText("Places"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.groups).setText("Groups"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

    }

    private void setupViewPager(){

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount(),keyword);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }


}

