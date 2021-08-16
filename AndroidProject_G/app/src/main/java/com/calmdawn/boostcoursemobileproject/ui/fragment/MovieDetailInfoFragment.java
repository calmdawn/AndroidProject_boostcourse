package com.calmdawn.boostcoursemobileproject.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.calmdawn.boostcoursemobileproject.R;
import com.calmdawn.boostcoursemobileproject.adapter.CommentRecyclerAdapter;
import com.calmdawn.boostcoursemobileproject.adapter.MovieDetailInfoGalleryRecyclerAdapter;
import com.calmdawn.boostcoursemobileproject.common.CustomToast;
import com.calmdawn.boostcoursemobileproject.databinding.FragmentMovieDetailInfoBinding;
import com.calmdawn.boostcoursemobileproject.model.MovieCommentListItem;
import com.calmdawn.boostcoursemobileproject.model.MovieDetailInfoItem;
import com.calmdawn.boostcoursemobileproject.model.MovieGalleryItem;
import com.calmdawn.boostcoursemobileproject.network.NetworkState;
import com.calmdawn.boostcoursemobileproject.ui.activity.MainActivity;
import com.calmdawn.boostcoursemobileproject.ui.activity.SeeAllCommentActivity;
import com.calmdawn.boostcoursemobileproject.ui.activity.WriteCommentActivity;
import com.calmdawn.boostcoursemobileproject.viewmodel.MovieDetailInfoViewModel;

import java.util.List;

public class MovieDetailInfoFragment extends BaseFragment implements View.OnClickListener {

    private final int MOVIE_DETAIL_INFO_REQUEST = 1000;
    private static final String ARG_PARAM_MOVIE_ID = "param1";
    private static final String ARG_PARAM_MOVIE_TITLE = "param2";

    private int mParamMovieId;
    private String mParamMovieTitle;

    FragmentMovieDetailInfoBinding detailInfoBinding;
    MovieDetailInfoViewModel detailInfoViewModel;
    Context context;
    CustomToast customToast;

    public MovieDetailInfoFragment() {

    }

