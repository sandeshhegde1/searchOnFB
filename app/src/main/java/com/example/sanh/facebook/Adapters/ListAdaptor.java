package com.example.sanh.facebook.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sanh.facebook.Models.ListModel;
import com.example.sanh.facebook.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by San H on 4/23/2017.
 */
public class ListAdaptor extends BaseAdapter {

    private Activity activity;
    private List<ListModel> items;


    public ListAdaptor(Activity activity, List<ListModel> items) {
        this.activity = activity;
        this.items = items;
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int location) {
        return items.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (null == view) {
            LayoutInflater inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_items, null);
        }


        ImageView profile=(ImageView)view.findViewById(R.id.profile_image);
        TextView name = (TextView) view.findViewById(R.id.name);
        ImageView favImage=(ImageView)view.findViewById(R.id.fav_image);
        ImageView details=(ImageView)view.findViewById(R.id.detail_click);

        //get url and load the image using picasso lib
        String url=items.get(position).getImageURL();
        Picasso.with(activity).load(url).into(profile);

        name.setText(items.get(position).getName());


        //TODO  get the id and check with fav array to set fav icon color

        favImage.setImageResource(R.drawable.favorites_off);
        details.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);

        return view;
    }
}

