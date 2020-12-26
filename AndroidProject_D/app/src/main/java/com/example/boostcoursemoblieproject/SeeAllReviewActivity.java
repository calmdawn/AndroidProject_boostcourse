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
import android.widget.Toast;

import java.util.ArrayList;

public class SeeAllReviewActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView seeAllListView;
    private ListViewAdapter seeAllAdapter;
    private CustomToast customToast;

    public static final int REQUEST_CODE_OF_SEE_ALL_REVIEW_ACTIVITY = 3000;
    public static final int RESULT_CODE_OF_SEE_ALL_REVIEW_ACTIVITY = 3001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all_review);

        setCustomActionBar();

        customToast = new CustomToast(getApplicationContext());

        ImageButton backImgBtn = findViewById(R.id.activity_see_all_review_back_iv);
        Button writeBtn = findViewById(R.id.activity_see_all_review_write_btn);

        seeAllListView = findViewById(R.id.activity_see_all_review_listview);
        seeAllAdapter = new ListViewAdapter(getApplicationContext());
        seeAllListView.setAdapter(seeAllAdapter);

        setListViewData(getIntent());

        backImgBtn.setOnClickListener(this);
        writeBtn.setOnClickListener(this);
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
            customToast.makeText(getResources().getString(R.string.see_all_review_toast_write), Toast.LENGTH_SHORT);
            startActivityForResult(intent, REQUEST_CODE_OF_SEE_ALL_REVIEW_ACTIVITY);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_OF_SEE_ALL_REVIEW_ACTIVITY) {
            if (resultCode == WriteReviewActivity.RESULT_CODE_OF_WRITE_REVIEW_ACTIVITY) {                // received WriteReviewActivity data
                float starScore = data.getFloatExtra("starScore", 0);
                String comment = String.valueOf(data.getStringExtra("comment"));
                seeAllAdapter.addItem(new Users("sonic", comment, R.drawable.user1, starScore));
                seeAllListView.setAdapter(seeAllAdapter);
            }
        }
    }

    //MainActivity로부터 가져온 listview data를 추가
    private void setListViewData(Intent intentData) {
        ArrayList<Users> usersData = (ArrayList<Users>) intentData.getSerializableExtra("reviewItemsData");
        for (int i = 0; i < usersData.size(); i++) {
            seeAllAdapter.addItem(usersData.get(i));
        }


    }


    @Override
    public void onBackPressed() {
        backPressEvent(new Intent(getApplicationContext(), MainActivity.class));
    }

    private void backPressEvent(Intent intent) {
        //한줄평 데이터들을 어댑터에서 가져온후 모두보기로 넘겨줌
        ArrayList<Users> reviewItemsData = seeAllAdapter.getReviewItems();
        intent.putExtra("reviewItemsData", reviewItemsData);
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