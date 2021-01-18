package com.example.boostcoursemoblieproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.boostcoursemoblieproject.R;
import com.example.boostcoursemoblieproject.activity.SeeAllReviewActivity;
import com.example.boostcoursemoblieproject.activity.WriteReviewActivity;
import com.example.boostcoursemoblieproject.adapter.ListViewAdapter;
import com.example.boostcoursemoblieproject.common.CustomToast;
import com.example.boostcoursemoblieproject.item.CommentList;
import com.example.boostcoursemoblieproject.item.Movie;
import com.example.boostcoursemoblieproject.item.ResponseMovieInfo;
import com.example.boostcoursemoblieproject.item.Users;
import com.example.boostcoursemoblieproject.network.AppHelper;
import com.google.gson.Gson;


public class MovieDetailInfoFragment extends Fragment implements View.OnClickListener {

    public static final String FRAGMENT_INFO_DETAIL_MOVIE_NAME = "detailMovieName";
    public static final String FRAGMENT_INFO_DETAIL_MOVIE_ID = "detailMovieId";
    public static final String FRAGMENT_INFO_DETAIL_MOVIE_RATE = "detailMovieRate";

    public static final int MAX_COMMENT_LIST_DETAIL_INFO_FRAGMENT = 3;
    public static final int REQUEST_CODE_OF_MOVIE_DETAIL_INFO_FRAGMENT = 1000;
    private static final int NETWORK_REQUEST_COUNT = 3;

    private int mMovieIdParam;

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

    private ImageView detailPosterIv;
    private TextView detailMovieNameTv;
    private TextView detailMovieDateTv;
    private TextView detailMovieCategoryTv;
    private TextView thumbUpTextView;
    private TextView thumbDownTextView;

    private TextView detailMovieReservationTv;
    private TextView detailMovieUserRateTv;
    private TextView detailMovieAudienceTv;

    private TextView detailMovieSynopsisTv;
    private TextView detailMovieDirectorTv;
    private TextView detailMovieActorTv;

    private RatingBar detailMovieUserRatingbar;

    private int MovieRequestCode = 201;
    private int CommentListRequestCode = 202;

    public MovieDetailInfoFragment() {

    }

    public static MovieDetailInfoFragment newInstance(int movieId) {
        MovieDetailInfoFragment movieDetailInfoFragment = new MovieDetailInfoFragment();
        Bundle args = new Bundle();
        args.putInt(FRAGMENT_INFO_DETAIL_MOVIE_ID, movieId);
        movieDetailInfoFragment.setArguments(args);
        return movieDetailInfoFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mMovieIdParam = getArguments().getInt(FRAGMENT_INFO_DETAIL_MOVIE_ID);
        }

        requestMovie(NETWORK_REQUEST_COUNT);
        requestCommentList(NETWORK_REQUEST_COUNT);
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

        detailMovieUserRatingbar = (RatingBar) rootView.findViewById(R.id.fragment_movie_detail_user_ratingbar);

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

