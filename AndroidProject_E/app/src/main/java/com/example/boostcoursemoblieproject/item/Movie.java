package com.example.boostcoursemoblieproject.item;

import java.util.ArrayList;

public class Movie {
    ArrayList<MovieDetailInfo> result = new ArrayList<MovieDetailInfo>();


    public class MovieDetailInfo {
        private String title;
        private int id;
        private String date;
        private float user_rating;
        private float audience_rating;
        private float reviewer_rating;
        private float reservation_rate;
        private int reservation_grade;
        private int grade;
        private String thumb;
        private String image;
        private String photos;
        private String videos;
        private String outlinks;
        private String genre;
        private int duration;
        private int audience;
        private String synopsis;
        private String director;
        private String actor;
        private int like;
        private int dislike;

        public String getTitle() {
            return title;
        }

        public int getId() {
            return id;
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

        public String getPhotos() {
            return photos;
        }

        public String getVideos() {
            return videos;
        }

        public String getOutlinks() {
            return outlinks;
        }

        public String getGenre() {
            return genre;
        }

        public int getDuration() {
            return duration;
        }

        public int getAudience() {
            return audience;
        }

        public String getSynopsis() {
            return synopsis;
        }

        public String getDirector() {
            return director;
        }

        public String getActor() {
            return actor;
        }

        public int getLike() {
            return like;
        }

        public int getDislike() {
            return dislike;
        }
    }

    public ArrayList<MovieDetailInfo> getResult() {
        return result;
    }

}
