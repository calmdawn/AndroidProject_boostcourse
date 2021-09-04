package com.calmdawn.boostcoursemobileproject.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.calmdawn.boostcoursemobileproject.R;
import com.calmdawn.boostcoursemobileproject.adapter.CommentRecyclerAdapter;
import com.calmdawn.boostcoursemobileproject.databinding.ActivitySeeAllCommentBinding;
import com.calmdawn.boostcoursemobileproject.model.MovieCommentListItem;
import com.calmdawn.boostcoursemobileproject.network.NetworkState;
import com.calmdawn.boostcoursemobileproject.viewmodel.SeeAllCommentViewModel;

public class SeeAllCommentActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int SEE_ALL_COMMENT_RESULT_OK = 2001;

    ActivitySeeAllCommentBinding seeBinding;
    SeeAllCommentViewModel commentViewModel;

    String movieName;
    int movieId;

    TextView actionBarTitleTv;
    ImageView actionBarIv;

    CommentRecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        seeBinding = DataBindingUtil.setContentView(this, R.layout.activity_see_all_comment);
        commentViewModel = new ViewModelProvider(this).get(SeeAllCommentViewModel.class);

        initUi();
        setupUi();

        if (getIntent() != null) {
            movieName = getIntent().getStringExtra(getString(R.string.movie_name));
            movieId = getIntent().getIntExtra(getString(R.string.movie_id), 0);
            seeBinding.activitySeeAllCommentMovieNameTv.setText(movieName);
        }

        if (NetworkState.getConnectivityStatus(this)) {
            Toast.makeText(this, "인터넷 연결됨", Toast.LENGTH_SHORT).show();
            commentViewModel.requestMovieAllCommentList(this, movieId);
        } else {
            Toast.makeText(this, "인터넷 연결끊김", Toast.LENGTH_SHORT).show();
            commentViewModel.getMovieCommentListRoomDB(this, movieId);
        }


        commentViewModel.getCommentListLiveData().observe(this, new Observer<MovieCommentListItem>() {
            @Override
            public void onChanged(MovieCommentListItem movieCommentListItem) {
                seeBinding.activitySeeAllCommentUserRatingbar.setRating((float) commentViewModel.getUserRatingAvg(movieCommentListItem));
                seeBinding.activitySeeAllCommentUserRatingTv.setText(String.valueOf(commentViewModel.getUserRatingAvg(movieCommentListItem)));
                seeBinding.activitySeeAllCommentTotalCountTv.setText("(" + commentViewModel.getTotalCommentCount(movieCommentListItem) + "명 참여)");
                recyclerAdapter.setItem(movieCommentListItem);
                recyclerAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v == actionBarIv) {
            setResult(SEE_ALL_COMMENT_RESULT_OK);
            finish();
        } else {
            Intent intent = new Intent(SeeAllCommentActivity.this, WriteCommentActivity.class);
            intent.putExtra(getString(R.string.movie_name), movieName);
            intent.putExtra(getString(R.string.movie_id), movieId);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        setResult(SEE_ALL_COMMENT_RESULT_OK);
        super.onBackPressed();
    }

    private void initUi() {
        setCustomActionBar();
        actionBarTitleTv = findViewById(R.id.common_layout_custom_action_bar_title_tv);
        actionBarIv = findViewById(R.id.common_layout_custom_action_bar_back_iv);
    }

    private void setupUi() {

        actionBarTitleTv.setText("한줄평 목록");

        actionBarIv.setOnClickListener(this);
        seeBinding.activitySeeAllCommentWriteIv.setOnClickListener(this);
        seeBinding.activitySeeAllCommentWriteTv.setOnClickListener(this);

        recyclerAdapter = new CommentRecyclerAdapter(this);
        seeBinding.activitySeeAllCommentRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        seeBinding.activitySeeAllCommentRecycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        seeBinding.activitySeeAllCommentRecycler.setAdapter(recyclerAdapter);

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