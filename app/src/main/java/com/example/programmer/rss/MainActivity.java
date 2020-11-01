package com.example.programmer.rss;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.programmer.rss.adapters.RecycleMainAdapter;
import com.example.programmer.rss.adapters.RecycleMainAdapterTrend;
import com.example.programmer.rss.adapters.RecycleMainSearchAdapetr;
import com.example.programmer.rss.models.PopulerMovie;
import com.example.programmer.rss.models.Results;
import com.example.programmer.rss.ui.OnItemClickMain;
import com.example.programmer.rss.view_models.MainViewModel;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, OnItemClickMain, RecycleMainAdapterTrend.OnItemClickMainTrend {

    public final static String BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w500/";
    public static SearchView searchView;
    public static List<Results> results;
    public static List<Results> resultTrens;
    private static ImageButton imageButton;
    // ui
    private SliderLayout mDemoSlider;
    private Boolean isShow = null;
    private Toolbar toolbar;
    private RecyclerView recyclerViewPopuler;
    private RecyclerView recyclerViewTrends;
    private ScrollView relativeLayout;
    private RecyclerView recyclerViewSearch;
    // var
    private Boolean is_back = false;
    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;
    private MainViewModel viewModel;
    private RecycleMainAdapter recycleMainAdapter;
    private RecycleMainAdapterTrend recycleMainAdapterTrend;
    private RecycleMainSearchAdapetr recycleMainSearchAdapetr;


    private static void showImageButton(boolean b) {
        if (b) {
            imageButton.setVisibility(View.VISIBLE);
        } else
            imageButton.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativeLayout = findViewById(R.id.realtive_view);
        recyclerViewSearch = findViewById(R.id.rec_search);


        inatilizeAuth();

        createToolBar();

        createDrawer();

        setupRecPopular();

        createImageButton();


        buildViewModel();

        fillPopulerData();
        fillTerndData();

        recycleMainAdapter = new RecycleMainAdapter(this);
        recycleMainAdapterTrend = new RecycleMainAdapterTrend(this);
        printKeyHash();
        createImageListener();

        recycleMainSearchAdapetr = new RecycleMainSearchAdapetr(this);
        sharedPreferences = LoginActivity.createSharedPerfernce(getApplicationContext());
        if (savedInstanceState != null && savedInstanceState.containsKey("is_show")) {
            isShow = savedInstanceState.getBoolean("is_show");
            fillSearchRec();
        } else
            isShow = false;
        if (isShow) {
            showImageButton(!isShow);
            showSearch(isShow);
        }


    }

    private void showSearch(boolean b) {
        if (b) {
            relativeLayout.setVisibility(View.INVISIBLE);
            recyclerViewSearch.setVisibility(View.VISIBLE);
        } else {
            relativeLayout.setVisibility(View.VISIBLE);
            recyclerViewSearch.setVisibility(View.GONE);

        }
    }

    private void setupRecPopular() {
        recyclerViewPopuler = findViewById(R.id.rec_rectangle1);
        recyclerViewPopuler.setHasFixedSize(true);
        recyclerViewPopuler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));


        recyclerViewTrends = findViewById(R.id.rec_rectangle2);
        recyclerViewTrends.setHasFixedSize(true);
        recyclerViewTrends.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));


        recyclerViewSearch.setHasFixedSize(true);
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


    }


    private void fillSearchRec() {

        if (results != null) {
            recycleMainSearchAdapetr.setList(results);
            recyclerViewSearch.setAdapter(recycleMainSearchAdapetr);
        }
    }

    void fillTerndData() {
        viewModel.getLiveDataTrend().observe(this, new Observer<PopulerMovie>() {
            @Override
            public void onChanged(PopulerMovie populerMovie) {
                resultTrens = new ArrayList<>(populerMovie.getResults());
                if (!resultTrens.isEmpty() && resultTrens != null) {

                    recycleMainAdapterTrend.setList(populerMovie.getResults());
                    recyclerViewTrends.setAdapter(recycleMainAdapterTrend);
                }


            }
        });

    }

    private void createImageListener() {

        ImageView imageView11 = findViewById(R.id.image_list11);
        imageView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoGridActivity("list");


            }
        });
        ImageView imageView22 = findViewById(R.id.image_list22);
        imageView22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoGridActivity("list1");

            }
        });


    }

    private void gotoGridActivity(String flag) {
        Intent intent = new Intent(getApplicationContext(), ShowGridActivity.class);
        intent.putExtra("type", flag);

        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    private void buildViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

    }

    private void fillPopulerData() {


        viewModel.getLiveDataPopuler().observe(this, new Observer<PopulerMovie>() {
            @Override
            public void onChanged(PopulerMovie populerMovie) {
                results = new ArrayList<>(populerMovie.getResults());
                if (!results.isEmpty() && results != null) {
                    createSlider();
                    recycleMainAdapter.setList(populerMovie.getResults());
                    recyclerViewPopuler.setAdapter(recycleMainAdapter);
                }


            }
        });

    }

    @Override
    public void onStop() {
        if (mDemoSlider != null)
            mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    void createSlider() {

        mDemoSlider = findViewById(R.id.slider2);

        HashMap<String, String> url_maps = new HashMap<>();

        url_maps.put(results.get(0).getTitle(), BASE_URL_IMAGE.concat(results.get(0).getPoster_path()));

        url_maps.put(results.get(1).getTitle(), BASE_URL_IMAGE.concat(results.get(1).getPoster_path()));
        url_maps.put(results.get(2).getTitle(), BASE_URL_IMAGE.concat(results.get(2).getPoster_path()));
        url_maps.put(results.get(3).getTitle(), BASE_URL_IMAGE.concat(results.get(3).getPoster_path()));
        url_maps.put(results.get(4).getTitle(), BASE_URL_IMAGE.concat(results.get(4).getPoster_path()));
        url_maps.put(results.get(5).getTitle(), BASE_URL_IMAGE.concat(results.get(5).getPoster_path()));


        for (int i = 0; i < 6; i++) {
            TextSliderView textSliderView = new TextSliderView(getApplicationContext());
            // initialize a SliderLayout
            textSliderView
                    .description(url_maps.keySet().toArray()[i].toString())
                    .image(url_maps.get(url_maps.keySet().toArray()[i].toString()))


                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);


            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(6000);
        mDemoSlider.addOnPageChangeListener(this);

    }

    private void inatilizeAuth() {
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
        createSearchView();

    }


    private void printKeyHash() {
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.example.programmer.rss", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            Log.e("KeyHash:", e.toString());
        }
    }


    private void createSearchView() {
        searchView = findViewById(R.id.search);

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isShow = true;
                showImageButton(!isShow);
                fillSearchRec();
                showSearch(isShow);
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                isShow = false;
                showImageButton(!isShow);
                showSearch(isShow);
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                recycleMainSearchAdapetr.getFilter().filter(newText);
                return false;
            }
        });


    }

    private void createImageButton() {
        imageButton = findViewById(R.id.image_button);

    }

    private void createDrawer() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void createToolBar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (is_back) {
                finish();

            } else {
                Toast.makeText(this, getString(R.string.error_toast), Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        is_back = false;
                    }
                }, 1000);
                is_back = true;
            }

        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Boolean isLogin = sharedPreferences.getBoolean("is_login", false);
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_login) {

            if (isLogin) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);

            } else {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);

            }

        } else if (id == R.id.nav_create_an_account) {
            if (isLogin) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);

            } else {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }


        } else if (id == R.id.nav_sharks_and_judgments) {

        } else if (id == R.id.nav_privacy_policy) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("is_show", isShow);

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(int pos) {
        int id = results.get(pos).getId();
        Intent intent = new Intent(getApplicationContext(), VideoPlayMain2Activity.class);
        intent.putExtra("id", id);
        intent.putExtra("nn", results.get(pos).getTitle());
        intent.putExtra("url", results.get(pos).getPoster_path());

        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    public void onClickTrend(int pos) {
        int id = resultTrens.get(pos).getId();
        Intent intent = new Intent(getApplicationContext(), VideoPlayMain2Activity.class);
        intent.putExtra("id", id);
        intent.putExtra("nn", results.get(pos).getTitle());
        intent.putExtra("url", resultTrens.get(pos).getPoster_path());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
