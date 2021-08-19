package com.calmdawn.boostcoursemobileproject.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.VolleyError;
import com.calmdawn.boostcoursemobileproject.R;
import com.calmdawn.boostcoursemobileproject.common.CustomAsyncTask;
import com.calmdawn.boostcoursemobileproject.common.CustomAsyncTaskCallback;
import com.calmdawn.boostcoursemobileproject.db.AppDataBase;
import com.calmdawn.boostcoursemobileproject.db.entity.MovieCommentEntity;
import com.calmdawn.boostcoursemobileproject.db.entity.MovieDetailInfoEntity;
import com.calmdawn.boostcoursemobileproject.model.MovieCommentListItem;
import com.calmdawn.boostcoursemobileproject.model.MovieDetailInfoItem;
import com.calmdawn.boostcoursemobileproject.model.MovieGalleryItem;
import com.calmdawn.boostcoursemobileproject.network.NetworkManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

public class MovieDetailInfoViewModel extends ViewModel {

    private final int MAX_COMMENT_LIST = 3;

    MutableLiveData<MovieDetailInfoEntity> detailInfoEntityLiveData = new MutableLiveData<>();
    MutableLiveData<MovieCommentListItem> commentListLiveData = new MutableLiveData<>();
    MutableLiveData<List<MovieGalleryItem>> galleryItemsLiveData = new MutableLiveData<>();

    CustomAsyncTask customAsyncTask = new CustomAsyncTask();

    public MutableLiveData<MovieDetailInfoEntity> getDetailInfoEntityLiveData() {
        return detailInfoEntityLiveData;
    }

    public MutableLiveData<MovieCommentListItem> getCommentListLiveData() {
        return commentListLiveData;
    }

    public MutableLiveData<List<MovieGalleryItem>> getGalleryItemsLiveData() {
        return galleryItemsLiveData;
    }

    public void requestMovieDetailInfo(Context context, int movieId) {
        String query = "?id=" + movieId;
        NetworkManager.getInstance(context).sendGetRequest(
                "/movie/readMovie",
                query,
                new NetworkManager.RequestResultListener() {
                    @Override
                    public void requestSuccess(String response) {
                        Log.d("영화상세 요청", "성공: " + response);
                        responseConvertMovieDetailInfoToGson(response, context);
                    }

                    @Override
                    public void requestError(VolleyError error) {
                        Log.d("영화상세 요청", "에러: " + error);
                    }
                });
    }

    public void requestMovieCommentList(Context context, int movieId) {
        String query = "?id=" + movieId + "&" + "limit=" + MAX_COMMENT_LIST;
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

    private void responseConvertMovieDetailInfoToGson(String response, Context context) {
        Gson gson = new Gson();
        MovieDetailInfoItem movieDetailInfoItem = gson.fromJson(response, MovieDetailInfoItem.class);

        if (movieDetailInfoItem.getCode() == 200) {
            if (!movieDetailInfoItem.getResult().isEmpty()) {
                detailInfoEntityLiveData.setValue(movieDetailInfoItem.getResult().get(0));
                galleryItemsLiveData.setValue(getMovieGalleryItems(movieDetailInfoItem.getResult().get(0)));
                saveMovieDetailInfoRoomDB(movieDetailInfoItem.getResult().get(0), context);
            }
        }
    }


    private void saveMovieDetailInfoRoomDB(MovieDetailInfoEntity movieDetailInfoEntity, Context context) {

        customAsyncTask.execute(new CustomAsyncTaskCallback() {
            @Override
            public void doInBackground() {
                AppDataBase.getAppDataBase(context).movieDetailInfoEntityDao().delete(movieDetailInfoEntity.getTitle());
                AppDataBase.getAppDataBase(context).movieDetailInfoEntityDao().insert(movieDetailInfoEntity);
            }

            @Override
            public void postExecute() {

            }
        });
    }

    public void getMovieDetailInfoRoomDB(Context context, String title) {

        final MovieDetailInfoEntity[] loadedDetailInfoEntity = new MovieDetailInfoEntity[1];

        customAsyncTask.execute(new CustomAsyncTaskCallback() {
            @Override
            public void doInBackground() {
                loadedDetailInfoEntity[0] = AppDataBase.getAppDataBase(context).movieDetailInfoEntityDao().get(title);
            }

            @Override
            public void postExecute() {
                if (loadedDetailInfoEntity[0] != null) {
                    detailInfoEntityLiveData.setValue(loadedDetailInfoEntity[0]);
                    galleryItemsLiveData.setValue(getMovieGalleryItems(loadedDetailInfoEntity[0]));
                }
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
                commentListItem.setResult(AppDataBase.getAppDataBase(context).movieCommentEntityDao().getCommentList(movieId, 3));
            }

            @Override
            public void postExecute() {
                commentListLiveData.setValue(commentListItem);
            }
        });
    }

    public int getGradeImg(Integer grade) {
        if (grade == 12) {
            return R.drawable.common_ic_grade_12;
        } else if (grade == 15) {
            return R.drawable.common_ic_grade_15;
        } else if (grade == 19) {
            return R.drawable.common_ic_grade_19;
        } else {
            return 0;
        }
    }

    private List<MovieGalleryItem> getMovieGalleryItems(MovieDetailInfoEntity detailInfoEntity) {
        List<MovieGalleryItem> galleryItems = new ArrayList<>();

        StringTokenizer st;
        if (detailInfoEntity.getPhotos() != null) {
            st = new StringTokenizer(detailInfoEntity.getPhotos(), ",");  //영화 이미지 url 넣기
            while (st.hasMoreTokens()) {
                galleryItems.add(new MovieGalleryItem(st.nextToken(), 1));
            }
        }

        if (detailInfoEntity.getVideos() != null) {
            st = new StringTokenizer(detailInfoEntity.getVideos(), ",");  //영화 동영상 url 넣기
            while (st.hasMoreTokens()) {
                galleryItems.add(new MovieGalleryItem(st.nextToken(), 2));
            }
        }

        return galleryItems;
    }

}
