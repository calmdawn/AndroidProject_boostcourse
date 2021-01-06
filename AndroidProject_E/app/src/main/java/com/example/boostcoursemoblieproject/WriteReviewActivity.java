package com.example.boostcoursemoblieproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class WriteReviewActivity extends AppCompatActivity implements View.OnClickListener {


    RatingBar scoreRatingBar;
    EditText commentEditText;
    int requestCode;

    CustomToast customToast;

    public static final int RESULT_CODE_OF_WRITE_REVIEW_ACTIVITY = 2001;
    private int movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("한줄평 작성");
        }

        requestCode = getIntent().getIntExtra("requestCode", 0);
        scoreRatingBar = findViewById(R.id.activity_writereview_ratingbar);
        commentEditText = findViewById(R.id.activity_writereview_edittext);
        Button saveBtn = findViewById(R.id.activity_writereview_save_btn);
        Button cancelBtn = findViewById(R.id.activity_writereview_cancel_btn);

        customToast = new CustomToast(getApplicationContext());

        saveBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);


    }

    private void postRequestCreateComment() {

        String url = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/createComment";

        StringRequest request = new StringRequest(
                Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(movieId));
                params.put("writer", "CalmDawn");
                params.put("time", "");
                params.put("rating", String.valueOf(scoreRatingBar.getRating()));
                params.put("contents", commentEditText.getText().toString());
                return params;
            }
        };

        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();


        if (id == R.id.activity_writereview_save_btn) {

            if (requestCode == MovieDetailInfoFragment.REQUEST_CODE_OF_MOVIE_DETAIL_INFO_FRAGMENT) {  //MainActivity에서 작성하기 버튼을 누른 경우
                customToast.makeText(getResources().getString(R.string.write_review_toast_success), Toast.LENGTH_SHORT);

            } else if (requestCode == SeeAllReviewActivity.REQUEST_CODE_OF_SEE_ALL_REVIEW_ACTIVITY) {  //SeeAllReviewActivity에서 작성하기 버튼을 누른 경우
                customToast.makeText(getResources().getString(R.string.write_review_toast_success), Toast.LENGTH_SHORT);

            }

            Intent intent = getIntent();
            movieId = intent.getIntExtra(MovieDetailInfoFragment.ARG_PARAM_INFO_DETAIL_MOVIE_ID, 0);
            postRequestCreateComment();
            finish();

        } else if (id == R.id.activity_writereview_cancel_btn) {
            customToast.makeText(getResources().getString(R.string.write_review_toast_fail), Toast.LENGTH_SHORT);
            finish();
        }

    }


}