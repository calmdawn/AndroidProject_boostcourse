package com.example.boostcoursemoblieproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MoviePosterContainerFragment moviePosterContainerFragment = new MoviePosterContainerFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_fragment_container, moviePosterContainerFragment).commit();


    }

    public void changeFragment() {
        MovieDetailInfoFragment movieDetailInfoFragment = new MovieDetailInfoFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_fragment_container, movieDetailInfoFragment).commit();
    }


}











