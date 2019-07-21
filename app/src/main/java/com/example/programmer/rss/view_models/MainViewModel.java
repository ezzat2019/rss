package com.example.programmer.rss.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.programmer.rss.data_base_room.MainDataBase;
import com.example.programmer.rss.models.ItemRoom;
import com.example.programmer.rss.models.ModelMain;
import com.example.programmer.rss.repositry.RepositryPrefer;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private MutableLiveData<List<ItemRoom>>mutableLiveData;
    private LiveData<List<ItemRoom>>  listLiveData;
    private RepositryPrefer prefer;
    public MainViewModel(@NonNull Application application) {
        super(application);
        mutableLiveData=new MutableLiveData<>();
        prefer=RepositryPrefer.getInstance(application);
    }
    public LiveData<List<ItemRoom>> getAllItems()
    {
        return  prefer.getAllList();
    }

}
