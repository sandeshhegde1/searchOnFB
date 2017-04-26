package com.example.sanh.facebook.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sanh.facebook.Adapters.PostAdapter;
import com.example.sanh.facebook.Models.ListModel;
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

/**
 * Created by San H on 4/24/2017.
 */
public class PostFragment extends Fragment {

    private String id;
    private HttpURLConnection urlConnection;
    private BufferedReader reader;
    private String JSON;
    private ArrayList<PostModel> postListData;
    private ListView postlv;
    private TextView noPostTv;
    private boolean hasPost;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.post_fragment, container, false);
        id = getArguments().getString("id");

        postListData=new ArrayList<>();
        postlv=(ListView) v.findViewById(R.id.post_listView);
        noPostTv=(TextView)v.findViewById(R.id.noposttv);

        new getJSON().execute();
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

            if(hasPost) {

                //hide the textview
                noPostTv.setVisibility(View.GONE);
                postlv.setVisibility(View.VISIBLE);
                PostAdapter adapter = new PostAdapter(getActivity(), postListData);
                postlv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }else{

                noPostTv.setVisibility(View.VISIBLE);
                postlv.setVisibility(View.GONE);
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
            String name=jObject.getString("name");

            JSONObject pictureDataObject = jObject.getJSONObject("picture").getJSONObject("data");
            String imageURL=pictureDataObject.getString("url");

            if(!jObject.has("posts")||!jObject.getJSONObject("posts").has("data")){

                hasPost=false;
            }else {

                hasPost=true;
                JSONArray jArray = jObject.getJSONObject("posts").getJSONArray("data");

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject dataObject = jArray.getJSONObject(i);

                    String message = dataObject.getString("message");
                    String time = dataObject.getString("created_time");

                    PostModel object = new PostModel(id, name, imageURL, time, message, false);
                    postListData.add(object);


                }

            }

        }catch(Exception e){
            Log.e("UserFragment", "JSON parse", e);
        }

    }

}
