package com.example.boostcoursemoblieproject.item;

import java.util.ArrayList;

public class MovieList {
    private ArrayList<MovieInfo> result = new ArrayList<MovieInfo>();

    public ArrayList<MovieInfo> getResult() {
        return result;
    }

    public class MovieInfo {

        private int id;
        private String title;
        private String title_eng;
        private String date;
        private float user_rating;
        private float audience_rating;
        private float reviewer_rating;
        private float reservation_rate;
        private int reservation_grade;
        private int grade;
        private String thumb;
        private String image;

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getTitle_eng() {
            return title_eng;
        }

        public String getDate() {
            return date;
        }

        public float getUser_rating() {
            return user_rating;
        }

        public float getAudience_rating() {
            return audience_rating;
        }

        public float getReviewer_rating() {
            return reviewer_rating;
        }

        public float getReservation_rate() {
            return reservation_rate;
        }

        public int getReservation_grade() {
            return reservation_grade;
        }

        public int getGrade() {
            return grade;
        }

        public String getThumb() {
            return thumb;
        }

        public String getImage() {
            return image;
        }
    }

}




