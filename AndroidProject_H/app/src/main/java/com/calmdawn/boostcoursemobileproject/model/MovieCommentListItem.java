package com.calmdawn.boostcoursemobileproject.model;

import androidx.room.Entity;

import com.calmdawn.boostcoursemobileproject.db.entity.MovieCommentEntity;
import com.calmdawn.boostcoursemobileproject.db.entity.MoviePosterEntity;

import java.util.ArrayList;
import java.util.List;


public class MovieCommentListItem {
    private String message;
    private int code;
    private String resultType;
    private int totalCount;
    private List<MovieCommentEntity> result = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public String getResultType() {
        return resultType;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public List<MovieCommentEntity> getResult() {
        return result;
    }

    public void setResult(List<MovieCommentEntity> result) {
        this.result = result;
    }
}
