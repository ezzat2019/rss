package com.example.programmer.rss.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.programmer.rss.models.ItemEmail;
import com.example.programmer.rss.models.ItemRoom;
import com.example.programmer.rss.repositry.RepositryEmail;
import com.example.programmer.rss.repositry.RepositryPrefer;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private RepositryEmail email;
    private RepositryPrefer prefer;

    public MainViewModel(@NonNull Application application) {
        super(application);

        prefer = RepositryPrefer.getInstance(application);
        email = RepositryEmail.getInstance(application);
    }

    public LiveData<List<ItemRoom>> getAllItems() {
        return prefer.getAllList();
    }

    public LiveData<List<ItemEmail>> getAllEmail(String s) {
        return email.getAllList(s);
    }

}
