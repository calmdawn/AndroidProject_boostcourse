package com.example.boostcoursemoblieproject.roomdb;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CommentListEntity {
    @PrimaryKey
    @NonNull
    private String time;
    private String writer;
    private String writer_image;
    private float rating;
    private String contents;
    private int id;

    public CommentListEntity(int id, String writer, String writer_image,
                             String time, float rating, String contents) {
        this.id = id;
        this.writer = writer;
        this.writer_image = writer_image;
        this.time = time;
        this.rating = rating;
        this.contents = contents;
    }

    @NonNull
    public String getTime() {
        return time;
    }

    public String getWriter() {
        return writer;
    }

    public String getWriter_image() {
        return writer_image;
    }

    public float getRating() {
        return rating;
    }

    public String getContents() {
        return contents;
    }

    public int getId() {
        return id;
    }
}
