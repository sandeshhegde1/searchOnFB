package com.example.sanh.facebook;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sanh.facebook.Adapters.DetailsAdapter;
import com.example.sanh.facebook.Fragments.UserFragment;
import com.example.sanh.facebook.Models.ListModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by San H on 4/24/2017.
 */
public class DetailsActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private String id;
    private boolean isFav;
    private String type;
    private ListModel rowData;
    private SharedPreferences sharedPref;
    private List<ListModel> userFavList;
    private List<ListModel> placeFavList;
    private List<ListModel> pageFavList;
    private List<ListModel> eventFavList;
    private List<ListModel> groupFavList;
    private List<String> idFavList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_main);

        id = getIntent().getStringExtra("EXTRA_ID");
        type=getIntent().getStringExtra("EXTRA_TYPE");
        String jsonRowData=getIntent().getStringExtra("EXTRA_ROW");
        rowData=new Gson().fromJson(jsonRowData, ListModel.class);


        sharedPref= getSharedPreferences("my_pref",MODE_PRIVATE);
        String jsonIdFavList =sharedPref.getString("idFavList", null);

        Gson gson = new Gson();


        //check if there is idFavList already present in shared pref
        if(jsonIdFavList ==null){
            idFavList=new ArrayList<>();
            isFav=false;



        }else{

            Type type = new TypeToken< List < String >>() {}.getType();
            idFavList=gson.fromJson(jsonIdFavList, type);


           if( idFavList.contains(id)){
               isFav=true;

           }else{
               isFav=false;

           }

        }

        //similarly for other type , check if there is FavList already present in shared pref, if not initialize it.
        if(type.equals("user")){

            String jsonUserFavList =sharedPref.getString("userFavList", null);
            if(jsonUserFavList ==null){
                userFavList=new ArrayList<>();
            }else{

                Type type = new TypeToken< List < ListModel >>() {}.getType();
                userFavList=gson.fromJson(jsonUserFavList, type);

            }


        }else if(type.equals("place")){
            String jsonPlaceFavList =sharedPref.getString("placeFavList", null);
            if(jsonPlaceFavList ==null){
                placeFavList=new ArrayList<>();
            }else{

                Type type = new TypeToken< List < ListModel >>() {}.getType();
                placeFavList=gson.fromJson(jsonPlaceFavList, type);

            }


        }else if(type.equals("page")){
            String jsonPageFavList =sharedPref.getString("pageFavList", null);
            if(jsonPageFavList ==null){
                pageFavList=new ArrayList<>();
            }else{

                Type type = new TypeToken< List < ListModel >>() {}.getType();
                pageFavList=gson.fromJson(jsonPageFavList, type);

            }


        }else if(type.equals("event")){
            String jsonEventFavList =sharedPref.getString("eventFavList", null);
            if(jsonEventFavList==null){
                eventFavList=new ArrayList<>();
            }else{

                Type type = new TypeToken< List < ListModel >>() {}.getType();
                eventFavList=gson.fromJson(jsonEventFavList, type);

            }


        }else if(type.equals("group")){
            String jsonGroupFavList =sharedPref.getString("groupFavList", null);
            if(jsonGroupFavList ==null){
                groupFavList=new ArrayList<>();
            }else{

                Type type = new TypeToken< List < ListModel >>() {}.getType();
                groupFavList=gson.fromJson(jsonGroupFavList, type);

            }


        }






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
            getSupportActionBar().setTitle("More Details");

        }


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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        switch (item.getItemId()) {
            // handle arrow click here
            case R.id.home:
                // close this activity and return to preview activity (if there is any)
                 finish();
                return true;
            case R.id.fav_menu:

                SharedPreferences sharedPreferences = getSharedPreferences("my_pref",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if(isFav){

                    //remove from fav
                    idFavList.remove(id);
                    String jsonIdFavList = new Gson().toJson(idFavList);
                    editor.putString("idFavList",jsonIdFavList);

                    if(type.equals("user")){
                        userFavList.remove(rowData);
                        String jsonUserFavList = new Gson().toJson(userFavList);
                        editor.putString("userFavList",jsonUserFavList);


                    }else if(type.equals("place")){
                        placeFavList.remove(rowData);
                        String jsonPlaceFavList = new Gson().toJson(placeFavList);
                        editor.putString("placeFavList",jsonPlaceFavList);


                    }else if(type.equals("page")){
                        pageFavList.remove(rowData);
                        String jsonPageFavList = new Gson().toJson(pageFavList);
                        editor.putString("pageFavList",jsonPageFavList);

                    }else if(type.equals("event")){

                        eventFavList.remove(rowData);
                        String jsonEventFavList = new Gson().toJson(eventFavList);
                        editor.putString("eventFavList",jsonEventFavList);

                    }else if(type.equals("group")) {

                        groupFavList.remove(rowData);
                        String jsonGroupFavList = new Gson().toJson(groupFavList);
                        editor.putString("groupFavList",jsonGroupFavList);
                    }

                    editor.commit();


                }else{
                    //add to fav block
                    //put the data to fav lists and save it.
                    idFavList.add(id);
                    String jsonIdFavList = new Gson().toJson(idFavList);
                    editor.putString("idFavList",jsonIdFavList);

                    //similarly for other type , check if there is FavList already present in shared pref, if not initialize it.
                    if(type.equals("user")){
                        userFavList.add(rowData);
                        String jsonUserFavList = new Gson().toJson(userFavList);
                        editor.putString("userFavList",jsonUserFavList);


                    }else if(type.equals("place")){
                        placeFavList.add(rowData);
                        String jsonPlaceFavList = new Gson().toJson(placeFavList);
                        editor.putString("placeFavList",jsonPlaceFavList);


                    }else if(type.equals("page")){
                        pageFavList.add(rowData);
                        String jsonPageFavList = new Gson().toJson(pageFavList);
                        editor.putString("pageFavList",jsonPageFavList);

                    }else if(type.equals("event")){

                        eventFavList.add(rowData);
                        String jsonEventFavList = new Gson().toJson(eventFavList);
                        editor.putString("eventFavList",jsonEventFavList);

                    }else if(type.equals("group")) {

                        groupFavList.add(rowData);
                        String jsonGroupFavList = new Gson().toJson(groupFavList);
                        editor.putString("groupFavList",jsonGroupFavList);
                    }

                        editor.commit();


                }



                return true;
            case R.id.share:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.detail_menu,menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(isFav){
            menu.findItem(R.id.fav_menu).setTitle("Remove from Favourites");
        }else{
            menu.findItem(R.id.fav_menu).setTitle("Add to Favourites");
        }

        return super.onPrepareOptionsMenu(menu);
    }
}
