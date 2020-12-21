package com.example.boostcoursemoblieproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageButton thumbUpImgBtn;
    private ImageButton thumbDownImgBtn;
    private int thumbState = 0;

    private ControlThumbUp controlThumbUp;
    private ControlThumbDown controlThumbDown;

    private ScrollView scrollView;

    private ListView listView;
    private ListViewAdapter adapter;

    private CustomToast customToast;

    public static final int REQUEST_CODE_OF_MAIN_ACTIVITY = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customToast = new CustomToast(getApplicationContext());

        controlThumbUp = new ControlThumbUp();
        controlThumbDown = new ControlThumbDown();

        scrollView = findViewById(R.id.activity_main_scrollview);

        Button writeBtn = findViewById(R.id.activity_main_write_btn);
        Button reviewAllBtn = findViewById(R.id.activity_main_review_all_btn);

        thumbUpImgBtn = findViewById(R.id.imagebtn_main_thumb_up);
        thumbDownImgBtn = findViewById(R.id.imagebtn_main_thumb_down);


        thumbUpImgBtn.setOnClickListener(thumbOnClickListener);
        thumbDownImgBtn.setOnClickListener(thumbOnClickListener);

        writeBtn.setOnClickListener(this);
        reviewAllBtn.setOnClickListener(this);


        //한줄평 리스트들
        listView = findViewById(R.id.activity_main_listview);
        adapter = new ListViewAdapter(getApplicationContext());

        adapter.addItem(new Users("kym71", "적당히 재밌다. 오랜만에 잠 안오는 영화 봤네요.", R.drawable.user1, (float) 3.0));
        adapter.addItem(new Users("shen", "훌륭한 영화의 표본입니다", R.drawable.user1, (float) 5.0));
        adapter.addItem(new Users("robert", "배우들의 연기가 일품", R.drawable.user1, (float) 5.0));
        adapter.addItem(new Users("james99", "무엇을 보여주려 하는지 알 수 없는 영화", R.drawable.user1, (float) 1.0));

        listView.setAdapter(adapter);

        //스크롤 뷰안에 리스트뷰가 중첩되어 스크롤 되지 않음을 해결
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scrollView.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_OF_MAIN_ACTIVITY) {
            if (resultCode == WriteReviewActivity.RESULT_CODE_OF_WRITE_REVIEW_ACTIVITY) {                // received WriteReviewActivity data
                float starScore = data.getFloatExtra("starScore", 0);
                String comment = String.valueOf(data.getStringExtra("comment"));
                adapter.addItem(new Users("sonic", comment, R.drawable.user1, starScore));
                listView.setAdapter(adapter);
            } else if (resultCode == SeeAllReviewActivity.RESULT_CODE_OF_SEE_ALL_REVIEW_ACTIVITY) {         // received SeeAllReviewActivity data
                updateListViewData(data);
            }
        }

    }

    private void updateListViewData(Intent data) {

        //기존의 한줄평 목록보다 클 경우에만 리스트뷰에 추가함
        ArrayList<Users> usersData = (ArrayList<Users>) data.getSerializableExtra("reviewItemsData");
        for (int i = adapter.getCount(); i < usersData.size(); i++) {
            adapter.addItem(usersData.get(i));
        }
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent;
        switch (id) {
            case R.id.activity_main_write_btn:
                customToast.makeText(getResources().getString(R.string.main_toast_write), Toast.LENGTH_SHORT);
                intent = new Intent(getApplicationContext(), WriteReviewActivity.class);
                intent.putExtra("requestCode", REQUEST_CODE_OF_MAIN_ACTIVITY);
                startActivityForResult(intent, REQUEST_CODE_OF_MAIN_ACTIVITY);
                break;

            case R.id.activity_main_review_all_btn:
                customToast.makeText(getResources().getString(R.string.main_toast_review_all), Toast.LENGTH_SHORT);
                intent = new Intent(getApplicationContext(), SeeAllReviewActivity.class);

                //한줄평 데이터들을 어댑터에서 가져온후 모두보기로 넘겨줌
                ArrayList<Users> reviewItemsData = adapter.getReviewItems();
                intent.putExtra("reviewItemsData", reviewItemsData);
                intent.putExtra("requestCode", REQUEST_CODE_OF_MAIN_ACTIVITY);
                startActivityForResult(intent, REQUEST_CODE_OF_MAIN_ACTIVITY);
                break;
        }
    }

    protected View.OnClickListener thumbOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int id = v.getId();

            //  thumbState ==  -1:싫어요 0:선택X 1:좋아요
            switch (id) {
                case R.id.imagebtn_main_thumb_up:
                    if (thumbState == 0) {
                        thumbState = 1;
                        controlThumbUp.clicked();

                    } else if (thumbState == 1) {
                        thumbState = 0;
                        controlThumbUp.unClicked();

                    } else if (thumbState == -1) {
                        thumbState = 1;
                        controlThumbUp.clicked();
                        controlThumbDown.unClicked();
                    }
                    break;
                case R.id.imagebtn_main_thumb_down:
                    if (thumbState == 0) {
                        thumbState = -1;
                        controlThumbDown.clicked();

                    } else if (thumbState == -1) {
                        thumbState = 0;
                        controlThumbDown.unClicked();

                    } else if (thumbState == 1) {
                        thumbState = -1;
                        controlThumbDown.clicked();
                        controlThumbUp.unClicked();
                    }
                    break;
            }
        }
    };

    //좋아요, 싫어요 인터페이스
    public interface IThumbControllable {
        public void clicked();

        public void unClicked();
    }


    //좋아요 버튼 컨트롤
    private class ControlThumbUp implements IThumbControllable {
        int numThumbUp;
        TextView thumbUpTextView = findViewById(R.id.textview_main_thumb_up);

        public ControlThumbUp() {

        }

        @Override
        public void clicked() {
            numThumbUp = Integer.parseInt(String.valueOf(thumbUpTextView.getText()));
            thumbUpImgBtn.setImageResource(R.drawable.ic_thumb_up_selected);
            thumbUpTextView.setText(String.valueOf(numThumbUp + 1));
        }

        @Override
        public void unClicked() {
            numThumbUp = Integer.parseInt(String.valueOf(thumbUpTextView.getText()));
            thumbUpImgBtn.setImageResource(R.drawable.ic_thumb_up);
            thumbUpTextView.setText(String.valueOf(numThumbUp - 1));
        }
    }


    //싫어요 버튼 컨트롤
    private class ControlThumbDown implements IThumbControllable {

        int numThumbDown;
        TextView thumbDownTextView = findViewById(R.id.textview_main_thumb_down);

        public ControlThumbDown() {

        }

        @Override
        public void clicked() {
            numThumbDown = Integer.parseInt(String.valueOf(thumbDownTextView.getText()));
            thumbDownImgBtn.setImageResource(R.drawable.ic_thumb_down_selected);
            thumbDownTextView.setText(String.valueOf(numThumbDown + 1));
        }

        @Override
        public void unClicked() {
            numThumbDown = Integer.parseInt(String.valueOf(thumbDownTextView.getText()));
            thumbDownImgBtn.setImageResource(R.drawable.ic_thumb_down);
            thumbDownTextView.setText(String.valueOf(numThumbDown - 1));
        }
    }


}











