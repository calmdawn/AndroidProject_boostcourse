package com.example.boostcoursemoblieproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

public class WriteReviewActivity extends AppCompatActivity implements View.OnClickListener {

    RatingBar scoreRatingBar;
    EditText commentEditText;
    int requestCode;

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

        saveBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.activity_writereview_save_btn) {
            if (requestCode == 1000) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("starScore", scoreRatingBar.getRating());
                intent.putExtra("comment", commentEditText.getText().toString());
                setResult(2001, intent);
                finish();
            } else if (requestCode == 3000) {
                Intent intent = new Intent(getApplicationContext(), SeeAllReviewActivity.class);
                intent.putExtra("starScore", scoreRatingBar.getRating());
                intent.putExtra("comment", commentEditText.getText().toString());
                setResult(2001, intent);
                finish();
            }
        } else if (id == R.id.activity_writereview_cancel_btn) {
            finish();
        }

    }
}