package com.example.boostcoursemoblieproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView thumbUpTextView;
    TextView thumbDownTextView;
    ImageButton thumbUpImgBtn;
    ImageButton thumbDownImgBtn;
    int thumbState = 0;

    ControlThumbUp controlThumbUp;
    ControlThumbDown controlThumbDown;

    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        controlThumbUp = new ControlThumbUp();
        controlThumbDown = new ControlThumbDown();

        scrollView = findViewById(R.id.activity_main_scrollview);

        Button writeBtn = findViewById(R.id.activity_main_write_btn);
        Button reviewAllBtn = findViewById(R.id.activity_main_review_all_btn);

        thumbUpImgBtn = findViewById(R.id.imagebtn_main_thumb_up);
        thumbDownImgBtn = findViewById(R.id.imagebtn_main_thumb_down);

        thumbUpTextView = findViewById(R.id.textview_main_thumb_up);
        thumbDownTextView = findViewById(R.id.textview_main_thumb_down);

        thumbUpImgBtn.setOnClickListener(this);
        thumbDownImgBtn.setOnClickListener(this);
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "작성하기 버튼이 눌렸습니다", Toast.LENGTH_SHORT).show();
            }
        });
        reviewAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "모두보기 버튼이 눌렸습니다", Toast.LENGTH_SHORT).show();
            }
        });

        //한줄평 리스트들
        ListView listView = findViewById(R.id.activity_main_listview);
        ListViewAdapter adapter = new ListViewAdapter(getApplicationContext());

        adapter.addItem(new Users("kym71", "적당히 재밌다. 오랜만에 잠 안오는 영화 봤네요.", R.drawable.user1, 3));
        adapter.addItem(new Users("shen", "훌륭한 영화의 표본입니다", R.drawable.user1, 5));
        adapter.addItem(new Users("robert", "배우들의 연기가 일품", R.drawable.user1, 5));
        adapter.addItem(new Users("james99", "무엇을 보여주려 하는지 알 수 없는 영화", R.drawable.user1, 1));

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

    //좋아요 버튼 컨트롤
    private class ControlThumbUp {

        int numThumbUp;

        public ControlThumbUp() {

        }

        public void clicked() {
            numThumbUp = Integer.parseInt(String.valueOf(thumbUpTextView.getText()));
            thumbUpImgBtn.setImageResource(R.drawable.ic_thumb_up_selected);
            thumbUpTextView.setText(String.valueOf(numThumbUp + 1));
        }

        public void unClicked() {
            numThumbUp = Integer.parseInt(String.valueOf(thumbUpTextView.getText()));
            thumbUpImgBtn.setImageResource(R.drawable.ic_thumb_up);
            thumbUpTextView.setText(String.valueOf(numThumbUp - 1));
        }
    }

    //싫어요 버튼 컨트롤
    private class ControlThumbDown {

        int numThumbDown;

        public ControlThumbDown() {

        }

        public void clicked() {
            numThumbDown = Integer.parseInt(String.valueOf(thumbDownTextView.getText()));
            thumbDownImgBtn.setImageResource(R.drawable.ic_thumb_down_selected);
            thumbDownTextView.setText(String.valueOf(numThumbDown + 1));
        }

        public void unClicked() {
            numThumbDown = Integer.parseInt(String.valueOf(thumbDownTextView.getText()));
            thumbDownImgBtn.setImageResource(R.drawable.ic_thumb_down);
            thumbDownTextView.setText(String.valueOf(numThumbDown - 1));
        }
    }


}











