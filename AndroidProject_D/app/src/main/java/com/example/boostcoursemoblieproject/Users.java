package com.example.boostcoursemoblieproject;

import java.io.Serializable;

public class Users implements Serializable {

    String name;
    String comment;

    int imgRes;
    float starScore;


    public Users(String name, String comment, int imgRes, float starScore) {
        this.name = name;
        this.comment = comment;
        this.imgRes = imgRes;
        this.starScore = starScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public float getStarScore() {
        return starScore;
    }

    public void setStarScore(float starScore) {
        this.starScore = starScore;
    }
}
