package com.example.boostcoursemoblieproject.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.boostcoursemoblieproject.R;
import com.example.boostcoursemoblieproject.adapter.ListViewAdapter;
import com.example.boostcoursemoblieproject.common.CustomToast;
import com.example.boostcoursemoblieproject.fragment.MovieDetailInfoFragment;
import com.example.boostcoursemoblieproject.item.CommentList;
import com.example.boostcoursemoblieproject.item.ResponseMovieInfo;
import com.example.boostcoursemoblieproject.item.Users;
import com.example.boostcoursemoblieproject.network.AppHelper;
import com.example.boostcoursemoblieproject.network.NetworkState;
import com.example.boostcoursemoblieproject.roomdb.AppDataBase;
import com.example.boostcoursemoblieproject.roomdb.CommentListEntity;
import com.example.boostcoursemoblieproject.roomdb.CommentListEntityDao;
import com.google.gson.Gson;

import java.util.List;

public class SeeAllReviewActivity extends AppCompatActivity implements View.OnClickListener {


    public static final int REQUEST_CODE_OF_SEE_ALL_REVIEW_ACTIVITY = 3000;
    public static final int RESULT_CODE_OF_SEE_ALL_REVIEW_ACTIVITY = 3001;
    private ListView seeAllListView;
    private ListViewAdapter seeAllAdapter;
    private CustomToast customToast;

    private int movieId;
    private CommentList commentList;

    private TextView reviewPeopleTv;

