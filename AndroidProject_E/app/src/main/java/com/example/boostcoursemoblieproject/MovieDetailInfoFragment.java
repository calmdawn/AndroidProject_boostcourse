package com.example.boostcoursemoblieproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;


public class MovieDetailInfoFragment extends Fragment implements View.OnClickListener {

    public static final int REQUEST_CODE_OF_MOVIE_DETAIL_INFO_FRAGMENT = 1000;

    private ImageButton thumbUpImgBtn;
    private ImageButton thumbDownImgBtn;
    private int thumbState = 0;

    private ControlThumbUp controlThumbUp;
    private ControlThumbDown controlThumbDown;

    private ScrollView scrollView;

    private ListView listView;
    private ListViewAdapter adapter;

    private CustomToast customToast;

    private Movie movie;
    private CommentList commentList;

    int MovieRequestCode = 100;
    int CommentListRequestCode = 200;

    ImageView detailPosterIv;
    TextView detailMovieNameTv;
    TextView detailMovieDateTv;
    TextView detailMovieCategoryTv;
    TextView thumbUpTextView;
    TextView thumbDownTextView;

    TextView detailMovieReservationTv;
    TextView detailMovieUserRateTv;
    TextView detailMovieAudienceTv;

    TextView detailMovieSynopsisTv;
    TextView detailMovieDirectorTv;
    TextView detailMovieActorTv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestMovie();
        requestCommentList();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_movie_detail_info, container, false);
        thumbUpImgBtn = rootView.findViewById(R.id.fragment_movie_detail_thumb_up_imgbtn);


        // 영화 상세화면

        customToast = new CustomToast(getActivity());

        detailPosterIv = (ImageView) rootView.findViewById(R.id.fragment_movie_detail_poster_iv);
        detailMovieNameTv = (TextView) rootView.findViewById(R.id.fragment_movie_detail_movie_name_tv);
        detailMovieDateTv = (TextView) rootView.findViewById(R.id.fragment_movie_detail_date_tv);
        detailMovieCategoryTv = (TextView) rootView.findViewById(R.id.fragment_movie_detail_category_tv);
        thumbUpTextView = (TextView) rootView.findViewById(R.id.fragment_movie_detail_thumb_up_tv);
        thumbDownTextView = (TextView) rootView.findViewById(R.id.fragment_movie_detail_thumb_down_tv);

        detailMovieReservationTv = (TextView) rootView.findViewById(R.id.fragment_movie_detail_reservation_tv);
        detailMovieUserRateTv = (TextView) rootView.findViewById(R.id.fragment_movie_detail_user_rate_tv);
        detailMovieAudienceTv = (TextView) rootView.findViewById(R.id.fragment_movie_detail_audience_tv);

        detailMovieSynopsisTv = (TextView) rootView.findViewById(R.id.fragment_movie_detail_synopsis_tv);
        detailMovieDirectorTv = (TextView) rootView.findViewById(R.id.fragment_movie_detail_director_name_tv);
        detailMovieActorTv = (TextView) rootView.findViewById(R.id.fragment_movie_detail_actor_name_tv);

        // 좋아요, 싫어요 부분
        controlThumbUp = new ControlThumbUp();
        controlThumbDown = new ControlThumbDown();

        thumbUpImgBtn = (ImageButton) rootView.findViewById(R.id.fragment_movie_detail_thumb_up_imgbtn);
        thumbDownImgBtn = (ImageButton) rootView.findViewById(R.id.fragment_movie_detail_thumb_down_imgbtn);

        thumbUpImgBtn.setOnClickListener(thumbOnClickListener);
        thumbDownImgBtn.setOnClickListener(thumbOnClickListener);


        scrollView = (ScrollView) rootView.findViewById(R.id.fragment_movie_detail_scrollview);

        // 한줄평 부분
        Button writeBtn = (Button) rootView.findViewById(R.id.fragment_movie_detail_write_btn);
        Button reviewAllBtn = (Button) rootView.findViewById(R.id.fragment_movie_detail_review_all_btn);


        writeBtn.setOnClickListener(this);
        reviewAllBtn.setOnClickListener(this);


        //한줄평 리스트들
        listView = (ListView) rootView.findViewById(R.id.fragment_movie_detail_listview);
        adapter = new ListViewAdapter(getActivity());

