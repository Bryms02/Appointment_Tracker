package com.example.mobileappdevpa.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mobileappdevpa.Entity.CourseEntity;
import com.example.mobileappdevpa.Entity.TermEntity;
import com.example.mobileappdevpa.UI.CourseActivity;

import java.util.List;

@Dao
public interface CourseDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CourseEntity course);

    @Update
    void update(CourseEntity course);

    @Delete
    void delete(CourseEntity courseEntity);

    @Query("DELETE FROM course_table")
    void deleteAllCourses();

    @Query("SELECT * FROM course_table")
    List<CourseEntity> getAllCourses();

}
