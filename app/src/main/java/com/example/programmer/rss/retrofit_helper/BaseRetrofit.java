package com.example.programmer.rss.retrofit_helper;


import com.example.programmer.rss.ui.MovieBaseApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseRetrofit {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final BaseRetrofit ourInstance = new BaseRetrofit();
    private MovieBaseApi api;


    private BaseRetrofit() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(MovieBaseApi.class);
    }

    public static BaseRetrofit getInstance() {
        return ourInstance;
    }

    public MovieBaseApi getApi() {
        return api;
    }
}
