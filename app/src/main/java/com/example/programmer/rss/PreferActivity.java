package com.example.programmer.rss;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.programmer.rss.adapters.RecycleMainAdapter;
import com.example.programmer.rss.adapters.RecyclePreferdAdapter;
import com.example.programmer.rss.fragments.MainFragment;
import com.example.programmer.rss.models.ItemRoom;
import com.example.programmer.rss.models.ModelMain;
import com.example.programmer.rss.view_models.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class PreferActivity extends AppCompatActivity {

    // ui
    private RecyclerView recyclerView;
    private RecyclePreferdAdapter adapter;

    // var
    private List<ModelMain> list;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefer);
        Toast.makeText(this, "sttart", Toast.LENGTH_SHORT).show();

        createRecycleView();

        createViewModel();
    }

    private void createViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getAllItems().observe(this, new Observer<List<ItemRoom>>() {
            @Override
            public void onChanged(List<ItemRoom> itemRooms) {
                adapter.setList(itemRooms);
                recyclerView.setAdapter(adapter);
               

            }
        });
    }

    private void createRecycleView() {

        recyclerView = findViewById(R.id.rec_prefer2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new RecyclePreferdAdapter();



    }
}