//        adapter.addItem(new Users("kym71", "적당히 재밌다. 오랜만에 잠 안오는 영화 봤네요.", R.drawable.user1, (float) 3.0));
//        adapter.addItem(new Users("shen", "훌륭한 영화의 표본입니다", R.drawable.user1, (float) 5.0));
//        adapter.addItem(new Users("robert", "배우들의 연기가 일품", R.drawable.user1, (float) 5.0));
//        adapter.addItem(new Users("james99", "무엇을 보여주려 하는지 알 수 없는 영화", R.drawable.user1, (float) 1.0));
//        listView.setAdapter(adapter);

        //스크롤 뷰안에 리스트뷰가 중첩되어 스크롤 되지 않음을 해결
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scrollView.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        return rootView;
    }

    private void requestMovie() {
        String url = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/readMovie";
        url += "?" + "id=1";


        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        processResponseConvertGson(response, MovieRequestCode);
                        Toast.makeText(getActivity(), movie.result.get(0).title, Toast.LENGTH_SHORT).show();
                        setMovieDetailInfo();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "응답에러" + error, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);
    }

    private void requestCommentList() {
        String url = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/readCommentList";
        url += "?" + "id=1";


        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        processResponseConvertGson(response, CommentListRequestCode);
                        Toast.makeText(getActivity(), commentList.result.get(0).contents, Toast.LENGTH_SHORT).show();
                        setListViewCommentList();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "응답에러" + error, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);
    }

    private void setListViewCommentList() {
        adapter.addItem(new Users(commentList.result.get(0).writer, commentList.result.get(0).contents, R.drawable.user1, commentList.result.get(0).rating));
        adapter.addItem(new Users(commentList.result.get(1).writer, commentList.result.get(1).contents, R.drawable.user1, commentList.result.get(1).rating));
        adapter.addItem(new Users(commentList.result.get(2).writer, commentList.result.get(2).contents, R.drawable.user1, commentList.result.get(2).rating));
        listView.setAdapter(adapter);

    }

    public void processResponseConvertGson(String response, int requestCode) {
        Gson gson = new Gson();
        ResponseMovieInfo responseMovieInfo = gson.fromJson(response, ResponseMovieInfo.class);

        if (responseMovieInfo.code == 200) {
            if (requestCode == MovieRequestCode) {
                movie = gson.fromJson(response, Movie.class);
            } else if (requestCode == CommentListRequestCode) {
                commentList = gson.fromJson(response, CommentList.class);
            }
        }

    }

    private void setMovieDetailInfo() {
        Glide.with(getActivity()).load(movie.result.get(0).image).into(detailPosterIv);
        detailMovieNameTv.setText(movie.result.get(0).title);
        detailMovieDateTv.setText(movie.result.get(0).date + " 개봉");
        detailMovieCategoryTv.setText(movie.result.get(0).genre + " / " + movie.result.get(0).duration + "분");
        thumbUpTextView.setText(String.valueOf(movie.result.get(0).like));
        thumbDownTextView.setText(String.valueOf(movie.result.get(0).dislike));

        detailMovieReservationTv.setText(movie.result.get(0).reservation_grade + "위" + " " + movie.result.get(0).reservation_rate + "%");
        detailMovieUserRateTv.setText(String.valueOf(movie.result.get(0).user_rating));
        detailMovieAudienceTv.setText(movie.result.get(0).audience + "명");

        detailMovieSynopsisTv.setText(movie.result.get(0).synopsis);
        detailMovieDirectorTv.setText(movie.result.get(0).director);
        detailMovieActorTv.setText(movie.result.get(0).actor);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_OF_MOVIE_DETAIL_INFO_FRAGMENT) {
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
            case R.id.fragment_movie_detail_write_btn:
                customToast.makeText(getResources().getString(R.string.movie_detail_info_toast_write), Toast.LENGTH_SHORT);
                intent = new Intent(getActivity(), WriteReviewActivity.class);
                intent.putExtra("requestCode", REQUEST_CODE_OF_MOVIE_DETAIL_INFO_FRAGMENT);
                startActivityForResult(intent, REQUEST_CODE_OF_MOVIE_DETAIL_INFO_FRAGMENT);
                break;

            case R.id.fragment_movie_detail_review_all_btn:
                customToast.makeText(getResources().getString(R.string.movie_detail_info_toast_review_all), Toast.LENGTH_SHORT);
                intent = new Intent(getActivity(), SeeAllReviewActivity.class);

                //한줄평 데이터들을 어댑터에서 가져온후 모두보기로 넘겨줌
                ArrayList<Users> reviewItemsData = adapter.getReviewItems();
                intent.putExtra("reviewItemsData", reviewItemsData);
                intent.putExtra("requestCode", REQUEST_CODE_OF_MOVIE_DETAIL_INFO_FRAGMENT);
                startActivityForResult(intent, REQUEST_CODE_OF_MOVIE_DETAIL_INFO_FRAGMENT);
                break;
        }
    }

    protected View.OnClickListener thumbOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int id = v.getId();

            //  thumbState ==  -1:싫어요 0:선택X 1:좋아요
            switch (id) {
                case R.id.fragment_movie_detail_thumb_up_imgbtn:
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
                case R.id.fragment_movie_detail_thumb_down_imgbtn:
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