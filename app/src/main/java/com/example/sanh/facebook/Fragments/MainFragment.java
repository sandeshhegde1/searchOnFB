package com.example.sanh.facebook.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.sanh.facebook.R;
import com.example.sanh.facebook.ResultActivity;

/**
 * Created by San H on 4/23/2017.
 */
public class MainFragment extends Fragment {

    private EditText input;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.main_fagment,container,false);

        input=(EditText)v.findViewById(R.id.editInput);


        //search function
        Button buttonSearch = (Button) v.findViewById(R.id.btn_search);
        buttonSearch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String keyword=input.getText().toString();
                Intent intent = new Intent(getActivity(), ResultActivity.class);
                intent.putExtra("EXTRA_INPUT",keyword);
                startActivity(intent);
            }
        });


        Button buttonClear = (Button) v.findViewById(R.id.btn_clear);
        buttonClear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                input.setText("");
            }
        });

        return v;


    }
}
