package com.example.cst2335_finalproject;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class BaseFragment extends Fragment {


    public BaseFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // to inflate the layout
        return inflater.inflate(R.layout.fragment_base, container, false);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }
}