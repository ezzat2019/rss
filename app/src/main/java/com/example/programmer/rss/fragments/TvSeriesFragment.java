package com.example.programmer.rss.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.programmer.rss.R;
import com.example.programmer.rss.adapters.RecycleFrag1Adapter;
import com.example.programmer.rss.adapters.RecycleFrag2Adapter;
import com.example.programmer.rss.adapters.RecycleFrag3Adapter;
import com.example.programmer.rss.adapters.RecycleMain2Adapter;
import com.example.programmer.rss.adapters.RecycleMainAdapter;
import com.example.programmer.rss.models.ModelMain;
import com.example.programmer.rss.ui.OnItemClickMain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvSeriesFragment extends Fragment  implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    // ui
    private SliderLayout mDemoSlider;
    private RecyclerView recyclerViewFrag1,recyclerViewFrag2,recyclerViewFrag3;
    private ImageView imageView11,imageView22,imageView33;


    //var
    private List<ModelMain> modelMains;
    private RecycleFrag1Adapter frag1Adapter;
    private RecycleFrag2Adapter frag2Adapter;
    private RecycleFrag3Adapter frag3Adapter;


    public TvSeriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        modelMains = new ArrayList<>();

        modelMains.add(new ModelMain(R.drawable.sor1));
        modelMains.add(new ModelMain(R.drawable.sor2));
        modelMains.add(new ModelMain(R.drawable.sor3));
        modelMains.add(new ModelMain(R.drawable.sor4));
        modelMains.add(new ModelMain(R.drawable.sor5));
        modelMains.add(new ModelMain(R.drawable.sor6));
        modelMains.add(new ModelMain(R.drawable.sor7));
        modelMains.add(new ModelMain(R.drawable.sor8));
        modelMains.add(new ModelMain(R.drawable.sor9));



        frag1Adapter=new RecycleFrag1Adapter();
        frag2Adapter=new RecycleFrag2Adapter();
        frag3Adapter=new RecycleFrag3Adapter();


        frag1Adapter.setList(modelMains);
        frag2Adapter.setList(modelMains);
        frag3Adapter.setList(modelMains);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_series, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createSlider(view);

        createImageListener(view);



        createRecycleFrag1(view);

        createRecycleFrag2(view);

        createRecycleFrag3(view);





    }



    private void createRecycleFrag3(View view) {
        recyclerViewFrag3=view.findViewById(R.id.rec_rectangle3);
        recyclerViewFrag3.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerViewFrag3.setAdapter(frag3Adapter);
        frag3Adapter.setOnItemClick(new OnItemClickMain() {
            @Override
            public void onClick(int pos) {
                Toast.makeText(getContext(), pos+"", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createRecycleFrag2(View view) {
        recyclerViewFrag2=view.findViewById(R.id.rec_rectangle2);
        recyclerViewFrag2.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,true));
        recyclerViewFrag2.setAdapter(frag2Adapter);
        frag2Adapter.setOnItemClick(new OnItemClickMain() {
            @Override
            public void onClick(int pos) {
                Toast.makeText(getContext(), pos+"", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createRecycleFrag1(View view) {
        recyclerViewFrag1=view.findViewById(R.id.rec_rectangle1);
        recyclerViewFrag1.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerViewFrag1.setAdapter(frag1Adapter);
        frag1Adapter.setOnItemClick(new OnItemClickMain() {
            @Override
            public void onClick(int pos) {
                Toast.makeText(getContext(), pos+"", Toast.LENGTH_SHORT).show();
            }
        });

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
        file_maps.put("شباب البومب 8", R.drawable.sor5);
        file_maps.put("ولد العلابه", R.drawable.sor4);
        file_maps.put("رحيم 2", R.drawable.sor3);
        file_maps.put("خمسه ونص", R.drawable.sor2);
        file_maps.put("لا تطفئ الشمعه", R.drawable.sor1);

        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getContext());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))

                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);


            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(7000);
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
