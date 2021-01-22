package com.example.boostcoursemoblieproject.roomdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {MoviePosterEntity.class, MovieDetailEntity.class, CommentListEntity.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    public abstract MoviePosterEntityDao moviePosterEntityDao();

    public abstract MovieDetailEntityDao movieDetailEntityDao();

    public abstract CommentListEntityDao commentListEntityDao();

    public static final int ROOM_QUERY_GET_ALL = 100;
    public static final int ROOM_QUERY_INSERT = 200;
    public static final int ROOM_QUERY_DELETE = 300;

    private static AppDataBase INSTANCE;


    public static AppDataBase getAppDataBase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, AppDataBase.class, "movie-db").build();
        }
        return INSTANCE;

    }


}