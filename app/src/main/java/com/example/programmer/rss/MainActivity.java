package com.example.programmer.rss;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.programmer.rss.adapters.ViewPagerAdapter;
import com.example.programmer.rss.custom_classes.CustomViewPager;
import com.example.programmer.rss.fragments.ChannelsFragment;
import com.example.programmer.rss.fragments.MainFragment;
import com.example.programmer.rss.fragments.ProgramFragment;
import com.example.programmer.rss.fragments.TheMoviesFragment;
import com.example.programmer.rss.fragments.TvSeriesFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // ui
    private SearchView searchView;
    private ImageButton imageButton;
    private Toolbar toolbar;
    private CustomViewPager viewPager;
    private TabLayout tabLayout;


    // var
    private Boolean is_back = false;
    private ViewPagerAdapter viewPagerAdapter;
    private List<Fragment> fragmentList;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inatilizeAuth();

        createToolBar();

        createDrawer();

        createImageButton();

        createSearchView();

        createListFragments();

        createViewPageAndTabLayOut();

        printKeyHash();


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
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("KeyHash:", e.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("KeyHash:", e.toString());
        }
    }

    private void createListFragments() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new MainFragment());
        fragmentList.add(new TvSeriesFragment());
        fragmentList.add(new TheMoviesFragment());
        fragmentList.add(new ProgramFragment());
        fragmentList.add(new ChannelsFragment());
    }

    private void createViewPageAndTabLayOut() {
        viewPager = (CustomViewPager)findViewById(R.id.view_pager);
        viewPager.setPagingEnabled(false);

        tabLayout = findViewById(R.id.tab);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);



    }

    private void createSearchView() {
        searchView = findViewById(R.id.search);

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageButton(false);
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                showImageButton(true);
                return false;
            }
        });
    }

    private void createImageButton() {
        imageButton = findViewById(R.id.image_button);

    }

    private void showImageButton(boolean b) {
        if (b) {
            imageButton.setVisibility(View.VISIBLE);
        } else
            imageButton.setVisibility(View.GONE);
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
                Toast.makeText(this, "press again to exit ", Toast.LENGTH_SHORT).show();
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
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_login) {
            // Handle the camera action
        } else if (id == R.id.nav_create_an_account) {
            Intent intent=new Intent(getApplicationContext(),SignUpActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

        } else if (id == R.id.nav_sharks_and_judgments) {

        } else if (id == R.id.nav_privacy_policy) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
