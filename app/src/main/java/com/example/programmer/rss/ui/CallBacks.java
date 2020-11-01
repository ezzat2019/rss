package com.example.programmer.rss.ui;

public interface CallBacks {
    void callbackObserver(Object obj);

    interface playerCallBack {
        void onItemClickOnItem(Integer albumId);

        void onPlayingEnd();
    }
}
