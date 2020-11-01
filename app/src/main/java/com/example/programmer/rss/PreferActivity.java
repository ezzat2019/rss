package com.example.programmer.rss;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.programmer.rss.adapters.RecyclepreferdAdapeter;
import com.example.programmer.rss.models.ItemRoom;
import com.example.programmer.rss.ui.OnItemClickMain;
import com.example.programmer.rss.view_models.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class PreferActivity extends AppCompatActivity implements OnItemClickMain {

    // ui
    private RecyclerView recyclerView;
    private RecyclepreferdAdapeter adapter;

    // var
    private List<ItemRoom> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefer);


        createSharedPref();

        createRecycleView();

        createViewModel();
    }

    private void createSharedPref() {
        SharedPreferences sharedPreferences = LoginActivity.createSharedPerfernce(getApplicationContext());

    }

    private void createViewModel() {

        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getAllItems().observe(this, new Observer<List<ItemRoom>>() {
            @Override
            public void onChanged(List<ItemRoom> itemRooms) {
                list = new ArrayList<>(itemRooms);
                adapter.setList(itemRooms);

                recyclerView.setAdapter(adapter);


            }
        });
//        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
//        viewModel.getAllEmail(sharedPreferences.getString("email", "")).observe(this, new Observer<List<ItemEmail>>() {
//            @Override
//            public void onChanged(List<ItemEmail> itemEmails) {
//                adapter.setList(itemEmails);
//                recyclerView.setAdapter(adapter);
//            }
//        });
    }

    private void createRecycleView() {

        recyclerView = findViewById(R.id.rec_prefer2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new RecyclepreferdAdapeter(this);


    }

    @Override
    public void onClick(int pos) {
        int id = list.get(pos).getId();
        Intent intent = new Intent(getApplicationContext(), VideoPlayMain2Activity.class);
        intent.putExtra("id", id);
        intent.putExtra("nn", list.get(pos).getName());
        intent.putExtra("url", list.get(pos).getSource());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
        finish();

    }
}
