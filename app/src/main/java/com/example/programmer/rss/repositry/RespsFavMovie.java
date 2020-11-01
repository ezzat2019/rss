package com.example.programmer.rss.repositry;

public class RespsFavMovie {
//    private static RespsFavMovie ourInstance;
//    private MutableLiveData<List<FavMovie>> mutableLiveData;
//    private BaseRoomDataBase dataBase;
//    private FavHelber helber;
//
//    private RespsFavMovie(Context context) {
//        mutableLiveData = new MutableLiveData<>();
//        dataBase = BaseRoomDataBase.getInstance(context);
//        helber = dataBase.getFavHelber();
//
//    }
//
//    public static RespsFavMovie getInstance(Context context) {
//        if (ourInstance == null) {
//            synchronized (RespMovieApi.class) {
//                if (ourInstance == null) {
//                    ourInstance = new RespsFavMovie(context);
//                }
//            }
//
//        }
//        return ourInstance;
//    }
//
//    public void insert(final FavMovie favMovie) {
//        favMovie.setFav(false);
//        AppExecutors.getInstance().diskIO().execute(new Runnable() {
//            @Override
//            public void run() {
//                helber.insertFav(favMovie);
//
//            }
//        });
//        Log.d("yyyyyyyyyy", "insert good");
//    }
//
//    public void update(final FavMovie favMovie, final boolean isFav) {
//
//        AppExecutors.getInstance().diskIO().execute(new Runnable() {
//            @Override
//            public void run() {
//                favMovie.setFav(isFav);
//                helber.updateFav(favMovie);
//
//            }
//        });
//        Log.d("yyyyyyyyyy", "update good");
//    }
//
//    public void delete(final FavMovie favMovie) {
//        AppExecutors.getInstance().diskIO().execute(new Runnable() {
//            @Override
//            public void run() {
//                helber.deleteFav(favMovie);
//
//            }
//        });
//        Log.d("yyyyyyyyyy", "delete good");
//    }
//
//    public LiveData<List<FavMovie>> getAllMoviesFav() {
//        AppExecutors.getInstance().diskIO().execute(new Runnable() {
//            @Override
//            public void run() {
//                mutableLiveData.postValue(helber.getAllFav());
//
//            }
//        });
//
//        return mutableLiveData;
//    }
//
//    public LiveData<Boolean> getMoviesFav(final int id) {
//        final MutableLiveData<Boolean> mutableLiveData = new MutableLiveData<>();
//        AppExecutors.getInstance().diskIO().execute(new Runnable() {
//            @Override
//            public void run() {
//                mutableLiveData.postValue(helber.getisFav(id));
//            }
//        });
//
//
//        return mutableLiveData;
//    }

}
