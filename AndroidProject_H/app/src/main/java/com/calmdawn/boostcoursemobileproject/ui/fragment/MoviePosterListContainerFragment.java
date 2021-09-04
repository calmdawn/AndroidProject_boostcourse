package com.calmdawn.boostcoursemobileproject.ui.fragment;


import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.calmdawn.boostcoursemobileproject.R;
import com.calmdawn.boostcoursemobileproject.adapter.MoviePosterListContainerViewPagerAdapter;
import com.calmdawn.boostcoursemobileproject.databinding.FragmentMoviePosterListContainerBinding;
import com.calmdawn.boostcoursemobileproject.model.MoviePosterListItem;
import com.calmdawn.boostcoursemobileproject.network.NetworkState;
import com.calmdawn.boostcoursemobileproject.ui.activity.MainActivity;
import com.calmdawn.boostcoursemobileproject.viewmodel.MoviePosterListContainerViewModel;

public class MoviePosterListContainerFragment extends BaseFragment {

    private FragmentMoviePosterListContainerBinding binding;
    private MoviePosterListContainerViewModel moviePosterListContainerViewModel;
    private Context context;
    MoviePosterListContainerViewPagerAdapter adapter;
    int movieListType = 1;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_poster_list_container, container, false);
        moviePosterListContainerViewModel = new ViewModelProvider(this).get(MoviePosterListContainerViewModel.class);

        View rootView = binding.getRoot();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);

        adapter = new MoviePosterListContainerViewPagerAdapter(getChildFragmentManager(), getLifecycle());
        binding.fragmentMoviePosterListContainerViewpager.setSaveEnabled(false);    //설정 안할시 백스택 사용시 오류발생
        binding.fragmentMoviePosterListContainerViewpager.setAdapter(adapter);
        binding.fragmentMoviePosterListContainerViewpager.setOffscreenPageLimit(3); //포스터 미리보기 사용을 위해 3개 먼저 불러오기
        binding.fragmentMoviePosterListContainerViewpager.setPageTransformer(new ViewPager2.PageTransformer() { //viewpager2 사용 + 영화포스터 크기 10% 만큼 미리보기
            @Override
            public void transformPage(@NonNull View page, float position) {
                page.setTranslationX(-getPosterPreviewWidth() * position);
            }
        });


        moviePosterListContainerViewModel.getMoviePosterListLiveData().observe(getViewLifecycleOwner(), new Observer<MoviePosterListItem>() {   //영화 포스터 목록 세팅
            @Override
            public void onChanged(MoviePosterListItem moviePosterList) {
                adapter.setItem(moviePosterList);
                adapter.notifyItemRangeChanged(0, moviePosterList.getResult().size());
            }
        });


        if (NetworkState.getConnectivityStatus(context)) {  //서버에서 데이터 가져옴
            Toast.makeText(context, "인터넷 연결됨", Toast.LENGTH_SHORT).show();
            moviePosterListContainerViewModel.requestMovieList(context, "?type=" + movieListType);
        } else {    //로컬DB에서 데이터 가져옴
            Toast.makeText(context, "인터넷 연결끊김", Toast.LENGTH_SHORT).show();
            moviePosterListContainerViewModel.getMovieListRoomDB(context);
        }

    }


    public void changeMovieListType(int type) { // 클릭된 정렬메뉴에 따라 서버에서 값을 가져옴 1.예매율 2.큐레이션 3.상영예정
        movieListType = type;
        moviePosterListContainerViewModel.requestMovieList(context, "?type=" + movieListType);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) context).getSupportActionBar().setTitle("영화 목록");    //onResume 이전에 설정시 액션바가 inflate 되기 전이기 때문에 에러 발생

    }

    private int getPosterPreviewWidth() {  //포스터 왼쪽 오른쪽 미리보기 너비
        Display display = getActivity().getWindowManager().getDefaultDisplay();  // in Activity
        Point size = new Point();
        display.getRealSize(size);
        int width = size.x;
        return (int) (width * 0.22);   //포스터 크기의 10%만큼을 반환  <- (width * 0.3)/2 + (width*0.7)/10
    }


}