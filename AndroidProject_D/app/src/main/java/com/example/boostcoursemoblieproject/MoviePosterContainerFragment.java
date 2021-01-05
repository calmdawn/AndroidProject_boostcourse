package com.example.boostcoursemoblieproject;

import android.content.res.TypedArray;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MoviePosterContainerFragment extends Fragment {

    public static final int NUM_OF_FRAGMENTS = 5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_movie_poster_container, container, false);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        int padding = (size.x) / 9;
        //int padding = (int) (40 * getResources().getDisplayMetrics().density);   dp를 px로 변환


        //  영화 목록화면 포스터 이미지, 이름, 예매율
        TypedArray largePosterImgs = getResources().obtainTypedArray(R.array.large_poster_imgs);
        String[] largePosterNames = getResources().getStringArray(R.array.large_poster_names);
        String[] largePosterInfos = getResources().getStringArray(R.array.large_poster_infos);

        //  영화 목록 + 뷰페이저

        ViewPager viewPager = rootView.findViewById(R.id.fragment_movie_poster_container_viewpager);

        MoviePosterPagerAdapter moviePosterPagerAdapter = new MoviePosterPagerAdapter(getChildFragmentManager());

        MoviePosterFragment[] moviePosterFragments = new MoviePosterFragment[NUM_OF_FRAGMENTS];

        for (int i = 0; i < moviePosterFragments.length; i++) {
            moviePosterFragments[i] = MoviePosterFragment.newInstance(largePosterImgs.getResourceId(i, -1), largePosterNames[i], largePosterInfos[i]);
            moviePosterPagerAdapter.addItem(moviePosterFragments[i]);
        }

        viewPager.setAdapter(moviePosterPagerAdapter);


        //좌,우 영화포스터 미리보기
        viewPager.setClipToPadding(false);
        viewPager.setPadding(padding, 0, padding, 0);


        return rootView;
    }


}