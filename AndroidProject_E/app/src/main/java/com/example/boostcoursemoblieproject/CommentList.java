package com.example.boostcoursemoblieproject;

import java.util.ArrayList;

public class CommentList {
    ArrayList<CommentInfo> result = new ArrayList<CommentInfo>();

    public class CommentInfo {
        public int id;
        public String writer;
        public int movieId;
        public String writer_image;
        public String time;
        public int timestamp;
        public float rating;
        public String contents;
        public int recommend;
    }
}
