package com.example.programmer.rss.ui;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.programmer.rss.models.ItemRoom;

import java.util.List;

@Dao
public interface QueryItemPrefer {

    @androidx.room.Query("Select * from item_prefer")
    LiveData<List<ItemRoom>> getLists();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void inertItem(ItemRoom modelMain);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateItem(ItemRoom modelMain);

    @Query("delete from item_prefer")
    void deleteAll();

}