    private AppDataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all_review);

        setCustomActionBar();

        customToast = new CustomToast(getApplicationContext());

        movieId = getIntent().getIntExtra(MovieDetailInfoFragment.FRAGMENT_INFO_DETAIL_MOVIE_ID, 0);
        String movieName = getIntent().getStringExtra(MovieDetailInfoFragment.FRAGMENT_INFO_DETAIL_MOVIE_NAME);
        float rating = getIntent().getFloatExtra(MovieDetailInfoFragment.FRAGMENT_INFO_DETAIL_MOVIE_RATE, 0);

        TextView titleMovieNameTv = findViewById(R.id.activity_see_all_review_title_tv);
        TextView ratingbarTv = findViewById(R.id.activity_see_all_review_ratingbar_tv);
        reviewPeopleTv = findViewById(R.id.activity_see_all_review_people_tv);

        titleMovieNameTv.setText(movieName);
        ratingbarTv.setText(String.valueOf(rating));

        RatingBar ratingBar = findViewById(R.id.activity_see_all_review_ratingbar);
        ratingBar.setRating(rating);

        ImageButton backImgBtn = findViewById(R.id.activity_see_all_review_back_iv);
        Button writeBtn = findViewById(R.id.activity_see_all_review_write_btn);

        seeAllListView = findViewById(R.id.activity_see_all_review_listview);
        seeAllAdapter = new ListViewAdapter(getApplicationContext());
        seeAllListView.setAdapter(seeAllAdapter);

        backImgBtn.setOnClickListener(this);
        writeBtn.setOnClickListener(this);

        db = AppDataBase.getAppDataBase(this);

        //인터넷 연결된 상태, 아닌 상태
        if (NetworkState.status == NetworkState.TYPE_CONNECT) {
            Toast.makeText(this, "인터넷 연결됨", Toast.LENGTH_SHORT).show();
            requestAllCommentList(movieId);
        } else if (NetworkState.status == NetworkState.TYPE_NOT_CONNECTED) {
            Toast.makeText(this, "인터넷 연결안됨 DB에서 불러옴", Toast.LENGTH_SHORT).show();
            try {
                List<CommentListEntity> allCommentListEntities = new SeeAllCommentListDaoAsyncTask(db.commentListEntityDao(), AppDataBase.ROOM_QUERY_GET_ALL, movieId).execute().get();
                setNotConnectedNetworkListViewAllCommentList(allCommentListEntities);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    private void setNotConnectedNetworkListViewAllCommentList(List<CommentListEntity> allCommentListEntities) {
        for (int i = 0; i < allCommentListEntities.size(); i++) {
            seeAllAdapter.addItem(new Users(allCommentListEntities.get(i).getWriter(),
                    allCommentListEntities.get(i).getTime(),
                    allCommentListEntities.get(i).getContents(), R.drawable.user1,
                    allCommentListEntities.get(i).getRating()));
        }
        seeAllListView.setAdapter(seeAllAdapter);
    }

    private void setListViewAllCommentList() {

        //기존의 DB값 제거
        new SeeAllCommentListDaoAsyncTask(db.commentListEntityDao(), AppDataBase.ROOM_QUERY_DELETE, movieId).execute();

        for (int i = 0; i < commentList.getResult().size(); i++) {
            seeAllAdapter.addItem(new Users(commentList.getResult().get(i).getWriter(),
                    commentList.getResult().get(i).getTime(), commentList.getResult().get(i).getContents(),
                    R.drawable.user1, commentList.getResult().get(i).getRating()));

            new SeeAllCommentListDaoAsyncTask(db.commentListEntityDao(), AppDataBase.ROOM_QUERY_INSERT, movieId).execute(new CommentListEntity(
                    movieId, commentList.getResult().get(i).getWriter(), commentList.getResult().get(i).getWriter_image(), commentList.getResult().get(i).getTime(),
                    commentList.getResult().get(i).getRating(), commentList.getResult().get(i).getContents()
            ));

        }
        seeAllListView.setAdapter(seeAllAdapter);
    }

    private void requestAllCommentList(int movieId) {
        String url = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/readCommentList";
        url += "?" + "id=" + movieId + "&" + "limit=" + "all";

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        processResponseConvertGson(response);
                        //기존에 설정된 값 제거 후 한줄평 목록 설정
                        seeAllAdapter.getReviewItems().clear();
                        setListViewAllCommentList();
                        reviewPeopleTv.setText("(" + commentList.getResult().size() + "명 참여)");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        requestAllCommentList(movieId);
                    }
                }
        );

        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);

    }


    public void processResponseConvertGson(String response) {
        Gson gson = new Gson();
        ResponseMovieInfo responseMovieInfo = gson.fromJson(response, ResponseMovieInfo.class);

        if (responseMovieInfo.getCode() == 200) {
            commentList = gson.fromJson(response, CommentList.class);
        }

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent;
        if (id == R.id.activity_see_all_review_back_iv) {
            intent = new Intent(getApplicationContext(), MainActivity.class);
            backPressEvent(intent);

        } else if (id == R.id.activity_see_all_review_write_btn) {
            intent = new Intent(getApplicationContext(), WriteReviewActivity.class);
            intent.putExtra("requestCode", REQUEST_CODE_OF_SEE_ALL_REVIEW_ACTIVITY);
            intent.putExtra(MovieDetailInfoFragment.FRAGMENT_INFO_DETAIL_MOVIE_ID, movieId);
            customToast.makeText(getResources().getString(R.string.see_all_review_toast_write), Toast.LENGTH_SHORT);
            startActivityForResult(intent, REQUEST_CODE_OF_SEE_ALL_REVIEW_ACTIVITY);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_OF_SEE_ALL_REVIEW_ACTIVITY) {
            if (resultCode == WriteReviewActivity.RESULT_CODE_OF_WRITE_REVIEW_ACTIVITY) {
                //  리뷰 작성시 바로 반영
                requestAllCommentList(movieId);
            }
        }

    }

    @Override
    public void onBackPressed() {
        backPressEvent(new Intent(getApplicationContext(), MainActivity.class));
    }

    private void backPressEvent(Intent intent) {

        setResult(RESULT_CODE_OF_SEE_ALL_REVIEW_ACTIVITY, intent);
        customToast.makeText(getResources().getString(R.string.see_all_review_toast_back), Toast.LENGTH_SHORT);
        finish();
    }

    //custom actionbar 생성
    private void setCustomActionBar() {
        ActionBar customActionBar = getSupportActionBar();

        customActionBar.setDisplayHomeAsUpEnabled(false);
        customActionBar.setDisplayShowTitleEnabled(false);
        customActionBar.setDisplayShowHomeEnabled(false);
        customActionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View actionbarView = inflater.inflate(R.layout.layout_custom_action_bar, null);

        customActionBar.setCustomView(actionbarView);
    }

    //    백그라운드 DB조작을 위한 AsyncTask
    private static class SeeAllCommentListDaoAsyncTask extends AsyncTask<CommentListEntity, Void, List<CommentListEntity>> {

        private CommentListEntityDao seeAllListEntityDao;
        private int command;
        private int movieId;

        public SeeAllCommentListDaoAsyncTask(CommentListEntityDao commentListEntityDao, int command, int movieId) {
            this.seeAllListEntityDao = commentListEntityDao;
            this.command = command;
            this.movieId = movieId;
        }

        @Override
        protected List<CommentListEntity> doInBackground(CommentListEntity... commentListEntities) {

            if (command == AppDataBase.ROOM_QUERY_GET_ALL) {
                return getCommentList();
            } else if (command == AppDataBase.ROOM_QUERY_INSERT) {
                seeAllListEntityDao.insert(commentListEntities[0]);
            } else if (command == AppDataBase.ROOM_QUERY_DELETE) {
                deleteCommentList();
            }
            return null;
        }

        private List<CommentListEntity> getCommentList() {
            if (movieId == 1) {
                return seeAllListEntityDao.getFirstSeeAllMovieCommentCount();
            } else if (movieId == 2) {
                return seeAllListEntityDao.getSecondSeeAllMovieCommentCount();
            } else if (movieId == 3) {
                return seeAllListEntityDao.getThirdSeeAllMovieCommentCount();
            } else if (movieId == 4) {
                return seeAllListEntityDao.getForthSeeAllMovieCommentCount();
            } else if (movieId == 5) {
                return seeAllListEntityDao.getFifthSeeAllMovieCommentCount();
            }
            return null;
        }

        private void deleteCommentList() {
            if (movieId == 1) {
                seeAllListEntityDao.deleteFirstCommentList();
            } else if (movieId == 2) {
                seeAllListEntityDao.deleteSecondCommentList();
            } else if (movieId == 3) {
                seeAllListEntityDao.deleteThirdCommentList();
            } else if (movieId == 4) {
                seeAllListEntityDao.deleteFourthCommentList();
            } else if (movieId == 5) {
                seeAllListEntityDao.deleteFifthCommentList();
            }
        }

    }
}