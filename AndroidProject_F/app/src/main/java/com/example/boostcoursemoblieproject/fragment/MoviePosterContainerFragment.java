package com.example.boostcoursemoblieproject.fragment;

import android.content.Context;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.boostcoursemoblieproject.R;
import com.example.boostcoursemoblieproject.adapter.MoviePosterPagerAdapter;
import com.example.boostcoursemoblieproject.roomdb.AppDataBase;
import com.example.boostcoursemoblieproject.item.MovieList;
import com.example.boostcoursemoblieproject.roomdb.MoviePosterEntity;
import com.example.boostcoursemoblieproject.roomdb.MoviePosterEntityDao;
import com.example.boostcoursemoblieproject.item.ResponseMovieInfo;
import com.example.boostcoursemoblieproject.network.AppHelper;
import com.example.boostcoursemoblieproject.network.NetworkState;
import com.google.gson.Gson;

import java.util.List;


public class MoviePosterContainerFragment extends Fragment {

    public static final int NUM_OF_FRAGMENTS = 5;
    private static final int NETWORK_REQUEST_COUNT = 3;


    MovieList movieList;

    private ViewPager viewPager;
    private MoviePosterPagerAdapter moviePosterPagerAdapter;
    private MoviePosterFragment[] moviePosterFragments;


    //    DB생성
    private AppDataBase db;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //        싱글톤으로 DB가져오기
        db = AppDataBase.getAppDataBase(context);
        NetworkState.getConnectivityStatus(context);
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

        //인터넷 연결된상태, 아닌상태

        if (NetworkState.status == NetworkState.TYPE_CONNECT) {
            //서버에서 영화정보 가져오기
            requestMovieList(NETWORK_REQUEST_COUNT);
            Toast.makeText(getActivity(), "인터넷 연결됨", Toast.LENGTH_SHORT).show();
        } else if (NetworkState.status == NetworkState.TYPE_NOT_CONNECTED) {

            try {
                List<MoviePosterEntity> entities = new DaoAsyncTask(db.moviePosterEntityDao(), AppDataBase.ROOM_QUERY_GET_ALL).execute().get();
                setNotConnectedNetMoviePosterFragment(entities);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return rootView;
    }


    private void setNotConnectedNetMoviePosterFragment(List<MoviePosterEntity> entities) {
        for (int i = 0; i < moviePosterFragments.length; i++) {
            String posterName = entities.get(i).getTitle();
            float posterRate = entities.get(i).getReservation_rate();
            int posterGrade = entities.get(i).getGrade();
            String drawableResId = entities.get(i).getImage();

            String posterInfo = "예매율 " + posterRate + "% | " + posterGrade + "세 관람가";
            int movieId = i + 1;

            moviePosterFragments[i] = MoviePosterFragment.newInstance(drawableResId, posterName, posterInfo, movieId);
            moviePosterPagerAdapter.addItem(moviePosterFragments[i]);
        }
        viewPager.setAdapter(moviePosterPagerAdapter);
    }


    private void setConnectedNetMoviePosterFragment() {
        for (int i = 0; i < moviePosterFragments.length; i++) {
            String posterName = movieList.getResult().get(i).getTitle();
            float posterRate = movieList.getResult().get(i).getReservation_rate();
            int posterGrade = movieList.getResult().get(i).getGrade();
            String drawableResId = movieList.getResult().get(i).getImage();

            String posterInfo = "예매율 " + posterRate + "% | " + posterGrade + "세 관람가";
            int movieId = i + 1;


            //  DB에 넣어주기
            new DaoAsyncTask(db.moviePosterEntityDao(), AppDataBase.ROOM_QUERY_INSERT).execute(new MoviePosterEntity(
                    posterName, posterRate,
                    posterGrade, drawableResId));


            Toast.makeText(getActivity(), "DB 인서트 성공이여", Toast.LENGTH_SHORT).show();


            moviePosterFragments[i] = MoviePosterFragment.newInstance(drawableResId, posterName, posterInfo, movieId);
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
                        setConnectedNetMoviePosterFragment();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "응답에러" + error, Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(), "응답에러영화목록카운트" + requestCount, Toast.LENGTH_SHORT).show();
                        requestMovieList((requestCount - 1));
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


    private static class DaoAsyncTask extends AsyncTask<MoviePosterEntity, Void, List<MoviePosterEntity>> {

        private MoviePosterEntityDao mDao;
        private int command;

        public DaoAsyncTask(MoviePosterEntityDao mDao, int command) {
            this.mDao = mDao;
            this.command = command;
        }

        @Override
        protected List<MoviePosterEntity> doInBackground(MoviePosterEntity... moviePosterEntities) {

            if (command == AppDataBase.ROOM_QUERY_GET_ALL) {
                return mDao.getAll();

            } else if (command == AppDataBase.ROOM_QUERY_INSERT) {
                //영화의 개수만큼까지만 DB에 저장.
                if (mDao.getAll().size() >= NUM_OF_FRAGMENTS) {
                    mDao.update(moviePosterEntities[0]);
                } else {
                    mDao.insert(moviePosterEntities[0]);
                }

            }

            return null;
        }
    }


}