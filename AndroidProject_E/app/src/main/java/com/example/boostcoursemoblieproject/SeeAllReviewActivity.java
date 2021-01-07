package com.example.boostcoursemoblieproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

public class SeeAllReviewActivity extends AppCompatActivity implements View.OnClickListener {


    public static final int REQUEST_CODE_OF_SEE_ALL_REVIEW_ACTIVITY = 3000;
    public static final int RESULT_CODE_OF_SEE_ALL_REVIEW_ACTIVITY = 3001;
    private ListView seeAllListView;
    private ListViewAdapter seeAllAdapter;
    private CustomToast customToast;

    private int movieId;
    private CommentList commentList;

    private TextView reviewPeopleTv;

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

        requestAllCommentList(movieId);

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
                        setListViewAllCommentList();
                        reviewPeopleTv.setText("(" + commentList.result.size() + "명 참여)");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);

    }

    private void setListViewAllCommentList() {
        for (int i = 0; i < commentList.result.size(); i++) {
            seeAllAdapter.addItem(new Users(commentList.result.get(i).writer, commentList.result.get(i).time, commentList.result.get(i).contents, R.drawable.user1, commentList.result.get(i).rating));
        }
        seeAllListView.setAdapter(seeAllAdapter);
    }

    public void processResponseConvertGson(String response) {
        Gson gson = new Gson();
        ResponseMovieInfo responseMovieInfo = gson.fromJson(response, ResponseMovieInfo.class);

        if (responseMovieInfo.code == 200) {
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
                seeAllAdapter.reviewItems.clear();
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

}