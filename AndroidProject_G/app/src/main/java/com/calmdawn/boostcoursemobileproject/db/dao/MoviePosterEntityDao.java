package com.calmdawn.boostcoursemobileproject.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.calmdawn.boostcoursemobileproject.db.entity.MoviePosterEntity;

import java.util.List;

@Dao
public interface MoviePosterEntityDao {
    @Query("SELECT * FROM MoviePosterEntity")
    List<MoviePosterEntity> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertALL(List<MoviePosterEntity> moviePosterEntity);

    @Query("DELETE FROM MoviePosterEntity")
    void deleteALL();

}
