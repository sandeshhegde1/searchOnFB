package com.example.sanh.facebook;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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

import com.example.sanh.facebook.Fragments.FavFragment;
import com.example.sanh.facebook.Fragments.MainFragment;



public class MainActivity extends AppCompatActivity  {

    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private String mLat;
    private String mLong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mLat=null;
        mLong=null;


        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                Log.d("lat:",Double.toString(location.getLatitude()));
                Log.d("long:",Double.toString(location.getLongitude()));

                mLat=Double.toString(location.getLatitude());
                mLong=Double.toString(location.getLongitude());

                storeLocation();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                Log.d("Location", "status changed = ");
            }

            @Override
            public void onProviderEnabled(String s) {
                Log.d("Location", "provider enabled = ");
            }

            @Override
            public void onProviderDisabled(String s) {
                mLat=null;
                mLong=null;
                storeLocation();
                Log.d("Location", "status disabled = ");
            }
        };



        //ask for permissions to the user to gain access to the external storage

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED  ) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_COARSE_LOCATION ,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET},
                    0);
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);
            return;
        }else{

            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);


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
                                Intent intent=new Intent(MainActivity.this,AboutActivity.class);
                                startActivity(intent);
                                return true;


                        }

                        return true;

                    }
                });






    }

    private void storeLocation(){

        SharedPreferences sharedPref=getSharedPreferences("my_pref",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("latitude",mLat);
        editor.putString("longitude",mLong);
        editor.commit();

    }

    /*

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       // super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 10 :
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);
                } else {
                    Log.d("location", "permission denied");
                }
        }
    }
    */
}
