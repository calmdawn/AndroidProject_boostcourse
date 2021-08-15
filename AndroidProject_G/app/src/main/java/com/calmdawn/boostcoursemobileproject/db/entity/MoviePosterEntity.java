package com.calmdawn.boostcoursemobileproject.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MoviePosterEntity {
    @PrimaryKey
    @NonNull
    private String title;
    private Integer id;
    private String title_eng;
    private String date;
    private Double user_rating;
    private Double audience_rating;
    private Double reviewer_rating;
    private Double reservation_rate;
    private Integer reservation_grade;
    private Integer grade;
    private String thumb;
    private String image;

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle_eng() {
        return title_eng;
    }

    public void setTitle_eng(String title_eng) {
        this.title_eng = title_eng;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getUser_rating() {
        return user_rating;
    }

    public void setUser_rating(Double user_rating) {
        this.user_rating = user_rating;
    }

    public Double getAudience_rating() {
        return audience_rating;
    }

    public void setAudience_rating(Double audience_rating) {
        this.audience_rating = audience_rating;
    }

    public Double getReviewer_rating() {
        return reviewer_rating;
    }

    public void setReviewer_rating(Double reviewer_rating) {
        this.reviewer_rating = reviewer_rating;
    }

    public Double getReservation_rate() {
        return reservation_rate;
    }

    public void setReservation_rate(Double reservation_rate) {
        this.reservation_rate = reservation_rate;
    }

    public Integer getReservation_grade() {
        return reservation_grade;
    }

    public void setReservation_grade(Integer reservation_grade) {
        this.reservation_grade = reservation_grade;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
