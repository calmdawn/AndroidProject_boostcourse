package com.calmdawn.boostcoursemobileproject.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MovieDetailInfoEntity {
    @PrimaryKey
    @NonNull
    private String title;
    private Integer id;
    private String date;
    private float user_rating;
    private Double audience_rating;
    private Double reviewer_rating;
    private Double reservation_rate;
    private Integer reservation_grade;
    private Integer grade;
    private String thumb;
    private String image;
    private String photos;
    private String videos;
    private String outlinks;
    private String genre;
    private Integer duration;
    private Integer audience;
    private String synopsis;
    private String director;
    private String actor;
    private Integer like;
    private Integer dislike;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getUser_rating() {
        return user_rating;
    }

    public void setUser_rating(float user_rating) {
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

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getVideos() {
        return videos;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }

    public String getOutlinks() {
        return outlinks;
    }

    public void setOutlinks(String outlinks) {
        this.outlinks = outlinks;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getAudience() {
        return audience;
    }

    public void setAudience(Integer audience) {
        this.audience = audience;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public Integer getDislike() {
        return dislike;
    }

    public void setDislike(Integer dislike) {
        this.dislike = dislike;
    }
}
