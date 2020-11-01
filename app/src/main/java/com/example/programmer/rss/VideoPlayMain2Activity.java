package com.example.programmer.rss;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.programmer.rss.adapters.RecycleVideoAdapter;
import com.example.programmer.rss.custom_classes.ExoPlayerManager;
import com.example.programmer.rss.models.ItemEmail;
import com.example.programmer.rss.models.ItemRoom;
import com.example.programmer.rss.models.ModelMain;
import com.example.programmer.rss.models.video.Results;
import com.example.programmer.rss.models.video.VideoTraier;
import com.example.programmer.rss.repositry.RepositryEmail;
import com.example.programmer.rss.repositry.RepositryPrefer;
import com.example.programmer.rss.retrofit_helper.BaseRetrofit;
import com.example.programmer.rss.ui.MovieBaseApi;
import com.example.programmer.rss.ui.OnItemClickMain;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoPlayMain2Activity extends AppCompatActivity {

    public static final String BASE_YOUTUBE_URL = "http://www.youtube.com/watch?v=";
    private static final String TAG = "ExoPlayerActivity";
    private static final String KEY_VIDEO_URI = "video_uri";
    private static SimpleExoPlayer simpleExoPlayer;
    private static int id;
    private static String downloadUrl;
    private static String videoUri;
    private static List<Results> list;
    public static String nameofFilm = null;
    private static boolean isLogin = false;
    // ui
    private PlayerView mPlayerView;
    private RecyclerView recTrailer;
    private View vs;
    // var
    private RecycleVideoAdapter videoAdapter;
    private ExoPlayerManager manager;
    private Switch aSwitch;
    private long posi, dur;


    private SharedPreferences sharedPreferences2;
    private RepositryPrefer prefer;

    public static Intent getStartIntent(Context context, String videoUri) {
        Intent intent = new Intent(context, VideoPlayMain2Activity.class);
        intent.putExtra(KEY_VIDEO_URI, videoUri);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play_main2);

        vs = findViewById(R.id.vs);
        SharedPreferences sharedPreferences = getSharedPreferences("full", MODE_PRIVATE);
        prefer = RepositryPrefer.getInstance(getApplicationContext());
        List<ModelMain> mains = new ArrayList<>();
        sharedPreferences2 = LoginActivity.createSharedPerfernce(getApplicationContext());


        if (savedInstanceState != null) {
            posi = savedInstanceState.getLong("posi");
            Log.d("rrrrrr22", posi + "");

            dur = savedInstanceState.getLong("dur");

        } else {
            posi = 0;
            dur = 0;
            Log.d("rrrrrr", posi + "");

        }
        if (getIntent().hasExtra("nn")) {
            nameofFilm = getIntent().getStringExtra("nn");
        }

        if (vs.getVisibility() == View.GONE) {
            manager = ExoPlayerManager.getSharedInstance(getApplicationContext());
            if (getIntent().hasExtra("id")) {
                list = new ArrayList<>();
                fillTrailer2(getIntent().getIntExtra("id", -1));

            } else {
                playVideo(downloadUrl);
            }


        } else {
            manager = ExoPlayerManager.getSharedInstance(getApplicationContext());
            Log.e("eeeeeeee", "port");
            if (getIntent().hasExtra("id")) {

                TextView txt = findViewById(R.id.txt_n2);
                txt.setText(getIntent().getStringExtra("nn"));

                TextView txt_title = findViewById(R.id.txt_name2);
                txt_title.setText(getIntent().getStringExtra("nn"));

                id = getIntent().getIntExtra("id", -1);

                list = new ArrayList<>();
                fillTrailer(id);

                videoAdapter.setOnItemClick(new OnItemClickMain() {
                    @Override
                    public void onClick(int pos) {
                        mPlayerView = null;
                        posi = 0;

                        extractYoutubeUrl(list.get(pos).getKey());


                    }
                });
            } else {
                Toast.makeText(this, getString(R.string.error_happen), Toast.LENGTH_SHORT).show();
            }
            createImageButton();
            createSwitch();
        }


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        posi = manager.getmPlayer().getContentPosition();

        Log.d("rrrrrr", posi + "");
        outState.putLong("posi", posi);
        outState.putLong("dur", dur);


    }

    private void extractYoutubeUrl(String id2) {


        @SuppressLint("StaticFieldLeak") YouTubeExtractor mExtractor = new YouTubeExtractor(this) {
            @Override
            protected void onExtractionComplete(SparseArray<YtFile> sparseArray, VideoMeta videoMeta) {
                if (sparseArray != null) {

                    List<Integer> iTags = Arrays.asList(22, 137, 18);

                    for (Integer iTag : iTags) {
                        Log.e("rrrrrr", iTag + "");
                        if (sparseArray.get(iTag) == null) {
                            continue;
                        }
                        downloadUrl = sparseArray.get(iTag).getUrl();


                        playVideo(downloadUrl);
                        return;


                    }

                }

            }


        };
        mExtractor.extract(BASE_YOUTUBE_URL.concat(id2), true, true);
    }

    private void playVideo(String downloadUrl) {


        mPlayerView = findViewById(R.id.mPlayerView);
        mPlayerView.setPlayer(ExoPlayerManager.getSharedInstance(getApplicationContext()).getPlayerView().getPlayer());

        ExoPlayerManager.getSharedInstance(getApplicationContext()).playStream(downloadUrl);

        if (posi > 0) {
            Log.d("rrrrrr00", posi + "");
            ExoPlayerManager.getSharedInstance(getApplicationContext()).getPlayerView().getPlayer().seekTo(posi);
            ExoPlayerManager.getSharedInstance(getApplicationContext()).getPlayerView().getPlayer().setPlayWhenReady(true);

        } else {
            Log.d("rrrrrr00", posi + "");
            ExoPlayerManager.getSharedInstance(getApplicationContext()).getPlayerView().getPlayer().setPlayWhenReady(true);

        }


    }


    private void fillTrailer(int id) {
        recTrailer = findViewById(R.id.rec_trailer);
        recTrailer.setHasFixedSize(true);
        recTrailer.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        videoAdapter = new RecycleVideoAdapter();
        BaseRetrofit.getInstance().getApi().getTrailerById(id, MovieBaseApi.KEY).enqueue(new Callback<VideoTraier>() {
            @Override
            public void onResponse(Call<VideoTraier> call, Response<VideoTraier> response) {
                list = response.body().getResults();
                videoAdapter.setList(list);
                recTrailer.setAdapter(videoAdapter);
                if (!list.isEmpty()) {
                    videoUri = list.get(0).getKey();
                    extractYoutubeUrl(videoUri);
                } else {
                    Toast.makeText(VideoPlayMain2Activity.this, R.string.no_trailer, Toast.LENGTH_SHORT).show();
                }


            }


            @Override
            public void onFailure(Call<VideoTraier> call, Throwable t) {


            }
        });

    }

    private void fillTrailer2(int id) {


        BaseRetrofit.getInstance().getApi().getTrailerById(id, MovieBaseApi.KEY).enqueue(new Callback<VideoTraier>() {
            @Override
            public void onResponse(Call<VideoTraier> call, Response<VideoTraier> response) {
                list = response.body().getResults();


                if (!list.isEmpty()) {
                    videoUri = list.get(0).getKey();
                    extractYoutubeUrl(videoUri);
                } else {
                    Toast.makeText(VideoPlayMain2Activity.this, R.string.no_trailer, Toast.LENGTH_SHORT).show();
                }


            }


            @Override
            public void onFailure(Call<VideoTraier> call, Throwable t) {


            }
        });

    }


    @Override
    protected void onStop() {
        super.onStop();
        manager.stopPlayer(true);
    }

    private void createImageButton() {
        ImageButton btn_add = findViewById(R.id.btn_add);
        ImageButton btn_share = findViewById(R.id.btn_share);

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        getString(R.string.intent) + BASE_YOUTUBE_URL.concat(id + ""));
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isLogin = sharedPreferences2.getBoolean("is_login", false);
                if (isLogin) {
                    Intent intent = new Intent(getApplicationContext(), PreferActivity.class);

                    prefer.insert(new ItemRoom(getIntent().getStringExtra("url"), getIntent().getStringExtra("nn")
                            , id));
                    RepositryEmail.getInstance(getApplicationContext())
                            .insert(new ItemEmail(sharedPreferences2.getString("email", getIntent().getStringExtra("url"))));

                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(VideoPlayMain2Activity.this);

                    // set title
                    alertDialogBuilder.setTitle(getString(R.string.attention));

                    // set dialog message
                    alertDialogBuilder
                            .setMessage(getString(R.string.attention2))
                            .setCancelable(false)
                            .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    startActivity(intent);

                                }
                            })
                            .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // if this button is clicked, just close
                                    // the dialog box and do nothing
                                    dialog.cancel();
                                }
                            });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                }


            }
        });
    }

    private void createSwitch() {
        aSwitch = findViewById(R.id.swittch);

        aSwitch.setChecked(sharedPreferences2.getBoolean("ads", true));

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, final boolean b) {
                if (!b) {
                    AlertDialog alertDialog = new AlertDialog.Builder(VideoPlayMain2Activity.this)
                            .setMessage(getString(R.string.ads))
                            .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    sharedPreferences2.edit().putBoolean("ads", false).commit();

                                }
                            }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    aSwitch.setChecked(true);
                                    sharedPreferences2.edit().putBoolean("ads", true).commit();

                                }
                            }).create();
                    alertDialog.show();

                } else if (b) {
                    sharedPreferences2.edit().putBoolean("ads", true).commit();
                }
            }
        });

    }


    public void back(View view) {
        onBackPressed();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.stopPlayer(true);
    }


    @Override
    public void onBackPressed() {
        if (vs.getVisibility() == View.GONE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        } else {
            super.onBackPressed();
        }
    }


}
