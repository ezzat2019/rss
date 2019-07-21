package com.example.programmer.rss.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "item_prefer")
public class ItemRoom {
    @PrimaryKey
    @ColumnInfo(name = "source")
    private int source;

    public ItemRoom(int source) {
        this.source = source;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }
}
