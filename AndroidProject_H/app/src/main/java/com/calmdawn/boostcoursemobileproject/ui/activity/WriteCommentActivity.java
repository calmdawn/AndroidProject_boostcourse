package com.calmdawn.boostcoursemobileproject.ui.activity;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.calmdawn.boostcoursemobileproject.R;
import com.calmdawn.boostcoursemobileproject.databinding.ActivityWriteCommentBinding;
import com.calmdawn.boostcoursemobileproject.network.NetworkState;
import com.calmdawn.boostcoursemobileproject.viewmodel.WriteCommentViewModel;

import java.util.HashMap;
import java.util.Map;

public class WriteCommentActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int WRITE_COMMENT_RESULT_OK = 3001;

    ActivityWriteCommentBinding writeCommentBinding;
    WriteCommentViewModel writeCommentViewModel;

    TextView actionBarTitleTv;
    ImageView actionBarIv;

    String movieName;
    int movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        writeCommentBinding = DataBindingUtil.setContentView(this, R.layout.activity_write_comment);
        writeCommentViewModel = new ViewModelProvider(this).get(WriteCommentViewModel.class);

        initUi();
        setupUi();

        if (getIntent() != null) {
            movieName = getIntent().getStringExtra(getString(R.string.movie_name));
            movieId = getIntent().getIntExtra(getString(R.string.movie_id), 0);
            writeCommentBinding.activityWriteCommentTitleTv.setText(movieName);
        }
    }


    @Override
    public void onClick(View v) {
        if (v == actionBarIv) {
            setResult(WRITE_COMMENT_RESULT_OK);
            finish();
        } else if (v == writeCommentBinding.activityWriteCommentSaveCpbtn) {
            sendMovieComment();
        } else if (v == writeCommentBinding.activityWriteCommentCancelCpbtn) {
            setResult(WRITE_COMMENT_RESULT_OK);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        setResult(WRITE_COMMENT_RESULT_OK);
        super.onBackPressed();
    }

    private void sendMovieComment() {

        if (!NetworkState.getConnectivityStatus(this)) {
            Toast.makeText(this, "인터넷 연결상태를 확인해주세요", Toast.LENGTH_SHORT).show();
        } else if (writeCommentBinding.activityWriteCommentEdittv.getText().toString().length() <= 0) {
            Toast.makeText(this, "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
        } else {
            Map<String, String> params = new HashMap<>();
            params.put("id", String.valueOf(movieId));
            params.put("writer", "CalmDawn");
            params.put("time", "");
            params.put("rating", String.valueOf(writeCommentBinding.activityWriteCommentRatingbar.getRating()));
            params.put("contents", writeCommentBinding.activityWriteCommentEdittv.getText().toString());
            writeCommentViewModel.sendMovieComment(this, movieId, params);
            finish();
        }
    }

    private void setupUi() {
        actionBarTitleTv.setText("한줄평 작성");

        //자동으로 줄바뀜 + ImeDone 사용
        writeCommentBinding.activityWriteCommentEdittv.setImeOptions(EditorInfo.IME_ACTION_DONE);
        writeCommentBinding.activityWriteCommentEdittv.setRawInputType(InputType.TYPE_CLASS_TEXT);

        actionBarIv.setOnClickListener(this);
        writeCommentBinding.activityWriteCommentSaveCpbtn.setOnClickListener(this);
        writeCommentBinding.activityWriteCommentCancelCpbtn.setOnClickListener(this);

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