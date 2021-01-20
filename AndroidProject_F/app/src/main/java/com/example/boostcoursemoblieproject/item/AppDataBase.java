package com.example.boostcoursemoblieproject.item;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {MoviePosterEntity.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract MoviePosterEntityDao moviePosterEntityDao();


    private static AppDataBase INSTANCE;


    public static AppDataBase getAppDataBase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, AppDataBase.class, "poster-db").build();
        }
        return INSTANCE;

    }


}