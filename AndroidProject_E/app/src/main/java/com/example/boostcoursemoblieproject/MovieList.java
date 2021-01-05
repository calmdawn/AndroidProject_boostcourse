package com.example.boostcoursemoblieproject;

import java.util.ArrayList;

public class MovieList {
    public ArrayList<MovieInfo> result = new ArrayList<MovieInfo>();

    public class MovieInfo {

        public int id;
        public String title;
        public String title_eng;
        public String date;
        public float user_rating;
        public float audience_rating;
        public float reviewer_rating;
        public float reservation_rate;
        public int reservation_grade;
        public int grade;
        public String thumb;
        public String image;

    }

}




