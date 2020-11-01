package com.example.programmer.rss.repositry;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.programmer.rss.data_base_room.MainDataBase;
import com.example.programmer.rss.models.ItemRoom;
import com.example.programmer.rss.ui.QueryItemPrefer;

import java.util.List;

public class RepositryPrefer {

    private static QueryItemPrefer queryItemPrefer;
    private static RepositryPrefer mInstance;

    public RepositryPrefer(Context context) {
        MainDataBase dataBase = MainDataBase.getInstance(context);
        queryItemPrefer = dataBase.queryItemPrefer();
    }

    public static synchronized RepositryPrefer getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new RepositryPrefer(context);
        }
        return mInstance;
    }

    public LiveData<List<ItemRoom>> getAllList() {
        return queryItemPrefer.getLists();
    }

    public void insert(ItemRoom modelMain) {
        new InsertAsyn(modelMain).execute();
    }

    public void update(ItemRoom modelMain) {
        new UpdateAsyn(modelMain).execute();
    }


    private static class InsertAsyn extends AsyncTask<Void, Void, Void> {
        private ItemRoom modelMain;

        public InsertAsyn(ItemRoom modelMain) {
            this.modelMain = modelMain;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            queryItemPrefer.inertItem(modelMain);
            Log.d("nameeeee", modelMain.getSource() + "");
            return null;
        }
    }

    private static class UpdateAsyn extends AsyncTask<Void, Void, Void> {
        private ItemRoom modelMain;

        public UpdateAsyn(ItemRoom modelMain) {
            this.modelMain = modelMain;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            queryItemPrefer.updateItem(modelMain);
            Log.d("nameeeee", modelMain.getSource() + "");
            return null;
        }
    }


}
