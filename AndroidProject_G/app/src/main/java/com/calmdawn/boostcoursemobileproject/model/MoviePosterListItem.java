package com.calmdawn.boostcoursemobileproject.model;


import com.calmdawn.boostcoursemobileproject.db.entity.MoviePosterEntity;

import java.util.ArrayList;
import java.util.List;


public class MoviePosterListItem {
    private String message;
    private Integer code;
    private String resultType;

    private List<MoviePosterEntity> result = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }


    public String getResultType() {
        return resultType;
    }

    public void setResult(List<MoviePosterEntity> result) {
        this.result = result;
    }

    public List<MoviePosterEntity> getResult() {
        return result;
    }


}
