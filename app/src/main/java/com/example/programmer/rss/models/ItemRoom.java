package com.example.programmer.rss.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "item_prefer")
public class ItemRoom {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "source")
    private String source;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "id")
    private int id;

    public ItemRoom() {
    }

    public ItemRoom(@NonNull String source, String name, int id) {
        this.source = source;
        this.name = name;
        this.id = id;
    }

    @Ignore
    public ItemRoom(String source, String name) {
        this.source = source;
        this.name = name;
    }

    @Ignore
    public ItemRoom(String source) {
        this.source = source;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
