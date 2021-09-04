package com.calmdawn.boostcoursemobileproject.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.calmdawn.boostcoursemobileproject.R;
import com.calmdawn.boostcoursemobileproject.common.CustomToast;
import com.calmdawn.boostcoursemobileproject.ui.fragment.MoviePosterListContainerFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends BaseActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private FragmentManager fragmentManager;
    private MoviePosterListContainerFragment moviePosterListContainerFragment;

    private ImageView sortMenuIv;
    private LinearLayout sortMenuViewLy;
    private Animation translateDown;
    private Animation translateUp;

    private CustomToast customToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUi();
        setupUi();

        translateDown = AnimationUtils.loadAnimation(this, R.anim.anim_translate_down);
        translateUp = AnimationUtils.loadAnimation(this, R.anim.anim_translate_up);

        fragmentManager = getSupportFragmentManager();
        moviePosterListContainerFragment = new MoviePosterListContainerFragment();

        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, moviePosterListContainerFragment).commitAllowingStateLoss();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_movie_list:
                        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);    // 영화상세보기 뒤로가기 이후 영화목록 중복호출로 인한 충돌방지
                        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, moviePosterListContainerFragment).commitAllowingStateLoss();
                        break;
                }

                drawer.closeDrawer(navigationView);
                return false;
            }
        });

        sortMenuIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sortMenuViewLy.getVisibility() == View.GONE) {
                    sortMenuViewLy.setVisibility(View.VISIBLE);
                    sortMenuViewLy.startAnimation(translateDown);
                } else if (sortMenuViewLy.getVisibility() == View.VISIBLE) {
                    sortMenuViewLy.setVisibility(View.GONE);
                    sortMenuViewLy.startAnimation(translateUp);
                }
                customToast.makeText("정렬 아이콘 클릭됨", Toast.LENGTH_SHORT);

            }
        });
    }

    private View.OnClickListener sortMenuClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == sortMenuViewLy.getChildAt(0)) {    //예매율순 클릭시
                sortMenuIv.setImageResource(R.drawable.order11);
                moviePosterListContainerFragment.changeMovieListType(1);
                customToast.makeText("정렬메뉴1 선택됨", Toast.LENGTH_SHORT);

            } else if (v == sortMenuViewLy.getChildAt(2)) {    //큐레이션 클릭시
                sortMenuIv.setImageResource(R.drawable.order22);
                moviePosterListContainerFragment.changeMovieListType(2);
                customToast.makeText("정렬메뉴2 선택됨", Toast.LENGTH_SHORT);

            } else if (v == sortMenuViewLy.getChildAt(4)) {    //상영예정 클릭시
                sortMenuIv.setImageResource(R.drawable.order33);
                moviePosterListContainerFragment.changeMovieListType(3);
                customToast.makeText("정렬메뉴3 선택됨", Toast.LENGTH_SHORT);
            }

            sortMenuViewLy.setVisibility(View.GONE);
            sortMenuViewLy.startAnimation(translateUp);

        }
    };


    private void initUi() {
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        sortMenuIv = findViewById(R.id.activity_main_sort_menu_iv);
        sortMenuViewLy = findViewById(R.id.activity_main_sort_menu_ly);
        customToast = new CustomToast(getApplicationContext());
    }

    private void setupUi() {
        setSupportActionBar(toolbar);
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_movie_list).setOpenableLayout(drawer).build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        sortMenuViewLy.getChildAt(0).setOnClickListener(sortMenuClickListener);
        sortMenuViewLy.getChildAt(2).setOnClickListener(sortMenuClickListener);
        sortMenuViewLy.getChildAt(4).setOnClickListener(sortMenuClickListener);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}