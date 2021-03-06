package com.example.boostcoursemoblieproject.fragment;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.boostcoursemoblieproject.network.AppHelper;
import com.example.boostcoursemoblieproject.item.MovieList;
import com.example.boostcoursemoblieproject.R;
import com.example.boostcoursemoblieproject.item.ResponseMovieInfo;
import com.example.boostcoursemoblieproject.adapter.MoviePosterPagerAdapter;
import com.google.gson.Gson;


public class MoviePosterContainerFragment extends Fragment {

    public static final int NUM_OF_FRAGMENTS = 5;
    private static final int NETWORK_REQUEST_COUNT = 3;

    MovieList movieList;

    private ViewPager viewPager;
    private MoviePosterPagerAdapter moviePosterPagerAdapter;
    private MoviePosterFragment[] moviePosterFragments;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //서버에서 영화정보 가져오기
        requestMovieList(NETWORK_REQUEST_COUNT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_movie_poster_container, container, false);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        int padding = (size.x) / 9;

        //  영화 목록 + 뷰페이저
        viewPager = rootView.findViewById(R.id.fragment_movie_poster_container_viewpager);
        moviePosterPagerAdapter = new MoviePosterPagerAdapter(getChildFragmentManager());
        moviePosterFragments = new MoviePosterFragment[NUM_OF_FRAGMENTS];


        //좌,우 영화포스터 미리보기
        viewPager.setClipToPadding(false);
        viewPager.setPadding(padding, 0, padding, 0);


        return rootView;
    }

    private void setMoviePosterFragment() {
        for (int i = 0; i < moviePosterFragments.length; i++) {
            String drawableResId = movieList.getResult().get(i).getImage();
            String PosterName = movieList.getResult().get(i).getTitle();
            String posterInfo = "예매율 " + movieList.getResult().get(i).getReservation_rate() + "% | " + movieList.getResult().get(i).getGrade() + "세 관람가";
            int movieId = i + 1;
            moviePosterFragments[i] = MoviePosterFragment.newInstance(drawableResId, PosterName, posterInfo, movieId);
            moviePosterPagerAdapter.addItem(moviePosterFragments[i]);
        }
        viewPager.setAdapter(moviePosterPagerAdapter);
    }

    public void requestMovieList(int requestCount) {
        String url = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/readMovieList";
        url += "?" + "type=1";

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        processResponseConvertGson(response);
                        setMoviePosterFragment();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "응답에러" + error, Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(), "응답에러영화목록카운트" + requestCount, Toast.LENGTH_SHORT).show();
                        requestMovieList((requestCount-1));
                    }
                }
        );

        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);

    }

    public void processResponseConvertGson(String response) {
        Gson gson = new Gson();
        ResponseMovieInfo responseMovieInfo = gson.fromJson(response, ResponseMovieInfo.class);

        if (responseMovieInfo.getCode() == 200) {
            movieList = gson.fromJson(response, MovieList.class);
        }
    }


}