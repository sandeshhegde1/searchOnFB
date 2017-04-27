package com.example.sanh.facebook;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.sanh.facebook.Fragments.AboutFragment;
import com.example.sanh.facebook.Fragments.FavFragment;
import com.example.sanh.facebook.Fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private int REQUEST_READWRITE_STORAGE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         //getSharedPreferences("my_pref",MODE_PRIVATE).edit().clear().commit();


        //ask for permissions to the user to gain access to the external storage
        int permissionCheck1 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionCheck2 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
     //   int permissionCheck3 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
     //   int permissionCheck4 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);


        if (permissionCheck1 != PackageManager.PERMISSION_GRANTED || permissionCheck2 != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_READWRITE_STORAGE);
        }


        setupToolbar();

        initNavigationView();

        //replace framelayout by the main_fragment layout when app opens
        MainFragment mainfragment=new MainFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,mainfragment);
        fragmentTransaction.commit();



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (isNavDrawerOpen()) {
            closeNavDrawer();
        } else {
            super.onBackPressed();
        }
    }

    protected boolean isNavDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START);
    }

    protected void closeNavDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       //set image for opening navigation view
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_reorder_black_24dp);
        ab.setDisplayHomeAsUpEnabled(true);


    }

    private void initNavigationView() {

        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //setting up selected item listener
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        //Checking if the item is in checked state or not, if not make it in checked state
                        if(menuItem.isChecked()) menuItem.setChecked(false);
                        else menuItem.setChecked(true);

                        //Closing drawer on item click
                        mDrawerLayout.closeDrawers();

                        //Check to see which item was being clicked and perform appropriate action
                        int id = menuItem.getItemId();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        switch (id) {



                            case R.id.nav_home:
                                MainFragment mainfragment=new MainFragment();
                                fragmentTransaction.replace(R.id.frame,mainfragment);
                                fragmentTransaction.commit();
                                return true;

                            case R.id.nav_fav:
                                FavFragment favfragment=new FavFragment();
                                fragmentTransaction.replace(R.id.frame,favfragment);
                                fragmentTransaction.commit();
                                return true;

                            case R.id.nav_about:
                                AboutFragment abtfragment = new AboutFragment();
                                fragmentTransaction.replace(R.id.frame,abtfragment);
                                fragmentTransaction.commit();
                                return true;


                        }

                        return true;

                    }
                });






    }



}
