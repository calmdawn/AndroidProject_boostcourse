package com.example.boostcoursemoblieproject.item;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {MoviePosterEntity.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract MoviePosterEntityDao moviePosterEntityDao();
}