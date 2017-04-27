package com.example.sanh.facebook.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.sanh.facebook.Adapters.FavListAdaptor;
import com.example.sanh.facebook.DetailsActivity;
import com.example.sanh.facebook.Models.ListModel;
import com.example.sanh.facebook.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by San H on 4/26/2017.
 */
public class FavGroupFragment extends Fragment {



    private List<ListModel> groupFavListData;
    private ListView grouplv;
    private FavListAdaptor adapter;
    private SharedPreferences sharedPref;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        View v = inflater.inflate(R.layout.fav_list_view,container,false);
        groupFavListData=new ArrayList<>();
        grouplv=(ListView) v.findViewById(R.id.fav_listView);
        getDataFillView();



        //onclick listview
        grouplv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {

                // Get the selected model ,its id,its type  and send to detail activity.
                String sendID = groupFavListData.get(position).getID();
                // get rowData and convert to string using gson
                ListModel rowData=groupFavListData.get(position);
                String jsonRowData = new Gson().toJson(rowData);

                String type="group";
                Intent intent =new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("EXTRA_ID",sendID);
                intent.putExtra("EXTRA_TYPE",type);
                intent.putExtra("EXTRA_ROW",jsonRowData);
                startActivity(intent);


            }
        });




        return v;
    }

    private void getDataFillView(){

        sharedPref= this.getActivity().getSharedPreferences("my_pref", Context.MODE_PRIVATE);
        String jsonGroupFavList =sharedPref.getString("groupFavList", null);

        if(jsonGroupFavList !=null){

            Type type = new TypeToken< List < ListModel >>() {}.getType();
            groupFavListData=new Gson().fromJson(jsonGroupFavList, type);
            Collections.reverse(groupFavListData);

            adapter = new FavListAdaptor(getActivity(), groupFavListData);
            grouplv.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }





    }





    @Override
    public void onResume() {

        getDataFillView();

        super.onResume();
    }
}
