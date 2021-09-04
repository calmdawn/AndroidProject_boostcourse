package com.calmdawn.boostcoursemobileproject.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.VolleyError;
import com.calmdawn.boostcoursemobileproject.common.CustomAsyncTask;
import com.calmdawn.boostcoursemobileproject.common.CustomAsyncTaskCallback;
import com.calmdawn.boostcoursemobileproject.db.AppDataBase;
import com.calmdawn.boostcoursemobileproject.db.entity.MovieCommentEntity;
import com.calmdawn.boostcoursemobileproject.model.MovieCommentListItem;
import com.calmdawn.boostcoursemobileproject.network.NetworkManager;
import com.google.gson.Gson;

import java.util.List;

public class SeeAllCommentViewModel extends ViewModel {
    MutableLiveData<MovieCommentListItem> commentListLiveData = new MutableLiveData<>();
    CustomAsyncTask customAsyncTask = new CustomAsyncTask();

    public MutableLiveData<MovieCommentListItem> getCommentListLiveData() {
        return commentListLiveData;
    }

    public void setCommentListLiveData(MutableLiveData<MovieCommentListItem> commentListLiveData) {
        this.commentListLiveData = commentListLiveData;
    }


    public void requestMovieAllCommentList(Context context, int movieId) {
        String query = "?id=" + movieId;
        NetworkManager.getInstance(context).sendGetRequest(
                "/movie/readCommentList",
                query,
                new NetworkManager.RequestResultListener() {
                    @Override
                    public void requestSuccess(String response) {
                        Log.d("영화한줄평 요청", "성공: " + response);
                        responseConvertMovieCommentListToGson(response, context);
                    }

                    @Override
                    public void requestError(VolleyError error) {
                        Log.d("영화한줄평 요청", "에러: " + error);
                    }
                });
    }

    private void responseConvertMovieCommentListToGson(String response, Context context) {
        Gson gson = new Gson();
        MovieCommentListItem movieCommentListItem = gson.fromJson(response, MovieCommentListItem.class);

        if (movieCommentListItem.getCode() == 200) {
            commentListLiveData.setValue(movieCommentListItem);
            saveMovieCommentListRoomDB(movieCommentListItem.getResult(), context);
        }
    }

    private void saveMovieCommentListRoomDB(List<MovieCommentEntity> result, Context context) {
        customAsyncTask.execute(new CustomAsyncTaskCallback() {
            @Override
            public void doInBackground() {
                AppDataBase.getAppDataBase(context).movieCommentEntityDao().insertALL(result);
            }

            @Override
            public void postExecute() {

            }
        });
    }

    public void getMovieCommentListRoomDB(Context context, int movieId) {
        MovieCommentListItem commentListItem = new MovieCommentListItem();
        customAsyncTask.execute(new CustomAsyncTaskCallback() {
            @Override
            public void doInBackground() {
                commentListItem.setResult(AppDataBase.getAppDataBase(context).movieCommentEntityDao().getCommentList(movieId, 10));
            }

            @Override
            public void postExecute() {
                commentListLiveData.setValue(commentListItem);
            }
        });
    }


    public double getUserRatingAvg(MovieCommentListItem movieCommentListItem) {

        int totalCount = movieCommentListItem.getResult().size();

        if (totalCount == 0) {
            return 0;
        } else {
            double totalRating = 0;

            for (int i = 0; i < totalCount; i++) {
                totalRating = totalRating + movieCommentListItem.getResult().get(i).getRating();
            }
            return ((int) ((totalRating / totalCount) * 10)) / 10.0;
        }

    }

    public int getTotalCommentCount(MovieCommentListItem movieCommentListItem) {
        return movieCommentListItem.getResult().size();
    }

}
