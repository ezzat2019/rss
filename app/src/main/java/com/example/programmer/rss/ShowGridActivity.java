package com.example.programmer.rss;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.programmer.rss.adapters.RecycleGridAdapter;
import com.example.programmer.rss.adapters.RecycleVideoAdapter;
import com.example.programmer.rss.fragments.MainFragment;
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

        getIntents();


    }

    private void getIntents() {
        String type=getIntent().getStringExtra("type");
        if (type!=null)
        {
            if (type.equals("list1"))

            {

                gridAdapter.setList(MainFragment.getListGame());
                recyclerView.setAdapter(gridAdapter);
            }
            else if (type.equals("list2"))
            {

                gridAdapter.setList(MainFragment.getMainModel());
                recyclerView.setAdapter(gridAdapter);

            }
            else if (type.equals("list3"))
            {
                gridAdapter.setList(MainFragment.getListLast());
                recyclerView.setAdapter(gridAdapter);

            }
            else if (type.equals("list4"))
            {
                gridAdapter.setList(MainFragment.getListDramaEgy());
                recyclerView.setAdapter(gridAdapter);

            }
        }
        gridAdapter.setOnItemClick(new OnItemClickMain() {
            @Override
            public void onClick(int pos) {
                gotoVodeoActivity();

            }
        });
    }
    private  void gotoVodeoActivity()
    { Intent intent=new Intent(this, VideoPlayMain2Activity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);

    }

    private void createRecycleView() {
        recyclerView=findViewById(R.id.rec_grid);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
        gridAdapter=new RecycleGridAdapter();

    }

    public void back(View view) {
        onBackPressed();
    }
}
