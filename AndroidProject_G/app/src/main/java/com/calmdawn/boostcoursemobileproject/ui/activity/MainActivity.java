package com.calmdawn.boostcoursemobileproject.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.calmdawn.boostcoursemobileproject.R;
import com.calmdawn.boostcoursemobileproject.ui.fragment.MoviePosterListContainerFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends BaseActivity {

    private AppBarConfiguration mAppBarConfiguration;
    Toolbar toolbar;

    private FragmentManager fragmentManager;
    private MoviePosterListContainerFragment moviePosterListContainerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();
        moviePosterListContainerFragment = new MoviePosterListContainerFragment();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_movie_list).setOpenableLayout(drawer).build();


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

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


    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}