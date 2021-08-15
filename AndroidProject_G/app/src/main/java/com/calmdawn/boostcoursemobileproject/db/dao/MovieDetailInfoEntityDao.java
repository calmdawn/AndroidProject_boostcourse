package com.calmdawn.boostcoursemobileproject.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.calmdawn.boostcoursemobileproject.db.entity.MovieDetailInfoEntity;

import java.util.List;

@Dao
public interface MovieDetailInfoEntityDao {
    @Query("SELECT * FROM MovieDetailInfoEntity WHERE title = :title")
    MovieDetailInfoEntity get(String title);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieDetailInfoEntity movieDetailInfoEntity);

    @Query("DELETE FROM MovieDetailInfoEntity WHERE title = :title")
    void delete(String title);

}
