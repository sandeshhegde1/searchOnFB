package com.example.sanh.facebook.Fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sanh.facebook.Adapters.ExpandableListAdapter;
import com.example.sanh.facebook.Models.PostModel;
import com.example.sanh.facebook.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by San H on 4/24/2017.
 */
public class AlbumFragment extends Fragment {

    private String id;
    private HttpURLConnection urlConnection;
    private BufferedReader reader;
    private String JSON;
    private List<String> urlList;
    private List<String> nameList;
    private HashMap<String,List<String>> albumListHashMap;
    private ExpandableListView albumlv;
    private TextView noAlbumTv;
    private boolean hasAlbum;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.album_fragment, container, false);
        id = getArguments().getString("id");

        urlList=new ArrayList<>();
        nameList=new ArrayList<>();
        albumListHashMap=new HashMap<>();

        albumlv=(ExpandableListView) v.findViewById(R.id.lvExp);
        noAlbumTv=(TextView)v.findViewById(R.id.noalbumtv);

        new getJSON().execute();

        albumlv.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if(groupPosition != previousGroup)
                    albumlv.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });



        return v;

    }

    private  class getJSON extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {

            try {


                String url_string="http://sandeshwebtech-env.us-west-2.elasticbeanstalk.com/index.php?id="+id+"&select=details&submit=TRUE";
                URL url = new URL(url_string);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream != null) {
                    //do nothing
                }
                {
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                        // But it does make debugging a *lot* easier if you print out the completed
                        // buffer for debugging.
                        buffer.append(line + "\n");
                    }

                    if (buffer.length() != 0) {

                        JSON = buffer.toString();
                    }
                }


            } catch (IOException e) {
                Log.e("UserFragment", "Error ", e);

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("UserFragment", "Error closing stream", e);
                    }
                }

                parseJSON(JSON);

                return null;


            }



        }

        @Override
        protected void onPostExecute(Void result) {

            if(hasAlbum) {

                //hide the textview
                noAlbumTv.setVisibility(View.GONE);
                albumlv.setVisibility(View.VISIBLE);
                ExpandableListAdapter adapter = new ExpandableListAdapter(getActivity(), nameList,albumListHashMap);
                albumlv.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }else{

                noAlbumTv.setVisibility(View.VISIBLE);
                albumlv.setVisibility(View.GONE);
            }

            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

    }


    private void parseJSON(String result){

        try {
            JSONObject jObject = new JSONObject(result);

            if(!jObject.has("albums")||!jObject.getJSONObject("albums").has("data")){

                hasAlbum=false;
            }else {

                hasAlbum=true;
                JSONArray jArray = jObject.getJSONObject("albums").getJSONArray("data");

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject dataObject = jArray.getJSONObject(i);

                    String name = dataObject.getString("name");
                    nameList.add(name);
                    if(dataObject.has("photos")&&dataObject.getJSONObject("photos").has("data")){

                        JSONArray picArray = dataObject.getJSONObject("photos").getJSONArray("data");

                        for(int j=0;j<picArray.length();j++){

                            JSONObject imageObject = picArray.getJSONObject(j);
                            String url=imageObject.getJSONArray("images").getJSONObject(0).getString("source");
                            urlList.add(url);


                        }

                    }


                    albumListHashMap.put(name,urlList);
                    urlList=new ArrayList<>();


                }

            }

        }catch(Exception e){
            Log.e("UserFragment", "JSON parse", e);
        }

    }



}
