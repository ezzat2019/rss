package com.example.programmer.rss.ui;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.programmer.rss.models.ItemEmail;

import java.util.List;

@Dao
public interface QueryItemEmail {

    @Query("select * from user where email=:my_e")
    LiveData<List<ItemEmail>> getEmail(String my_e);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEmail(ItemEmail email);


}
