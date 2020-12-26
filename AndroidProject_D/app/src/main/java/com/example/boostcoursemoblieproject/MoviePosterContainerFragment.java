package com.example.boostcoursemoblieproject;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MoviePosterContainerFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_movie_poster_container, container, false);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        int padding = (size.x) / 9;
        //int padding = (int) (40 * getResources().getDisplayMetrics().density);   dp를 px로 변환

        //영화 목록 + 뷰페이저

        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.fragment_movie_poster_container_viewpager);

        MoviePosterPagerAdapter moviePosterPagerAdapter = new MoviePosterPagerAdapter(getChildFragmentManager());

        MoviePosterFragment1 moviePosterFragment1 = new MoviePosterFragment1();
        MoviePosterFragment2 moviePosterFragment2 = new MoviePosterFragment2();
        MoviePosterFragment3 moviePosterFragment3 = new MoviePosterFragment3();
        MoviePosterFragment4 moviePosterFragment4 = new MoviePosterFragment4();
        MoviePosterFragment5 moviePosterFragment5 = new MoviePosterFragment5();

        moviePosterPagerAdapter.addItem(moviePosterFragment1);
        moviePosterPagerAdapter.addItem(moviePosterFragment2);
        moviePosterPagerAdapter.addItem(moviePosterFragment3);
        moviePosterPagerAdapter.addItem(moviePosterFragment4);
        moviePosterPagerAdapter.addItem(moviePosterFragment5);
        viewPager.setAdapter(moviePosterPagerAdapter);

        //좌,우 영화포스터 미리보기
        viewPager.setClipToPadding(false);
        viewPager.setPadding(padding, 0, padding, 0);


        return rootView;
    }
}