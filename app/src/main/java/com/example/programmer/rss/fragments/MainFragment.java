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
import com.example.programmer.rss.adapters.RecycleMainAdapter;
import com.example.programmer.rss.models.ModelMain;
import com.example.programmer.rss.ui.OnItemClickMain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    // ui
    private SliderLayout mDemoSlider;
    private RecyclerView recyclerView;
    private ImageView imageView;


    // var
    private RecycleMainAdapter adapter;
    private List<ModelMain> modelMains;


    public MainFragment() {
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
        adapter = new RecycleMainAdapter();
        adapter.setList(modelMains);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createSlider(view);

        createImageListener(view);

        createRecycleView(view);


    }

    private void createRecycleView(View view) {
        recyclerView = view.findViewById(R.id.rec_main);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClick(new OnItemClickMain() {
            @Override
            public void onClick(int pos) {
                Toast.makeText(getContext(), pos+"", Toast.LENGTH_SHORT).show();
            }
        });



    }


    private void createImageListener(View view) {
        imageView = view.findViewById(R.id.image_list);
        imageView.setOnClickListener(new View.OnClickListener() {
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
        Toast.makeText(getContext(), slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
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
}