    public static MovieDetailInfoFragment newInstance(int param1, String param2) {
        MovieDetailInfoFragment fragment = new MovieDetailInfoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM_MOVIE_ID, param1);
        args.putString(ARG_PARAM_MOVIE_TITLE, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParamMovieId = getArguments().getInt(ARG_PARAM_MOVIE_ID);
            mParamMovieTitle = getArguments().getString(ARG_PARAM_MOVIE_TITLE);
        }
        customToast = new CustomToast(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        detailInfoBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_movie_detail_info, container, false);
        detailInfoViewModel = new ViewModelProvider(this).get(MovieDetailInfoViewModel.class);
        return detailInfoBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);

        CommentRecyclerAdapter commentRecyclerAdapter = new CommentRecyclerAdapter(context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        detailInfoBinding.fragmentMovieDetailInfoCommentsRecycler.setLayoutManager(linearLayoutManager);
        detailInfoBinding.fragmentMovieDetailInfoCommentsRecycler.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        detailInfoBinding.fragmentMovieDetailInfoCommentsRecycler.setAdapter(commentRecyclerAdapter);

        MovieDetailInfoGalleryRecyclerAdapter galleryRecyclerAdapter = new MovieDetailInfoGalleryRecyclerAdapter(context);
        LinearLayoutManager galleryLinearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        detailInfoBinding.fragmentMovieDetailInfoGalleryRecycler.setLayoutManager(galleryLinearLayoutManager);
        detailInfoBinding.fragmentMovieDetailInfoGalleryRecycler.setAdapter(galleryRecyclerAdapter);


        detailInfoBinding.fragmentMovieDetailInfoThumbUpIv.setOnClickListener(thumbOnClickListener);
        detailInfoBinding.fragmentMovieDetailInfoThumbDownIv.setOnClickListener(thumbOnClickListener);
        detailInfoBinding.fragmentMovieDetailInfoWriteCommentsTv.setOnClickListener(this);
        detailInfoBinding.fragmentMovieDetailInfoViewAllCommentsCpbtn.setOnClickListener(this);

        if (NetworkState.getConnectivityStatus(context)) {
            Toast.makeText(context, "인터넷 연결됨", Toast.LENGTH_SHORT).show();
            detailInfoViewModel.requestMovieDetailInfo(context, mParamMovieId);
            detailInfoViewModel.requestMovieCommentList(context, mParamMovieId);
        } else {
            Toast.makeText(context, "인터넷 연결끊김 " + mParamMovieId, Toast.LENGTH_SHORT).show();
            detailInfoViewModel.getMovieDetailInfoRoomDB(context, mParamMovieTitle);
            detailInfoViewModel.getMovieCommentListRoomDB(context, mParamMovieId);
        }

        detailInfoViewModel.getDetailInfoLiveData().observe(getViewLifecycleOwner(), new Observer<MovieDetailInfoItem>() {
            @Override
            public void onChanged(MovieDetailInfoItem movieDetailInfoItem) {
                if (!movieDetailInfoItem.getResult().isEmpty()) {
                    Glide.with(context).load(movieDetailInfoItem.getResult().get(0).getImage()).into(detailInfoBinding.fragmentMovieDetailInfoPosterIv);
                    detailInfoBinding.fragmentMovieDetailInfoAgeIv.setImageResource(detailInfoViewModel.getGradeImg(movieDetailInfoItem.getResult().get(0).getGrade()));
                    detailInfoBinding.fragmentMovieDetailInfoTitleTv.setText(movieDetailInfoItem.getResult().get(0).getTitle());
                    detailInfoBinding.fragmentMovieDetailInfoDateTv.setText(movieDetailInfoItem.getResult().get(0).getDate() + " 개봉");
                    detailInfoBinding.fragmentMovieDetailInfoCategoryTv.setText(movieDetailInfoItem.getResult().get(0).getGenre() + " / " + movieDetailInfoItem.getResult().get(0).getDuration() + "분");
                    detailInfoBinding.fragmentMovieDetailInfoThumbUpTv.setText(String.valueOf(movieDetailInfoItem.getResult().get(0).getLike()));
                    detailInfoBinding.fragmentMovieDetailInfoThumbDownTv.setText(String.valueOf(movieDetailInfoItem.getResult().get(0).getDislike()));
                    detailInfoBinding.fragmentMovieDetailInfoReservationRateValueTv.setText(movieDetailInfoItem.getResult().get(0).getReservation_grade() + "위 " + movieDetailInfoItem.getResult().get(0).getReservation_rate());
                    detailInfoBinding.fragmentMovieDetailInfoUserRatingbar.setRating(movieDetailInfoItem.getResult().get(0).getUser_rating());
                    detailInfoBinding.fragmentMovieDetailInfoUserRateValueTv.setText(String.valueOf(movieDetailInfoItem.getResult().get(0).getUser_rating()));
                    detailInfoBinding.fragmentMovieDetailInfoAudienceValueTv.setText(movieDetailInfoItem.getResult().get(0).getAudience() + "명");

                    detailInfoBinding.fragmentMovieDetailInfoSynopsisTv.setText(movieDetailInfoItem.getResult().get(0).getSynopsis());
                    detailInfoBinding.fragmentMovieDetailInfoDirectorValueTv.setText(movieDetailInfoItem.getResult().get(0).getDirector());
                    detailInfoBinding.fragmentMovieDetailInfoActorValueTv.setText(movieDetailInfoItem.getResult().get(0).getActor());

                    Log.d(TAG, "onChanged: 불렸음");
                }
            }
        });

        detailInfoViewModel.getCommentListLiveData().observe(getViewLifecycleOwner(), new Observer<MovieCommentListItem>() {
            @Override
            public void onChanged(MovieCommentListItem movieCommentListItem) {
                commentRecyclerAdapter.setItem(movieCommentListItem);
                commentRecyclerAdapter.notifyDataSetChanged();
            }
        });

        detailInfoViewModel.getGalleryItemsLiveData().observe(getViewLifecycleOwner(), new Observer<List<MovieGalleryItem>>() {
            @Override
            public void onChanged(List<MovieGalleryItem> movieGalleryItems) {
                galleryRecyclerAdapter.setItem(movieGalleryItems);
                galleryRecyclerAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) context).getSupportActionBar().setTitle("영화 상세");
    }

    @Override
    public void onClick(View v) {
        if (v == detailInfoBinding.fragmentMovieDetailInfoWriteCommentsTv) {    //한줄평 작성하기
            Intent intent = new Intent(context, WriteCommentActivity.class);
            intent.putExtra(getString(R.string.movie_name), detailInfoViewModel.getDetailInfoLiveData().getValue().getResult().get(0).getTitle());
            intent.putExtra(getString(R.string.movie_id), mParamMovieId);
            startActivityForResult(intent, MOVIE_DETAIL_INFO_REQUEST);
            customToast.makeText("작성하기 버튼이 눌렸습니다", Toast.LENGTH_SHORT);

        } else if (v == detailInfoBinding.fragmentMovieDetailInfoViewAllCommentsCpbtn) {    //한줄평 모두보기
            Intent intent = new Intent(context, SeeAllCommentActivity.class);
            intent.putExtra(getString(R.string.movie_name), detailInfoViewModel.getDetailInfoLiveData().getValue().getResult().get(0).getTitle());
            intent.putExtra(getString(R.string.movie_id), mParamMovieId);
            startActivityForResult(intent, MOVIE_DETAIL_INFO_REQUEST);
            customToast.makeText("모두보기 버튼이 눌렸습니다", Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == MOVIE_DETAIL_INFO_REQUEST) {
            if (resultCode == SeeAllCommentActivity.SEE_ALL_COMMENT_RESULT_OK) {
                customToast.makeText("모두보기 화면에서 돌아왔습니다", Toast.LENGTH_SHORT);
            } else if (resultCode == WriteCommentActivity.WRITE_COMMENT_RESULT_OK) {
                customToast.makeText("작성하기 화면에서 돌아왔습니다", Toast.LENGTH_SHORT);
            }
        }
    }

    private View.OnClickListener thumbOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int likeCount = Integer.parseInt(detailInfoBinding.fragmentMovieDetailInfoThumbUpTv.getText().toString());
            int disLikeCount = Integer.parseInt(detailInfoBinding.fragmentMovieDetailInfoThumbDownTv.getText().toString());


            if (v == detailInfoBinding.fragmentMovieDetailInfoThumbUpIv) {
                Log.d(TAG, "onClick: 업");
                if (v.getTag() == null) {
                    ((ImageView) v).setImageResource(R.drawable.movie_detail_info_ic_thumb_up_selected);
                    v.setTag("selected");
                    detailInfoBinding.fragmentMovieDetailInfoThumbUpTv.setText(String.valueOf(likeCount + 1));

                } else {
                    ((ImageView) v).setImageResource(R.drawable.movie_detail_info_ic_thumb_up);
                    v.setTag(null);
                    detailInfoBinding.fragmentMovieDetailInfoThumbUpTv.setText(String.valueOf(likeCount - 1));
                }

                if (detailInfoBinding.fragmentMovieDetailInfoThumbDownIv.getTag() != null) {
                    detailInfoBinding.fragmentMovieDetailInfoThumbDownIv.setImageResource(R.drawable.movie_detail_info_ic_thumb_down);
                    detailInfoBinding.fragmentMovieDetailInfoThumbDownIv.setTag(null);
                    detailInfoBinding.fragmentMovieDetailInfoThumbDownTv.setText(String.valueOf(disLikeCount - 1));
                }


            } else {
                Log.d(TAG, "onClick: 다운");

                if (v.getTag() == null) {
                    ((ImageView) v).setImageResource(R.drawable.movie_detail_info_ic_thumb_down_selected);
                    v.setTag("selected");
                    detailInfoBinding.fragmentMovieDetailInfoThumbDownTv.setText(String.valueOf(disLikeCount + 1));
                } else {
                    ((ImageView) v).setImageResource(R.drawable.movie_detail_info_ic_thumb_down);
                    v.setTag(null);
                    detailInfoBinding.fragmentMovieDetailInfoThumbDownTv.setText(String.valueOf(disLikeCount - 1));
                }

                if (detailInfoBinding.fragmentMovieDetailInfoThumbUpIv.getTag() != null) {
                    detailInfoBinding.fragmentMovieDetailInfoThumbUpIv.setImageResource(R.drawable.movie_detail_info_ic_thumb_up);
                    detailInfoBinding.fragmentMovieDetailInfoThumbUpIv.setTag(null);
                    detailInfoBinding.fragmentMovieDetailInfoThumbUpTv.setText(String.valueOf(likeCount - 1));
                }


            }
        }
    };

}