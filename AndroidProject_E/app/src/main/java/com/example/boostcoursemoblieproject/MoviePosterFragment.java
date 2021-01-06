package com.example.boostcoursemoblieproject;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class MoviePosterFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_POSTER_IMG = "param1";
    private static final String ARG_PARAM_POSTER_NAME = "param2";
    private static final String ARG_PARAM_POSTER_INFO = "param3";

    // TODO: Rename and change types of parameters
    private String mImgParam;
    private String mPosterNameParam;
    private String mPosterInfoParam;

    public ChangeFragmentCallBack changeFragmentCallBack;

    public MoviePosterFragment() {

    }


    public static MoviePosterFragment newInstance(String drawableResId, String largePosterName, String largePosterInfo) {
        MoviePosterFragment moviePosterFragment = new MoviePosterFragment();

        Bundle args = new Bundle();
        args.putString(ARG_PARAM_POSTER_IMG, drawableResId);
        args.putString(ARG_PARAM_POSTER_NAME, largePosterName);
        args.putString(ARG_PARAM_POSTER_INFO, largePosterInfo);
        moviePosterFragment.setArguments(args);
        return moviePosterFragment;
    }

    public interface ChangeFragmentCallBack{
        public void fragmentChange();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof ChangeFragmentCallBack){
            changeFragmentCallBack = (ChangeFragmentCallBack)context;
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mImgParam = getArguments().getString(ARG_PARAM_POSTER_IMG);
            mPosterNameParam = getArguments().getString(ARG_PARAM_POSTER_NAME);
            mPosterInfoParam = getArguments().getString(ARG_PARAM_POSTER_INFO);
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
                changeFragmentCallBack.fragmentChange();
            }
        });

        return rootView;
    }


}