package com.example.programmer.rss.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "user", foreignKeys = {

        @ForeignKey(entity = ItemRoom.class
                , parentColumns = "source"
                , childColumns = "url_image"
                , onDelete = ForeignKey.CASCADE)})
public class ItemEmail {
    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "url_image")
    private String url;


    public ItemEmail(int id, String email, String url) {
        this.id = id;
        this.email = email;
        this.url = url;

    }

    @Ignore
    public ItemEmail(String email) {
        this.email = email;
    }

    @Ignore
    public ItemEmail(String email, String url) {
        this.email = email;
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
