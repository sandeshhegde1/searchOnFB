package com.example.sanh.facebook.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.sanh.facebook.Adapters.ListAdaptor;
import com.example.sanh.facebook.DetailsActivity;
import com.example.sanh.facebook.Models.ListModel;
import com.example.sanh.facebook.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by San H on 4/23/2017.
 */
public class PlaceFragment extends Fragment {

    private String key;
    private HttpURLConnection urlConnection;
    private BufferedReader reader;
    private String JSON;
    private ArrayList<ListModel> placeListData;
    private ListView placelv;
    private Button previous;
    private Button next;
    private String url_string;
    private boolean isNextPresent;
    private boolean isPreviousPresent;
    private String nextURL;
    private String previousURL;
    private ListAdaptor adapter;
    private Context context;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        View v = inflater.inflate(R.layout.place_fragment,container,false);
        key = getArguments().getString("key");

        SharedPreferences sharedPref=getActivity().getSharedPreferences("my_pref", Context.MODE_PRIVATE);
        String longitude=sharedPref.getString("longitude", null);
        String latitude=sharedPref.getString("latitude", null);

        Log.d("place location:",longitude+","+latitude);


        if(longitude==null || latitude==null){
            url_string = "http://sandeshwebtech-env.us-west-2.elasticbeanstalk.com/index.php?input_keyword=" + key + "&select=place&input_location=&submit=TRUE&device=android";
        }else{
            url_string = "http://sandeshwebtech-env.us-west-2.elasticbeanstalk.com/index.php?input_keyword=" + key + "&select=place&input_location=&submit=TRUE&device=android&input_location="+latitude+","+longitude;

    }


        placeListData=new ArrayList<>();
        placelv=(ListView) v.findViewById(R.id.place_listView);
        previous=(Button) v.findViewById(R.id.btn_previous);
        next=(Button) v.findViewById(R.id.btn_next);
        new getJSON().execute();


        //onclick listview
        placelv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {

                // Get the selected model ,its id,its type  and send to detail activity.
                String sendID = placeListData.get(position).getID();
                // get rowData and convert to string using gson
                ListModel rowData=placeListData.get(position);
                String jsonRowData = new Gson().toJson(rowData);

                String type="place";
                Intent intent =new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("EXTRA_ID",sendID);
                intent.putExtra("EXTRA_TYPE",type);
                intent.putExtra("EXTRA_ROW",jsonRowData);
                startActivity(intent);


            }
        });

        //onclick peviouos and next buttons
        previous.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //clear the existing list and get new data
                url_string=previousURL;
                placeListData=new ArrayList<>();
                new getJSON().execute();
            }
        });

        next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {   //clear the existing list and get new data
                url_string=nextURL;
                placeListData=new ArrayList<>();
                new getJSON().execute();

            }
        });



        return v;
    }



    private  class getJSON extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {

            try {


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
                Log.e("PlaceFragment", "Error ", e);

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceFragment", "Error closing stream", e);
                    }
                }

                parseJSON(JSON);

                return null;


            }



        }

        @Override
        protected void onPostExecute(Void result) {

            adapter = new ListAdaptor(context, placeListData);
            placelv.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            if(isNextPresent){
                next.setEnabled(true);
            }else{
                next.setEnabled(false);
            }

            if(isPreviousPresent){
                previous.setEnabled(true);
            }else{
                previous.setEnabled(false);
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
            JSONArray jArray = jObject.getJSONArray("data");

            for(int i=0;i<jArray.length();i++){
                JSONObject dataObject = jArray.getJSONObject(i);

                String id= dataObject.getString("id");
                String name=dataObject.getString("name");

                JSONObject pictureDataObject = dataObject.getJSONObject("picture").getJSONObject("data");
                String imageURL=pictureDataObject.getString("url");

                //TODO fav part boolean thing
                //create model object and add it to arryalist
                ListModel object=new ListModel(id,name,imageURL,false);
                placeListData.add(object);

                //check if pagination is present
                JSONObject PageObject =jObject.getJSONObject("paging");
                if(PageObject.has("next")){
                    isNextPresent=true;
                    nextURL=PageObject.getString("next");


                }else{
                    isNextPresent=false;

                }

                if(PageObject.has("previous")){
                    isPreviousPresent=true;
                    previousURL=PageObject.getString("previous");

                }else{
                    isPreviousPresent=false;

                }



            }

        }catch(Exception e){
            Log.e("PlaceFragment", "JSON parse", e);
        }

    }


    @Override
    public void onResume() {

        if(adapter!=null){
            adapter = new ListAdaptor(context, placeListData);
            placelv.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }

        super.onResume();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context=getActivity().getApplicationContext();
    }
}
