package com.example.programmer.rss.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.programmer.rss.R;
import com.example.programmer.rss.adapters.RecycleFrag1Adapter;
import com.example.programmer.rss.adapters.RecycleFrag2Adapter;
import com.example.programmer.rss.adapters.RecycleFrag3Adapter;
import com.example.programmer.rss.models.ModelMain;
import com.example.programmer.rss.ui.OnItemClickMain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TheMoviesFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, OnItemClickMain {
    // ui
    private SliderLayout mDemoSlider;
    private RecyclerView recyclerViewFrag1, recyclerViewFrag2, recyclerViewFrag3;
    private ImageView imageView11, imageView22, imageView33;
    private View view;


    //var
    private List<ModelMain> modelMains;
    private RecycleFrag1Adapter frag1Adapter;
    private RecycleFrag2Adapter frag2Adapter;
    private RecycleFrag3Adapter frag3Adapter;


    public TheMoviesFragment() {
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


        frag1Adapter = new RecycleFrag1Adapter();
        frag2Adapter = new RecycleFrag2Adapter();
        frag3Adapter = new RecycleFrag3Adapter();


        frag1Adapter.setList(modelMains);
        frag2Adapter.setList(modelMains);
        frag3Adapter.setList(modelMains);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_the_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        createSlider(view);

        createImageListener(view);

        createRecycleFrag1(view);

        createRecycleFrag2(view);

        createRecycleFrag3(view);


        Toast.makeText(getContext(), "create view", Toast.LENGTH_SHORT).show();


    }


    private void createRecycleFrag3(View view) {
        recyclerViewFrag3 = view.findViewById(R.id.rec_rectangle3);
        recyclerViewFrag3.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewFrag3.setAdapter(frag3Adapter);

    }


    private void createRecycleFrag2(View view) {
        recyclerViewFrag2 = view.findViewById(R.id.rec_rectangle2);
        recyclerViewFrag2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewFrag2.setAdapter(frag2Adapter);

    }

    private void createRecycleFrag1(View view) {
        recyclerViewFrag1 = view.findViewById(R.id.rec_rectangle1);
        recyclerViewFrag1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewFrag1.setAdapter(frag1Adapter);


    }


    private void createImageListener(View view) {

        imageView11 = view.findViewById(R.id.image_list11);
        imageView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        imageView22 = view.findViewById(R.id.image_list22);
        imageView22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        imageView33 = view.findViewById(R.id.image_list33);
        imageView33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    void createSlider(View view) {
        mDemoSlider = (SliderLayout) view.findViewById(R.id.slider2);

        HashMap<String, String> url_maps = new HashMap<String, String>();

        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");

        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Hannibal", R.drawable.album);
        file_maps.put("Big Bang Theory", R.drawable.aritist);
        file_maps.put("House of Cards", R.drawable.aritist);
        file_maps.put("Game of Thrones", R.drawable.album);

        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getContext());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image("https://via.placeholder.com/600/92c952")

                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);


            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);

    }

    @Override
    public void onStop() {
        //To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        TvSeriesFragment.gotoVodeoActivity(getContext());
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onClick(int pos) {
        Toast.makeText(getContext(), "66666666", Toast.LENGTH_SHORT).show();
    }
}
