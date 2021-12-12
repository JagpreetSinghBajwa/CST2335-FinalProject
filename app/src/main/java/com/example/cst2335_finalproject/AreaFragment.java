package com.example.cst2335_finalproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class AreaFragment extends Fragment {

    private EditText shared;

    // Created empty public constructor

    public AreaFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SharedPreferences setPreference = getContext().getSharedPreferences("MyPreference", 0); // 0 - for private mode
        SharedPreferences.Editor editor = setPreference.edit();

        View result=inflater.inflate(R.layout.fragment_area, container, false); // to create view from XML
        shared=result.findViewById(R.id.feedback);
        editor.putString("save",shared.getText().toString());
        editor.apply();
        result.findViewById(R.id.send).setOnClickListener(v -> {
            shared.setText(setPreference.getString("save",null));
        });


        return result;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }


}