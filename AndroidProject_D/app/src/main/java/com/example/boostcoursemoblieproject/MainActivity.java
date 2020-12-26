package com.example.boostcoursemoblieproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private MoviePosterContainerFragment moviePosterContainerFragment;
    private MovieDetailInfoFragment movieDetailInfoFragment;

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private FragmentManager fragmentManager;


    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        //NavigationDrawer 생성
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_movie_list, R.id.nav_movie_api, R.id.nav_movie_reserve)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.activity_main_nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_movie_list:
                        if (moviePosterContainerFragment == null) {
                            moviePosterContainerFragment = new MoviePosterContainerFragment();
                            fragmentManager.beginTransaction().add(R.id.activity_main_nav_host_fragment, moviePosterContainerFragment).commit();
                        } else {
                            fragmentManager.beginTransaction().show(moviePosterContainerFragment).commit();
                            fragmentManager.beginTransaction().hide(movieDetailInfoFragment).commit();
                        }
                        toolbar.setTitle(getResources().getString(R.string.menu_movie_list));
                        break;
                    default:
                        break;
                }

                drawer.closeDrawer(navigationView);
                return false;
            }
        });


    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.activity_main_nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void changeFragment() {
        if (movieDetailInfoFragment == null) {
            movieDetailInfoFragment = new MovieDetailInfoFragment();
            fragmentManager.beginTransaction().add(R.id.activity_main_nav_host_fragment, movieDetailInfoFragment).commit();
        } else {
            fragmentManager.beginTransaction().show(movieDetailInfoFragment).commit();
            fragmentManager.beginTransaction().hide(moviePosterContainerFragment).commit();
        }

        toolbar.setTitle((getResources().getString(R.string.movie_detail_info_poster_title)));
    }


}











