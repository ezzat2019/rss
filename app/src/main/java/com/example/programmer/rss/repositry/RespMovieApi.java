package com.example.programmer.rss.repositry;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.programmer.rss.models.PopulerMovie;
import com.example.programmer.rss.retrofit_helper.BaseRetrofit;
import com.example.programmer.rss.ui.MovieBaseApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RespMovieApi {
    private static final RespMovieApi ourInstance = new RespMovieApi();
    private MutableLiveData<PopulerMovie> liveDataPopuler;
    private MutableLiveData<PopulerMovie> liveDataTrend;
    private BaseRetrofit retrofit;

    private RespMovieApi() {
        retrofit = BaseRetrofit.getInstance();
        fillPopulerMovie();
        fillTrendMovie();


    }

    public static RespMovieApi getInstance() {
        return ourInstance;
    }

    public LiveData<PopulerMovie> getLiveDataPopuler() {
        return liveDataPopuler;
    }

    public LiveData<PopulerMovie> getLiveDataTrend() {
        return liveDataTrend;
    }

    private void fillTrendMovie() {
        liveDataTrend = new MutableLiveData<>();
        retrofit.getApi().getAllMoviesTrend(MovieBaseApi.KEY)
                .enqueue(new Callback<PopulerMovie>() {
                    @Override
                    public void onResponse(Call<PopulerMovie> call, Response<PopulerMovie> response) {

                        liveDataTrend.postValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<PopulerMovie> call, Throwable t) {

                    }
                });
    }

    private void fillPopulerMovie() {
        liveDataPopuler = new MutableLiveData<>();
        retrofit.getApi().getAllMovies(MovieBaseApi.KEY).enqueue(new Callback<PopulerMovie>() {
            @Override
            public void onResponse(Call<PopulerMovie> call, Response<PopulerMovie> response) {
                liveDataPopuler.postValue(response.body());


            }

            @Override
            public void onFailure(Call<PopulerMovie> call, Throwable t) {


            }
        });

    }
}
