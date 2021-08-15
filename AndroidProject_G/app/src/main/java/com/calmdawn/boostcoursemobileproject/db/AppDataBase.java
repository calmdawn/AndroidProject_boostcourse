package com.calmdawn.boostcoursemobileproject.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.calmdawn.boostcoursemobileproject.db.dao.MovieCommentEntityDao;
import com.calmdawn.boostcoursemobileproject.db.dao.MovieDetailInfoEntityDao;
import com.calmdawn.boostcoursemobileproject.db.dao.MoviePosterEntityDao;
import com.calmdawn.boostcoursemobileproject.db.entity.MovieCommentEntity;
import com.calmdawn.boostcoursemobileproject.db.entity.MovieDetailInfoEntity;
import com.calmdawn.boostcoursemobileproject.db.entity.MoviePosterEntity;


@Database(entities = {MoviePosterEntity.class, MovieDetailInfoEntity.class, MovieCommentEntity.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {


    public abstract MoviePosterEntityDao moviePosterEntityDao();

    public abstract MovieDetailInfoEntityDao movieDetailInfoEntityDao();

    public abstract MovieCommentEntityDao movieCommentEntityDao();


    private static AppDataBase INSTANCE;

    public static AppDataBase getAppDataBase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, AppDataBase.class, "movie_db").build();
        }
        return INSTANCE;

    }


}