package com.example.programmer.rss.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.programmer.rss.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClipsFragment extends Fragment {


    public ClipsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_clips, container, false);
    }

}
