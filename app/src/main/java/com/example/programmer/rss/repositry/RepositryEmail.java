package com.example.programmer.rss.repositry;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.programmer.rss.data_base_room.MainDataBase;
import com.example.programmer.rss.models.ItemEmail;
import com.example.programmer.rss.ui.QueryItemEmail;

import java.util.List;

public class RepositryEmail {

    private static QueryItemEmail queryItemEmail;
    private static RepositryEmail mInstance;

    public RepositryEmail(Context context) {
        MainDataBase dataBase = MainDataBase.getInstance(context);
        queryItemEmail = dataBase.queryItemEmail();
    }

    public static synchronized RepositryEmail getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new RepositryEmail(context);
        }
        return mInstance;
    }

    public LiveData<List<ItemEmail>> getAllList(String e) {
        return queryItemEmail.getEmail(e);
    }

    public void insert(ItemEmail modelMain) {
        new InsertAsyn(modelMain).execute();
    }


    private static class InsertAsyn extends AsyncTask<Void, Void, Void> {
        private ItemEmail modelMain;

        public InsertAsyn(ItemEmail modelMain) {
            this.modelMain = modelMain;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (modelMain != null)
                queryItemEmail.insertEmail(modelMain);
            return null;
        }
    }


}
