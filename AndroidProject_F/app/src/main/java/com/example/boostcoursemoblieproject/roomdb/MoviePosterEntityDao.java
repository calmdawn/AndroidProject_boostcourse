package com.example.boostcoursemoblieproject.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.boostcoursemoblieproject.roomdb.MoviePosterEntity;

import java.util.List;

@Dao
public interface MoviePosterEntityDao {
    @Query("SELECT * FROM MoviePosterEntity")
    List<MoviePosterEntity> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MoviePosterEntity moviePosterEntity);

    @Update
    void update(MoviePosterEntity moviePosterEntity);

    @Delete
    void delete(MoviePosterEntity moviePosterEntity);

    @Query("DELETE FROM MoviePosterEntity")
    void clearALL();

}
