package com.example.boostcoursemoblieproject.roomdb;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MovieDetailEntity {
    @PrimaryKey
    @NonNull
    private String title;
    private String date;
    private float user_rating;
    private float audience_rating;
    private float reservation_rate;
    private int reservation_grade;
    private String image;
    private String genre;
    private int duration;
    private int audience;
    private String synopsis;
    private String director;
    private String actor;
    private int like;
    private int dislike;
    private int movieId;

    public MovieDetailEntity(int movieId, String title, String date, float user_rating, float audience_rating,
                             float reservation_rate, int reservation_grade, String image, String genre, int duration,
                             int audience, String synopsis, String director, String actor, int like, int dislike) {
        this.movieId = movieId;
        this.title = title;
        this.date = date;
        this.user_rating = user_rating;
        this.audience_rating = audience_rating;
        this.reservation_rate = reservation_rate;
        this.reservation_grade = reservation_grade;
        this.image = image;
        this.genre = genre;
        this.duration = duration;
        this.audience = audience;
        this.synopsis = synopsis;
        this.director = director;
        this.actor = actor;
        this.like = like;
        this.dislike = dislike;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
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

    public float getReservation_rate() {
        return reservation_rate;
    }

    public int getReservation_grade() {
        return reservation_grade;
    }

    public String getImage() {
        return image;
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
