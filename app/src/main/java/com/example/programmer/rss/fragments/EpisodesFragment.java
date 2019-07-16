package com.example.programmer.rss.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.programmer.rss.R;
import com.example.programmer.rss.adapters.RecycleChannelAdapter;
import com.example.programmer.rss.adapters.RecycleVideoAdapter;
import com.example.programmer.rss.models.ModelMain;
import com.example.programmer.rss.models.ModelVideoEp;
import com.example.programmer.rss.ui.OnItemClickMain;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class EpisodesFragment extends Fragment {

    // ui
    private RecyclerView recyclerView;

    // var
    private RecycleVideoAdapter videoAdapter;
    private List<ModelVideoEp> modelMains;
    private String type;


    public EpisodesFragment() {
        // Required empty public constructor

    }
    public EpisodesFragment(String t) {
        // Required empty public constructor
        this.type=t;

    }
    private void getIntents() {

        if (type != null) {
            if (type.equals("list0")) {
                videoAdapter.setList(MainFragment.getMainModel());



            } else if (type.equals("list1")) {
                videoAdapter.setList(MainFragment.getListGame());



            } else if (type.equals("list2")) {
                videoAdapter.setList(MainFragment.getMainModel());

            } else if (type.equals("list3")) {
                videoAdapter.setList(MainFragment.getListLast());


            } else if (type.equals("list4")) {
                videoAdapter.setList(MainFragment.getListDramaEgy());


            }

        }
        else
        {

             SharedPreferences sharedPreferences=getActivity().getSharedPreferences("full",Context.MODE_PRIVATE);
             type=sharedPreferences.getString("type",null);
             getIntents();
        }
        recyclerView.setAdapter(videoAdapter);

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        videoAdapter=new RecycleVideoAdapter();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_episodes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        createRecycleVideo(view);
        getIntents();

    }



    private void createRecycleVideo(View view) {
        recyclerView=view.findViewById(R.id.rec_episodes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(videoAdapter);

        videoAdapter.setOnItemClick(new OnItemClickMain() {
            @Override
            public void onClick(int pos) {
                Toast.makeText(getContext(), pos+"", Toast.LENGTH_SHORT).show();

            }
        });
    }
}