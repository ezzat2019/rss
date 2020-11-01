package com.example.programmer.rss.models.reviews;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Reviews implements Parcelable {
    public static final Creator<Reviews> CREATOR = new Creator<Reviews>() {
        @Override
        public Reviews createFromParcel(Parcel source) {
            return new Reviews(source);
        }

        @Override
        public Reviews[] newArray(int size) {
            return new Reviews[size];
        }
    };
    private int id;
    private int page;
    private int total_pages;
    private int total_results;
    private List<ReviwesResult> results;

    public Reviews() {
    }

    public Reviews(int id, int page, int total_pages, int total_results, List<ReviwesResult> results) {
        this.id = id;
        this.page = page;
        this.total_pages = total_pages;
        this.total_results = total_results;
        this.results = results;
    }

    protected Reviews(Parcel in) {
        this.id = in.readInt();
        this.page = in.readInt();
        this.total_pages = in.readInt();
        this.total_results = in.readInt();
        this.results = in.createTypedArrayList(ReviwesResult.CREATOR);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public List<ReviwesResult> getResults() {
        return results;
    }

    public void setResults(List<ReviwesResult> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.page);
        dest.writeInt(this.total_pages);
        dest.writeInt(this.total_results);
        dest.writeTypedList(this.results);
    }
}
