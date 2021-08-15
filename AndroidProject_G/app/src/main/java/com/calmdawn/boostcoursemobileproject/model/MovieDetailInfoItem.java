package com.calmdawn.boostcoursemobileproject.model;

import androidx.room.Entity;

import com.calmdawn.boostcoursemobileproject.db.entity.MovieDetailInfoEntity;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailInfoItem {
    private String message;
    private Integer code;
    private String resultType;
    private List<MovieDetailInfoEntity> result = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    public String getResultType() {
        return resultType;
    }

    public List<MovieDetailInfoEntity> getResult() {
        return result;
    }

    public void setResult(List<MovieDetailInfoEntity> result) {
        this.result = result;
    }
}

