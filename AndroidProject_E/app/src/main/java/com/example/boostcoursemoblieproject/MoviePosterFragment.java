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


public class MoviePosterFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_POSTER_IMG = "param1";
    private static final String ARG_PARAM_POSTER_NAME = "param2";
    private static final String ARG_PARAM_POSTER_INFO = "param3";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;
    private String mParam3;

    public ChangeFragmentCallBack changeFragmentCallBack;

    public MoviePosterFragment() {

    }


    public static MoviePosterFragment newInstance(int drawableResId, String largePosterName, String largePosterInfo) {
        MoviePosterFragment fragment1 = new MoviePosterFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_PARAM_POSTER_IMG, drawableResId);
        args.putString(ARG_PARAM_POSTER_NAME, largePosterName);
        args.putString(ARG_PARAM_POSTER_INFO, largePosterInfo);
        fragment1.setArguments(args);
        return fragment1;
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
            mParam1 = getArguments().getInt(ARG_PARAM_POSTER_IMG);
            mParam2 = getArguments().getString(ARG_PARAM_POSTER_NAME);
            mParam3 = getArguments().getString(ARG_PARAM_POSTER_INFO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_movie_poster, container, false);

        ImageView posterIv = rootView.findViewById(R.id.fragment_movie_poster_iv);
        posterIv.setImageResource(mParam1);

        TextView posterNameTv = rootView.findViewById(R.id.fragment_movie_poster_name_tv);
        posterNameTv.setText(mParam2);

        TextView posterInfoTv = rootView.findViewById(R.id.fragment_movie_poster_info_tv);
        posterInfoTv.setText(mParam3);

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