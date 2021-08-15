package com.calmdawn.boostcoursemobileproject.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.calmdawn.boostcoursemobileproject.R;
import com.calmdawn.boostcoursemobileproject.databinding.FragmentMoviePosterBinding;
import com.calmdawn.boostcoursemobileproject.ui.activity.MainActivity;


public class MoviePosterFragment extends BaseFragment {


    private static final String ARG_PARAM_POSTER_IMG_URL = "param1";
    private static final String ARG_PARAM_POSTER_TITLE = "param2";
    private static final String ARG_PARAM_POSTER_INFO = "param3";
    private static final String ARG_PARAM_POSTER_MOVIE_ID = "param4";

    private String mParamPosterImgUrl;
    private String mParamPosterTitle;
    private String mParamPosterInfo;
    private int mParamPosterMovieId;

    Context context;

    FragmentMoviePosterBinding posterBinding;

    public MoviePosterFragment() {

    }

    public static MoviePosterFragment newInstance(String param1, String param2, String param3, int param4) {
        MoviePosterFragment fragment = new MoviePosterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_POSTER_IMG_URL, param1);
        args.putString(ARG_PARAM_POSTER_TITLE, param2);
        args.putString(ARG_PARAM_POSTER_INFO, param3);
        args.putInt(ARG_PARAM_POSTER_MOVIE_ID, param4);
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
            mParamPosterImgUrl = getArguments().getString(ARG_PARAM_POSTER_IMG_URL);
            mParamPosterTitle = getArguments().getString(ARG_PARAM_POSTER_TITLE);
            mParamPosterInfo = getArguments().getString(ARG_PARAM_POSTER_INFO);
            mParamPosterMovieId = getArguments().getInt(ARG_PARAM_POSTER_MOVIE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        posterBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_movie_poster, container, false);
        return posterBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);

        Glide.with(context).load(mParamPosterImgUrl).into(posterBinding.fragmentMoviePosterIv);
        posterBinding.fragmentMoviePosterTitleTv.setText(mParamPosterTitle);
        posterBinding.fragmentMoviePosterInfoTv.setText(mParamPosterInfo);

        posterBinding.fragmentMoviePosterDetailViewCpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   //영화 상세보기
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.nav_host_fragment, MovieDetailInfoFragment.newInstance(mParamPosterMovieId, mParamPosterTitle)).commitAllowingStateLoss();
            }
        });
    }
}