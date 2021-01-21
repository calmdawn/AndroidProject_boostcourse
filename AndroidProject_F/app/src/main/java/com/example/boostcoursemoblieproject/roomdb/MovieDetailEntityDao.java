package com.example.boostcoursemoblieproject.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MovieDetailEntityDao {
    @Query("SELECT * FROM MovieDetailEntity")
    List<MovieDetailEntity> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieDetailEntity movieDetailEntity);

    @Update
    void update(MovieDetailEntity movieDetailEntity);

    @Delete
    void delete(MovieDetailEntity movieDetailEntity);

    @Query("DELETE FROM MovieDetailEntity")
    void clearALL();
}
