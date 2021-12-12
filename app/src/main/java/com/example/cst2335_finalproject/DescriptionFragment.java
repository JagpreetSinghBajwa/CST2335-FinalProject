package com.example.cst2335_finalproject;

import  android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class DescriptionFragment extends Fragment {


    public DescriptionFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle data = getArguments();
        String description = data.getString("fragmentDesc");
        String link1 = data.getString("fragmentLink");

        // to inflate the layout
        View result = inflater.inflate(R.layout.fragment_description, container, false);

        TextView desc = result.findViewById(R.id.fragmentdescription);
        TextView link = result.findViewById(R.id.url);
        desc.setText(description);
        link.setText(link1);

        return result;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AppCompatActivity parentActivity = (AppCompatActivity) context;
    }
}