package com.calmdawn.boostcoursemobileproject.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.calmdawn.boostcoursemobileproject.db.entity.MovieCommentEntity;

import java.util.List;

@Dao
public interface MovieCommentEntityDao {

    @Query("SELECT * FROM MovieCommentEntity WHERE movieId = :movieId ORDER BY timestamp DESC LIMIT :limitCount")
    List<MovieCommentEntity> getCommentList(int movieId, int limitCount);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertALL(List<MovieCommentEntity> MovieCommentEntity);

    @Query("DELETE FROM MovieCommentEntity WHERE movieId = :movieId")
    void deleteALL(int movieId);

}
