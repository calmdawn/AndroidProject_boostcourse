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

public class WriteReviewActivity extends AppCompatActivity implements View.OnClickListener {


    RatingBar scoreRatingBar;
    EditText commentEditText;
    int requestCode;

    CustomToast customToast;

    public static final int RESULT_CODE_OF_WRITE_REVIEW_ACTIVITY = 2001;

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

    @Override
    public void onClick(View v) {
        int id = v.getId();


        if (id == R.id.activity_writereview_save_btn) {

            if (requestCode == MovieDetailInfoFragment.REQUEST_CODE_OF_MOVIE_DETAIL_INFO_FRAGMENT) {  //MainActivity에서 작성하기 버튼을 누른 경우
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("starScore", scoreRatingBar.getRating());
                intent.putExtra("comment", commentEditText.getText().toString());
                setResult(RESULT_CODE_OF_WRITE_REVIEW_ACTIVITY, intent);
                customToast.makeText(getResources().getString(R.string.write_review_toast_success), Toast.LENGTH_SHORT);
                finish();
            } else if (requestCode == SeeAllReviewActivity.REQUEST_CODE_OF_SEE_ALL_REVIEW_ACTIVITY) {  //SeeAllReviewActivity에서 작성하기 버튼을 누른 경우
                Intent intent = new Intent(getApplicationContext(), SeeAllReviewActivity.class);
                intent.putExtra("starScore", scoreRatingBar.getRating());
                intent.putExtra("comment", commentEditText.getText().toString());
                setResult(RESULT_CODE_OF_WRITE_REVIEW_ACTIVITY, intent);
                customToast.makeText(getResources().getString(R.string.write_review_toast_success), Toast.LENGTH_SHORT);
                finish();
            }
        } else if (id == R.id.activity_writereview_cancel_btn) {
            customToast.makeText(getResources().getString(R.string.write_review_toast_fail), Toast.LENGTH_SHORT);
            finish();
        }

    }


}