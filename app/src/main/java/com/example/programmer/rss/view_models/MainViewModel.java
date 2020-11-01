package com.example.programmer.rss.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.programmer.rss.models.ItemEmail;
import com.example.programmer.rss.models.ItemRoom;
import com.example.programmer.rss.models.PopulerMovie;
import com.example.programmer.rss.repositry.RepositryEmail;
import com.example.programmer.rss.repositry.RepositryPrefer;
import com.example.programmer.rss.repositry.RespMovieApi;
import com.example.programmer.rss.repositry.RespsFavMovie;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private RespMovieApi api;
    private RespsFavMovie favMovie;
    //private LiveData<List<FavMovie>> listLiveData;


    private RepositryEmail email;
    private RepositryPrefer prefer;

    public MainViewModel(@NonNull Application application) {
        super(application);
        api = RespMovieApi.getInstance();
        //  favMovie = RespsFavMovie.getInstance(application);
        prefer = RepositryPrefer.getInstance(application);
        email = RepositryEmail.getInstance(application);
    }

    public LiveData<List<ItemRoom>> getAllItems() {
        return prefer.getAllList();
    }

    public LiveData<List<ItemEmail>> getAllEmail(String s) {
        return email.getAllList(s);
    }

    public RespsFavMovie getFavMovie() {
        return favMovie;
    }

    public LiveData<PopulerMovie> getLiveDataPopuler() {
        return api.getLiveDataPopuler();
    }

    public LiveData<PopulerMovie> getLiveDataTrend() {
        return api.getLiveDataTrend();
    }

//    public LiveData<List<FavMovie>> getListLiveData() {
//        listLiveData = favMovie.getAllMoviesFav();
//        return listLiveData;
//    }
//
//    public LiveData<Boolean> getMoviesFav(int id) {
//
//        return favMovie.getMoviesFav(id);
//    }
}
