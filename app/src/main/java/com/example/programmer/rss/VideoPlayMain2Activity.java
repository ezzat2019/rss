package com.example.programmer.rss;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.programmer.rss.adapters.ViewPagerAdapter2;
import com.example.programmer.rss.custom_classes.CustomViewPager;
import com.example.programmer.rss.custom_classes.VideoPlayerConfig;
import com.example.programmer.rss.fragments.ClipsFragment;
import com.example.programmer.rss.fragments.EpisodesFragment;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class VideoPlayMain2Activity extends AppCompatActivity implements Player.EventListener {

    private static final String TAG = "ExoPlayerActivity";
    private static final String KEY_VIDEO_URI = "video_uri";


    // ui
    private static PlayerView videoFullScreenPlayer;
    private TextView txt_title, txt_desc;
    private Switch aSwitch;
    private ImageButton btn_share, btn_add;
    private CustomViewPager viewPager;
    private TabLayout tableLayout;


    // var
    private static String videoUri;
    private static SimpleExoPlayer player;
    private static Handler mHandler;
    private static Runnable mRunnable;
    private static Boolean toggleScreen = false;
    private ViewPagerAdapter2 pagerAdapter2;
    private static List<Fragment> list;
    private static String type;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (toggleScreen) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        } else {

        }
        setContentView(R.layout.activity_video_play_main2);

        videoFullScreenPlayer = findViewById(R.id.videoFullScreenPlayer);
        videoUri = VideoPlayerConfig.DEFAULT_VIDEO_URL;

        sharedPreferences=getSharedPreferences("full",MODE_PRIVATE);



        if (!toggleScreen) {
            createTxtName();

            createSwitch();

            createImageButton();

        }


        setUp();




    }

    @Override
    protected void onResume() {
        super.onResume();
        type = getIntent().getStringExtra("type");


        if (!toggleScreen)
        {
            createViewPageAndTabLayaout();

        }

    }



    private void createViewPageAndTabLayaout() {
        viewPager = findViewById(R.id.view_pager2);
        tableLayout = findViewById(R.id.tab_vedio);

        ClipsFragment clipsFragment = new ClipsFragment();
        EpisodesFragment episodesFragment = new EpisodesFragment(type);

        list = new ArrayList<>();
        list.add(episodesFragment);
        list.add(clipsFragment);

        pagerAdapter2 = new ViewPagerAdapter2(getSupportFragmentManager(), list);

        viewPager.setAdapter(pagerAdapter2);
        tableLayout.setupWithViewPager(viewPager);
        viewPager.setPagingEnabled(false);


    }

    private void createImageButton() {
        btn_add = findViewById(R.id.btn_add);
        btn_share = findViewById(R.id.btn_share);

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out my app at: https://play.google.com/store/apps/details?id=com.google.android.apps.plus");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void createSwitch() {
        aSwitch = findViewById(R.id.swittch);

        aSwitch.setChecked(true);

    }

    private void createTxtName() {
        txt_title = findViewById(R.id.txt_name);
        txt_desc = findViewById(R.id.txt_desc);
    }

    private void setUp() {
        initializePlayer();
        if (videoUri == null) {
            return;
        }
        buildMediaSource(Uri.parse(videoUri));
    }

    public void back(View view) {
        onBackPressed();
    }

    private void initializePlayer() {
        if (player == null) {
            // 1. Create a default TrackSelector
            LoadControl loadControl = new DefaultLoadControl(
                    new DefaultAllocator(true, 16),
                    VideoPlayerConfig.MIN_BUFFER_DURATION,
                    VideoPlayerConfig.MAX_BUFFER_DURATION,
                    VideoPlayerConfig.MIN_PLAYBACK_START_BUFFER,
                    VideoPlayerConfig.MIN_PLAYBACK_RESUME_BUFFER, -1, true);

            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);
            // 2. Create the player
            player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(this), trackSelector, loadControl);
            videoFullScreenPlayer.setPlayer(player);
        }
    }

    public static Intent getStartIntent(Context context, String videoUri) {
        Intent intent = new Intent(context, VideoPlayMain2Activity.class);
        intent.putExtra(KEY_VIDEO_URI, videoUri);
        return intent;
    }

    private void buildMediaSource(Uri mUri) {
        // Measures bandwidth during playback. Can be null if not required.
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, getString(R.string.app_name)), bandwidthMeter);
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(mUri);
        // Prepare the player with the source.
        player.prepare(videoSource);
        player.setPlayWhenReady(false);
        player.addListener(this);
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {


    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        switch (playbackState) {

            case Player.STATE_BUFFERING:
                //spinnerVideoDetails.setVisibility(View.VISIBLE);
                break;
            case Player.STATE_ENDED:
                // Activate the force enable
                break;
            case Player.STATE_IDLE:

                break;
            case Player.STATE_READY:
                // spinnerVideoDetails.setVisibility(View.GONE);

                break;
            default:
                // status = PlaybackStatus.IDLE;
                break;
        }
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }

    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    private void pausePlayer() {
        if (player != null) {
            player.setPlayWhenReady(false);
            player.getPlaybackState();
        }
    }

    private void resumePlayer() {
        if (player != null) {
            player.setPlayWhenReady(true);
            player.getPlaybackState();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        pausePlayer();
        if (mRunnable != null) {
            mHandler.removeCallbacks(mRunnable);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        resumePlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }


    public void full(View view) {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("type",type);
        editor.commit();




        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        toggleScreen = true;
    }

    @Override
    public void onBackPressed() {
        if (toggleScreen) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            toggleScreen = false;
        } else {
            toggleScreen = false;
            super.onBackPressed();
        }
    }
}