    private void requestMovie(int requestCount) {
        String url = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/readMovie";
        url += "?" + "id=" + mMovieIdParam;


        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        processResponseConvertGson(response, MovieRequestCode);
                        setMovieDetailInfo();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "응답에러" + error, Toast.LENGTH_SHORT).show();
                        if (requestCount > 0) {
                            Toast.makeText(getActivity(), "응답에러영화카운트" + requestCount, Toast.LENGTH_SHORT).show();
                            requestMovie((requestCount - 1));
                        }

                    }
                }
        );

        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);
    }

    private void requestCommentList(int requestCount) {
        String url = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/readCommentList";
        url += "?" + "id=" + mMovieIdParam + "&" + "limit=" + MAX_COMMENT_LIST_DETAIL_INFO_FRAGMENT;

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        processResponseConvertGson(response, CommentListRequestCode);
                        //기존에 설정된 값 제거 후 한줄평 목록 설정
                        adapter.getReviewItems().clear();
                        setListViewCommentList();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "응답에러" + error, Toast.LENGTH_SHORT).show();
                        if (requestCount > 0) {
                            Toast.makeText(getActivity(), "응답에러코멘트카운트" + requestCount, Toast.LENGTH_SHORT).show();
                            requestCommentList((requestCount - 1));
                        }

                    }
                }
        );

        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);
    }

    //MAX_COMMENT_LIST_DETAIL_INFO_FRAGMENT에 설정한 한줄평 최대값만큼만을 보여줌
    private void setListViewCommentList() {
        for (int i = 0; i < commentList.getResult().size(); i++) {
            adapter.addItem(new Users(commentList.getResult().get(i).getWriter(), commentList.getResult().get(i).getTime(), commentList.getResult().get(i).getContents(), R.drawable.user1, commentList.getResult().get(i).getRating()));
        }
        listView.setAdapter(adapter);

    }

    public void processResponseConvertGson(String response, int requestCode) {
        Gson gson = new Gson();
        ResponseMovieInfo responseMovieInfo = gson.fromJson(response, ResponseMovieInfo.class);

        if (responseMovieInfo.getCode() == 200) {
            if (requestCode == MovieRequestCode) {
                movie = gson.fromJson(response, Movie.class);
            } else if (requestCode == CommentListRequestCode) {
                commentList = gson.fromJson(response, CommentList.class);
            }
        }

    }

    private void setMovieDetailInfo() {
        Glide.with(getActivity()).load(movie.getResult().get(0).getImage()).into(detailPosterIv);
        detailMovieNameTv.setText(movie.getResult().get(0).getTitle());
        detailMovieDateTv.setText(movie.getResult().get(0).getDate() + " 개봉");
        detailMovieCategoryTv.setText(movie.getResult().get(0).getGenre() + " / " + movie.getResult().get(0).getDuration() + "분");
        thumbUpTextView.setText(String.valueOf(movie.getResult().get(0).getLike()));
        thumbDownTextView.setText(String.valueOf(movie.getResult().get(0).getDislike()));

        detailMovieReservationTv.setText(movie.getResult().get(0).getReservation_grade() + "위" + " " + movie.getResult().get(0).getReservation_rate() + "%");
        detailMovieUserRateTv.setText(String.valueOf(movie.getResult().get(0).getUser_rating()));
        detailMovieUserRatingbar.setRating(movie.getResult().get(0).getUser_rating());
        detailMovieAudienceTv.setText(movie.getResult().get(0).getAudience() + "명");

        detailMovieSynopsisTv.setText(movie.getResult().get(0).getSynopsis());
        detailMovieDirectorTv.setText(movie.getResult().get(0).getDirector());
        detailMovieActorTv.setText(movie.getResult().get(0).getActor());
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
                //영화 이름, 영화 아이디를 넘겨줌
                intent.putExtra(FRAGMENT_INFO_DETAIL_MOVIE_NAME, movie.getResult().get(0).getTitle());
                intent.putExtra(FRAGMENT_INFO_DETAIL_MOVIE_ID, mMovieIdParam);
                startActivityForResult(intent, REQUEST_CODE_OF_MOVIE_DETAIL_INFO_FRAGMENT);
                break;

            case R.id.fragment_movie_detail_review_all_btn:
                customToast.makeText(getResources().getString(R.string.movie_detail_info_toast_review_all), Toast.LENGTH_SHORT);
                intent = new Intent(getActivity(), SeeAllReviewActivity.class);
                //영화 이름, 아이디, 별점을 넘겨줌
                intent.putExtra(FRAGMENT_INFO_DETAIL_MOVIE_NAME, movie.getResult().get(0).getTitle());
                intent.putExtra(FRAGMENT_INFO_DETAIL_MOVIE_ID, mMovieIdParam);
                intent.putExtra(FRAGMENT_INFO_DETAIL_MOVIE_RATE, movie.getResult().get(0).getUser_rating());

                startActivityForResult(intent, REQUEST_CODE_OF_MOVIE_DETAIL_INFO_FRAGMENT);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //한줄평 작성, 모두보기 화면에서 돌아올 경우 한줄평 리스트 업데이트

        if (requestCode == REQUEST_CODE_OF_MOVIE_DETAIL_INFO_FRAGMENT) {
            if (resultCode == WriteReviewActivity.RESULT_CODE_OF_WRITE_REVIEW_ACTIVITY) {
                requestCommentList(NETWORK_REQUEST_COUNT);
            } else if (resultCode == SeeAllReviewActivity.RESULT_CODE_OF_SEE_ALL_REVIEW_ACTIVITY) {
                requestCommentList(NETWORK_REQUEST_COUNT);
            }
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