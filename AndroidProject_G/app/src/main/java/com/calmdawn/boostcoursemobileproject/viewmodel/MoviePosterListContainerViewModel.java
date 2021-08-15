package com.calmdawn.boostcoursemobileproject.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.VolleyError;
import com.calmdawn.boostcoursemobileproject.common.CustomAsyncTask;
import com.calmdawn.boostcoursemobileproject.common.CustomAsyncTaskCallback;
import com.calmdawn.boostcoursemobileproject.db.AppDataBase;
import com.calmdawn.boostcoursemobileproject.db.entity.MoviePosterEntity;
import com.calmdawn.boostcoursemobileproject.model.MoviePosterListItem;
import com.calmdawn.boostcoursemobileproject.network.NetworkManager;
import com.google.gson.Gson;

import java.util.List;

public class MoviePosterListContainerViewModel extends ViewModel {


    private MutableLiveData<MoviePosterListItem> moviePosterListLiveData = new MutableLiveData<>();
    private CustomAsyncTask customAsyncTask = new CustomAsyncTask();


    public MutableLiveData<MoviePosterListItem> getMoviePosterListLiveData() {
        return moviePosterListLiveData;
    }

    public void setMoviePosterListLiveData(MutableLiveData<MoviePosterListItem> moviePosterListLiveData) {
        this.moviePosterListLiveData = moviePosterListLiveData;
    }

    public void requestMovieList(Context context) {
        String query = "?type=1";
        NetworkManager.getInstance(context).sendGetRequest(
                "/movie/readMovieList",
                query,
                new NetworkManager.RequestResultListener() {
                    @Override
                    public void requestSuccess(String response) {
                        Log.d("영화목록 요청", "성공: " + response);
                        responseConvertMoviePosterListToGson(response, context);
                    }

                    @Override
                    public void requestError(VolleyError error) {
                        Log.d("영화목록 요청", "에러: " + error);
                    }
                });
    }

    private void responseConvertMoviePosterListToGson(String response, Context context) {
        Gson gson = new Gson();
        MoviePosterListItem moviePosterList = gson.fromJson(response, MoviePosterListItem.class);

        if (moviePosterList.getCode() == 200) {
            moviePosterListLiveData.setValue(moviePosterList);
            saveMovieListRoomDB(moviePosterList.getResult(), context);
        }

    }

    public void getMovieListRoomDB(Context context) {
        MoviePosterListItem moviePosterListItem = new MoviePosterListItem();

        customAsyncTask.execute(new CustomAsyncTaskCallback() {
            @Override
            public void doInBackground() {
                moviePosterListItem.setResult(AppDataBase.getAppDataBase(context).moviePosterEntityDao().getAll());
            }

            @Override
            public void postExecute() {
                moviePosterListLiveData.setValue(moviePosterListItem);
            }

        });
    }

    private void saveMovieListRoomDB(List<MoviePosterEntity> result, Context context) {
        customAsyncTask.execute(new CustomAsyncTaskCallback() {
            @Override
            public void doInBackground() {
                AppDataBase.getAppDataBase(context).moviePosterEntityDao().deleteALL();
                AppDataBase.getAppDataBase(context).moviePosterEntityDao().insertALL(result);
            }

            @Override
            public void postExecute() {

            }

        });
    }


}