package com.example.programmer.rss.ui;

import com.example.programmer.rss.models.PopulerMovie;
import com.example.programmer.rss.models.reviews.Reviews;
import com.example.programmer.rss.models.video.VideoTraier;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieBaseApi {

    String KEY = "d0c5d1c31cf3704a929ffd58eabf053d";

    @GET("movie/popular")
    Call<PopulerMovie> getAllMovies(@Query("api_key") String key);

    @GET("movie/top_rated")
    Call<PopulerMovie> getAllMoviesTrend(@Query("api_key") String key);

    @GET("movie/{id}/videos")
    Call<VideoTraier> getTrailerById(@Path("id") int id, @Query("api_key") String key);

    @GET("movie/{id}/reviews")
    Call<Reviews> getReviesById(@Path("id") int id, @Query("api_key") String key);

}
