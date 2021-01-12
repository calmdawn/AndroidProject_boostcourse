package com.example.boostcoursemoblieproject.item;

import java.util.ArrayList;

public class CommentList {

    ArrayList<CommentInfo> result = new ArrayList<CommentInfo>();

    public ArrayList<CommentInfo> getResult() {
        return result;
    }

    public class CommentInfo {
        private int id;
        private String writer;
        private int movieId;
        private String writer_image;
        private String time;
        private int timestamp;
        private float rating;
        private String contents;
        private int recommend;

        public int getId() {
            return id;
        }

        public String getWriter() {
            return writer;
        }

        public int getMovieId() {
            return movieId;
        }

        public String getWriter_image() {
            return writer_image;
        }

        public String getTime() {
            return time;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public float getRating() {
            return rating;
        }

        public String getContents() {
            return contents;
        }

        public int getRecommend() {
            return recommend;
        }
    }


}
