package com.example.programmer.rss;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.programmer.rss.adapters.RecycleGridAdapter;
import com.example.programmer.rss.ui.OnItemClickMain;

public class ShowGridActivity extends AppCompatActivity {

    // ui
    private RecyclerView recyclerView;


    // var

    private RecycleGridAdapter gridAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_grid);

        createRecycleView();


    }

    private void gotoVideoActivity() {
        Intent intent = new Intent(this, VideoPlayMain2Activity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);

    }

    private void createRecycleView() {
        recyclerView = findViewById(R.id.rec_grid);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        gridAdapter = new RecycleGridAdapter();

        getIntents();

    }

    private void getIntents() {
        String type = getIntent().getStringExtra("type");
        if (type != null) {
            if (type.equals("list")) {

                gridAdapter.setList(MainActivity.results);
                recyclerView.setAdapter(gridAdapter);
            } else if (type.equals("list1")) {

                gridAdapter.setList(MainActivity.resultTrens);
                recyclerView.setAdapter(gridAdapter);

            }
        }
        gridAdapter.setOnItemClick(new OnItemClickMain() {
            @Override
            public void onClick(int pos) {
                Intent intent = new Intent(getApplicationContext(), VideoPlayMain2Activity.class);
                String type = getIntent().getStringExtra("type");
                int id = -1;

                if (type != null) {
                    if (type.equals("list")) {

                        gridAdapter.setList(MainActivity.results);
                        intent.putExtra("nn", MainActivity.results.get(pos).getTitle());
                        id = MainActivity.results.get(pos).getId();
                        intent.putExtra("url", MainActivity.results.get(pos).getPoster_path());
                        recyclerView.setAdapter(gridAdapter);
                    } else if (type.equals("list1")) {
                        id = MainActivity.resultTrens.get(pos).getId();
                        intent.putExtra("nn", MainActivity.resultTrens.get(pos).getTitle());
                        intent.putExtra("url", MainActivity.resultTrens.get(pos).getPoster_path());
                        gridAdapter.setList(MainActivity.resultTrens);
                        recyclerView.setAdapter(gridAdapter);

                    }


                    intent.putExtra("id", id);

                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);

                }
            }
        });
    }


    public void back(View view) {
        onBackPressed();
    }
}
