package com.example.boostcoursemoblieproject.item;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class MoviePosterEntity {
    @PrimaryKey(autoGenerate = true)
    private int uid;
    private String title;
    private float reservation_rate;
    private int grade;
    private String image;

    public MoviePosterEntity(String title, float reservation_rate, int grade, String image) {

        this.title = title;
        this.reservation_rate = reservation_rate;
        this.grade = grade;
        this.image = image;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public float getReservation_rate() {
        return reservation_rate;
    }

    public int getGrade() {
        return grade;
    }

    public String getImage() {
        return image;
    }
}

