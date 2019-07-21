package com.example.programmer.rss;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.programmer.rss.adapters.RecyclePreferdAdapter;
import com.example.programmer.rss.models.ItemEmail;
import com.example.programmer.rss.models.ModelMain;
import com.example.programmer.rss.ui.OnItemClickMain;
import com.example.programmer.rss.view_models.MainViewModel;

import java.util.List;

public class PreferActivity extends AppCompatActivity {

    // ui
    private RecyclerView recyclerView;
    private RecyclePreferdAdapter adapter;

    // var
    private List<ModelMain> list;
    private MainViewModel viewModel;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefer);


        createSharedPref();

        createRecycleView();

        createViewModel();
    }

    private void createSharedPref() {
        sharedPreferences = LoginActivity.createSharedPerfernce(getApplicationContext());

    }

    private void createViewModel() {
//        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
//        viewModel.getAllItems().observe(this, new Observer<List<ItemRoom>>() {
//            @Override
//            public void onChanged(List<ItemRoom> itemRooms) {
//                adapter.setList(itemRooms);
//                Log.d("222222",itemRooms.size()+"");
//                recyclerView.setAdapter(adapter);
//
//
//            }
//        });
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getAllEmail(sharedPreferences.getString("email", "")).observe(this, new Observer<List<ItemEmail>>() {
            @Override
            public void onChanged(List<ItemEmail> itemEmails) {
                adapter.setList(itemEmails);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    private void createRecycleView() {

        recyclerView = findViewById(R.id.rec_prefer2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new RecyclePreferdAdapter();
        adapter.setOnItemClick(new OnItemClickMain() {
            @Override
            public void onClick(int pos) {
                Toast.makeText(PreferActivity.this, pos + "", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
