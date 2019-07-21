package com.example.programmer.rss.data_base_room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.programmer.rss.models.ItemRoom;
import com.example.programmer.rss.ui.QueryItemPrefer;

@Database(entities = {ItemRoom.class}, exportSchema = false, version = 2)
public abstract class MainDataBase extends RoomDatabase {
    private static MainDataBase mInstace;

    public static synchronized MainDataBase getInstance(Context context) {
        if (mInstace == null) {
            mInstace = Room.databaseBuilder(context, MainDataBase.class, "ezzat2").build();
        }
        return mInstace;
    }

    public abstract QueryItemPrefer queryItemPrefer();

}
