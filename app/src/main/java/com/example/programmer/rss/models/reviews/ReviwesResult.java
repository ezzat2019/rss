package com.example.programmer.rss.models.reviews;

import android.os.Parcel;
import android.os.Parcelable;

public class ReviwesResult implements Parcelable {
    public static final Creator<ReviwesResult> CREATOR = new Creator<ReviwesResult>() {
        @Override
        public ReviwesResult createFromParcel(Parcel source) {
            return new ReviwesResult(source);
        }

        @Override
        public ReviwesResult[] newArray(int size) {
            return new ReviwesResult[size];
        }
    };
    private String author;
    private String content;
    private String id;
    private String url;

    public ReviwesResult() {
    }

    public ReviwesResult(String author, String content, String id, String url) {
        this.author = author;
        this.content = content;
        this.id = id;
        this.url = url;
    }

    protected ReviwesResult(Parcel in) {
        this.author = in.readString();
        this.content = in.readString();
        this.id = in.readString();
        this.url = in.readString();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.author);
        dest.writeString(this.content);
        dest.writeString(this.id);
        dest.writeString(this.url);
    }
}
