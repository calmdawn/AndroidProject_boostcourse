package com.example.boostcoursemoblieproject.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.boostcoursemoblieproject.R;


public class MoviePosterFragment extends Fragment {


    private static final String ARG_PARAM_POSTER_IMG = "paramImg";
    private static final String ARG_PARAM_POSTER_NAME = "paramName";
    private static final String ARG_PARAM_POSTER_INFO = "paramInfo";
    private static final String ARG_PARAM_POSTER_MOVIE_ID = "paramMovieId";


    private String mImgParam;
    private String mPosterNameParam;
    private String mPosterInfoParam;
    private int mMovieIdParam;

    public ChangeFragmentCallBack changeFragmentCallBack;

    public MoviePosterFragment() {

    }

    //생성자가 아닌 newInstance로 값을 전달
    public static MoviePosterFragment newInstance(String drawableResId, String largePosterName, String largePosterInfo, int movieId) {
        MoviePosterFragment moviePosterFragment = new MoviePosterFragment();

        Bundle args = new Bundle();
        args.putString(ARG_PARAM_POSTER_IMG, drawableResId);
        args.putString(ARG_PARAM_POSTER_NAME, largePosterName);
        args.putString(ARG_PARAM_POSTER_INFO, largePosterInfo);
        args.putInt(ARG_PARAM_POSTER_MOVIE_ID, movieId);
        moviePosterFragment.setArguments(args);
        return moviePosterFragment;
    }

    public interface ChangeFragmentCallBack {
        public void fragmentChange(int mMovieIdParam);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof ChangeFragmentCallBack) {
            changeFragmentCallBack = (ChangeFragmentCallBack) context;
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mImgParam = getArguments().getString(ARG_PARAM_POSTER_IMG);
            mPosterNameParam = getArguments().getString(ARG_PARAM_POSTER_NAME);
            mPosterInfoParam = getArguments().getString(ARG_PARAM_POSTER_INFO);
            mMovieIdParam = getArguments().getInt(ARG_PARAM_POSTER_MOVIE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_movie_poster, container, false);

        ImageView posterIv = rootView.findViewById(R.id.fragment_movie_poster_iv);
        Glide.with(getActivity()).load(mImgParam).into(posterIv);

        TextView posterNameTv = rootView.findViewById(R.id.fragment_movie_poster_name_tv);
        posterNameTv.setText(mPosterNameParam);

        TextView posterInfoTv = rootView.findViewById(R.id.fragment_movie_poster_info_tv);
        posterInfoTv.setText(mPosterInfoParam);

        Button testBtn = (Button) rootView.findViewById(R.id.fragment_movie_poster1_btn);
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragmentCallBack.fragmentChange(mMovieIdParam);
            }
        });

        return rootView;
    }


}