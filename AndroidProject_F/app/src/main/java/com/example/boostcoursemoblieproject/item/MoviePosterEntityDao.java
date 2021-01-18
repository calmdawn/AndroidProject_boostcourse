package com.example.boostcoursemoblieproject.item;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MoviePosterEntityDao {
    @Query("SELECT * FROM MoviePosterEntity")
    List<MoviePosterEntity> getAll();

    @Insert
    void insert(MoviePosterEntity moviePosterEntity);

    @Update
    void update(MoviePosterEntity moviePosterEntity);

    @Delete
    void delete(MoviePosterEntity moviePosterEntity);

    @Query("DELETE FROM MoviePosterEntity")
    void clearALL();

}
