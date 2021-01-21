package com.example.boostcoursemoblieproject.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CommentListEntityDao {
    @Query("SELECT * FROM CommentListEntity")
    List<CommentListEntity> getAll();


    @Query("SELECT * FROM CommentListEntity ORDER BY id = 1 DESC LIMIT 3")
    List<CommentListEntity> getFirstLimitedMovieCommentCount();

    @Query("SELECT * FROM CommentListEntity ORDER BY id = 2 DESC LIMIT 3")
    List<CommentListEntity> getSecondLimitedMovieCommentCount();

    @Query("SELECT * FROM CommentListEntity ORDER BY id = 3 DESC LIMIT 3")
    List<CommentListEntity> getThirdLimitedMovieCommentCount();

    @Query("SELECT * FROM CommentListEntity ORDER BY id = 4 DESC LIMIT 3")
    List<CommentListEntity> getForthLimitedMovieCommentCount();

    @Query("SELECT * FROM CommentListEntity ORDER BY id = 5 DESC LIMIT 3")
    List<CommentListEntity> getFifthLimitedMovieCommentCount();


    @Query("SELECT * FROM CommentListEntity WHERE id = 1")
    List<CommentListEntity> getFirstSeeAllMovieCommentCount();

    @Query("SELECT * FROM CommentListEntity WHERE id = 2")
    List<CommentListEntity> getSecondSeeAllMovieCommentCount();

    @Query("SELECT * FROM CommentListEntity WHERE id = 3")
    List<CommentListEntity> getThirdSeeAllMovieCommentCount();

    @Query("SELECT * FROM CommentListEntity WHERE id = 4")
    List<CommentListEntity> getForthSeeAllMovieCommentCount();

    @Query("SELECT * FROM CommentListEntity WHERE id = 5")
    List<CommentListEntity> getFifthSeeAllMovieCommentCount();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CommentListEntity commentListEntity);

    @Update
    void update(CommentListEntity commentListEntity);


    @Query("DELETE FROM CommentListEntity WHERE id = 1")
    void deleteFirstCommentList();

    @Query("DELETE FROM CommentListEntity WHERE id = 2")
    void deleteSecondCommentList();

    @Query("DELETE FROM CommentListEntity WHERE id = 3")
    void deleteThirdCommentList();

    @Query("DELETE FROM CommentListEntity WHERE id = 4")
    void deleteFourthCommentList();

    @Query("DELETE FROM CommentListEntity WHERE id = 5")
    void deleteFifthCommentList();

    @Query("DELETE FROM CommentListEntity")
    void clearALL();
}
