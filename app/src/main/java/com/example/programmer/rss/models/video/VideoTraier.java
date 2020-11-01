package com.example.programmer.rss.models.video;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class VideoTraier implements Parcelable {
    public static final Creator<VideoTraier> CREATOR = new Creator<VideoTraier>() {
        @Override
        public VideoTraier createFromParcel(Parcel source) {
            return new VideoTraier(source);
        }

        @Override
        public VideoTraier[] newArray(int size) {
            return new VideoTraier[size];
        }
    };
    private int id;
    private List<Results> results;

    public VideoTraier() {
    }

    public VideoTraier(int id, List<Results> results) {
        this.id = id;
        this.results = results;
    }

    protected VideoTraier(Parcel in) {
        this.id = in.readInt();
        this.results = in.createTypedArrayList(Results.CREATOR);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeTypedList(this.results);
    }
}
