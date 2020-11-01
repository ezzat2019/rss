package com.example.programmer.rss.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Results implements Parcelable {
    public static final Creator<Results> CREATOR = new Creator<Results>() {
        @Override
        public Results createFromParcel(Parcel source) {
            return new Results(source);
        }

        @Override
        public Results[] newArray(int size) {
            return new Results[size];
        }
    };
    private Double popularity;
    private int vote_count;
    private Boolean video;
    private String poster_path;
    private int id;
    private Boolean adult;
    private String backdrop_path;
    private String original_language;
    private String original_title;
    private String release_date;
    private Integer[] genre_ids;
    private Double vote_average;
    private String overview;
    private String title;

    public Results(Double popularity, int vote_count, Boolean video, String poster_path, int id, Boolean adult, String backdrop_path, String original_language, String original_title, String release_date, Integer[] genre_ids, Double vote_average, String overview, String title) {
        this.popularity = popularity;
        this.vote_count = vote_count;
        this.video = video;
        this.poster_path = poster_path;
        this.id = id;
        this.adult = adult;
        this.backdrop_path = backdrop_path;
        this.original_language = original_language;
        this.original_title = original_title;
        this.release_date = release_date;
        this.genre_ids = genre_ids;
        this.vote_average = vote_average;
        this.overview = overview;
        this.title = title;
    }

    public Results() {
    }

    protected Results(Parcel in) {
        this.popularity = (Double) in.readValue(Double.class.getClassLoader());
        this.vote_count = in.readInt();
        this.video = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.poster_path = in.readString();
        this.id = in.readInt();
        this.adult = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.backdrop_path = in.readString();
        this.original_language = in.readString();
        this.original_title = in.readString();
        this.release_date = in.readString();

        this.vote_average = (Double) in.readValue(Double.class.getClassLoader());
        this.overview = in.readString();
        this.title = in.readString();
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setGenre_ids(Integer[] genre_ids) {
        this.genre_ids = genre_ids;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.popularity);
        dest.writeInt(this.vote_count);
        dest.writeValue(this.video);
        dest.writeString(this.poster_path);
        dest.writeInt(this.id);
        dest.writeValue(this.adult);
        dest.writeString(this.backdrop_path);
        dest.writeString(this.original_language);
        dest.writeString(this.original_title);
        dest.writeString(this.release_date);

        dest.writeValue(this.vote_average);
        dest.writeString(this.overview);
        dest.writeString(this.title);
    }
}
