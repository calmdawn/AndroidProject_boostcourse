package com.calmdawn.boostcoursemobileproject.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.databinding.DataBindingUtil;

import com.calmdawn.boostcoursemobileproject.R;
import com.calmdawn.boostcoursemobileproject.databinding.ActivityMoviePhotoBinding;

public class MoviePhotoActivity extends BaseActivity implements View.OnClickListener {

    ActivityMoviePhotoBinding photoBinding;

    TextView actionBarTitleTv;
    ImageView actionBarIv;

    ScaleGestureDetector scaleGestureDetector;
    private float scaleFactor = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photoBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_photo);

        initUi();
        setupUi();

        //        Bitmap photoBitmap = (Bitmap) getIntent().getParcelableExtra("photoBitmap");
        //        photoBinding.activityMoviePhotoIv.setImageBitmap(photoBitmap);
        byte[] photoBitmapByteArr = getIntent().getByteArrayExtra("photoBitmapByteArr");
        Bitmap bitmap = BitmapFactory.decodeByteArray(photoBitmapByteArr, 0, photoBitmapByteArr.length);
        photoBinding.activityMoviePhotoIv.setImageBitmap(bitmap);
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleGestureListener());

    }

    private class ScaleGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor = scaleFactor * detector.getScaleFactor();
            scaleFactor = Math.max(1.0f, Math.min(scaleFactor, 10.0f));

            photoBinding.activityMoviePhotoIv.setScaleX(scaleFactor);
            photoBinding.activityMoviePhotoIv.setScaleY(scaleFactor);
            return true;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        scaleGestureDetector.onTouchEvent(event);

        return true;
    }


    @Override
    public void onClick(View v) {
        if (v == actionBarIv) {
            finish();
        }
    }

    private void setupUi() {
        actionBarTitleTv.setText("사진 보기");
        actionBarIv.setOnClickListener(this);
    }

    private void initUi() {
        setCustomActionBar();
        actionBarTitleTv = findViewById(R.id.common_layout_custom_action_bar_title_tv);
        actionBarIv = findViewById(R.id.common_layout_custom_action_bar_back_iv);
    }

    private void setCustomActionBar() {     //custom actionbar 생성
        ActionBar customActionBar = getSupportActionBar();

        customActionBar.setDisplayHomeAsUpEnabled(false);
        customActionBar.setDisplayShowTitleEnabled(false);
        customActionBar.setDisplayShowHomeEnabled(false);
        customActionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View actionbarView = inflater.inflate(R.layout.common_layout_custom_action_bar, null);

        customActionBar.setCustomView(actionbarView);
    }
}