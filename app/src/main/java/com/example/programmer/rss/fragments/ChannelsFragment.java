package com.example.programmer.rss.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.programmer.rss.R;
import com.example.programmer.rss.adapters.RecycleChannelAdapter;
import com.example.programmer.rss.adapters.RecycleFrag1Adapter;
import com.example.programmer.rss.adapters.RecycleFrag2Adapter;
import com.example.programmer.rss.adapters.RecycleFrag3Adapter;
import com.example.programmer.rss.adapters.RecycleMainAdapter;
import com.example.programmer.rss.models.ModelMain;
import com.example.programmer.rss.ui.OnItemClickMain;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChannelsFragment extends Fragment {

    // ui
    private RecyclerView recyclerViewChannel;
    private RecyclerView recyclerView;

    // var
    private RecycleChannelAdapter channelAdapter;
    private List<ModelMain> modelMains;



    public ChannelsFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        modelMains = new ArrayList<>();

        modelMains.add(new ModelMain("https://via.placeholder.com/600/637984"));
        modelMains.add(new ModelMain("https://via.placeholder.com/600/7644fe"));
        modelMains.add(new ModelMain("https://via.placeholder.com/600/1279e9"));
        modelMains.add(new ModelMain("https://via.placeholder.com/600/f9f067"));
        modelMains.add(new ModelMain("https://via.placeholder.com/600/ea51da"));
        modelMains.add(new ModelMain("https://via.placeholder.com/600/637984"));
        modelMains.add(new ModelMain("https://via.placeholder.com/600/7644fe"));
        modelMains.add(new ModelMain("https://via.placeholder.com/600/1279e9"));
        modelMains.add(new ModelMain("https://via.placeholder.com/600/f9f067"));
        modelMains.add(new ModelMain("https://via.placeholder.com/600/ea51da"));


      channelAdapter=new RecycleChannelAdapter();
      channelAdapter.setList(modelMains);


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_channels, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        createRecycle(view);
    }

    private void createRecycle(View view) {
        recyclerView=view.findViewById(R.id.rec_channel);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        recyclerView.setAdapter(channelAdapter);
        channelAdapter.setOnItemClick(new OnItemClickMain() {
            @Override
            public void onClick(int pos) {
                Toast.makeText(getContext(), pos+"", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
