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

import java.util.ArrayList;

public class SeeAllReviewActivity extends AppCompatActivity implements View.OnClickListener {

    ListView seeAllListView;
    ListViewAdapter seeAllAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all_review);

        setCustomActionBar();

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
            intent.putExtra("requestCode", 3000);
            startActivityForResult(intent, 3000);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3000) {
            if (resultCode == 2001) {                // received WriteReviewActivity data
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
        setResult(3001, intent);
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