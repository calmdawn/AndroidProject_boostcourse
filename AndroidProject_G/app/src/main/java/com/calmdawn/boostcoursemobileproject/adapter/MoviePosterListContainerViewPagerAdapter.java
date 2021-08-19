package com.calmdawn.boostcoursemobileproject.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.calmdawn.boostcoursemobileproject.model.MoviePosterListItem;
import com.calmdawn.boostcoursemobileproject.ui.fragment.MoviePosterFragment;
/**
 * 영화 포스터 목록 어댑터
 */
public class MoviePosterListContainerViewPagerAdapter extends FragmentStateAdapter {

    MoviePosterListItem item = new MoviePosterListItem();

    public MoviePosterListContainerViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }


    public void setItem(MoviePosterListItem item) {
        this.item = item;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        String imgUrl = item.getResult().get(position).getImage();
        String posterTitle = item.getResult().get(position).getTitle();
        String posterInfo = "예매율" + item.getResult().get(position).getReservation_rate() +
                "% | " + item.getResult().get(position).getGrade() + "세 관람가";
        int movieId = item.getResult().get(position).getId();
        return MoviePosterFragment.newInstance(imgUrl, posterTitle, posterInfo, movieId);
    }

    @Override
    public int getItemCount() {
        return item.getResult().size();
    }
}
